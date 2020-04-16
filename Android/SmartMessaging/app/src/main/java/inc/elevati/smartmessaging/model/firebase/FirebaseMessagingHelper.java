package inc.elevati.smartmessaging.model.firebase;

import com.google.firebase.iid.FirebaseInstanceId;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FirebaseMessagingHelper {

    private static FirebaseMessagingHelper instance;

    public static FirebaseMessagingHelper getInstance() {
        if (instance == null)
            instance = new FirebaseMessagingHelper();
        return instance;
    }

    public LiveData<String> checkToken() {
        MutableLiveData<String> token = new MutableLiveData<>();
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                token.setValue(task.getResult().getToken());
            } else {
                token.setValue(null);
            }
        });
        return token;
    }
}
