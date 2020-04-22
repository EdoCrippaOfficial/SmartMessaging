package inc.elevati.smartmessaging.model.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import inc.elevati.smartmessaging.model.Message;
import inc.elevati.smartmessaging.model.User;

public class FirebaseMessagingHelper extends FirebaseMessagingService {

    private static FirebaseMessagingHelper instance;

    public static FirebaseMessagingHelper getInstance() {
        if (instance == null)
            instance = new FirebaseMessagingHelper();
        return instance;
    }

    public LiveData<String> checkToken() {
        MutableLiveData<String> token = new MutableLiveData<>();
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                token.setValue(task.getResult().getToken());
            } else {
                token.setValue(null);
            }
        });
        return token;
    }

    @Override
    public void onNewToken(@NonNull String token) {
        User user = FirebaseAuthHelper.getInstance().getCurrentUser().getValue();
        if (user != null) {
            FirebaseFirestoreHelper.getInstance().writeToken(token, user.getName());
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String id = remoteMessage.getData().get("id");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String url = remoteMessage.getData().get("image");
        int priority = Integer.parseInt(remoteMessage.getData().get("priority"));
        String receivers = remoteMessage.getData().get("receivers");
        long timestamp = Long.parseLong(remoteMessage.getData().get("timestamp"));
        boolean cc = Boolean.parseBoolean(remoteMessage.getData().get("cc"));
        if(!cc){
            User user = FirebaseAuthHelper.getInstance().getCurrentUser().getValue();
            if (user != null)
                receivers = user.getName();
        }

        Message m = new Message(id, title, body, url, priority, receivers, timestamp, cc);
        FirebaseFirestoreHelper.getInstance().addMessage(m);


    }

}
