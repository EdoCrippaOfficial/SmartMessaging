package inc.elevati.smartmessaging.view.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.model.Message;
import inc.elevati.smartmessaging.view.login.AuthActivity;
import inc.elevati.smartmessaging.viewmodel.AuthViewModel;
import inc.elevati.smartmessaging.viewmodel.MessageDetailViewModel;
import inc.elevati.smartmessaging.viewmodel.MessagesListViewModel;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ViewModelProvider viewModelProvider;
    private Observer<List<Message>> messagesObserver;
    private SwipeRefreshLayout messagesRefresher;
    private MessageItemAdapter messageItemAdapter;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean darkTheme = isDarkTheme();
        if (darkTheme)
            setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_main);
        viewModelProvider = new ViewModelProvider(this);
        initToolbar();
        initDrawer(darkTheme);
        initMessagesAdapter();
        messagesRefresher = findViewById(R.id.messages_refresher);
        messagesRefresher.setOnRefreshListener(this);
        messagesObserver = messages -> {
            messagesRefresher.setRefreshing(false);
            messageItemAdapter.updateMessages(messages);
        };
        showMessages();
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

    private void initDrawer(boolean darkTheme) {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView drawerView = findViewById(R.id.drawer_view);
        if (darkTheme)
            drawerView.setCheckedItem(R.id.bn_dark);
        else
            drawerView.setCheckedItem(R.id.bn_light);
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
        AuthViewModel authViewModel = viewModelProvider.get(AuthViewModel.class);
        authViewModel.getCurrentUser().observe(this, user -> {
            View headerView = drawerView.getHeaderView(0);
            TextView tvUser = headerView.findViewById(R.id.tv_username);
            TextView tvEmail = headerView.findViewById(R.id.tv_email);
            tvUser.setText(user.getName());
            tvEmail.setText(user.getEmail());
        });
    }

    private void initMessagesAdapter() {
        RecyclerView container = findViewById(R.id.messages_container);
        messageItemAdapter = new MessageItemAdapter(this::showMessageDialog);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        container.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(container.getContext(), layoutManager.getOrientation());
        container.addItemDecoration(dividerItemDecoration);
        container.setAdapter(messageItemAdapter);
    }

    private void showMessageDialog(Message message) {
        MessageDetailViewModel messageDetailViewModel = viewModelProvider.get(MessageDetailViewModel.class);
        messageDetailViewModel.setMessage(message);
        MessageDialog messageDialog = MessageDialog.newInstance();
        messageDialog.show(getSupportFragmentManager(), "");
    }

    private void showMessages() {
        AuthViewModel authViewModel = viewModelProvider.get(AuthViewModel.class);
        authViewModel.getCurrentUser().observe(this, user -> {
            MessagesListViewModel messagesListViewModel = viewModelProvider.get(MessagesListViewModel.class);
            messagesListViewModel.getFilteredByReceiverList(user.getName()).observe(this, messagesObserver);
        });
    }

    public void logOut() {
        AuthViewModel authViewModel = viewModelProvider.get(AuthViewModel.class);
        authViewModel.signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
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
            case R.id.bn_newest:
                messageItemAdapter.setSortCriteria(MessageItemAdapter.SORT_NEWEST);
                break;
            case R.id.bn_oldest:
                messageItemAdapter.setSortCriteria(MessageItemAdapter.SORT_OLDEST);
                break;
            case R.id.bn_priority_high:
                messageItemAdapter.setSortCriteria(MessageItemAdapter.SORT_PRIORITY_HIGH);
                break;
            case R.id.bn_priority_low:
                messageItemAdapter.setSortCriteria(MessageItemAdapter.SORT_PRIORITY_LOW);
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
        recreate();
    }
}
