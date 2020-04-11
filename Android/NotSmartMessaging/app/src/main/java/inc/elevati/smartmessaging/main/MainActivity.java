package inc.elevati.smartmessaging.main;

import android.content.Intent;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.login.LoginActivity;
import inc.elevati.smartmessaging.utils.Message;

/** The main activity that contains the ViewPager with the fragments */
public class MainActivity extends AppCompatActivity implements MainContracts.MainView, SwipeRefreshLayout.OnRefreshListener {

    /** The presenter associated to this view */
    private MainPresenter presenter;

    /** Main menu drawer */
    private DrawerLayout drawerLayout;

    private NavigationView drawerView;

    private MessagesAdapter messageAdapter;

    private SwipeRefreshLayout refresher;

    /**
     * Called when app starts and after orientation changes or activity re-creations,
     * here all the activity components are initialized and layout is shown
     * @param savedInstanceState a Bundle containing saved data to be restored
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Presenter creation
        presenter = (MainPresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) presenter = new MainPresenter();
        presenter.attachView(this);

        // Action Bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        // Main menu
        initDrawer();

        // UserName and email in navigator header
        View headerView = drawerView.getHeaderView(0);
        TextView tvUser = headerView.findViewById(R.id.tv_username);
        TextView tvEmail = headerView.findViewById(R.id.tv_email);
        tvUser.setText(presenter.getCurrentUserName());
        tvEmail.setText(presenter.getCurrentUserEmail());

        refresher = findViewById(R.id.messages_refresher);
        refresher.setOnRefreshListener(this);
        initMessagesAdapter();
        refresher.setRefreshing(true);
        presenter.loadMessages();
    }

    private void initDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer_view);
        drawerView.setNavigationItemSelectedListener(item -> {
            presenter.onMenuItemClicked(item.getItemId());
            return false;
        });
        View headerView = drawerView.getHeaderView(0);
        TextView tvUser = headerView.findViewById(R.id.tv_username);
        TextView tvEmail = headerView.findViewById(R.id.tv_email);
        tvUser.setText(presenter.getCurrentUserName());
        tvEmail.setText(presenter.getCurrentUserEmail());
    }

    private void initMessagesAdapter() {
        RecyclerView container = findViewById(R.id.messages_container);
        messageAdapter = new MessagesAdapter(this, presenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        container.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(container.getContext(), layoutManager.getOrientation());
        container.addItemDecoration(dividerItemDecoration);
        container.setAdapter(messageAdapter);
    }

    /**
     * Called at orientation changes or activity re-creations, it retains the presenter
     * @return the presenter associated to the view
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    public MainPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void updateMessages(List<Message> messages) {
        messageAdapter.updateMessages(messages);
    }

    @Override
    public void resetRefreshing() {
        refresher.setRefreshing(false);
    }

    @Override
    public void showMessageDialog(Message message) {
        MessageDialog dialog = MessageDialog.newInstance(message);
        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.bn_newest:
                messageAdapter.sortMessages(MainContracts.SORT_DATE_NEWEST);
                break;
            case R.id.bn_oldest:
                messageAdapter.sortMessages(MainContracts.SORT_DATE_OLDEST);
                break;
            case R.id.bn_priority_high:
                messageAdapter.sortMessages(MainContracts.SORT_PRIORITY_HIGH);
                break;
            case R.id.bn_priority_low:
                messageAdapter.sortMessages(MainContracts.SORT_PRIORITY_LOW);
                break;
            case R.id.bn_logout:
                presenter.onMenuItemClicked(R.id.bn_logout);
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_items, menu);
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START, true);
        else
            super.onBackPressed();

    }

    @Override
    public void onRefresh() {
        presenter.loadMessages();
    }
}
