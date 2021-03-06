package inc.elevati.smartmessaging.view.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.databinding.MessageListItemBinding;
import inc.elevati.smartmessaging.model.Message;

public class MessageItemAdapter extends RecyclerView.Adapter<MessageItemAdapter.MessageViewHolder> {

    // Criteri di ordinamento
    final static int SORT_NEWEST = 0;
    final static int SORT_OLDEST = 1;
    final static int SORT_PRIORITY_HIGH = 2;
    final static int SORT_PRIORITY_LOW = 3;

    private Comparator<Message> sorter;
    private List<Message> messages;
    private OnMessageClickListener messageClickListener;

    MessageItemAdapter(OnMessageClickListener messageClickListener, int sortCriteria) {
        this.messageClickListener = messageClickListener;
        this.messages = new ArrayList<>();
        setSortCriteria(sortCriteria);
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {

        // Nessun riferimento alle views ma solo al binding, al quale bisogna fornire il messaggio di cui visualizzare i dati
        MessageListItemBinding binding;

        MessageViewHolder(MessageListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MessageListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.message_list_item, parent, false);

        // Listener fornito al binding, settato nel onClick nel xml del bottone
        binding.setListener(messageClickListener);
        return new MessageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position) {
        holder.binding.setMessage(messages.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    void updateMessages(List<Message> messages) {
        this.messages = messages;
        Collections.sort(messages, sorter);
        notifyDataSetChanged();
    }

    void setSortCriteria(int sortCriteria) {
        switch (sortCriteria) {
            case SORT_NEWEST:
                this.sorter = (o1, o2) -> Long.compare(o2.getTimestamp(), o1.getTimestamp());
                break;
            case SORT_OLDEST:
                this.sorter = (o1, o2) -> Long.compare(o1.getTimestamp(), o2.getTimestamp());
                break;
            case SORT_PRIORITY_HIGH:
                this.sorter = (o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority());
                break;
            case SORT_PRIORITY_LOW:
                this.sorter = (o1, o2) -> Long.compare(o1.getPriority(), o2.getPriority());
                break;
        }
        Collections.sort(messages, sorter);
        notifyDataSetChanged();
    }
}
