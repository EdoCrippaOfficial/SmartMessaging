package inc.elevati.smartmessaging.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.utils.Message;
import inc.elevati.smartmessaging.utils.Utils;

import static inc.elevati.smartmessaging.main.MainContracts.SORT_DATE_NEWEST;
import static inc.elevati.smartmessaging.main.MainContracts.SORT_DATE_OLDEST;
import static inc.elevati.smartmessaging.main.MainContracts.SORT_PRIORITY_HIGH;
import static inc.elevati.smartmessaging.main.MainContracts.SORT_PRIORITY_LOW;

/** Adapter class that organizes message data to show it in a RecyclerView hosted in AllmessagesFragment */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    /** The context reference */
    private Context context;

    /** The message list to be shown */
    private List<Message> messages;

    /** The presenter that allows communication with the view */
    private MainContracts.MainPresenter presenter;

    private int order = SORT_DATE_NEWEST;

    /** Class that holds the required View objects in a layout */
    static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout message_layout;
        TextView tv_title, tv_body, tv_date;
        ImageView iv_image;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.message_layout = itemView.findViewById(R.id.message_layout);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_body = itemView.findViewById(R.id.tv_body);
            this.iv_image = itemView.findViewById(R.id.iv_image);
            this.tv_date = itemView.findViewById(R.id.tv_date);
        }
    }

    MessagesAdapter(Context context, MainContracts.MainPresenter presenter) {
        this.messages = new ArrayList<>();
        this.context = context;
        this.presenter = presenter;
    }

    /**
     * Called when a single item holder in RecyclerView is created, here
     * click listener is attached to ViewHolder
     * @param parent the parent View
     * @param viewType the view type
     * @return the ViewHolder created
     */
    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onMessageClicked(messages.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    /**
     * Method called when a message is bound to ViewHolder, here all Views are initialized
     * @param holder the ViewHolder
     * @param position the message position in the list
     */
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Message message = messages.get(position);

        // Background color
        Utils.setBackgroundColor(holder.message_layout, message.getPriority());
        holder.tv_title.setText(message.getTitle());
        holder.tv_body.setText(message.getBody());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);
        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(context);
        Date date = new Date(message.getTimestamp());
        String completeDate = dateFormat.format(date) + ", " + timeFormat.format(date);
        holder.tv_date.setText(completeDate);
        if (message.getImageUrl().length() > 0) {
            holder.iv_image.setVisibility(View.VISIBLE);
            CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
            progressDrawable.setStrokeWidth(3f);
            progressDrawable.setCenterRadius(20f);
            progressDrawable.start();

            // Image loading from cloud storage with Glide
            Glide.with(context).load(message.getImageUrl()).placeholder(progressDrawable).error(R.drawable.ic_no_image).into(holder.iv_image);
        } else {
            holder.iv_image.setImageDrawable(null);
            holder.iv_image.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Method called to change the messages order in the list
     * @param order the requested order
     */
    void sortMessages(int order) {
        this.order = order;
        switch (order) {
            case SORT_DATE_NEWEST:
                Collections.sort(messages, (r1, r2) -> Long.compare(r2.getTimestamp(), r1.getTimestamp()));
                break;
            case SORT_DATE_OLDEST:
                Collections.sort(messages, (r1, r2) -> Long.compare(r1.getTimestamp(), r2.getTimestamp()));
                break;
            case SORT_PRIORITY_HIGH:
                Collections.sort(messages, (r1, r2) -> Integer.compare(r2.getPriority(), r1.getPriority()));
                break;
            case SORT_PRIORITY_LOW:
                Collections.sort(messages, (r1, r2) -> Integer.compare(r1.getPriority(), r2.getPriority()));
                break;
        }
        notifyDataSetChanged();
    }

    /**
     * Method called to update the messages list
     * @param messages the new message list
     */
    void updateMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        sortMessages(order);
    }
}
