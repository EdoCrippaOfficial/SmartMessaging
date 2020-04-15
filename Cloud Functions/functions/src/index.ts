const functions = require('firebase-functions');
const admin = require('firebase-admin');

// Firebase app initialization
admin.initializeApp();

exports.deleteMessage = functions.firestore.document('messages/{messageId}').onUpdate((change, context) => {

	const message = change.after.data();

	const visualizing = message.visualizing;

	// Delete message if no one is visualizig it
	if (visualizing.length === 0) {
		return change.after.ref.delete();
	}

	// Otherwise do nothing
	return null;
});