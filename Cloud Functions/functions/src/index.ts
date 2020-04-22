const functions = require('firebase-functions');
const admin = require('firebase-admin');

// Firebase app initialization
admin.initializeApp();


exports.deleteMessage = functions.firestore.document('messages/{messageId}').onUpdate((change, context) => {

	const visualizing = change.after.data().visualizing;

	// Delete message if no one is visualizig it
	if (visualizing.length === 0) {
		return change.after.ref.delete();
	}

	// Otherwise do nothing
	return null;
});


exports.updateReceived = functions.firestore.document('messages/{messageId}').onCreate((snapshot, context) => {

	const visualizing = snapshot.data().visualizing;
	const usersRef = admin.firestore().collection('users');

	// Promises to return
	const promises = [];

	// Check each receiver and increase 'received' value in 'users' collection
    visualizing.forEach(function(id) {

    	// Read document reference
		promises.push(usersRef.doc(id).get().then(snapshot => {

			// Previous received count
			let receivedCount = snapshot.data().received;
			if (!receivedCount) {
          		receivedCount = 0;
        	}

	    	// Promise that increases 'received' value
	      	return snapshot.ref.set({received: receivedCount+1}, {merge: true});
		}));
    });

    return Promise.all(promises)
});