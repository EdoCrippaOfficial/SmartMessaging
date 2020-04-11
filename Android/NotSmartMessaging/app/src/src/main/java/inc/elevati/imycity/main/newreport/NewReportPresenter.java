package inc.elevati.smartmessaging.main.newmessage;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

import inc.elevati.smartmessaging.firebase.FirebaseContracts;
import inc.elevati.smartmessaging.firebase.FirestoreHelper;
import inc.elevati.smartmessaging.firebase.StorageHelper;
import inc.elevati.smartmessaging.main.MainContracts;
import inc.elevati.smartmessaging.utils.EspressoIdlingResource;
import inc.elevati.smartmessaging.utils.MvpContracts;
import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;
import inc.elevati.smartmessaging.utils.message;

/** Presenter associated to {@link NewmessageFragment} */
public class NewmessagePresenter implements MainContracts.NewmessagePresenter {

    /** The view associated to this presenter */
    private MainContracts.NewmessageView view;

    /** This flag is set when a task had to be executed when no view was attached to this presenter */
    private boolean pendingTask;

    /** Used only if pendingTask flag is set, if not null indicates that onSendTaskComplete has to be executed */
    private MainContracts.SendTaskResult result;

    /**{@inheritDoc}*/
    @Override
    public void attachView(MvpContracts.MvpView view) {
        this.view = (MainContracts.NewmessageView) view;

        // If there were pending tasks, execute them now
        if (pendingTask) {

            // If resultCode is not 0, then onSendTaskComplete has to be executed
            if (result != null) {
                onSendTaskComplete(result);
                result = null;
            }
            pendingTask = false;
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void detachView() {
        this.view = null;
    }

    /**{@inheritDoc}*/
    @Override
    public void onSendButtonClicked(String title, String description, Context appContext, Uri imageUri, LatLng position) {
        if (imageUri == null) {
            view.notifyInvalidImage();
        } else if (title.equals("")) {
            view.notifyInvalidTitle();
        } else if (description.equals("")) {
            view.notifyInvalidDescription();
        } else {
            EspressoIdlingResource.increment();
            view.showProgressDialog();
            String uuid = UUID.randomUUID().toString();
            String userId = FirebaseAuthHelper.getUserId();
            String userName = FirebaseAuthHelper.getUserName();
            message message = new message(uuid, title, description, System.currentTimeMillis(), userId, userName, position);

            // Store image (normal and thumbnail) in Firebase Storage
            FirebaseContracts.StorageWriter writer = new StorageHelper(this);
            writer.sendImage(message, appContext, imageUri);
        }
    }

    /**{@inheritDoc}*/
    @Override
    public void sendmessageData(message message) {
        FirebaseContracts.DatabaseWriter writer = new FirestoreHelper(this);
        writer.sendmessage(message);
    }

    /**{@inheritDoc}*/
    @Override
    public void onSendTaskComplete(MainContracts.SendTaskResult result) {

        // If view is detached, set the pendingTask flag
        if (view == null) {
            pendingTask = true;
            this.result = result;
            return;
        }
        EspressoIdlingResource.decrement();
        view.dismissProgressDialog();
        view.notifySendTaskCompleted(result);
    }
}
