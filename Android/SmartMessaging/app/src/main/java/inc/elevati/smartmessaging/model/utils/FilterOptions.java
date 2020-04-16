package inc.elevati.smartmessaging.model.utils;

public class FilterOptions {

    private int minPriority, maxPriority;
    private boolean showSingleMessages, showGroupMessages;

    public FilterOptions(int minPriority, int maxPriority, boolean showSingleMessages, boolean showGroupMessages) {
        this.minPriority = minPriority;
        this.maxPriority = maxPriority;
        this.showSingleMessages = showSingleMessages;
        this.showGroupMessages = showGroupMessages;
    }

    public int getMinPriority() {
        return minPriority;
    }

    public int getMaxPriority() {
        return maxPriority;
    }

    public boolean isShowSingleMessages() {
        return showSingleMessages;
    }

    public boolean isShowGroupMessages() {
        return showGroupMessages;
    }
}
