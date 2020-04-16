package inc.elevati.smartmessaging.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import inc.elevati.smartmessaging.model.Message;
import inc.elevati.smartmessaging.model.firebase.FirebaseFirestoreHelper;

// ViewModel che si occupa semplicemente di tenere salvato il messaggio da visualizzare nel dialog mentre questo viene creato
public class MessageHandlerViewModel extends ViewModel {

    public final static int DELETE_SUCCESS = FirebaseFirestoreHelper.DELETE_SUCCESS;
    public final static int DELETE_FAILED = FirebaseFirestoreHelper.DELETE_FAILED;
    private final static int DELETE_NONE = 3;

    private String userID;
    private MutableLiveData<Message> message;
    private MutableLiveData<Integer> deleteResult;

    public LiveData<Message> getMessage() {
        if (message == null) {
            message = new MutableLiveData<>();
        }
        return message;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setMessage(Message m) {
        if (message == null) {
            message = new MutableLiveData<>();
        }
        message.setValue(m);
    }

    public LiveData<Integer> deleteMessage(Message message) {
        deleteResult = FirebaseFirestoreHelper.getInstance().deleteMessage(message, userID);
        return deleteResult;
    }

    public LiveData<Integer> requestDeleteResult() {
        if (deleteResult == null) {
            deleteResult = new MutableLiveData<>();
            deleteResult.setValue(DELETE_NONE);
        }
        return deleteResult;
    }
}
