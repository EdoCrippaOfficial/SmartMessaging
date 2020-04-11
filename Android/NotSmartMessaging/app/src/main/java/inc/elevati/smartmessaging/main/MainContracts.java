package inc.elevati.smartmessaging.main;

import java.util.List;

import inc.elevati.smartmessaging.login.LoginActivity;
import inc.elevati.smartmessaging.utils.Message;
import inc.elevati.smartmessaging.utils.MvpContracts;

/** Generic interface that defines contracts for main package */
public interface MainContracts {

    enum DeleteMessageTaskResult {
        RESULT_OK,
        RESULT_FAILED
    }

    /** Possible message sorting criteria */
    int SORT_DATE_NEWEST = 1;
    int SORT_DATE_OLDEST = 2;
    int SORT_PRIORITY_HIGH = 3;
    int SORT_PRIORITY_LOW = 4;

    /** Implemented by the class that handles general main view (activity) */
    interface MainView extends MvpContracts.MvpView {

        /**
         * Starts {@link LoginActivity}
         */
        void startLoginActivity();

        /**
         * Updates the message list shown
         * @param messages the message list to show
         */
        void updateMessages(List<Message> messages);

        /**
         * Hides the refreshing view
         */
        void resetRefreshing();

        /**
         Shows a full-screen dialog with a message information
         * @param message the message to show
         */
        void showMessageDialog(Message message);

        MainPresenter getPresenter();
    }

    /** Implemented by the class that handles general main presenter */
    interface MainPresenter extends MvpContracts.MvpPresenter {

        /**
         * Notifies this presenter that a menu item was clicked
         * @param itemId the id of the clicked item
         */
        void onMenuItemClicked(int itemId);

        /**
         * @return the current user email
         */
        String getCurrentUserEmail();

        /**
         * @return the current user display name
         */
        String getCurrentUserName();

        /**
         * Attach a {@link MessageDialogView} to this presenter
         * @param view the {@link MessageDialogView} to attach
         */
        void attachMessageDialogView(MessageDialogView view);

        /**
         * Detaches the {@link MessageDialogView} from the presenter, if one was attached
         */
        void detachMessageDialogView();

        /**
         * Starts the message retrieving task from database
         */
        void loadMessages();

        /**
         * Notifies this presenter that a message retrieving task has completed
         * @param messages the query result
         */
        void onLoadMessagesTaskComplete(List<Message> messages);

        /**
         * Notifies this presenter that a message list update task has completed
         */
        void onUpdateTaskComplete();

        /**
         * Notifies this presenter that a message in the list was clicked
         * @param message the clicked message
         */
        void onMessageClicked(Message message);

        /**
         * Notifies this presenter that the delete button of a message
         * @param message the message to delete
         */
        void onDeleteMessageButtonClicked(Message message);

        /**
         * Called when a message delete task is completed
         * @param result the task result
         */
        void onDeleteMessageTaskComplete(DeleteMessageTaskResult result);
    }

    interface MessageDialogView extends MvpContracts.MvpView {

        /**
         * Dismisses the message dialog
         */
        void dismissDialog();

        /**
         * Notifies this view that an error occurred during message delete task
         */
        void notifyDeleteMessageError();
    }
}
