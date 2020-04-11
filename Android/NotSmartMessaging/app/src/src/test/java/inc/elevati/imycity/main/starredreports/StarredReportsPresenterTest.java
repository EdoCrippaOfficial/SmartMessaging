package inc.elevati.smartmessaging.main.starredmessages;

import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;
import inc.elevati.smartmessaging.firebase.FirestoreHelper;
import inc.elevati.smartmessaging.main.MainContracts;
import inc.elevati.smartmessaging.main.messageDialog;
import inc.elevati.smartmessaging.utils.message;

import static inc.elevati.smartmessaging.main.MainContracts.DeletemessageTaskResult.RESULT_FAILED;
import static inc.elevati.smartmessaging.main.MainContracts.DeletemessageTaskResult.RESULT_OK;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StarredmessagesPresenter.class, FirebaseAuthHelper.class})
public class StarredmessagesPresenterTest {

    @Mock
    private MainContracts.messageListView view;

    @Mock
    private FirestoreHelper firestoreHelper;

    private String uid;

    private StarredmessagesPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(StarredmessagesFragment.class);
        presenter = new StarredmessagesPresenter();
        presenter.attachView(view);
        firestoreHelper = mock(FirestoreHelper.class);
        whenNew(FirestoreHelper.class).withArguments(presenter).thenReturn(firestoreHelper);

        // Mock this static method used inside the tested code
        uid = "dummy";
        mockStatic(FirebaseAuthHelper.class);
        when(FirebaseAuthHelper.getUserId()).thenReturn(uid);
    }

    @Test
    public void loadmessagesTest() {
        presenter.loadmessages();
        verify(firestoreHelper).readStarredmessages(uid);
    }

    @Test
    public void onLoadmessagesTaskCompleteTest() {
        presenter.onLoadmessagesTaskComplete(any(QuerySnapshot.class));
        verify(view).updatemessages(anyListOf(message.class));
    }

    @Test
    public void onUpdateTaskCompleteTest(){
        presenter.onUpdateTaskComplete();
        verify(view).resetRefreshing();
    }

    @Test
    public void showmessageTest(){
        message message = mock(message.class);
        presenter.onmessageClicked(message);
        verify(view).showmessageDialog(message);
    }

    @Test
    public void starsButtonClickedTest() {

        // Not mocked because we want to test the true and false cases of "starred" field
        message message = new message(null, null, null, 0, null, null, null);
        message.setStarred(true);
        presenter.onStarsButtonClicked(message);

        // Check if unstarmessage method is called, as message was starred
        verify(firestoreHelper).unstarmessage(message, uid);
        message.setStarred(false);
        presenter.onStarsButtonClicked(message);

        // Check if starmessage method is called, as message was not starred
        verify(firestoreHelper).starmessage(message, uid);
    }

    @Test
    public void onStarOperationCompleteTest() {
        presenter.onStarTaskComplete();
        verify(firestoreHelper).readStarredmessages(uid);
    }

    @Test
    public void onDeletemessageButtonClickedTest() {

        // Create a mock messageDialogView
        MainContracts.messageDialogView dialogView = mock(messageDialog.class);
        presenter.attachmessageDialogView(dialogView);
        message message = mock(message.class);
        presenter.onDeletemessageButtonClicked(message);

        // Verify methods call
        verify(dialogView).showProgressDialog();
        verify(firestoreHelper).deletemessage(message.getId());
    }

    @Test
    public void onDeletemessageTaskCompleteTest() {

        // Create a mock messageDialogView
        MainContracts.messageDialogView dialogView = mock(messageDialog.class);
        presenter.attachmessageDialogView(dialogView);

        // Set result to RESULT_OK
        presenter.onDeletemessageTaskComplete(RESULT_OK);

        // Verify methods calls
        verify(dialogView).dismissDialog();

        // Set result to RESULT_FAILED
        presenter.onDeletemessageTaskComplete(RESULT_FAILED);

        // Verify methods calls
        verify(dialogView, times(2)).dismissProgressDialog();
        verify(dialogView).notifyDeletemessageError();
    }
}