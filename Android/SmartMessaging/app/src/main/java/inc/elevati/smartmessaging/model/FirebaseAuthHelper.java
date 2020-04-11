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

    public final static int REGISTER_ACCOUNT_CREATED = 0;
    public final static int REGISTER_FAILED_ALREADY_EXISTS = 1;
    public final static int REGISTER_FAILED_UNKNOWN = 2;
    public final static int LOGIN_OK = 10;
    public final static int LOGIN_FAILED_NO_ACCOUNT = 11;
    public final static int LOGIN_FAILED_WRONG_PASSWORD = 12;
    public final static int LOGIN_FAILED_TOO_MANY_REQUESTS = 13;
    public final static int LOGIN_FAILED_UNKNOWN = 14;

    private static FirebaseAuthHelper instance;
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
            setCurrentUser();
        }
        return currentUser;
    }

    public LiveData<Integer> register(final String name, String email, String password) {
        MutableLiveData<Integer> result = new MutableLiveData<>();

        // Try to create account
        Task<Void> mainTask = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)

                // Update user name
                .continueWithTask(previousTask -> {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();
                    return previousTask.getResult().getUser().updateProfile(profileUpdates);
                });

        // Result listener
        mainTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                result.setValue(REGISTER_ACCOUNT_CREATED);
            } else {
                System.out.println(task.getException());

                // Account already exists
                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                    result.setValue(REGISTER_FAILED_ALREADY_EXISTS);

                // Unknown error
                else
                    result.setValue(REGISTER_FAILED_UNKNOWN);
            }
        });
        return result;
    }

    public LiveData<Integer> login(String email, String password) {
        MutableLiveData<Integer> result = new MutableLiveData<>();

        // Try to sign in
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        setCurrentUser();
                        result.setValue(LOGIN_OK);
                    } else {

                        System.out.println(task.getException());

                        // Account doesn't exists
                        if (task.getException() instanceof FirebaseAuthInvalidUserException)
                            result.setValue(LOGIN_FAILED_NO_ACCOUNT);

                        // Wrong password
                        else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            result.setValue(LOGIN_FAILED_WRONG_PASSWORD);

                        // Too many requests
                        else if (task.getException() instanceof FirebaseTooManyRequestsException)
                            result.setValue(LOGIN_FAILED_TOO_MANY_REQUESTS);

                        // Unknown error
                        else
                            result.setValue(LOGIN_FAILED_UNKNOWN);
                    }
                });
        return result;
    }

    private void setCurrentUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getUid());
        currentUser = new MutableLiveData<>();
        currentUser.setValue(user);
    }
}
