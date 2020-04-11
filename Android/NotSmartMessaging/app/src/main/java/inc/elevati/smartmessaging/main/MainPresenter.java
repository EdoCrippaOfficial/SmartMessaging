package inc.elevati.smartmessaging.main;

import java.util.List;

import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.firebase.DummyMessagesFetcher;
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

    private List<Message> results;

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
        if (itemId == R.id.bn_logout) {
            FirebaseAuthHelper.signOut();
            view.startLoginActivity();
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
        new DummyMessagesFetcher(this).fetchMessages();
    }

    @Override
    public void onLoadMessagesTaskComplete(List<Message> messages) {
        view.updateMessages(messages);
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
        new DummyMessagesFetcher(this).deleteMessage(message);
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
}
