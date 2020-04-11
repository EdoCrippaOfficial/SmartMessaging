package inc.elevati.smartmessaging.login;

import inc.elevati.smartmessaging.login.register.RegisterFragment;
import inc.elevati.smartmessaging.login.register.RegisterPresenter;
import inc.elevati.smartmessaging.login.signin.SignInFragment;
import inc.elevati.smartmessaging.login.signin.SignInPresenter;
import inc.elevati.smartmessaging.utils.MvpContracts;
import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;

/** Presenter associated to {@link LoginActivity} */
public class LoginPresenter implements LoginContracts.LoginPresenter {

    /** The view associated to this presenter */
    private LoginContracts.LoginView view;

    /** The presenter associated to child view {@link RegisterFragment} */
    private LoginContracts.RegisterPresenter registerPresenter;

    /** The presenter associated to child view {@link SignInFragment} */
    private LoginContracts.SignInPresenter signInPresenter;

    /**
     * Constructor where children presenters ({@link LoginContracts.RegisterPresenter}
     * and {@link LoginContracts.SignInPresenter}) are instantiated too
     */
    LoginPresenter() {
        registerPresenter = new RegisterPresenter();
        signInPresenter = new SignInPresenter();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(MvpContracts.MvpView view) {
        this.view = (LoginContracts.LoginView) view;
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        this.view = null;
    }

    /** {@inheritDoc} */
    public LoginContracts.RegisterPresenter getRegisterPresenter() {
        return registerPresenter;
    }

    /** {@inheritDoc} */
    public LoginContracts.SignInPresenter getSignInPresenter() {
        return signInPresenter;
    }

    /** {@inheritDoc} */
    @Override
    public void checkIfAlreadySignedIn() {
        if (FirebaseAuthHelper.isAuthenticated()) view.startMainActivity();
    }
}
