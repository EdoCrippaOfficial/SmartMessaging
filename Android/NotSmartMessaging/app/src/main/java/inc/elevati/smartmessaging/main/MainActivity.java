package inc.elevati.smartmessaging.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.slider.Slider;

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
import inc.elevati.smartmessaging.utils.FilterOptions;
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
        presenter.setFilterOptions(1, 5, true, true);

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

    @Override
    public void showFilterDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_filter);
        FilterOptions filterOptions = presenter.getFilterOptions();
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
            presenter.setFilterOptions((int) minPriority, (int) maxPriority, showSingleMessages, showGroupMessages);
            presenter.loadMessages();
        });
        dialog.show();
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
    public void sortMessages(int sortCriteria) {
        messageAdapter.sortMessages(sortCriteria);
    }

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onMenuItemClicked(item.getItemId());
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
