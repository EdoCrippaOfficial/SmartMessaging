package inc.elevati.smartmessaging.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {

    private String id, title, body, imageUrl, receivers;
    private int priority;
    private long timestamp;
    private boolean isCC;

    public Message(String id, String title, String body, String imageUrl, int priority, String receivers, long timestamp, boolean isCC) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.priority = priority;
        this.receivers = receivers;
        this.timestamp = timestamp;
        this.isCC = isCC;
    }

    /**
     * Constructor required by Parcelable interface
     * @param in the Parcel object that represents this report
     */
    private Message(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.body = in.readString();
        this.imageUrl = in.readString();
        this.priority = in.readInt();
        this.receivers = in.readString();
        this.timestamp = in.readLong();
        this.isCC = in.readInt() == 1;
    }

    public String getId() {
        return id;
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

    public String getReceivers() {
        return receivers;
    }

    public int getPriority() {
        return priority;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isCC() {
        return isCC;
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
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(imageUrl);
        dest.writeInt(priority);
        dest.writeString(receivers);
        dest.writeLong(timestamp);
        dest.writeInt(isCC ? 1 : 0);
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
