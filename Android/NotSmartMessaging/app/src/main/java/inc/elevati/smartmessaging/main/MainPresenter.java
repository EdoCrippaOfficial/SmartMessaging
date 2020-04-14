package inc.elevati.smartmessaging.main;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.firebase.FirebaseFirestoreHelper;
import inc.elevati.smartmessaging.utils.FilterOptions;
import inc.elevati.smartmessaging.utils.Message;
import inc.elevati.smartmessaging.utils.MvpContracts;
import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;

import static inc.elevati.smartmessaging.main.MainContracts.DeleteMessageTaskResult.RESULT_OK;

/** Presenter associated to {@link MainActivity} */
public class MainPresenter implements MainContracts.MainPresenter {

    /** The view associated to this presenter */
    private MainContracts.MainView view;

    /** The messageDialogView that may be associated to this presenter */
    private MainContracts.MessageDialogView messageDialogView;

    /** This flag is set when a task had to be executed when no view was attached to this presenter */
    private boolean pendingTask;

    /** This flag is set when {@link inc.elevati.smartmessaging.main.MainPresenter#onUpdateTaskComplete()}
     * is called while View is detached */
    private boolean update;

    private QuerySnapshot results;

    private FilterOptions filterOptions;

    /** {@inheritDoc} */
    @Override
    public void attachView(MvpContracts.MvpView view) {
        this.view = (MainContracts.MainView) view;
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
    public void onMenuItemClicked(int itemId) {
        switch (itemId) {
            case android.R.id.home:
                view.openDrawer();
                break;
            case R.id.bn_logout:
                FirebaseAuthHelper.signOut();
                view.startLoginActivity();
                break;
            case R.id.bn_filter:
                view.showFilterDialog();
                break;
            case R.id.bn_newest:
                view.sortMessages(MainContracts.SORT_DATE_NEWEST);
                break;
            case R.id.bn_oldest:
                view.sortMessages(MainContracts.SORT_DATE_OLDEST);
                break;
            case R.id.bn_priority_high:
                view.sortMessages(MainContracts.SORT_PRIORITY_HIGH);
                break;
            case R.id.bn_priority_low:
                view.sortMessages(MainContracts.SORT_PRIORITY_LOW);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getCurrentUserEmail() {
        return FirebaseAuthHelper.getUserEmail();
    }

    /** {@inheritDoc} */
    @Override
    public String getCurrentUserName() {
        return FirebaseAuthHelper.getUserName();
    }

    /** {@inheritDoc} */
    @Override
    public void attachMessageDialogView(MainContracts.MessageDialogView view) {
        this.messageDialogView = view;
    }

    /** {@inheritDoc} */
    @Override
    public void detachMessageDialogView() {
        this.messageDialogView = null;
    }

    @Override
    public void loadMessages() {
        new FirebaseFirestoreHelper(this).fetchMessages(getCurrentUserName());
    }

    @Override
    public void onLoadMessagesTaskComplete(QuerySnapshot data) {
        // If view is detached, set the pendingTask flag
        if (view == null) {
            pendingTask = true;
            this.results = data;
            return;
        }

        List<Message> messages = new ArrayList<>();
        for (QueryDocumentSnapshot snap: data) {
            String id = snap.getId();
            String title = snap.getString("title");
            String body = snap.getString("body");
            String image = snap.getString("image");
            int priority = (int) (long) snap.getLong("priority");
            List<String> receivers = (List<String>) snap.get("receivers");
            long timestamp = snap.getLong("timestamp");
            boolean CC = snap.getBoolean("cc");
            messages.add(new Message(id, title, body, image, priority, receivers, timestamp, CC));
        }

        List<Message> filtered = new ArrayList<>();
        for (Message message: messages) {
            int priority = message.getPriority();
            if (priority >= filterOptions.getMinPriority() && priority <= filterOptions.getMaxPriority()) {
                boolean CC = message.isCC();
                if (filterOptions.isShowGroupMessages() || filterOptions.isShowSingleMessages())
                    if (CC == filterOptions.isShowGroupMessages() || !CC == filterOptions.isShowSingleMessages())
                        filtered.add(message);
            }
        }
        view.updateMessages(filtered);
    }

    @Override
    public void onUpdateTaskComplete() {
        // If view is detached, set the pendingTask flag
        if (view == null) {
            pendingTask = true;
            this.update = true;
            return;
        }
        view.resetRefreshing();
    }

    @Override
    public void onMessageClicked(Message message) {
        view.showMessageDialog(message);
    }

    @Override
    public void onDeleteMessageButtonClicked(Message message) {
        new FirebaseFirestoreHelper(this).deleteMessage(message, getCurrentUserName());
    }

    @Override
    public void onDeleteMessageTaskComplete(MainContracts.DeleteMessageTaskResult result) {
        if (messageDialogView == null) return;

        if (result.equals(RESULT_OK)) {
            messageDialogView.dismissDialog();
            loadMessages();
        } else {
            messageDialogView.notifyDeleteMessageError();
        }
    }

    @Override
    public void setFilterOptions(int minPriority, int maxPriority, boolean showSingleMessages, boolean showGroupMessages) {
        filterOptions = new FilterOptions(minPriority, maxPriority, showSingleMessages, showGroupMessages);
    }

    @Override
    public FilterOptions getFilterOptions() {
        return filterOptions;
    }
}
