package inc.elevati.smartmessaging.firebase;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import inc.elevati.smartmessaging.main.MainContracts;
import inc.elevati.smartmessaging.utils.Message;

public class FirebaseFirestoreHelper implements FirebaseContracts.FirestoreHelper {

    private List<Message> messages;
    private MainContracts.MainPresenter presenter;

    public FirebaseFirestoreHelper(MainContracts.MainPresenter presenter) {
        messages = new ArrayList<>();
        this.presenter = presenter;
    }

    public static void writeToken(String token, String userID) {
        FirebaseFirestore dbReference = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        dbReference.collection("users").document(userID).set(data);
    }

    @Override
    public void fetchMessages(String userID) {
        FirebaseFirestore dbReference = FirebaseFirestore.getInstance();
        dbReference.collection("messages")
                .whereArrayContains("visualizing", userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        presenter.onLoadMessagesTaskComplete(task.getResult());
                    }
                    presenter.onUpdateTaskComplete();
                });
    }

    @Override
    public void deleteMessage(Message message, String userID) {
        FirebaseFirestore dbReference = FirebaseFirestore.getInstance();
        Map<String, Object> userRemover = new HashMap<>();
        userRemover.put("visualizing", FieldValue.arrayRemove(userID));
        dbReference.collection("messages").document(message.getId())
                .update(userRemover)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        presenter.onDeleteMessageTaskComplete(MainContracts.DeleteMessageTaskResult.RESULT_OK);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        presenter.onDeleteMessageTaskComplete(MainContracts.DeleteMessageTaskResult.RESULT_FAILED);
                    }
                });
    }
}
