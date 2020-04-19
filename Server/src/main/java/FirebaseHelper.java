import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class FirebaseHelper {

    private static FirebaseHelper instance = null;

    public static FirebaseHelper getInstance(){
        if (instance == null)
            instance = new FirebaseHelper();
        return instance;
    }

    private FirebaseHelper() {
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
    }

    private List<String> getTokens(List<String> dest){
        Firestore db = FirestoreClient.getFirestore();
        List<String> result = new ArrayList<>();
        for (String name : dest) {
            DocumentReference docRef = db.collection("users").document(name);
            // asynchronously retrieve the document
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = null;
            String token = null;
            try {
                document = future.get();
                if (document.exists()) {
                    token = document.getString("token");
                    if (token.equals(""))
                        System.err.println("The user " + name + " has null token. He may have disconnected from the system");
                    else
                        result.add(token);
                    System.out.println("Document data for user: " + name + " | " + token);
                } else {
                    throw new RuntimeException("No document for user: " + name);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void sendMessage(Messaggio mess){

        mess.img = mess.img != null ? mess.img : "";

        List<String> tokens = getTokens(mess.destinatari);

        if (tokens.size() == 0)
            throw new RuntimeException("No valid users");

        String receivers = "";
        for (String name: mess.destinatari) {
            receivers = receivers + name + ";";
        }
        receivers = receivers.substring(0, receivers.length() - 1);

        Map<String, Object> messageDataDB = new HashMap<>();
        messageDataDB.put("title", mess.titolo);
        messageDataDB.put("body", mess.corpo);
        messageDataDB.put("image", mess.img);
        messageDataDB.put("receivers", receivers);
        messageDataDB.put("visualizing", mess.destinatari);
        messageDataDB.put("priority", mess.priorita);
        messageDataDB.put("timestamp", System.currentTimeMillis());
        messageDataDB.put("cc", mess.cc);

        Map<String, String> messageData = new HashMap<>();
        messageData.put("id", UUID.randomUUID().toString());
        messageData.put("title", (String) messageDataDB.get("title"));
        messageData.put("body", (String) messageDataDB.get("body"));
        messageData.put("image", (String) messageDataDB.get("image"));
        messageData.put("receivers", (String) messageDataDB.get("receivers"));
        messageData.put("priority", String.valueOf(messageDataDB.get("priority")));
        messageData.put("timestamp", String.valueOf(messageDataDB.get("timestamp")));
        messageData.put("cc", String.valueOf(messageDataDB.get("cc")));

        Notification notification = Notification.builder()
                .setTitle((String) messageDataDB.get("title"))
                .setBody((String) messageDataDB.get("body"))
                .setImage((String) messageDataDB.get("image"))
                .build();

        MulticastMessage messages = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(notification)
                .putAllData(messageData)
                .build();

        BatchResponse response = null;
        try {
            response = FirebaseMessaging.getInstance().sendMulticast(messages);
            System.out.println(response.getSuccessCount() + " messages were sent successfully");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("messages");
        collectionRef.document(messageData.get("id")).create(messageDataDB);
    }
}
