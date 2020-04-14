package inc.elevati.smartmessaging.model;

import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import inc.elevati.smartmessaging.R;

public class Message {

    private String id, title, body, imageUrl;
    private List<String> receivers;
    private int priority;
    private long timestamp;
    private boolean isToday, isThisYear, isCC;

    public Message(String id, String title, String body, String imageUrl, int priority, List<String> receivers, long timestamp, boolean isCC) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.priority = priority;
        this.receivers = receivers;
        this.timestamp = timestamp;
        this.isCC = isCC;

        // Controllo se la data è di oggi o in quest'anno per cambiare la visualizzazione
        this.isToday = DateUtils.isToday(timestamp);
        Time time = new Time();
        time.set(timestamp);
        int thenYear = time.year;
        time.set(System.currentTimeMillis());
        this.isThisYear = (thenYear == time.year);
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

    public List<String> getReceivers() {
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

    public boolean isToday() {
        return isToday;
    }

    public boolean isThisYear() {
        return isThisYear;
    }

    // Metodo per caricare l'immagine. Settato nel xml del layout e chiamato via data binding
    @BindingAdapter("image")
    public static void loadImage(final ImageView imageView, String imageUrl) {
        if (imageUrl != null && imageUrl.length() > 0) {
            imageView.setVisibility(View.VISIBLE);
            CircularProgressDrawable progressDrawable = new CircularProgressDrawable(imageView.getContext());
            progressDrawable.setStrokeWidth(3f);
            progressDrawable.setCenterRadius(20f);
            progressDrawable.start();
            Glide.with(imageView.getContext()).load(imageUrl).placeholder(progressDrawable).error(R.drawable.ic_no_image).into(imageView);
        } else {
            imageView.setImageDrawable(null);
            imageView.setVisibility(View.GONE);
        }
    }
}
