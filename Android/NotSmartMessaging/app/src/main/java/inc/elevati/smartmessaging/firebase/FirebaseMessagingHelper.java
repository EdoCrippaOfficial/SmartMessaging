package inc.elevati.smartmessaging.firebase;

import com.google.firebase.iid.FirebaseInstanceId;

import inc.elevati.smartmessaging.main.MainContracts;

public class FirebaseMessagingHelper implements FirebaseContracts.MessagingHelper {

    private MainContracts.MainPresenter presenter;

    public FirebaseMessagingHelper(MainContracts.MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void checkToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                presenter.onCheckTokenResult(task.getResult().getToken());
            } else {
                presenter.onCheckTokenResult(null);
            }
        });
    }
}
