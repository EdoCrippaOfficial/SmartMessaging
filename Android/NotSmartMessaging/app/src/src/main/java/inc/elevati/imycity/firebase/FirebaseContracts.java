package inc.elevati.smartmessaging.firebase;

import android.content.Context;
import android.net.Uri;

import inc.elevati.smartmessaging.utils.message;

import static inc.elevati.smartmessaging.utils.message.*;


/** Generic interface that defines contracts for firebase package */
public interface FirebaseContracts {

    /**
     * Implemented by class that handles Firebase Storage tasks
     */
    interface StorageWriter {

        /**
         * Sends a new image to the storage
         * @param message the message related to the image
         * @param appContext the application context, needed to load and compress the image
         * @param imageUri the image Uri path
         */
        void sendImage(final message message, Context appContext, Uri imageUri);
    }

    /**
     * Implemented by class that handles Firebase Firestore reading tasks
     */
    interface DatabaseReader {

        /**
         * Read all the messages in database
         */
        void readAllmessages();

        /**
         * Reads the messages created by the specified user
         * @param userId the user id
         */
        void readMymessages(String userId);

        /**
         * Reads the messages starred by the specified user
         * @param userId the user id
         */
        void readStarredmessages(String userId);

        /**
         * Reads the messages with status {@link Status#STATUS_COMPLETED}
         */
        void readCompletedmessages();
    }

    /**
     * Implemented by class that handles Firebase Firestore writing tasks
     */
    interface DatabaseWriter {

        /**
         * Deletes the message with the specified id
         * @param messageId the id of the message to delete
         */
        void deletemessage(String messageId);

        /**
         * Creates a new message record in database
         * @param message the message to send
         */
        void sendmessage(final message message);

        /**
         * Adds the specified user to the list that starred the specified message
         * @param message the message to star
         * @param userId the id of the user who starred the message
         */
        void starmessage(final message message, final String userId);

        /**
         * Removes the specified user to the list that starred the specified message
         * @param message the message to un-star
         * @param userId the id of the user who un-starred the message
         */
        void unstarmessage(final message message, final String userId);
    }

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
}
