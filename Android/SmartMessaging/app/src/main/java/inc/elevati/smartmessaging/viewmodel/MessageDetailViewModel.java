package inc.elevati.smartmessaging.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import inc.elevati.smartmessaging.model.Message;

public class MessageDetailViewModel extends ViewModel {

    private MutableLiveData<Message> message;

    public LiveData<Message> getMessage() {
        if (message == null) {
            message = new MutableLiveData<>();
        }
        return message;
    }

    public void setMessage(Message m) {
        if (message == null) {
            message = new MutableLiveData<>();
        }
        message.setValue(m);
    }
}
