package inc.elevati.smartmessaging.viewmodel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import inc.elevati.smartmessaging.model.MessagesHandler;
import inc.elevati.smartmessaging.model.Message;
import inc.elevati.smartmessaging.model.User;

// ViewModel che si occupa della richiesta dei messaggi da visualizzare (si dovrà interfacciare con Firebase)
public class MessagesListViewModel extends ViewModel {

    private MutableLiveData<List<Message>> messages;

    private void initMessages() {
        if (messages == null) {
            messages = MessagesHandler.getInstance().fetchMessages();
        }
    }

    // Prende tutti i messaggi e li filtra per destinatario (per visualizzare solo i messaggi dell'utente)
    // Quando sarà interfacciato con Firebase sarebbe opportuno fare questa operazione a livello di DB e non qua
    public LiveData<List<Message>> getFilteredByReceiverList(String userID) {
        initMessages();
        return Transformations.map(messages, fullList -> {
            List<Message> filteredList = new ArrayList<>();
            for (Message message: fullList) {
                for (User user: message.getReceivers()) {
                    if (user.getName().equals(userID))
                        filteredList.add(message);
                }
            }
            return filteredList;
        });
    }

    public void refreshList() {
        MessagesHandler.getInstance().refreshMessages();
    }

    // Finta eliminazione perchè viene ricreato quando si aggiorna
    public void deleteMessage(Message message) {
        MessagesHandler.getInstance().deleteMessage(message);
    }
}
