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

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.view.main.MainActivity;
import inc.elevati.smartmessaging.view.utils.ProgressDialog;
import inc.elevati.smartmessaging.viewmodel.AuthViewModel;

public class RegisterFragment extends Fragment {

    private final static String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private final static String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    private TextInputEditText textInputName, textInputEmail, textInputPassword, textInputPassword2;
    private TextInputLayout textLayoutName, textLayoutEmail, textLayoutPassword, textLayoutPassword2;
    private ProgressDialog progressDialog;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_register, container, false);
        textInputName = layout.findViewById(R.id.text_input_edit_name);
        textInputEmail = layout.findViewById(R.id.text_input_edit_email);
        textInputPassword = layout.findViewById(R.id.text_input_edit_password);
        textInputPassword2 = layout.findViewById(R.id.text_input_edit_password2);
        textLayoutName = layout.findViewById(R.id.text_input_layout_name);
        textLayoutEmail = layout.findViewById(R.id.text_input_layout_email);
        textLayoutPassword = layout.findViewById(R.id.text_input_layout_password);
        textLayoutPassword2 = layout.findViewById(R.id.text_input_layout_password2);

        // Clear error when users provides input in TextInputEditTexts
        clearEditTextErrorOnInput();

        // Recupero di un eventuale ProgressDialog già presente. Questo vuol dire che l'activity è
        // stata distrutta mentre era in corso un'operazione, quindi si richiede al ViewModel l'esito
        progressDialog = (ProgressDialog) getParentFragmentManager().findFragmentByTag("progress");
        if (progressDialog != null)
            requireRegistrationResult();

        // Switch to login fragment
        layout.findViewById(R.id.tv_login).setOnClickListener(v -> switchToLoginFragment());

        // Register button clicked
        layout.findViewById(R.id.bn_register).setOnClickListener(v -> {
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
            register(name, email, password, password2);
        });
        return layout;
    }

    private void switchToLoginFragment() {
        getParentFragmentManager().popBackStack();
    }

    private void register(String name, String email, String password, String password2) {
        if (name.equals("")) {
            notifyInvalidName();
            return;
        }
        if (!Pattern.matches(emailRegex, email)) {
            notifyInvalidEmail();
            return;
        }
        if (!Pattern.matches(passwordRegex, password)) {
            notifyInvalidPassword();
            return;
        }
        if (!password.equals(password2)) {
            notifyPasswordNotMatch();
            return;
        }
        showProgressDialog();
        AuthViewModel authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Observer sull'oggetto LiveData. Viene chiamata la funzione handleRegistrationResult quando vi è un cambiamento sul LiveData
        authViewModel.register(name, email, password).observe(getViewLifecycleOwner(), this::handleRegistrationResult);
    }

    private void requireRegistrationResult() {
        AuthViewModel authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // In questo caso il ViewModel ritorna il LiveData che la view stava osservando prima di essere distrutta
        authViewModel.requireRegistrationResult().observe(getViewLifecycleOwner(), this::handleRegistrationResult);
    }

    private void handleRegistrationResult(int result) {
        switch (result) {
            case AuthViewModel.REGISTER_ACCOUNT_CREATED:
                startMainActivity();
                break;
            case AuthViewModel.REGISTER_FAILED_ALREADY_EXISTS:
                notifyEmailAlreadyExists();
                break;
            case AuthViewModel.REGISTER_FAILED_UNKNOWN:
                notifyUnknownError();
                break;
        }
        dismissProgressDialog();
    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.newInstance(R.string.register_loading);
        progressDialog.show(getParentFragmentManager(), "progress");
        getParentFragmentManager().executePendingTransactions();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    private void startMainActivity() {
        Activity activity = requireActivity();
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("login", true);
        startActivity(intent);
        activity.finish();
    }

    private void notifyEmailAlreadyExists() {
        if (isAdded()) textLayoutEmail.setError(getString(R.string.register_email_already_exists));
    }

    private void notifyUnknownError() {
        if (isAdded()) Toast.makeText(getContext(), R.string.register_unknown_error, Toast.LENGTH_SHORT).show();
    }

    private void notifyInvalidName() {
        textLayoutName.setError(getString(R.string.register_no_name));
    }

    private void notifyInvalidEmail() {
        textLayoutEmail.setError(getString(R.string.register_no_email));
    }

    private void notifyInvalidPassword() {
        textLayoutPassword.setError(getString(R.string.register_no_password));
    }

    private void notifyPasswordNotMatch() {
        textLayoutPassword2.setError(getString(R.string.register_password_not_match));
    }

    // Cancellazione degli errori quando vi è un nuovo input
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
                textLayoutPassword2.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }
}
