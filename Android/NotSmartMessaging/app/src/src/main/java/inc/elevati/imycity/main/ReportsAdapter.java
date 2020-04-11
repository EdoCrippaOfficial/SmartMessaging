package inc.elevati.smartmessaging.main;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.utils.GlideApp;
import inc.elevati.smartmessaging.utils.message;

import static inc.elevati.smartmessaging.main.MainContracts.message_SORT_DATE_NEWEST;
import static inc.elevati.smartmessaging.main.MainContracts.message_SORT_DATE_OLDEST;
import static inc.elevati.smartmessaging.main.MainContracts.message_SORT_STARS_LESS;
import static inc.elevati.smartmessaging.main.MainContracts.message_SORT_STARS_MORE;

/** Adapter class that organizes message data to show it in a RecyclerView hosted in AllmessagesFragment */
public class messagesAdapter extends RecyclerView.Adapter<messagesAdapter.MyViewHolder> {

    /** The context reference */
    private Fragment context;

    /** The message list to be shown */
    private List<message> messages;

    /** The presenter that allows communication with the view */
    private MainContracts.messageListPresenter presenter;

    /** Class that holds the required View objects in a layout */
    static class MyViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pb_loading;
        TextView tv_title, tv_desc, tv_stars;
        ImageView iv_image, iv_stars;
        LinearLayout bn_stars;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pb_loading = itemView.findViewById(R.id.pb_image);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_desc = itemView.findViewById(R.id.tv_desc);
            this.iv_image = itemView.findViewById(R.id.iv_message_image);
            this.tv_stars = itemView.findViewById(R.id.tv_stars);
            this.iv_stars = itemView.findViewById(R.id.iv_stars);
            this.bn_stars = itemView.findViewById(R.id.bn_stars);
        }
    }

    public messagesAdapter(Fragment context, MainContracts.messageListPresenter presenter) {
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
    public messagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onmessageClicked(messages.get(viewHolder.getAdapterPosition()));
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
        final message message = messages.get(position);
        holder.tv_title.setText(message.getTitle());
        holder.tv_desc.setText(message.getDescription());
        holder.pb_loading.setVisibility(View.VISIBLE);
        holder.tv_stars.setText(Integer.toString(message.getnStars()));
        int color;
        if (message.isStarred())
            color = ContextCompat.getColor(context.getContext(), R.color.gold);
        else
            color = ContextCompat.getColor(context.getContext(), R.color.light_grey);

        holder.iv_stars.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);

        // Image loading from cloud storage with Glide
        GlideApp.with(context)
                .load(message.getImageReference(message.IMAGE_THUMBNAIL))
                .placeholder(R.drawable.ic_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.pb_loading.setVisibility(View.GONE);
                        holder.iv_image.setImageResource(R.drawable.ic_no_image);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pb_loading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.iv_image);

        // Stars button listener
        holder.bn_stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStarsButtonClicked(message);
                notifyDataSetChanged();
            }
        });
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
    public void sortmessages(int order) {
        switch (order) {
            case message_SORT_DATE_NEWEST:
                Collections.sort(messages, new Comparator<message>() {
                    @Override
                    public int compare(message r1, message r2) {
                        return Long.compare(r2.getTimestamp(), r1.getTimestamp());
                    }
                });
                break;
            case message_SORT_DATE_OLDEST:
                Collections.sort(messages, new Comparator<message>() {
                    @Override
                    public int compare(message r1, message r2) {
                        return Long.compare(r1.getTimestamp(), r2.getTimestamp());
                    }
                });
                break;
            case message_SORT_STARS_MORE:
                Collections.sort(messages, new Comparator<message>() {
                    @Override
                    public int compare(message r1, message r2) {
                        return Integer.compare(r2.getnStars(), r1.getnStars());
                    }
                });
                break;
            case message_SORT_STARS_LESS:
                Collections.sort(messages, new Comparator<message>() {
                    @Override
                    public int compare(message r1, message r2) {
                        return Integer.compare(r1.getnStars(), r2.getnStars());
                    }
                });
                break;
        }
        notifyDataSetChanged();
    }

    /**
     * Method called to update the messages list
     * @param messages the new message list
     * @param order the order used to show messages
     */
    public void updatemessages(List<message> messages, int order) {
        this.messages.clear();
        this.messages.addAll(messages);
        sortmessages(order);
    }

    /**
     * Method called to retrieve a message from the list
     * @param position the message position in the list
     * @return the message in the specified positon
     */
    public message getItemAt(int position) {
        return messages.get(position);
    }
}
