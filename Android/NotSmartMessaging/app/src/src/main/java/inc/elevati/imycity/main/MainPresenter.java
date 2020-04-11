package inc.elevati.smartmessaging.main;

import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.main.allmessages.AllmessagesFragment;
import inc.elevati.smartmessaging.main.allmessages.AllmessagesPresenter;
import inc.elevati.smartmessaging.main.completedmessages.CompletedmessagesFragment;
import inc.elevati.smartmessaging.main.completedmessages.CompletedmessagesPresenter;
import inc.elevati.smartmessaging.main.mymessages.MymessagesFragment;
import inc.elevati.smartmessaging.main.mymessages.MymessagesPresenter;
import inc.elevati.smartmessaging.main.newmessage.NewmessageFragment;
import inc.elevati.smartmessaging.main.newmessage.NewmessagePresenter;
import inc.elevati.smartmessaging.main.starredmessages.StarredmessagesFragment;
import inc.elevati.smartmessaging.main.starredmessages.StarredmessagesPresenter;
import inc.elevati.smartmessaging.utils.MvpContracts;
import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;

/** Presenter associated to {@link MainActivity} */
public class MainPresenter implements MainContracts.MainPresenter {

    /** The view associated to this presenter */
    private MainContracts.MainView view;

    /** The presenter associated to child view {@link NewmessageFragment} */
    private MainContracts.NewmessagePresenter newmessagePresenter;

    /** The presenter associated to child view {@link AllmessagesFragment} */
    private MainContracts.messageListPresenter allmessagesPresenter;

    /** The presenter associated to child view {@link MymessagesFragment} */
    private MainContracts.messageListPresenter mymessagesPresenter;

    /** The presenter associated to child view {@link CompletedmessagesFragment} */
    private MainContracts.messageListPresenter completedmessagesPresenter;

    /** The presenter associated to child view {@link StarredmessagesFragment} */
    private MainContracts.messageListPresenter starredmessagesPresenter;

    /**
     * Constructor where children presenters are instantiated too
     */
    MainPresenter() {
        this.allmessagesPresenter = new AllmessagesPresenter();
        this.newmessagePresenter = new NewmessagePresenter();
        this.mymessagesPresenter = new MymessagesPresenter();
        this.starredmessagesPresenter = new StarredmessagesPresenter();
        this.completedmessagesPresenter = new CompletedmessagesPresenter();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(MvpContracts.MvpView view) {
        this.view = (MainContracts.MainView) view;
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        this.view = null;
    }

    /** {@inheritDoc} */
    @Override
    public MainContracts.NewmessagePresenter getNewmessagePresenter() {
        return newmessagePresenter;
    }

    /** {@inheritDoc} */
    @Override
    public MainContracts.messageListPresenter getAllmessagesPresenter() {
        return allmessagesPresenter;
    }

    /** {@inheritDoc} */
    @Override
    public MainContracts.messageListPresenter getCompletedmessagesPresenter() {
        return completedmessagesPresenter;
    }

    /** {@inheritDoc} */
    @Override
    public MainContracts.messageListPresenter getMymessagesPresenter() {
        return mymessagesPresenter;
    }

    /** {@inheritDoc} */
    @Override
    public MainContracts.messageListPresenter getStarredmessagesPresenter() {
        return starredmessagesPresenter;
    }

    /** {@inheritDoc} */
    @Override
    public void onMenuItemClicked(int itemId) {
        switch (itemId) {
            case R.id.menu_new:
                view.scrollToPage(MainContracts.PAGE_NEW);
                break;
            case R.id.menu_all:
                view.scrollToPage(MainContracts.PAGE_ALL);
                break;
            case R.id.menu_my:
                view.scrollToPage(MainContracts.PAGE_MY);
                break;
            case R.id.menu_starred:
                view.scrollToPage(MainContracts.PAGE_STARRED);
                break;
            case R.id.menu_completed:
                view.scrollToPage(MainContracts.PAGE_COMPLETED);
                break;
            case R.id.menu_logout:
                FirebaseAuthHelper.signOut();
                view.startLoginActivity();
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onPageScrolled(int page) {
        switch (page) {
            case MainContracts.PAGE_NEW:
                view.setCheckedMenuItem(R.id.menu_new);
                break;
            case MainContracts.PAGE_ALL:
                view.setCheckedMenuItem(R.id.menu_all);
                break;
            case MainContracts.PAGE_MY:
                view.setCheckedMenuItem(R.id.menu_my);
                break;
            case MainContracts.PAGE_STARRED:
                view.setCheckedMenuItem(R.id.menu_starred);
                break;
            case MainContracts.PAGE_COMPLETED:
                view.setCheckedMenuItem(R.id.menu_completed);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getCurrentUserEmail() {
        return FirebaseAuthHelper.getUserEmail();
    }

    /** {@inheritDoc} */
    @Override
    public String getCurrentUserName() {
        return FirebaseAuthHelper.getUserName();
    }
}
