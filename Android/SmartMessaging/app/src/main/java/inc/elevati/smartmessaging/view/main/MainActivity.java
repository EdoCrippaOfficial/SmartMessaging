package inc.elevati.smartmessaging.view.main;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.slider.Slider;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.model.Message;
import inc.elevati.smartmessaging.view.login.AuthActivity;
import inc.elevati.smartmessaging.viewmodel.AuthViewModel;
import inc.elevati.smartmessaging.viewmodel.MessageHandlerViewModel;
import inc.elevati.smartmessaging.viewmodel.MessagesListViewModel;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ViewModelProvider viewModelProvider;
    private SwipeRefreshLayout messagesRefresher;
    private MessageItemAdapter messageItemAdapter;
    private DrawerLayout drawerLayout;
    private String currentUser;
    private int sortCriteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean darkTheme = isDarkTheme();
        if (darkTheme)
            setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_main);
        sortCriteria = getIntent().getIntExtra("sort", MessageItemAdapter.SORT_NEWEST);

        // Oggetto che permette di recuperare i ViewModel
        viewModelProvider = new ViewModelProvider(this);
        initToolbar();
        View headerView = initDrawer(darkTheme);
        initMessagesAdapter();
        messagesRefresher = findViewById(R.id.messages_refresher);
        messagesRefresher.setOnRefreshListener(this);
        showMessages(headerView);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    private View initDrawer(boolean darkTheme) {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView drawerView = findViewById(R.id.drawer_view);

        // Evidenziato il bottone tema scuro
        if (darkTheme)
            drawerView.setCheckedItem(R.id.bn_dark);
        else
            drawerView.setCheckedItem(R.id.bn_light);

        // Click sulla barra laterale
        drawerView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bn_dark:
                    if (darkTheme)
                        drawerView.setCheckedItem(R.id.bn_light);
                    else
                        drawerView.setCheckedItem(R.id.bn_dark);
                    setDarkTheme(!darkTheme);
                    break;
                case R.id.bn_logout:
                    logOut();
                    break;
            }
            return false;
        });
        return drawerView.getHeaderView(0);
    }

    private void initMessagesAdapter() {
        RecyclerView container = findViewById(R.id.messages_container);

        // Creazione adapter, l'argomento è la funzione da chiamare quando viene cliccato un messaggio (interfaccia OnMessageClickListener)
        messageItemAdapter = new MessageItemAdapter(this::showMessageDialog, sortCriteria);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        container.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(container.getContext(), layoutManager.getOrientation());
        container.addItemDecoration(dividerItemDecoration);
        container.setAdapter(messageItemAdapter);
    }

    private void showMessageDialog(Message message) {
        MessageHandlerViewModel messageHandlerViewModel = viewModelProvider.get(MessageHandlerViewModel.class);

        // Salvataggio dell'oggetto message in un apposito ViewModel, ci penserà il dialog creato a recuperarlo
        messageHandlerViewModel.setMessage(message);
        messageHandlerViewModel.setUserID(currentUser);
        MessageDialog messageDialog = MessageDialog.newInstance();
        messageDialog.show(getSupportFragmentManager(), "");
    }

    private void showMessages(View headerView) {
        messagesRefresher.post(() -> messagesRefresher.setRefreshing(true));
        AuthViewModel authViewModel = viewModelProvider.get(AuthViewModel.class);

        // Recupero dei dati dell'utente
        authViewModel.getCurrentUser().observe(this, user -> {
            TextView tvUser = headerView.findViewById(R.id.tv_username);
            TextView tvEmail = headerView.findViewById(R.id.tv_email);
            tvUser.setText(user.getName());
            tvEmail.setText(user.getEmail());
            currentUser = user.getName();

            // Controllo se è il caso di settare il token nel DB
            Bundle extras = getIntent().getExtras();
            boolean checkToken = extras != null && extras.getBoolean("login");
            if (checkToken) {
                authViewModel.checkToken().observe(this, token -> {
                    if (token != null) {
                        authViewModel.sendTokenToServer(token, currentUser);
                    }
                });
            }

            // Osservazione del LiveData richiesto che conterrà i messaggi quando sono pronti per essere visualizzati
            MessagesListViewModel messagesListViewModel = viewModelProvider.get(MessagesListViewModel.class);
            messagesListViewModel.setUserID(currentUser);
            messagesListViewModel.initFilterOptions();
            messagesListViewModel.getFilteredMessages().observe(this, messages -> {
                messageItemAdapter.updateMessages(messages);
                messagesRefresher.post(() -> messagesRefresher.setRefreshing(false));
            });
        });
    }

    public void logOut() {
        AuthViewModel authViewModel = viewModelProvider.get(AuthViewModel.class);
        authViewModel.signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    private void createFilterDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_filter);
        MessagesListViewModel messagesListViewModel = viewModelProvider.get(MessagesListViewModel.class);

        // Ottenimento opzioni salvate
        messagesListViewModel.getFilterOptions().observe(this, filterOptions -> {
            Slider slider_priority = dialog.findViewById(R.id.slider_priority);
            slider_priority.setValues((float) filterOptions.getMinPriority(), (float) filterOptions.getMaxPriority());
            CheckBox check_single = dialog.findViewById(R.id.check_single);
            CheckBox check_group = dialog.findViewById(R.id.check_group);
            check_single.setChecked(filterOptions.isShowSingleMessages());
            check_group.setChecked(filterOptions.isShowGroupMessages());
            dialog.setOnCancelListener(dialog1 -> {
                float minPriority = slider_priority.getValues().get(0);
                float maxPriority = slider_priority.getValues().get(1);
                boolean showSingleMessages = check_single.isChecked();
                boolean showGroupMessages = check_group.isChecked();

                // Richiesta con i dati aggiornati
                messagesListViewModel.setFilterOptions((int) minPriority, (int) maxPriority, showSingleMessages, showGroupMessages);
                messagesListViewModel.getFilteredMessages();
            });
        });
        dialog.show();
    }

    @Override
    public void onRefresh() {
        final MessagesListViewModel messagesListViewModel = viewModelProvider.get(MessagesListViewModel.class);
        messagesListViewModel.refreshList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.bn_filter:
                createFilterDialog();
                break;

            // Cambio dell'ordine di visualizzazione
            case R.id.bn_newest:
                sortCriteria = MessageItemAdapter.SORT_NEWEST;
                messageItemAdapter.setSortCriteria(sortCriteria);
                break;
            case R.id.bn_oldest:
                sortCriteria = MessageItemAdapter.SORT_OLDEST;
                messageItemAdapter.setSortCriteria(sortCriteria);
                break;
            case R.id.bn_priority_high:
                sortCriteria = MessageItemAdapter.SORT_PRIORITY_HIGH;
                messageItemAdapter.setSortCriteria(sortCriteria);
                break;
            case R.id.bn_priority_low:
                sortCriteria = MessageItemAdapter.SORT_PRIORITY_LOW;
                messageItemAdapter.setSortCriteria(sortCriteria);
                break;
            case R.id.bn_logout:
                logOut();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_items, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START, true);
        else
            super.onBackPressed();

    }

    private boolean isDarkTheme() {
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        return preferences.getBoolean("dark", false);
    }

    private void setDarkTheme(boolean dark) {
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("dark", dark);
        editor.apply();
        if (dark)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);
        getIntent().putExtra("sort", sortCriteria);
        recreate();
    }
}
