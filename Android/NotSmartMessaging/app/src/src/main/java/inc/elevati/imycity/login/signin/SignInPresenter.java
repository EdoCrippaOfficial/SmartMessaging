package inc.elevati.smartmessaging.login.signin;

import inc.elevati.smartmessaging.firebase.FirebaseContracts;
import inc.elevati.smartmessaging.login.LoginContracts;
import inc.elevati.smartmessaging.utils.EspressoIdlingResource;
import inc.elevati.smartmessaging.utils.MvpContracts;
import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;

import static inc.elevati.smartmessaging.login.LoginContracts.SignInTaskResult.LOGIN_FAILED_NO_ACCOUNT;
import static inc.elevati.smartmessaging.login.LoginContracts.SignInTaskResult.LOGIN_FAILED_WRONG_PASSWORD;

/** Presenter associated to {@link SignInFragment} */
public class SignInPresenter implements LoginContracts.SignInPresenter {

    /** The view associated to this presenter */
    private LoginContracts.SignInView view;

    /** This flag is set when a task had to be executed when no view was attached to this presenter */
    private boolean pendingTask;

    /** Used only if pendingTask flag is set, if not null indicates that
     * {@link SignInPresenter#onSignInTaskComplete(LoginContracts.SignInTaskResult)} has to be executed */
    private LoginContracts.SignInTaskResult result;

    /** {@inheritDoc} */
    @Override
    public void attachView(MvpContracts.MvpView view) {
        this.view = (LoginContracts.SignInView) view;

        // If there were pending tasks, execute them now
        if (pendingTask) {

            // If result is not null, then onSignInTaskComplete has to be executed
            if (result != null) {
                onSignInTaskComplete(result);
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
    public void onRegisterButtonClicked() {
        view.switchToRegisterView();
    }

    /** {@inheritDoc} */
    @Override
    public void onSignInButtonClicked(String email, String password) {
        if (email.equals("")) {
            view.notifyInvalidEmail();
            return;
        } else if (password.equals("")) {
            view.notifyInvalidPassword();
            return;
        }

        EspressoIdlingResource.increment();
        view.showProgressDialog();

        FirebaseContracts.AuthHelper helper = new FirebaseAuthHelper(this);
        helper.signIn(email, password);
    }

    /** {@inheritDoc} */
    @Override
    public void onSignInTaskComplete(LoginContracts.SignInTaskResult result) {

        // If view is detached, set the pendingTask flag
        if (view == null) {
            pendingTask = true;
            this.result = result;
            return;
        }

        if (result.equals(LoginContracts.SignInTaskResult.LOGIN_OK)) {
            view.startMainActivity();
        } else if (result.equals(LOGIN_FAILED_NO_ACCOUNT)) {
            view.notifyAccountNotExists();
        } else if (result.equals(LOGIN_FAILED_WRONG_PASSWORD)) {
            view.notifyWrongPassword();
        } else {
            view.notifyUnknownError();
        }
        view.dismissProgressDialog();
        EspressoIdlingResource.decrement();
    }
}
