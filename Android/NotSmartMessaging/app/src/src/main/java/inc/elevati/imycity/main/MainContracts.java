package inc.elevati.smartmessaging.main;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import inc.elevati.smartmessaging.login.LoginActivity;
import inc.elevati.smartmessaging.main.allmessages.AllmessagesFragment;
import inc.elevati.smartmessaging.main.completedmessages.CompletedmessagesFragment;
import inc.elevati.smartmessaging.main.mymessages.MymessagesFragment;
import inc.elevati.smartmessaging.main.starredmessages.StarredmessagesFragment;
import inc.elevati.smartmessaging.utils.MvpContracts;
import inc.elevati.smartmessaging.utils.message;

/** Generic interface that defines contracts for login package */
public interface MainContracts {

    /** Possible results for message send task */
    enum SendTaskResult {

        /** Send task completed correctly */
        RESULT_SEND_OK,

        /** Send task failed due to image compressing error */
        RESULT_SEND_ERROR_IMAGE,

        /** Send task failed due to database */
        RESULT_SEND_ERROR_DB
    }

    /** Possible results for delete message task */
    enum DeletemessageTaskResult {
        RESULT_OK,
        RESULT_FAILED
    }

    /** Menu pages list */
    int PAGE_NEW = 0;
    int PAGE_ALL = 1;
    int PAGE_MY = 2;
    int PAGE_STARRED = 3;
    int PAGE_COMPLETED = 4;

    /** Possible message sorting criteria */
    int message_SORT_DATE_NEWEST = 1;
    int message_SORT_DATE_OLDEST = 2;
    int message_SORT_STARS_MORE = 3;
    int message_SORT_STARS_LESS = 4;

    /** Implemented by the class that handles general main view (activity) */
    interface MainView extends MvpContracts.MvpView {

        /**
         * Tells view to scroll to the selected page
         * @param page the page to scroll to
         */
        void scrollToPage(int page);

        /**
         * Starts {@link LoginActivity}
         */
        void startLoginActivity();

        /**
         * Sets as checked the provided menu item
         * @param itemId the item id
         */
        void setCheckedMenuItem(int itemId);
    }

    /** Implemented by the class that handles general main presenter */
    interface MainPresenter extends MvpContracts.MvpPresenter {

        /**
         * Notifies this presenter that a menu item was clicked
         * @param itemId the id of the clicked item
         */
        void onMenuItemClicked(int itemId);

        /**
         * Notifies this presenter that a scroll action has occurred
         * @param page the page to scroll to
         */
        void onPageScrolled(int page);

        /**
         * @return the current user email
         */
        String getCurrentUserEmail();

        /**
         * @return the current user display name
         */
        String getCurrentUserName();

        /**
         * @return the child {@link NewmessagePresenter} instance
         */
        NewmessagePresenter getNewmessagePresenter();

        /**
         * @return the child {@link messageListPresenter} instance associated to {@link AllmessagesFragment}
         */
        messageListPresenter getAllmessagesPresenter();

        /**
         * @return the child {@link messageListPresenter} instance associated to {@link CompletedmessagesFragment}
         */
        messageListPresenter getCompletedmessagesPresenter();

        /**
         * @return the child {@link messageListPresenter} instance associated to {@link MymessagesFragment}
         */
        messageListPresenter getMymessagesPresenter();

        /**
         * @return the child {@link messageListPresenter} instance associated to {@link StarredmessagesFragment}
         */
        messageListPresenter getStarredmessagesPresenter();
    }

    /** Implemented by the class that handles new message presenter */
    interface NewmessagePresenter extends MvpContracts.MvpPresenter {

        /**
         * Notifies this presenter that send button was clicked
         * @param title the title provided
         * @param description the description provided
         * @param appContext the appContext provided
         * @param imageUri the imageUri provided
         * @param position the position provided
         */
        void onSendButtonClicked(String title, String description, Context appContext, Uri imageUri, LatLng position);

        /**
         * Continues the message sending task after the image was sent to storage,
         * it sends the remaining message data to database
         * @param message the message to send
         */
        void sendmessageData(message message);

        /**
         * Notifies this presenter that a send message task has completed
         * @param result the task result
         */
        void onSendTaskComplete(SendTaskResult result);
    }

    /** Implemented by classes that handle presenter associated to views with a message list */
    interface messageListPresenter extends MvpContracts.MvpPresenter {

        /**
         * Attach a {@link messageDialogView} to this presenter
         * @param view the {@link messageDialogView} to attach
         */
        void attachmessageDialogView(messageDialogView view);

        /**
         * Detaches the {@link messageDialogView} from the presenter, if one was attached
         */
        void detachmessageDialogView();

        /**
         * Starts the message retrieving task from database
         */
        void loadmessages();

        /**
         * Notifies this presenter that a message retrieving task has completed
         * @param result the query result
         */
        void onLoadmessagesTaskComplete(QuerySnapshot result);

        /**
         * Notifies this presenter that a message list update task has completed
         */
        void onUpdateTaskComplete();

        /**
         * Notifies this presenter that a message in the list was clicked
         * @param message the clicked message
         */
        void onmessageClicked(message message);

        /**
         * Notifies this presenter that the star button of a message was clicked
         * @param message the message associated with the star button clicked
         */
        void onStarsButtonClicked(message message);

        /**
         * Notifies this presenter that a star task has completed
         */
        void onStarTaskComplete();

        /**
         * Notifies this presenter that the delete button of a message
         * @param message the message to delete
         */
        void onDeletemessageButtonClicked(message message);

        /**
         * Called when a message delete task is completed
         * @param result the task result
         */
        void onDeletemessageTaskComplete(DeletemessageTaskResult result);
    }

    /** Implemented by the class that handles new message view (fragment) */
    interface NewmessageView extends MvpContracts.MvpView {

        /**
         * Shows a non-cancelable progress dialog
         */
        void showProgressDialog();

        /**
         * Notifies this view that the image provided is not valid
         */
        void notifyInvalidImage();

        /**
         * Notifies this view that the title provided is not valid
         */
        void notifyInvalidTitle();

        /**
         * Notifies this view that the description provided is not valid
         */
        void notifyInvalidDescription();

        /**
         * Dismisses the progress dialog if shown
         */
        void dismissProgressDialog();

        /**
         * Notifies this view that a message send task has completed
         * @param result the task result
         */
        void notifySendTaskCompleted(SendTaskResult result);
    }

    /** Implemented by classes that handle messages list (fragments) */
    interface messageListView extends MvpContracts.MvpView {

        /**
         * Updates the message list shown
         * @param messages the message list to show
         */
        void updatemessages(List<message> messages);

        /**
         * Hides the refreshing view
         */
        void resetRefreshing();

        /**
         Shows a full-screen dialog with a message information
         * @param message the message to show
         */
        void showmessageDialog(message message);
    }

    interface messageDialogView extends MvpContracts.MvpView {

        /**
         * Shows a non-cancelable progress dialog
         */
        void showProgressDialog();

        /**
         * Dismisses the progress dialog if shown
         */
        void dismissProgressDialog();

        /**
         * Dismisses the message dialog
         */
        void dismissDialog();

        /**
         * Notifies this view that an error occurred during message delete task
         */
        void notifyDeletemessageError();
    }
}
