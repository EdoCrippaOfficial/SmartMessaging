package inc.elevati.smartmessaging.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import inc.elevati.smartmessaging.model.firebase.FirebaseAuthHelper;
import inc.elevati.smartmessaging.model.firebase.FirebaseFirestoreHelper;
import inc.elevati.smartmessaging.model.firebase.FirebaseMessagingHelper;
import inc.elevati.smartmessaging.model.User;

// ViewModel che si occupa della comunizazione tra View e Firebase Auth
public class AuthViewModel extends ViewModel {

    // Replica delle costanti per evitare di riferirsi a oggetti di Model nelle View
    public final static int REGISTER_ACCOUNT_CREATED = FirebaseAuthHelper.REGISTER_ACCOUNT_CREATED;
    public final static int REGISTER_FAILED_ALREADY_EXISTS = FirebaseAuthHelper.REGISTER_FAILED_ALREADY_EXISTS;
    public final static int REGISTER_FAILED_UNKNOWN = FirebaseAuthHelper.REGISTER_FAILED_UNKNOWN;
    public final static int LOGIN_OK = FirebaseAuthHelper.LOGIN_OK;
    public final static int LOGIN_FAILED_NO_ACCOUNT = FirebaseAuthHelper.LOGIN_FAILED_NO_ACCOUNT;
    public final static int LOGIN_FAILED_WRONG_PASSWORD = FirebaseAuthHelper.LOGIN_FAILED_WRONG_PASSWORD;
    public final static int LOGIN_FAILED_TOO_MANY_REQUESTS = FirebaseAuthHelper.LOGIN_FAILED_TOO_MANY_REQUESTS;
    public final static int LOGIN_FAILED_UNKNOWN = FirebaseAuthHelper.LOGIN_FAILED_UNKNOWN;

    // Salvataggio dei risultati delle operazioni nel caso le View fossero distrutte
    // e ricreate e avessero a quel punto bisogno di conocere il risultato
    private LiveData<Integer> registrationResult, loginResult;

    public LiveData<User> getCurrentUser() {
       return FirebaseAuthHelper.getInstance().getCurrentUser();
    }

    public boolean isAuthenticated() {
        return FirebaseAuthHelper.getInstance().isAuthenticated();
    }

    public void signOut() {
        FirebaseAuthHelper.getInstance().signOut();
    }

    public LiveData<Integer> register(String name, String email, String password) {
        registrationResult = FirebaseAuthHelper.getInstance().register(name, email, password);
        return registrationResult;
    }

    public LiveData<Integer> requireRegistrationResult() {
        if (registrationResult == null) {
            registrationResult = new MutableLiveData<>();

            // Settaggio di errore sconosciuto nel caso (?) questa funzione fosse chiamata con nessun risultato in sospeso
            ((MutableLiveData<Integer>) registrationResult).setValue(REGISTER_FAILED_UNKNOWN);
        }
        return registrationResult;
    }

    public LiveData<Integer> login(String email, String password) {
        loginResult = FirebaseAuthHelper.getInstance().login(email, password);
        return loginResult;
    }

    public LiveData<Integer> requireLoginResult() {
        if (loginResult == null) {
            loginResult = new MutableLiveData<>();

            // Settaggio di errore sconosciuto nel caso (?) questa funzione fosse chiamata con nessun risultato in sospeso
            ((MutableLiveData<Integer>) loginResult).setValue(LOGIN_FAILED_UNKNOWN);
        }
        return loginResult;
    }

    public LiveData<String> checkToken() {
        return FirebaseMessagingHelper.getInstance().checkToken();
    }

    public void sendTokenToServer(String token, String user) {
        FirebaseFirestoreHelper.getInstance().writeToken(token, user);
    }
}
