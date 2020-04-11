package inc.elevati.smartmessaging.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.text.format.Time;

import java.util.ArrayList;
import java.util.List;

public class Message implements Parcelable {

    private String title, body, imageUrl;
    private List<String> receivers;
    private int priority;
    private long timestamp;
    private boolean isToday, isThisYear;

    public Message(String title, String body, String imageUrl, int priority, List<String> receivers, long timestamp) {
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.priority = priority;
        this.receivers = receivers;
        this.timestamp = timestamp;
        this.isToday = DateUtils.isToday(timestamp);
        Time time = new Time();
        time.set(timestamp);
        int thenYear = time.year;
        time.set(System.currentTimeMillis());
        this.isThisYear = (thenYear == time.year);
    }

    /**
     * Constructor required by Parcelable interface
     * @param in the Parcel object that represents this report
     */
    private Message(Parcel in) {
        this.title = in.readString();
        this.body = in.readString();
        this.imageUrl = in.readString();
        this.priority = in.readInt();
        int nReceivers = in.readInt();
        this.receivers = new ArrayList<>();
        for (int i = 0; i < nReceivers; i++) {
            receivers.add(in.readString());
        }
        this.timestamp = in.readLong();
        this.isToday = DateUtils.isToday(timestamp);
        Time time = new Time();
        time.set(timestamp);
        int thenYear = time.year;
        time.set(System.currentTimeMillis());
        this.isThisYear = (thenYear == time.year);
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public int getPriority() {
        return priority;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isToday() {
        return isToday;
    }

    public boolean isThisYear() {
        return isThisYear;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method required by Parcelable interface
     * @param dest the Parcel object to be written
     * @param flags write flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(imageUrl);
        dest.writeInt(priority);
        dest.writeInt(receivers.size());
        for (String r: receivers)
            dest.writeString(r);
        dest.writeLong(timestamp);
    }

    /** Required by Parcelable interface */
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
