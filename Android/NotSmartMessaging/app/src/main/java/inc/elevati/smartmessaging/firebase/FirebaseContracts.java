package inc.elevati.smartmessaging.firebase;


import inc.elevati.smartmessaging.utils.Message;

/** Generic interface that defines contracts for firebase package */
public interface FirebaseContracts {

    /**
     * Implemented by class that handles Firebase Auth tasks
     */
    interface AuthHelper {

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

        void checkToken();
    }

    interface FirestoreHelper {

        void fetchMessages(String userID);

        void deleteMessage(Message message, String userID);
    }
}
