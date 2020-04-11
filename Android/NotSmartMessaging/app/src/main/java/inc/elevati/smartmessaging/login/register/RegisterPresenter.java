package inc.elevati.smartmessaging.login.register;

import java.util.regex.Pattern;

import inc.elevati.smartmessaging.firebase.FirebaseContracts;
import inc.elevati.smartmessaging.login.LoginContracts;
import inc.elevati.smartmessaging.utils.MvpContracts;
import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;

import static inc.elevati.smartmessaging.login.LoginContracts.RegisterTaskResult.REGISTER_ACCOUNT_CREATED;
import static inc.elevati.smartmessaging.login.LoginContracts.RegisterTaskResult.REGISTER_FAILED_ALREADY_EXISTS;

/** Presenter associated to {@link RegisterFragment} */
public class RegisterPresenter implements LoginContracts.RegisterPresenter {

    /** Regex for email */
    private final static String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

    /** Regex for password */
    private final static String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    /** The view associated to this presenter */
    private LoginContracts.RegisterView view;

    /** This flag is set when a task had to be executed when no view was attached to this presenter */
    private boolean pendingTask;

    /** Used only if pendingTask flag is set, if not null indicates that
     * {@link RegisterPresenter#onRegisterTaskComplete(LoginContracts.RegisterTaskResult)} has to be executed */
    private LoginContracts.RegisterTaskResult result;

    /** {@inheritDoc} */
    @Override
    public void attachView(MvpContracts.MvpView view) {
        this.view = (LoginContracts.RegisterView) view;

        // If there were pending tasks, execute them now
        if (pendingTask) {

            // If result is not null, then onRegisterTaskComplete has to be executed
            if (result != null) {
                onRegisterTaskComplete(result);
                result = null;
            }
            pendingTask = false;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        this.view = null;
    }

    /** {@inheritDoc} */
    @Override
    public void onSignInButtonClicked() {
        view.switchToSignInView();
    }

    /** {@inheritDoc} */
    @Override
    public void onRegisterButtonClicked(String name, String email, String password, String password2) {

        // Name check
        if (name.equals("")) {
            view.notifyInvalidName();
            return;
        }

        // Email check
        if (!Pattern.matches(emailRegex, email)) {
            view.notifyInvalidEmail();
            return;
        }

        // Password check
        if (!Pattern.matches(passwordRegex, password)) {
            view.notifyInvalidPassword();
            return;
        }

        if (!password.equals(password2)) {
            view.notifyPasswordsNotMatch();
            return;
        }

        // Everything is ok, proceed with register process
        view.showProgressDialog();
        FirebaseContracts.AuthHelper helper = new FirebaseAuthHelper(this);
        helper.register(name, email, password);
    }

    /** {@inheritDoc} */
    @Override
    public void onRegisterTaskComplete(LoginContracts.RegisterTaskResult result) {

        // If view is detached, set the pendingTask flag
        if (view == null) {
            pendingTask = true;
            this.result = result;
            return;
        }

        if (result.equals(REGISTER_ACCOUNT_CREATED)) {
            view.startMainActivity();
        } else if (result.equals(REGISTER_FAILED_ALREADY_EXISTS)) {
            view.notifyEmailAlreadyExists();
        } else {
            view.notifyUnknownError();
        }
        view.dismissProgressDialog();
    }
}
