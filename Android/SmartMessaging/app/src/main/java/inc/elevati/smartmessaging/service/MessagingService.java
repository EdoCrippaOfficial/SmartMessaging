package inc.elevati.smartmessaging.service;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import inc.elevati.smartmessaging.view.main.MainActivity;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //TODO mostrare effettivamente il messaggio
        Log.d("DEBUG", "Messaggio ricevuto!");

    }

    private void sendNotification(String messageID) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getApplicationContext().startActivity(intent);
    }

}
