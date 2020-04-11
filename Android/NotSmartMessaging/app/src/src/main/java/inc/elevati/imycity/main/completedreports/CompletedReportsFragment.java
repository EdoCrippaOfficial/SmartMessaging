package inc.elevati.smartmessaging.main.completedmessages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.main.MainActivity;
import inc.elevati.smartmessaging.main.MainContracts;
import inc.elevati.smartmessaging.main.messageDialog;
import inc.elevati.smartmessaging.main.messagesAdapter;
import inc.elevati.smartmessaging.utils.message;

import static inc.elevati.smartmessaging.main.MainContracts.PAGE_COMPLETED;
import static inc.elevati.smartmessaging.main.MainContracts.message_SORT_DATE_NEWEST;
import static inc.elevati.smartmessaging.main.MainContracts.message_SORT_DATE_OLDEST;
import static inc.elevati.smartmessaging.main.MainContracts.message_SORT_STARS_LESS;
import static inc.elevati.smartmessaging.main.MainContracts.message_SORT_STARS_MORE;

/** This fragment shows all completed messages retrieved from the database */
public class CompletedmessagesFragment extends Fragment implements MainContracts.messageListView, SwipeRefreshLayout.OnRefreshListener {

    /** The sorting criteria chosen */
    private static int sort_criteria;

    /** The RecyclerView adapter */
    private messagesAdapter messagesAdapter;

    /** The presenter associated to this view */
    private MainContracts.messageListPresenter presenter;

    /** Object used to refresh the message list */
    private SwipeRefreshLayout refresher;

    /**
     * Method called when the View associated to this fragment is created (the first time this
     * fragment is shown, at orientation changes, at activity re-creations...); here the layout
     * is inflated and all Views owned by this fragment are initialized. In addition
     * SharedPreferences are read to retrieve the preferred message sorting criteria
     * @param inflater the layout inflater
     * @param container this fragment parent view
     * @param savedInstanceState a Bundle containing saved data to be restored
     * @return the View initialized and inflated
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_messages_list, container, false);
        setHasOptionsMenu(true);

        // Swipe refresh
        refresher = v.findViewById(R.id.refresher);
        refresher.setOnRefreshListener(this);
        refresher.setColorSchemeResources(R.color.color_primary);

        // Presenter retrieval
        presenter = ((MainActivity) getActivity()).getPresenter().getCompletedmessagesPresenter();
        messagesAdapter = new messagesAdapter(this, presenter);

        // Shared preferences for message sorting criteria
        if (savedInstanceState == null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("IMC", Context.MODE_PRIVATE);
            sort_criteria = sharedPreferences.getInt("sort", message_SORT_DATE_NEWEST);
        }

        // RecyclerView
        RecyclerView recyclerView = v.findViewById(R.id.recycler_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(messagesAdapter);

        // Load my messages
        refresher.setRefreshing(true);
        presenter.loadmessages();
        return v;
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

    /**
     * Static method that returns an instance of this fragment
     * @return a NewmessageFragment instance
     */
    public static CompletedmessagesFragment newInstance() {
        return new CompletedmessagesFragment();
    }

    /** {@inheritDoc} */
    @Override
    public void updatemessages(List<message> messages) {
        messagesAdapter.updatemessages(messages, sort_criteria);
    }

    /**{@inheritDoc}*/
    @Override
    public void showmessageDialog(message message) {
        messageDialog dialog = messageDialog.newInstance(message, PAGE_COMPLETED);
        dialog.show(getFragmentManager(), null);
    }

    /**{@inheritDoc}*/
    @Override
    public void resetRefreshing() {
        refresher.setRefreshing(false);
    }

    /**{@inheritDoc}*/
    @Override
    public void onRefresh() {
        presenter.loadmessages();
    }

    /**
     * Method called when action bar is created. In this fragment the sort button is
     * retrieved and then is moved on the screen with an animation, and a listener
     * is attached to it to allow user to change the message sort criteria
     * @param menu the menu Object
     * @param inflater the layout inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Set a click listener on button created and animated in from Activity
        MenuItem sortButton = menu.findItem(R.id.bn_sort);
        sortButton.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dialog that prompts user the sort criteria selection
                final AppCompatDialog dialog = new AppCompatDialog(getContext());
                dialog.setTitle(R.string.sort_message_title);
                dialog.setContentView(R.layout.dialog_sort);

                // Set the button selected by default (based on previous choice)
                RadioGroup radioGroup = dialog.findViewById(R.id.radio_sort);
                int idChecked = R.id.bn_newest;
                switch (sort_criteria) {
                    case message_SORT_DATE_NEWEST:
                        idChecked = R.id.bn_newest;
                        break;
                    case message_SORT_DATE_OLDEST:
                        idChecked = R.id.bn_oldest;
                        break;
                    case message_SORT_STARS_MORE:
                        idChecked = R.id.bn_more_stars;
                        break;
                    case message_SORT_STARS_LESS:
                        idChecked = R.id.bn_less_stars;
                        break;
                }
                radioGroup.check(idChecked);

                // Listeners for dialog buttons
                dialog.findViewById(R.id.bn_newest).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if (sort_criteria != message_SORT_DATE_NEWEST) changeSortCriteria(message_SORT_DATE_NEWEST);
                    }
                });
                dialog.findViewById(R.id.bn_oldest).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if (sort_criteria != message_SORT_DATE_OLDEST) changeSortCriteria(message_SORT_DATE_OLDEST);
                    }
                });
                dialog.findViewById(R.id.bn_more_stars).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if (sort_criteria != message_SORT_STARS_MORE) changeSortCriteria(message_SORT_STARS_MORE);
                    }
                });
                dialog.findViewById(R.id.bn_less_stars).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if (sort_criteria != message_SORT_STARS_LESS) changeSortCriteria(message_SORT_STARS_LESS);
                    }
                });
                dialog.show();
            }
        });
    }

    /**
     * Tells the adapter the sort criteria for the messages and then saves it in SharedPreferences
     * @param sortCriteria the sort criteria chosen
     */
    private void changeSortCriteria(int sortCriteria) {
        sort_criteria = sortCriteria;
        messagesAdapter.sortmessages(sortCriteria);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("IMC", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("sort", sortCriteria).apply();
    }
}
