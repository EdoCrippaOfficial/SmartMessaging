package inc.elevati.smartmessaging.main;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.utils.Message;
import inc.elevati.smartmessaging.utils.Utils;

/**
 * In this class it is defined the style of the dialog shown when user clicks on a message,
 * it uses the presenter related to the view from where the message is clicked
 */
public class MessageDialog extends DialogFragment implements MainContracts.MessageDialogView {

    /** The presenter associated to this parent view */
    private MainContracts.MainPresenter presenter;

    /**
     * Static method that returns an instance of messageDialog
     * with the specified message as an argument
     * @param message the message to be shown
     * @return a messageDialog instance
     */
    public static MessageDialog newInstance(Message message) {
        MessageDialog dialog = new MessageDialog();
        Bundle args = new Bundle();
        args.putParcelable("message", message);
        dialog.setArguments(args);
        return dialog;
    }

    /**
     * Called when the dialog View is created, here all Views are
     * initialized and the message data is adapted to user interface
     * @param inflater the layout inflater
     * @param container this dialog parent
     * @param savedInstanceState a Bundle containing saved data to be restored
     * @return the created View
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.message_layout, null);

        // message retrieving from arguments
        final Message message = getArguments().getParcelable("message");

        // Presenter retrieving
        presenter = ((MainActivity) requireActivity()).getPresenter();

        LinearLayout message_layout = v.findViewById(R.id.message_layout);
        Utils.setBackgroundColor(message_layout, message.getPriority());
        TextView tv_title = v.findViewById(R.id.tv_title);
        TextView tv_body = v.findViewById(R.id.tv_body);
        TextView tv_date = v.findViewById(R.id.tv_date);
        TextView tv_receivers = v.findViewById(R.id.tv_receivers);
        ImageView bn_delete = v.findViewById(R.id.bn_delete);
        bn_delete.setOnClickListener(v1 -> {
            Dialog confirmDialog = new Dialog(requireContext());
            confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            confirmDialog.setContentView(R.layout.dialog_confirm);
            confirmDialog.findViewById(R.id.bn_delete_no).setOnClickListener(v3 -> confirmDialog.dismiss());
            confirmDialog.findViewById(R.id.bn_delete_yes).setOnClickListener(v3 -> {
                confirmDialog.dismiss();
                presenter.onDeleteMessageButtonClicked(message);
            });
            confirmDialog.show();
        });
        ImageView iv_image = v.findViewById(R.id.iv_image);
        tv_title.setText(message.getTitle());
        tv_body.setText(message.getBody());
        StringBuilder s = new StringBuilder();
        for (String r: message.getReceivers()) {
            s.append(r).append("; ");
        }
        tv_receivers.setText(s.toString());

        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());
        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getContext());
        Date date = new Date(message.getTimestamp());
        String completeDate = dateFormat.format(date) + ", " + timeFormat.format(date);
        tv_date.setText(completeDate);

        if (message.getImageUrl().length() > 0) {
            iv_image.setVisibility(View.VISIBLE);
            CircularProgressDrawable progressDrawable = new CircularProgressDrawable(requireContext());
            progressDrawable.setStrokeWidth(3f);
            progressDrawable.setCenterRadius(20f);
            progressDrawable.start();

            Glide.with(this).load(message.getImageUrl()).placeholder(progressDrawable).error(R.drawable.ic_no_image).into(iv_image);
        } else {
            iv_image.setImageDrawable(null);
            iv_image.setVisibility(View.GONE);
        }
        return v;
    }

    /** {@inheritDoc} */
    @Override
    public void dismissDialog() {
        this.dismiss();
    }

    /** {@inheritDoc} */
    @Override
    public void notifyDeleteMessageError() {
        Toast.makeText(getContext(), R.string.delete_fail, Toast.LENGTH_SHORT).show();
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
        presenter.attachMessageDialogView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachMessageDialogView();
    }
}
