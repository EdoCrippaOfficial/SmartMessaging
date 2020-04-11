package inc.elevati.smartmessaging.main.newmessage;

import android.content.Context;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;
import inc.elevati.smartmessaging.firebase.FirestoreHelper;
import inc.elevati.smartmessaging.firebase.StorageHelper;
import inc.elevati.smartmessaging.main.MainContracts;
import inc.elevati.smartmessaging.utils.message;

import static inc.elevati.smartmessaging.main.MainContracts.SendTaskResult.RESULT_SEND_OK;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NewmessagePresenter.class, FirebaseAuthHelper.class})
public class NewmessagePresenterTest {

    @Mock
    private MainContracts.NewmessageView view;

    private NewmessagePresenter presenter;

    @Before
    public void setUp() {
        view = mock(NewmessageFragment.class);
        presenter = new NewmessagePresenter();
        presenter.attachView(view);
    }

    @Test
    public void sendButtonClickedTest() throws Exception {
        StorageHelper helper = mock(StorageHelper.class);
        whenNew(StorageHelper.class).withArguments(presenter).thenReturn(helper);
        message message = mock(message.class);
        whenNew(message.class).withAnyArguments().thenReturn(message);
        Context appContext = mock(Context.class);
        Uri imageUri = mock(Uri.class);

        // Mock this static method used inside the tested code
        mockStatic(FirebaseAuthHelper.class);
        when(FirebaseAuthHelper.getUserName()).thenReturn(null);

        // Call the function to test with no image
        presenter.onSendButtonClicked("", "", appContext, null, null);
        verify(view).notifyInvalidImage();

        // Call the function with image and no title
        presenter.onSendButtonClicked("", "", appContext, imageUri, null);
        verify(view).notifyInvalidTitle();

        // Call the function with image, title and no description
        presenter.onSendButtonClicked("Title", "", appContext, imageUri, null);
        verify(view).notifyInvalidDescription();

        // Call the function correctly (note that position is optional)
        presenter.onSendButtonClicked("Title", "Description", appContext, imageUri, null);
        verify(view).showProgressDialog();
        verify(helper).sendImage(message, appContext, imageUri);
    }

    @Test
    public void sendmessageDataTest() throws Exception {
        FirestoreHelper helper = PowerMockito.mock(FirestoreHelper.class);
        whenNew(FirestoreHelper.class).withArguments(presenter).thenReturn(helper);
        message message = mock(message.class);
        presenter.sendmessageData(message);
        verify(helper).sendmessage(message);
    }

    @Test
    public void onSendTaskCompleteTest() {
        MainContracts.SendTaskResult resultCode = RESULT_SEND_OK;
        presenter.onSendTaskComplete(resultCode);
        verify(view).dismissProgressDialog();
        verify(view).notifySendTaskCompleted(resultCode);
    }
}
