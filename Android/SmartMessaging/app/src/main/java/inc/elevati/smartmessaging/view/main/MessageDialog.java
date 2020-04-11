package inc.elevati.smartmessaging.view.main;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.databinding.MessageLayoutBinding;
import inc.elevati.smartmessaging.viewmodel.MessageDetailViewModel;
import inc.elevati.smartmessaging.model.Message;
import inc.elevati.smartmessaging.model.User;
import inc.elevati.smartmessaging.viewmodel.MessagesListViewModel;

public class MessageDialog extends DialogFragment {

    private ViewModelProvider viewModelProvider;

    static MessageDialog newInstance() {
        return new MessageDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final MessageLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.message_layout, container, false);

        // Recuper dell'oggetto Message da visualizzare (showMessage)
        viewModelProvider = new ViewModelProvider(requireActivity());
        MessageDetailViewModel messageDetailViewModel = viewModelProvider.get(MessageDetailViewModel.class);
        messageDetailViewModel.getMessage().observe(getViewLifecycleOwner(), message -> showMessage(message, binding));

        // Non cè bisogno di settare i dati sugli oggetti grafici perchè sono già presenti nel xml sfruttando il data binding
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void showMessage(Message message, MessageLayoutBinding binding) {
        binding.setMessage(message);

        // Visualizzazione destinatari (no data binding perchè è una lista con lunghezza dinamica)
        TextView tv_receivers = binding.getRoot().findViewById(R.id.tv_receivers);
        StringBuilder builder = new StringBuilder();
        for (User user: message.getReceivers()) {
            builder.append(user.getName()).append("; ");
        }
        tv_receivers.setText(getString(R.string.receivers, builder.toString()));

        // Listener bottone cancella, operazione sempre attraverso il ViewModel
        binding.getRoot().findViewById(R.id.bn_delete).setOnClickListener(v -> {
            MessagesListViewModel messagesListViewModel = viewModelProvider.get(MessagesListViewModel.class);
            messagesListViewModel.deleteMessage(message);

            // Dismiss dialog
            dismiss();
        });
    }
}
