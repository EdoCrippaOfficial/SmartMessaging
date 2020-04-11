package inc.elevati.smartmessaging.firebase;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import inc.elevati.smartmessaging.main.MainContracts;
import inc.elevati.smartmessaging.utils.message;

import static inc.elevati.smartmessaging.main.MainContracts.DeletemessageTaskResult.RESULT_FAILED;
import static inc.elevati.smartmessaging.main.MainContracts.DeletemessageTaskResult.RESULT_OK;
import static inc.elevati.smartmessaging.main.MainContracts.SendTaskResult.RESULT_SEND_ERROR_DB;
import static inc.elevati.smartmessaging.main.MainContracts.SendTaskResult.RESULT_SEND_OK;

/** This class is an helper that handles with Firebase Firestore related tasks */
public class FirestoreHelper implements FirebaseContracts.DatabaseReader, FirebaseContracts.DatabaseWriter {

    /** The listener that gets notified about message handling tasks */
    private MainContracts.messageListPresenter messageListListener;

    /** The listener that gets notified about message creating tasks */
    private MainContracts.NewmessagePresenter newmessageListener;

    /**
     * Constructor used when message handling tasks are needed
     * @param messageListListener the presenter that is requesting services
     */
    public FirestoreHelper(MainContracts.messageListPresenter messageListListener) {
        this.messageListListener = messageListListener;
    }

    /**
     * Constructor used when message creating tasks are needed
     * @param newmessageListener the presenter that is requesting services
     */
    public FirestoreHelper(MainContracts.NewmessagePresenter newmessageListener) {
        this.newmessageListener = newmessageListener;
    }

    /** {@inheritDoc} */
    @Override
    public void deletemessage(String messageId) {
        FirebaseFirestore dbRef = FirebaseFirestore.getInstance();
        dbRef.collection("messages").document(messageId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        messageListListener.onDeletemessageTaskComplete(RESULT_OK);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        messageListListener.onDeletemessageTaskComplete(RESULT_FAILED);
                    }
                });
    }

    /** {@inheritDoc} */
    @Override
    public void sendmessage(final message message) {
        FirebaseFirestore dbRef = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("id", message.getId());
        map.put("title", message.getTitle());
        map.put("description", message.getDescription());
        map.put("timestamp", message.getTimestamp());
        map.put("user_id", message.getUserId());
        map.put("user_name", message.getUserName());
        map.put("operator_id", "");
        map.put("n_stars", 0);
        map.put("reply", "");
        map.put("status", message.Status.STATUS_WAITING.value);
        map.put("position", message.getPosition());
        map.put("users_starred", new ArrayList<String>());
        map.put("category", "0");
        dbRef.collection("messages").document(message.getId())
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        newmessageListener.onSendTaskComplete(RESULT_SEND_OK);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Delete the image uploaded previously on storage
                        StorageHelper.deleteImage(message);
                        newmessageListener.onSendTaskComplete(RESULT_SEND_ERROR_DB);
                    }
                });
    }

    /** {@inheritDoc} */
    @Override
    public void starmessage(final message message, final String userId) {
        FirebaseFirestore dbRef = FirebaseFirestore.getInstance();
        dbRef.collection("messages")
                .document(message.getId())
                .update("users_starred", FieldValue.arrayUnion(userId))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        messageListListener.onStarTaskComplete();
                    }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void unstarmessage(final message message, final String userId) {
        FirebaseFirestore dbRef = FirebaseFirestore.getInstance();
        dbRef.collection("messages")
                .document(message.getId())
                .update("users_starred", FieldValue.arrayRemove(userId))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        messageListListener.onStarTaskComplete();
                    }
                });
    }

    /** {@inheritDoc} */
    @Override
    public void readAllmessages() {
        FirebaseFirestore dbRef = FirebaseFirestore.getInstance();
        dbRef.collection("messages")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) messageListListener.onLoadmessagesTaskComplete(task.getResult());

                        // Hide refresh image
                        messageListListener.onUpdateTaskComplete();
                    }
                });
    }

    /** {@inheritDoc} */
    @Override
    public void readMymessages(String userId) {
        FirebaseFirestore dbRef = FirebaseFirestore.getInstance();
        dbRef.collection("messages")
                .whereEqualTo("user_id", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) messageListListener.onLoadmessagesTaskComplete(task.getResult());

                        // Hide refresh image
                        messageListListener.onUpdateTaskComplete();
                    }
                });
    }

    /** {@inheritDoc} */
    @Override
    public void readStarredmessages(String userId) {
        FirebaseFirestore dbRef = FirebaseFirestore.getInstance();
        dbRef.collection("messages")
                .whereArrayContains("users_starred", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) messageListListener.onLoadmessagesTaskComplete(task.getResult());

                        // Hide refresh image
                        messageListListener.onUpdateTaskComplete();
                    }
                });
    }

    /** {@inheritDoc} */
    @Override
    public void readCompletedmessages() {
        FirebaseFirestore dbRef = FirebaseFirestore.getInstance();
        dbRef.collection("messages")
                .whereEqualTo("status", message.Status.STATUS_COMPLETED.value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) messageListListener.onLoadmessagesTaskComplete(task.getResult());

                        // Hide refresh image
                        messageListListener.onUpdateTaskComplete();
                    }
                });
    }
}
