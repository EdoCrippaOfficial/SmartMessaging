package inc.elevati.smartmessaging.main.allmessages;

import android.graphics.ColorFilter;
import android.graphics.PorterDuffColorFilter;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;
import inc.elevati.smartmessaging.main.MainActivity;
import inc.elevati.smartmessaging.main.messagesAdapter;
import inc.elevati.smartmessaging.utils.EspressoIdlingResource;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static inc.elevati.smartmessaging.CustomActions.clickChildViewWithId;
import static inc.elevati.smartmessaging.CustomMatchers.atPosition;
import static inc.elevati.smartmessaging.CustomMatchers.hasItems;
import static inc.elevati.smartmessaging.CustomMatchers.withColorFilter;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AllmessagesViewAndroidTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws InterruptedException {

        // Reset IdlingResource
        EspressoIdlingResource.reset();

        // Register IdlingResource
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());

        Thread.sleep(500);
    }

    @Test
    public void sortDialogTest() {

        // Click on sort button
        onView(withId(R.id.bn_sort)).perform(click());

        // Check if dialog is displayed by checking one of its contents
        onView(withText(R.string.sort_message_title)).inRoot(isDialog()).check(matches(isDisplayed()));

        // Newest first
        onView(withId(R.id.bn_newest)).perform(click());

        // Get first and last message timestamp for validation
        RecyclerView recyclerView = intentsRule.getActivity().findViewById(R.id.recycler_messages);
        messagesAdapter adapter = (messagesAdapter) recyclerView.getAdapter();
        assert adapter != null;
        long time1 = adapter.getItemAt(0).getTimestamp();
        int last = adapter.getItemCount() - 1;
        long time2 = adapter.getItemAt(last).getTimestamp();
        assertTrue(time1 > time2);

        // Click again on sort button
        onView(withId(R.id.bn_sort)).perform(click());

        // Oldest first
        onView(withId(R.id.bn_oldest)).perform(click());

        // Get first and last message timestamp for validation
        time1 = adapter.getItemAt(0).getTimestamp();
        last = adapter.getItemCount() - 1;
        time2 = adapter.getItemAt(last).getTimestamp();
        assertTrue(time1 < time2);
    }

    @Test
    public void loadmessagesTest() {

        // Update list
        onView(allOf(isDisplayed(), withId(R.id.refresher))).perform(swipeDown());

        // Check if list is not empty
        onView(allOf(isDisplayed(), withId(R.id.recycler_messages))).check(matches(hasItems()));
    }

    @Test
    public void messageDialogTest() {

        // Click on first message
        onView(allOf(isDisplayed(), withId(R.id.recycler_messages))).perform(actionOnItemAtPosition(0, click()));

        // Get first message title for validation
        RecyclerView recyclerView = intentsRule.getActivity().findViewById(R.id.recycler_messages);
        messagesAdapter adapter = (messagesAdapter) recyclerView.getAdapter();
        assert adapter != null;
        String title = adapter.getItemAt(0).getTitle();

        // Check if the right title is displayed in dialog
        onView(withText(title)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void starmessageTest() {
        onView(allOf(isDisplayed(), withId(R.id.refresher))).perform(swipeDown());

        // Get before state
        RecyclerView recyclerView = intentsRule.getActivity().findViewById(R.id.recycler_messages);
        messagesAdapter adapter = (messagesAdapter) recyclerView.getAdapter();
        assert adapter != null;
        boolean beforeStarred = adapter.getItemAt(0).isStarred();
        int beforeNumber = adapter.getItemAt(0).getnStars();

        // Click on first message star button
        onView(allOf(isDisplayed(), withId(R.id.recycler_messages))).perform(actionOnItemAtPosition(0, clickChildViewWithId(R.id.bn_stars)));

        // Check if ui is correct
        String expectedText;
        ColorFilter expectedColorFilter;
        if (beforeStarred) {
            expectedText = Integer.toString(beforeNumber - 1);
            int expectedColor = ContextCompat.getColor(intentsRule.getActivity(), R.color.light_grey);
            expectedColorFilter = new PorterDuffColorFilter(expectedColor, android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            expectedText = Integer.toString(beforeNumber + 1);
            int expectedColor = ContextCompat.getColor(intentsRule.getActivity(), R.color.gold);
            expectedColorFilter = new PorterDuffColorFilter(expectedColor, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        onView(allOf(isDisplayed(), withId(R.id.recycler_messages))).check(matches(atPosition(0, hasDescendant(withText(expectedText)))));
        onView(allOf(isDisplayed(), withId(R.id.recycler_messages))).check(matches(atPosition(0, hasDescendant(withColorFilter(expectedColorFilter)))));
    }

    @Test
    public void deletemessageTest() {

        // Click on first message
        onView(allOf(isDisplayed(), withId(R.id.recycler_messages))).perform(actionOnItemAtPosition(0, click()));

        // Get first message user id
        RecyclerView recyclerView = intentsRule.getActivity().findViewById(R.id.recycler_messages);
        messagesAdapter adapter = (messagesAdapter) recyclerView.getAdapter();
        assert adapter != null;
        String messageId = adapter.getItemAt(0).getId();
        String uid = adapter.getItemAt(0).getUserId();
        String currentUid = FirebaseAuthHelper.getUserId();

        // Check if we can delete the message
        if (currentUid.equals(uid)) {
            onView(withId(R.id.bn_delete)).perform(click());
            onView(withId(R.id.bn_delete_yes)).perform(click());

            // Update list to be sure
            onView(allOf(isDisplayed(), withId(R.id.refresher))).perform(swipeDown());

            // Check if first message is not the deleted one anymore
            String newId = adapter.getItemAt(0).getId();
            assert !(newId.equals(messageId));
        } else {

            // Check if delete button is not displayed
            onView(withId(R.id.bn_delete)).check(matches(not(isDisplayed())));
        }
    }
}
