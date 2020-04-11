package inc.elevati.smartmessaging.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.view.main.MainActivity;
import inc.elevati.smartmessaging.viewmodel.AuthViewModel;

public class AuthActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkTheme())
            setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_login);

        // Creazione fragment se l'activity è lanciata la prima volta (altrimenti ci pensa da solo)
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_login, LoginFragment.newInstance());
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        AuthViewModel authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Controllo se vi è già un utente autenticato
        if (authViewModel.isAuthenticated())
            startMainActivity();
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isDarkTheme() {
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        return preferences.getBoolean("dark", false);
    }
}
