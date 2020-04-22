package inc.elevati.smartmessaging.firebase;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.annotation.NonNull;
import inc.elevati.smartmessaging.login.LoginContracts;

import static inc.elevati.smartmessaging.login.LoginContracts.SignInTaskResult.LOGIN_FAILED_NO_ACCOUNT;
import static inc.elevati.smartmessaging.login.LoginContracts.SignInTaskResult.LOGIN_FAILED_UNKNOWN;
import static inc.elevati.smartmessaging.login.LoginContracts.SignInTaskResult.LOGIN_FAILED_WRONG_PASSWORD;
import static inc.elevati.smartmessaging.login.LoginContracts.RegisterTaskResult.REGISTER_ACCOUNT_CREATED;
import static inc.elevati.smartmessaging.login.LoginContracts.RegisterTaskResult.REGISTER_FAILED_ALREADY_EXISTS;
import static inc.elevati.smartmessaging.login.LoginContracts.RegisterTaskResult.REGISTER_FAILED_UNKNOWN;

/** This class is an helper that handles with Firebase Auth related tasks */
public class FirebaseAuthHelper implements FirebaseContracts.AuthHelper {

    /** The listener that gets notified about register-related tasks */
    private LoginContracts.RegisterPresenter registerPresenter;

    /** The listener that gets notified about login-related tasks */
    private LoginContracts.SignInPresenter loginPresenter;

    /** Current user displayed name */
    private static String userName;

    /** Current user email */
    private static String userEmail;

    /** Current user id */
    private static String userId;

    private static FirebaseAuthHelper instance;

    private FirebaseAuthHelper() {}

    public static FirebaseAuthHelper getInstance() {
        if (instance == null)
            instance = new FirebaseAuthHelper();
        return instance;
    }

    @Override
    public void setPresenter(LoginContracts.SignInPresenter presenter) {
        this.loginPresenter = presenter;
    }

    @Override
    public void setPresenter(LoginContracts.RegisterPresenter presenter) {
        this.registerPresenter = presenter;
    }

    /**
     * @return the current user displayed name
     */
    public static String getUserName() {
        if (userName == null)
            userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        return userName;
    }

    /**
     * @return the current user email
     */
    public static String getUserEmail() {
        if (userEmail == null)
            userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        return userEmail;
    }

    /**
     * @return the current user id
     */
    public static String getUserId() {
        if (userId == null)
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return userId;
    }

    /**
     * @return if an user is currently authenticated
     */
    public static boolean isAuthenticated() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    /**
     * Executes the sign out task for the current user
     */
    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    /** {@inheritDoc} */
    @Override
    public void register(final String name, String email, String password) {

        // Try to create account
        Task<Void> mainTask = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)

                // Update user name
                .continueWithTask(new Continuation<AuthResult, Task<Void>>() {
                    @Override
                    public Task<Void> then(@NonNull Task<AuthResult> t) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();
                        return t.getResult().getUser().updateProfile(profileUpdates);
                    }
                });

        // Result listener
        mainTask.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    registerPresenter.onRegisterTaskComplete(REGISTER_ACCOUNT_CREATED);
                } else {

                    // Account already exists
                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                        registerPresenter.onRegisterTaskComplete(REGISTER_FAILED_ALREADY_EXISTS);

                    // Unknown error
                    else
                        registerPresenter.onRegisterTaskComplete(REGISTER_FAILED_UNKNOWN);
                }
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void signIn(String email, String password) {
        // Try to sign in
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // On sign in set current user information
                            userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                            userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            loginPresenter.onSignInTaskComplete(LoginContracts.SignInTaskResult.LOGIN_OK);
                        } else {

                            // Account doesn't exists
                            if (task.getException() instanceof FirebaseAuthInvalidUserException)
                                loginPresenter.onSignInTaskComplete(LOGIN_FAILED_NO_ACCOUNT);

                            // Wrong password
                            else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                loginPresenter.onSignInTaskComplete(LOGIN_FAILED_WRONG_PASSWORD);

                            // Unknown error
                            else
                                loginPresenter.onSignInTaskComplete(LOGIN_FAILED_UNKNOWN);
                        }
                    }
                });
    }
}
