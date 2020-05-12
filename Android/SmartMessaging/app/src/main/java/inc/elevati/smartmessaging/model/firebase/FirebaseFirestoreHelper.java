package inc.elevati.smartmessaging.model.firebase;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import inc.elevati.smartmessaging.model.Message;

public class FirebaseFirestoreHelper {

    public final static int USERNAME_OK = 10;
    public final static int USERNAME_EXISTS = 11;
    public final static int USERNAME_ERROR = 12;
    public final static int DELETE_FAILED = 1;
    public final static int DELETE_SUCCESS = 0;

    private static FirebaseFirestoreHelper instance;
    private MutableLiveData<List<Message>> messages;

    private FirebaseFirestoreHelper() {}

    public static FirebaseFirestoreHelper getInstance() {
        if (instance == null)
            instance = new FirebaseFirestoreHelper();
        return instance;
    }

    void addMessage(Message message) {
        List<Message> messagesList = messages.getValue();
        if (messagesList == null)
            messagesList = new ArrayList<>();
        messagesList.add(message);
        messages.postValue(messagesList);
    }

    public LiveData<List<Message>> fetchMessages(String userID) {
        if (messages == null) {
            messages = new MutableLiveData<>();
        }
        refreshMessages(userID);
        return messages;
    }

    public void refreshMessages(String userID) {
        FirebaseFirestore dbReference = FirebaseFirestore.getInstance();
        dbReference.collection("messages")
                .whereArrayContains("visualizing", userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        parseData(task.getResult(), userID);
                    } else {
                        // Finto refresh
                        if (messages.getValue() == null) {
                            messages.setValue(new ArrayList<>());
                        } else {
                            messages.setValue(messages.getValue());
                        }
                    }
                });
    }

    private void parseData(QuerySnapshot data, String userID) {
        List<Message> messagesList = new ArrayList<>();
        for (QueryDocumentSnapshot snap: data) {
            String id = snap.getId();
            String title = snap.getString("title");
            String body = snap.getString("body");
            String image = snap.getString("image");
            int priority = (int) (long) snap.getLong("priority");
            long timestamp = snap.getLong("timestamp");
            boolean cc = snap.getBoolean("cc");
            String receivers = cc ? snap.getString("receivers") : userID;
            messagesList.add(new Message(id, title, body, image, priority, receivers, timestamp, cc));
        }
        messages.setValue(messagesList);
    }

    public MutableLiveData<Integer> deleteMessage(Message message, String userID) {
        MutableLiveData<Integer> result = new MutableLiveData<>();
        FirebaseFirestore dbReference = FirebaseFirestore.getInstance();
        Map<String, Object> userRemover = new HashMap<>();
        userRemover.put("visualizing", FieldValue.arrayRemove(userID));
        dbReference.collection("messages").document(message.getId())
                .update(userRemover)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) result.setValue(DELETE_SUCCESS);
                    else result.setValue(DELETE_FAILED);
                });
        return result;
    }

    public void writeToken(String token, String user) {
        FirebaseFirestore dbReference = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        dbReference.collection("users").document(user).set(data);
    }

    public LiveData<Integer> checkUsername(String name) {
        MutableLiveData<Integer> result = new MutableLiveData<>();
        FirebaseFirestore dbReference = FirebaseFirestore.getInstance();
        dbReference.collection("users").document(name).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    result.setValue(USERNAME_EXISTS);
                } else {
                    result.setValue(USERNAME_OK);
                }
            } else {
                result.setValue(USERNAME_ERROR);
            }
        });
        return result;
    }
}
