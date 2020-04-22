package inc.elevati.smartmessaging.firebase;


import inc.elevati.smartmessaging.login.LoginContracts;
import inc.elevati.smartmessaging.main.MainContracts;
import inc.elevati.smartmessaging.utils.Message;

/** Generic interface that defines contracts for firebase package */
public interface FirebaseContracts {

    /**
     * Implemented by class that handles Firebase Auth tasks
     */
    interface AuthHelper {

        void setPresenter(LoginContracts.SignInPresenter presenter);

        void setPresenter(LoginContracts.RegisterPresenter presenter);

        /**
         * Registers a new user in the Firebase Auth system
         * @param name the user displayed name
         * @param email the user email
         * @param password the user password
         */
        void register(final String name, String email, String password);

        /**
         * Makes a sign-in request to Firebase Auth system with specified data
         * @param email the user email
         * @param password the user password
         */
        void signIn(String email, String password);
    }

    interface MessagingHelper {

        void setPresenter(MainContracts.MainPresenter presenter);

        void checkToken();
    }

    interface FirestoreHelper {

        void setPresenter(MainContracts.MainPresenter presenter);

        void fetchMessages(String userID);

        void deleteMessage(Message message, String userID);
    }
}
