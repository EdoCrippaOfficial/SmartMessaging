package inc.elevati.smartmessaging.view.login;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.view.main.MainActivity;
import inc.elevati.smartmessaging.view.utils.ProgressDialog;
import inc.elevati.smartmessaging.viewmodel.AuthViewModel;

public class LoginFragment extends Fragment {

    private TextInputEditText textInputEmail, textInputPassword;
    private TextInputLayout textLayoutEmail, textLayoutPassword;
    private ProgressDialog progressDialog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        textInputEmail = layout.findViewById(R.id.text_input_edit_email);
        textInputPassword = layout.findViewById(R.id.text_input_edit_password);
        textLayoutEmail = layout.findViewById(R.id.text_input_layout_email);
        textLayoutPassword = layout.findViewById(R.id.text_input_layout_password);

        // Clear error when users provides input in TextInputEditTexts
        clearEditTextErrorOnInput();

        // Recupero di un eventuale ProgressDialog già presente. Questo vuol dire che l'activity è
        // stata distrutta mentre era in corso un'operazione, quindi si richiede al ViewModel l'esito
        progressDialog = (ProgressDialog) getParentFragmentManager().findFragmentByTag("progress");
        if (progressDialog != null)
            requireLoginResult();

        // Switch to register fragment
        layout.findViewById(R.id.tv_register).setOnClickListener(v -> switchToRegisterFragment());

        // Login button clicked
        layout.findViewById(R.id.bn_login).setOnClickListener(v -> {
            textInputEmail.clearFocus();
            textInputPassword.clearFocus();
            textLayoutEmail.setError(null);
            textLayoutPassword.setError(null);
            String email = textInputEmail.getText().toString();
            String password = textInputPassword.getText().toString();
            login(email, password);
        });
        return layout;
    }

    private void switchToRegisterFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container_login, RegisterFragment.newInstance());
        fragmentTransaction.commit();
    }

    private void login(String email, String password) {
        if (email.equals("")) {
            textLayoutEmail.setError(getString(R.string.login_no_email));
            return;
        } else if (password.equals("")) {
            textLayoutPassword.setError(getString(R.string.login_no_password));
            return;
        }
        showProgressDialog();
        AuthViewModel authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Observer sull'oggetto LiveData. Viene chiamata la funzione handleLoginResult quando vi è un cambiamento sul LiveData
        authViewModel.login(email, password).observe(getViewLifecycleOwner(), this::handleLoginResult);
    }

    private void requireLoginResult() {
        AuthViewModel authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // In questo caso il ViewModel ritorna il LiveData che la view stava osservando prima di essere distrutta
        authViewModel.requireLoginResult().observe(getViewLifecycleOwner(), this::handleLoginResult);
    }

    private void handleLoginResult(int result) {
        switch (result) {
            case AuthViewModel.LOGIN_OK:
                startMainActivity();
                break;
            case AuthViewModel.LOGIN_FAILED_NO_ACCOUNT:
                notifyAccountNotExists();
                break;
            case AuthViewModel.LOGIN_FAILED_WRONG_PASSWORD:
                notifyWrongPassword();
                break;
            case AuthViewModel.LOGIN_FAILED_TOO_MANY_REQUESTS:
                notifyTooManyRequestsError();
                break;
            case AuthViewModel.LOGIN_FAILED_UNKNOWN:
                notifyUnknownError();
                break;
        }
        dismissProgressDialog();
    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.newInstance(R.string.login_loading);
        progressDialog.show(getParentFragmentManager(), "progress");
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }

   private void startMainActivity() {
        Activity activity = requireActivity();
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
        activity.finish();
    }

   private void notifyAccountNotExists() {
       if (isAdded()) textLayoutEmail.setError(getString(R.string.login_account_not_exists));
    }

    private void notifyWrongPassword() {
        if (isAdded()) textLayoutPassword.setError(getString(R.string.login_wrong_password));
    }

    private void notifyTooManyRequestsError() {
        if (isAdded()) Toast.makeText(getContext(), R.string.login_too_many_requests, Toast.LENGTH_LONG).show();
    }

    private void notifyUnknownError() {
        if (isAdded()) Toast.makeText(getContext(), R.string.login_unknown_error, Toast.LENGTH_SHORT).show();
    }

    // Cancellazione degli errori quando vi è un nuovo input
    private void clearEditTextErrorOnInput() {
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
    }
}