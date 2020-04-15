import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import java.io.FileInputStream;
import java.io.IOException;
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
        DocumentReference docRef = db.collection("users").document("Edoardo");

        // asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = null;
        try {
            document = future.get();
            if (document.exists()) {
                token = document.get("token").toString();
                System.out.println("Document data: " + token);
            } else {
                System.out.println("No such document!");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // This registration token comes from the client FCM SDKs.
        String registrationToken = token;

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .setToken(registrationToken)
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


    }
}
