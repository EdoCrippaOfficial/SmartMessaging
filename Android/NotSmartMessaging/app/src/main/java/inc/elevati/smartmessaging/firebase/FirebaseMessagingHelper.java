package inc.elevati.smartmessaging.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import inc.elevati.smartmessaging.main.MainContracts;

public class FirebaseMessagingHelper extends FirebaseMessagingService implements FirebaseContracts.MessagingHelper {

    private static FirebaseMessagingHelper instance;
    private MainContracts.MainPresenter presenter;

    public static FirebaseMessagingHelper getInstance() {
        if (instance == null)
            instance = new FirebaseMessagingHelper();
        return instance;
    }

    @Override
    public void setPresenter(MainContracts.MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void checkToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                presenter.onCheckTokenResult(task.getResult().getToken());
            } else {
                presenter.onCheckTokenResult(null);
            }
        });
    }

    @Override
    public void onNewToken(@NonNull String token) {
        String user = FirebaseAuthHelper.getUserName();
        if (user != null) {
            FirebaseFirestoreHelper.writeToken(token, user);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        // BRUTTO ma non si può fare di meglio con questa architettura che io sappia
        FirebaseFirestoreHelper.getInstance().fetchMessages(FirebaseAuthHelper.getUserName());
    }
}
