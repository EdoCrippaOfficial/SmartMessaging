package inc.elevati.smartmessaging.login.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.login.LoginActivity;
import inc.elevati.smartmessaging.login.LoginContracts;
import inc.elevati.smartmessaging.main.MainActivity;
import inc.elevati.smartmessaging.utils.ProgressDialog;

/** Fragment displayed when the user wants to sign-up */
public class RegisterFragment extends Fragment implements LoginContracts.RegisterView {

    /** {@link TextInputEditText}s for user data */
    private TextInputEditText textInputName, textInputEmail, textInputPassword, textInputPassword2;

    /** {@link TextInputLayout}s for user data */
    private TextInputLayout textLayoutName, textLayoutEmail, textLayoutPassword, textLayoutPassword2;

    /** The presenter associated to this view */
    private LoginContracts.RegisterPresenter presenter;

    /** Dialog displayed during tasks execution */
    private ProgressDialog progressDialog;

    /**
     * Method that has to be used to get a RegisterFragment instance instead of constructor
     * @return a new RegisterFragment instance
     */
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    /**
     * Method called when the View associated to this fragment is created (the first time this
     * fragment is shown, at orientation changes, at activity re-creations...); here the layout
     * is inflated and all Views owned by this fragment are initialized.
     * @param inflater the layout inflater
     * @param container this fragment parent view
     * @param savedInstanceState a Bundle containing saved data to be restored
     * @return the View initialized and inflated
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        textInputName = v.findViewById(R.id.text_input_edit_name);
        textInputEmail = v.findViewById(R.id.text_input_edit_email);
        textInputPassword = v.findViewById(R.id.text_input_edit_password);
        textInputPassword2 = v.findViewById(R.id.text_input_edit_password2);
        textLayoutName = v.findViewById(R.id.text_input_layout_name);
        textLayoutEmail = v.findViewById(R.id.text_input_layout_email);
        textLayoutPassword = v.findViewById(R.id.text_input_layout_password);
        textLayoutPassword2 = v.findViewById(R.id.text_input_layout_password2);


        // Clear error when users provides input in TextInputEditTexts
        clearEditTextErrorOnInput();

        // ProgressDialog retrieval
        progressDialog = (ProgressDialog) getFragmentManager().findFragmentByTag("progress");

        // Presenter retrieval
        presenter = ((LoginActivity) getActivity()).getPresenter().getRegisterPresenter();

        // Switch to login fragment
        v.findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSignInButtonClicked();
            }
        });

        // Register button clicked
        v.findViewById(R.id.bn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputName.clearFocus();
                textInputEmail.clearFocus();
                textInputPassword.clearFocus();
                textInputPassword2.clearFocus();
                textLayoutName.setError(null);
                textLayoutEmail.setError(null);
                textLayoutPassword.setError(null);
                textLayoutPassword2.setError(null);
                String name = textInputName.getText().toString();
                String email = textInputEmail.getText().toString();
                String password = textInputPassword.getText().toString();
                String password2 = textInputPassword2.getText().toString();
                presenter.onRegisterButtonClicked(name, email, password, password2);
            }
        });
        return v;
    }

    /** {@inheritDoc} */
    @Override
    public void switchToSignInView() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }

    /** {@inheritDoc} */
    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.newInstance(R.string.register_loading);
        progressDialog.show(getParentFragmentManager(), "progress");
    }

    /** {@inheritDoc} */
    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    /** {@inheritDoc} */
    @Override
    public void startMainActivity() {
        Activity activity = requireActivity();
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("login", true);
        startActivity(intent);
        activity.finish();
    }

    /** {@inheritDoc} */
    @Override
    public void notifyEmailAlreadyExists() {
        if (isAdded())
            textLayoutEmail.setError(getString(R.string.register_email_already_exists));
    }

    /** {@inheritDoc} */
    @Override
    public void notifyUnknownError() {
        if (isAdded())
            Toast.makeText(getContext(), R.string.register_unknown_error, Toast.LENGTH_SHORT).show();
    }

    /** {@inheritDoc} */
    @Override
    public void notifyInvalidName() {
        textLayoutName.setError(getString(R.string.register_no_name));
    }

    /** {@inheritDoc} */
    @Override
    public void notifyInvalidEmail() {
        textLayoutEmail.setError(getString(R.string.register_no_email));
    }

    /** {@inheritDoc} */
    @Override
    public void notifyInvalidPassword() {
        textLayoutPassword.setError(getString(R.string.register_no_password));
    }

    /** {@inheritDoc} */
    @Override
    public void notifyPasswordsNotMatch() {
        textLayoutPassword2.setError(getString(R.string.register_password_not_match));
    }

    /**
     * Method called to hide error when user provides new text in {@link TextInputEditText}s
     */
    private void clearEditTextErrorOnInput() {
        textInputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textLayoutName.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        textInputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textLayoutEmail.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        textInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textLayoutPassword.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        textInputPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputPassword2.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
