package inc.elevati.smartmessaging.viewmodel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import inc.elevati.smartmessaging.model.FilterOptions;
import inc.elevati.smartmessaging.model.FirebaseFirestoreHelper;
import inc.elevati.smartmessaging.model.Message;

// ViewModel che si occupa della richiesta dei messaggi da visualizzare (si dovrà interfacciare con Firebase)
public class MessagesListViewModel extends ViewModel {

    private LiveData<List<Message>> inboxMessages;
    private MutableLiveData<FilterOptions> filterOptions;
    private String userID;

    private void initMessages() {
        if (inboxMessages == null) {
            inboxMessages = FirebaseFirestoreHelper.getInstance().fetchMessages(userID);
        }
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LiveData<List<Message>> getFilteredMessages() {
        initMessages();

        // switchMap fa in modo di modificare il LiveData ritornato ogni volta che il contenuto di filterOptions viene cambiato
        // in questo modo la funzione interna è chiamata con la variabile options sempre diversa e torna la lista giusta
        return Transformations.switchMap(filterOptions, options -> Transformations.map(inboxMessages, messagesList -> {
            List<Message> filteredList = new ArrayList<>();
            for (Message message: messagesList) {
                int priority = message.getPriority();
                if (priority >= options.getMinPriority() && priority <= options.getMaxPriority()) {
                    boolean CC = message.isCC();
                    if (options.isShowGroupMessages() || options.isShowSingleMessages())
                        if (CC == options.isShowGroupMessages() || !CC == options.isShowSingleMessages())
                            filteredList.add(message);
                }
            }
            return filteredList;
        }));
    }

    // Chiamando questa funzione si modifica il contenuto di filterOptions e quindi viene
    // aggiornato automaticamente il LiveData contenente i messaggi da visualizzare
    public void setFilterOptions(int minPriority, int maxPriority, boolean showSingleMessages, boolean showGroupMessages) {
        if (filterOptions == null)
            filterOptions = new MutableLiveData<>();

        FilterOptions newFilterOptions = new FilterOptions(minPriority, maxPriority, showSingleMessages, showGroupMessages);
        filterOptions.setValue(newFilterOptions);
    }

    public LiveData<FilterOptions> getFilterOptions() {
        return filterOptions;
    }

    public void refreshList() {
        FirebaseFirestoreHelper.getInstance().refreshMessages(userID);
    }
}
