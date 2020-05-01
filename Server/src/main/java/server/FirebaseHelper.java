package server;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
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

    public static FirebaseHelper getInstance() {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getTokens(List<String> dest) {
        Firestore db = FirestoreClient.getFirestore();
        List<String> result = new ArrayList<>();
        for (String name : dest) {
            DocumentReference docRef = db.collection("users").document(name);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document;
            String token;
            try {
                document = future.get();
                if (document.exists()) {
                    token = document.getString("token");
                    if ("".equals(token))
                        System.out.println("WARNING: L\'utente " + name + " non è correntemente collegato al sistema.\n");
                    else
                        result.add(token);
                } else {
                    throw new RuntimeException("Nel sistema non è presente l'utente: " + name);
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
            throw new RuntimeException("Nessun utente valido per l'invio");

        StringBuilder receivers = new StringBuilder();
        for (String name: mess.destinatari) {
            receivers.append(name).append("; ");
        }
        receivers = new StringBuilder(receivers.substring(0, receivers.length() - 2));

        Map<String, Object> messageDataDB = new HashMap<>();
        messageDataDB.put("title", mess.titolo);
        messageDataDB.put("body", Format.formatText(mess.corpo, mess.formattazione));
        messageDataDB.put("image", mess.img);
        messageDataDB.put("receivers", receivers.toString());
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

        try {
            String dest = tokens.size() > 1 ? "destinatari" : "destinatario";
            System.out.println("Invio messaggio #" + mess.id + " a " + tokens.size() + " " + dest + " in corso...");
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(messages);
            int success = response.getSuccessCount();
            int failed = response.getFailureCount();
            String sent = success > 1 ? " messaggi " : " messaggio ";
            String fail = success > 1 ? " messaggi " : " messaggio ";
            if (success > 0)
                System.out.println("Invio di " + success + sent + "completato con successo.\n");
            if (failed > 0)
                System.out.println("Invio di " + success + fail + "non riuscito.\n");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection("messages");
        collectionRef.document(messageData.get("id")).create(messageDataDB);
    }

    private void printUser(DocumentSnapshot document) {
        String name = document.getId();
        long received = (long) document.get("received");
        System.out.println("UTENTE: " + name + " | Messaggi ricevuti: " + received);
    }

    public void printStats(String user) {
        System.out.println("------------ STATISTICHE UTENTE: " + user + " ------------");
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document(user);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                printUser(document);
            } else {
                System.out.println("Nessun utente " + user + " è presente nel sistema.\n");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.print("\n");
    }

    public void printStats() {
        System.out.println("------------ STATISTICHE UTENTI ------------");
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference ref = db.collection("users");
        ApiFuture<QuerySnapshot> future = ref.get();
        try {
            future.get().getDocuments().forEach(this::printUser);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.print("\n");
    }
}
