import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class AdminTest {

    public static void main(String[] args) {

        try {

            FileInputStream serviceAccount = new FileInputStream("firebase-admin-key.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://smartmessaging-4.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);

            System.out.println("FIREBASE INIZIALIZZATO!");


        } catch (IOException e) {
            e.printStackTrace();
        }

        String token = null;
        
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document("Simone");

        // asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = null;
        try {
            document = future.get();
            if (document.exists()) {
                token = document.getString("token");
                System.out.println("Document data: " + token);
            } else {
                System.out.println("No such document!");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // This registration token comes from the client FCM SDKs.
        String registrationToken = token;

        String receivers = "Edoardo; Simone; Andrea";
        List<String> visualizing = Arrays.asList(receivers.split("; "));

        Map<String, Object> messageDataDB = new HashMap<>();
        messageDataDB.put("title", "Titolo");
        messageDataDB.put("body", "Corpo");
        messageDataDB.put("image", "");
        messageDataDB.put("receivers", receivers);
        messageDataDB.put("visualizing", visualizing);
        messageDataDB.put("priority", 1);
        messageDataDB.put("timestamp", System.currentTimeMillis());
        messageDataDB.put("cc", false);

        Map<String, String> messageData = new HashMap<>();
        messageData.put("id", UUID.randomUUID().toString());
        messageData.put("title", (String) messageDataDB.get("title"));
        messageData.put("body", (String) messageDataDB.get("body"));
        messageData.put("image", (String) messageDataDB.get("image"));
        messageData.put("receivers", (String) messageDataDB.get("receivers"));
        messageData.put("priority", String.valueOf(messageDataDB.get("priority")));
        messageData.put("timestamp", String.valueOf(messageDataDB.get("timestamp")));
        messageData.put("cc", String.valueOf(messageDataDB.get("cc")));

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .setToken(registrationToken)
                .setNotification(Notification.builder().setTitle("Ciao").build())
                .putAllData(messageData)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);

        CollectionReference collectionRef = db.collection("messages");
        collectionRef.document(messageData.get("id")).create(messageDataDB);
    }
}
