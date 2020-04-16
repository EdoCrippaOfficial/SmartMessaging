package inc.elevati.smartmessaging.model;

import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.StateSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.model.utils.Colors;

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

    // Metodo per settare lo sfondo del messaggio col colore giusto e cambiando colore al touch
    @BindingAdapter("android:background")
    public static void setBackground(final LinearLayout layout, int priority) {
        int[] attrs = {R.attr.colorPrimary, Colors.getColorByPriority(priority)};
        TypedArray typedArray = layout.getContext().obtainStyledAttributes(attrs);
        int primaryColor = typedArray.getColor(0, ContextCompat.getColor(layout.getContext(), R.color.colorPrimaryLight));
        int priorityColor = typedArray.getColor(1, ContextCompat.getColor(layout.getContext(), R.color.colorPriorityLight1));
        typedArray.recycle();

        GradientDrawable pressedDrawable = new GradientDrawable();
        pressedDrawable.setColor(primaryColor);

        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setColor(priorityColor);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(StateSet.WILD_CARD, defaultDrawable);

        layout.setBackground(stateListDrawable);
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
