package inc.elevati.smartmessaging.main.allmessages;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;
import inc.elevati.smartmessaging.firebase.FirebaseContracts;
import inc.elevati.smartmessaging.firebase.FirestoreHelper;
import inc.elevati.smartmessaging.main.MainContracts;
import inc.elevati.smartmessaging.utils.EspressoIdlingResource;
import inc.elevati.smartmessaging.utils.MvpContracts;
import inc.elevati.smartmessaging.utils.message;

import static inc.elevati.smartmessaging.main.MainContracts.DeletemessageTaskResult.RESULT_OK;
import static inc.elevati.smartmessaging.utils.message.Status.STATUS_ACCEPTED;
import static inc.elevati.smartmessaging.utils.message.Status.STATUS_COMPLETED;
import static inc.elevati.smartmessaging.utils.message.Status.STATUS_REFUSED;
import static inc.elevati.smartmessaging.utils.message.Status.STATUS_WAITING;

/** Presenter associated to {@link AllmessagesFragment} */
public class AllmessagesPresenter implements MainContracts.messageListPresenter {

    /** The view associated to this presenter */
    private MainContracts.messageListView view;

    /** The messageDialogView that may be associated to this presenter */
    private MainContracts.messageDialogView messageDialogView;

    /** This flag is set when a task had to be executed when no view was attached to this presenter */
    private boolean pendingTask;

    /** Used only if pendingTask flag is set, if not null indicates that
     * {@link AllmessagesPresenter#onLoadMessagesTaskComplete(QuerySnapshot)} has to be executed */
    private QuerySnapshot results;

    /** This flag is set when {@link AllmessagesPresenter#onUpdateTaskComplete()}
     * is called while View is detached */
    private boolean update;

    /** {@inheritDoc} */
    @Override
    public void attachView(MvpContracts.MvpView view) {
        this.view = (MainContracts.messageListView) view;

        // If there were pending tasks, execute them now
        if (pendingTask) {

            // If results is not null, then onLoadmessagesTaskComplete has to be executed
            if (results != null) {
                onLoadMessagesTaskComplete(results);
                results = null;
            }

            // If update is true, then onUpdateTaskComplete has to be executed
            if (update) {
                onUpdateTaskComplete();
                update = false;
            }
            pendingTask = false;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        this.view = null;
    }

    /** {@inheritDoc} */
    @Override
    public void attachmessageDialogView(MainContracts.messageDialogView view) {
        this.messageDialogView = view;
    }

    /** {@inheritDoc} */
    @Override
    public void detachmessageDialogView() {
        this.messageDialogView = null;
    }

    /** {@inheritDoc} */
    @Override
    public void loadmessages() {
        EspressoIdlingResource.increment();
        FirebaseContracts.DatabaseReader reader = new FirestoreHelper(this);
        reader.readAllmessages();
    }

    /** {@inheritDoc} */
    @Override
    public void onLoadmessagesTaskComplete(QuerySnapshot results) {

        // If view is detached, set the pendingTask flag
        if (view == null) {
            pendingTask = true;
            this.results = results;
            return;
        }
        List<message> messages = new ArrayList<>();
        if (results != null) {
            for (QueryDocumentSnapshot snap: results) {
                String id = snap.getString("id");
                String title = snap.getString("title");
                String description = snap.getString("description");
                long timestamp = snap.getLong("timestamp");
                String userId = snap.getString("user_id");
                String userName = snap.getString("user_name");
                long nStars = snap.getLong("n_stars");
                String reply = snap.getString("reply");
                message.Status status = STATUS_WAITING;
                switch (snap.getString("status")) {
                    case "1":
                        status = STATUS_ACCEPTED;
                        break;
                    case "2":
                        status = STATUS_REFUSED;
                        break;
                    case "3":
                        status = STATUS_COMPLETED;
                        break;
                    case "4":
                        status = STATUS_WAITING;
                        break;
                }
                GeoPoint position = (GeoPoint) snap.get("position");

                ArrayList<String> usersStarred = (ArrayList<String>) snap.get("users_starred");
                boolean starred = usersStarred.contains(FirebaseAuthHelper.getUserId());

                message message = new message(id, userId, userName, title, description, reply,
                        timestamp, (int) nStars, position, status, starred);

                messages.add(message);
            }
        }
        view.updatemessages(messages);
    }

    /** {@inheritDoc} */
    public void onUpdateTaskComplete() {

        // If view is detached, set the pendingTask flag
        if (view == null) {
            pendingTask = true;
            this.update = true;
            return;
        }
        EspressoIdlingResource.decrement();
        view.resetRefreshing();
    }

    /** {@inheritDoc} */
    @Override
    public void onmessageClicked(message message) {
        view.showmessageDialog(message);
    }

    /** {@inheritDoc} */
    @Override
    public void onStarsButtonClicked(message message) {
        FirebaseContracts.DatabaseWriter writer = new FirestoreHelper(this);
        if (message.isStarred()) {
            message.setStarred(false);
            message.decreaseStars();
            writer.unstarmessage(message, FirebaseAuthHelper.getUserId());
        } else {
            message.setStarred(true);
            message.increaseStars();
            writer.starmessage(message, FirebaseAuthHelper.getUserId());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onStarTaskComplete() {
        loadMessages();
    }

    /** {@inheritDoc} */
    @Override
    public void onDeletemessageButtonClicked(message message) {
        EspressoIdlingResource.increment();
        messageDialogView.showProgressDialog();
        FirebaseContracts.DatabaseWriter writer = new FirestoreHelper(this);
        writer.deletemessage(message.getId());
    }

    /** {@inheritDoc} */
    @Override
    public void onDeletemessageTaskComplete(MainContracts.DeletemessageTaskResult result) {
        if (messageDialogView == null) return;

        messageDialogView.dismissProgressDialog();
        if (result.equals(RESULT_OK)) {
            messageDialogView.dismissDialog();
            EspressoIdlingResource.decrement();
            loadMessages();
        } else {
            messageDialogView.notifyDeletemessageError();
        }
    }
}
