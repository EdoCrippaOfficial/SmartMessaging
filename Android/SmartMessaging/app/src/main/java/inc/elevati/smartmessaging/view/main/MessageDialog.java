package inc.elevati.smartmessaging.view.main;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.databinding.MessageLayoutBinding;
import inc.elevati.smartmessaging.model.Message;
import inc.elevati.smartmessaging.view.utils.ProgressDialog;
import inc.elevati.smartmessaging.viewmodel.MessageHandlerViewModel;
import inc.elevati.smartmessaging.viewmodel.MessagesListViewModel;

public class MessageDialog extends DialogFragment {

    private ViewModelProvider viewModelProvider;
    private ProgressDialog progressDialog;

    static MessageDialog newInstance() {
        return new MessageDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final MessageLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.message_layout, container, false);

        // Recupero di un eventuale ProgressDialog già presente. Questo vuol dire che l'activity è
        // stata distrutta mentre era in corso un'operazione, quindi si richiede al ViewModel l'esito
        progressDialog = (ProgressDialog) getParentFragmentManager().findFragmentByTag("progress");
        if (progressDialog != null) {
            requireRegistrationResult();
        } else {
            // Recuper dell'oggetto Message da visualizzare (showMessage)
            viewModelProvider = new ViewModelProvider(requireActivity());
            MessageHandlerViewModel messageHandlerViewModel = viewModelProvider.get(MessageHandlerViewModel.class);
            messageHandlerViewModel.getMessage().observe(getViewLifecycleOwner(), message -> showMessage(message, binding));
        }

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
        for (String user: message.getReceivers()) {
            builder.append(user).append("; ");
        }
        tv_receivers.setText(getString(R.string.receivers, builder.toString()));

        // Listener bottone cancella, operazione sempre attraverso il ViewModel
        binding.getRoot().findViewById(R.id.bn_delete).setOnClickListener(v -> {
            Dialog confirmDialog = new Dialog(requireContext());
            confirmDialog.setContentView(R.layout.dialog_confirm);
            confirmDialog.findViewById(R.id.bn_delete_yes).setOnClickListener(v2 -> {
                showProgressDialog();
                deleteMessage(message);
                confirmDialog.dismiss();
            });
            confirmDialog.findViewById(R.id.bn_delete_no).setOnClickListener(v2 -> confirmDialog.dismiss());
            confirmDialog.show();
        });
    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.newInstance(R.string.delete_delete);
        progressDialog.show(getParentFragmentManager(), "progress");
        getParentFragmentManager().executePendingTransactions();
    }

    private void deleteMessage(Message message) {
        MessageHandlerViewModel messageHandlerViewModel = viewModelProvider.get(MessageHandlerViewModel.class);
        messageHandlerViewModel.deleteMessage(message).observe(getViewLifecycleOwner(), this::handleDeleteResult);
    }

    private void requireRegistrationResult() {
        MessageHandlerViewModel messageHandlerViewModel = viewModelProvider.get(MessageHandlerViewModel.class);

        // In questo caso il ViewModel ritorna il LiveData che la view stava osservando prima di essere distrutta
        messageHandlerViewModel.requestDeleteResult().observe(getViewLifecycleOwner(), this::handleDeleteResult);
    }

    private void handleDeleteResult(int result) {
        if (result == MessageHandlerViewModel.DELETE_SUCCESS) {
            dismiss();
            MessagesListViewModel messagesListViewModel = viewModelProvider.get(MessagesListViewModel.class);
            messagesListViewModel.refreshList();
        } else if (result == MessageHandlerViewModel.DELETE_FAILED){
            Toast.makeText(requireActivity(), R.string.delete_fail, Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }
}
