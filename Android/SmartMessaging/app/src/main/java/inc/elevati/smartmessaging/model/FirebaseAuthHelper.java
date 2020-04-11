package inc.elevati.smartmessaging.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FirebaseAuthHelper {

    // Costanti per il risultato delle operazioni
    public final static int REGISTER_ACCOUNT_CREATED = 0;
    public final static int REGISTER_FAILED_ALREADY_EXISTS = 1;
    public final static int REGISTER_FAILED_UNKNOWN = 2;
    public final static int LOGIN_OK = 10;
    public final static int LOGIN_FAILED_NO_ACCOUNT = 11;
    public final static int LOGIN_FAILED_WRONG_PASSWORD = 12;
    public final static int LOGIN_FAILED_TOO_MANY_REQUESTS = 13;
    public final static int LOGIN_FAILED_UNKNOWN = 14;

    // Singleton
    private static FirebaseAuthHelper instance;

    // Utente attualmente loggato
    private MutableLiveData<User> currentUser;

    private FirebaseAuthHelper() { }

    public static FirebaseAuthHelper getInstance() {
        if (instance == null)
            instance = new FirebaseAuthHelper();
        return instance;
    }

    public boolean isAuthenticated() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        currentUser = null;
    }

    public LiveData<User> getCurrentUser() {
        if (currentUser == null) {

            // Inizializza l'oggetto LiveData che sarà osservato dalla View interessata
            setCurrentUser();
        }
        return currentUser;
    }

    public LiveData<Integer> register(final String name, String email, String password) {
        MutableLiveData<Integer> result = new MutableLiveData<>();

        // Creazione account
        Task<Void> mainTask = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)

                // Setta il nome utente dopo la registrazione di email e password
                .continueWithTask(previousTask -> {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();
                    return previousTask.getResult().getUser().updateProfile(profileUpdates);
                });

        // Risultato dell'operazione
        mainTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                result.setValue(REGISTER_ACCOUNT_CREATED);
            } else {

                // Account già esistente
                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                    result.setValue(REGISTER_FAILED_ALREADY_EXISTS);

                // Errore sconosciuto
                else
                    result.setValue(REGISTER_FAILED_UNKNOWN);
            }
        });

        // LiveData osservabile
        return result;
    }

    public LiveData<Integer> login(String email, String password) {
        MutableLiveData<Integer> result = new MutableLiveData<>();

        // Tentativo di login
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        setCurrentUser();
                        result.setValue(LOGIN_OK);
                    } else {

                        // Account non esiste
                        if (task.getException() instanceof FirebaseAuthInvalidUserException)
                            result.setValue(LOGIN_FAILED_NO_ACCOUNT);

                        // Password errata
                        else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            result.setValue(LOGIN_FAILED_WRONG_PASSWORD);

                        // Richieste bloccate da Firebase
                        else if (task.getException() instanceof FirebaseTooManyRequestsException)
                            result.setValue(LOGIN_FAILED_TOO_MANY_REQUESTS);

                        // Errore sconosciuto
                        else
                            result.setValue(LOGIN_FAILED_UNKNOWN);
                    }
                });

        // LiveData osservabile
        return result;
    }

    // Set dell'utente corrente in un oggetto LiveData
    private void setCurrentUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getUid());
        currentUser = new MutableLiveData<>();
        currentUser.setValue(user);
    }
}
