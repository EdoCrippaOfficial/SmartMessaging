package inc.elevati.smartmessaging.main;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.utils.GlideApp;
import inc.elevati.smartmessaging.utils.ProgressDialog;
import inc.elevati.smartmessaging.utils.message;
import inc.elevati.smartmessaging.firebase.FirebaseAuthHelper;

/**
 * In this class it is defined the style of the dialog shown when user clicks on a message,
 * it uses the presenter related to the view from where the message is clicked
 */
public class messageDialog extends DialogFragment implements MainContracts.messageDialogView {

    /** Dialog displayed during database and storage sending */
    private ProgressDialog progressDialog;

    /** The presenter associated to this parent view */
    private MainContracts.messageListPresenter presenter;

    /**
     * Static method that returns an instance of messageDialog
     * with the specified message as an argument
     * @param message the message to be shown
     * @return a messageDialog instance
     */
    public static messageDialog newInstance(message message, int parentPage) {
        messageDialog dialog = new messageDialog();
        Bundle args = new Bundle();
        args.putParcelable("message", message);
        args.putInt("parent", parentPage);
        dialog.setArguments(args);
        return dialog;
    }

    /**
     * Called when the dialog is created, here it's specified its style
     * @param savedInstanceState a Bundle containing saved data to be restored
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
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
        View v = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.dialog_message, null);

        // message retrieving from arguments
        final message message = getArguments().getParcelable("message");

        // Presenter retrieving
        int parentPage = getArguments().getInt("parent");
        switch (parentPage) {
            case MainContracts.PAGE_ALL:
                presenter = ((MainActivity) getActivity()).getPresenter().getAllmessagesPresenter();
                break;
            case MainContracts.PAGE_MY:
                presenter = ((MainActivity) getActivity()).getPresenter().getMymessagesPresenter();
                break;
            case MainContracts.PAGE_STARRED:
                presenter = ((MainActivity) getActivity()).getPresenter().getStarredmessagesPresenter();
                break;
            case MainContracts.PAGE_COMPLETED:
                presenter = ((MainActivity) getActivity()).getPresenter().getCompletedmessagesPresenter();
                break;
        }
        TextView tv_status = v.findViewById(R.id.tv_status);
        TextView tv_title = v.findViewById(R.id.tv_title);
        TextView tv_desc = v.findViewById(R.id.tv_desc);
        TextView tv_reply = v.findViewById(R.id.tv_reply);
        TextView tv_date = v.findViewById(R.id.tv_date);
        Button bn_delete = v.findViewById(R.id.bn_delete);
        MapView mapView = v.findViewById(R.id.map_view);

        // Show message status
        switch (message.getStatus()) {
            case STATUS_ACCEPTED:
                tv_status.setText(R.string.message_accepted);
                tv_status.setBackgroundColor(getContext().getResources().getColor(R.color.color_primary));
                break;
            case STATUS_REFUSED:
                tv_status.setText(R.string.message_refused);
                tv_status.setBackgroundColor(getContext().getResources().getColor(R.color.red));
                break;
            case STATUS_COMPLETED:
                tv_status.setText(R.string.message_completed);
                tv_status.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                break;
            case STATUS_WAITING:
                tv_status.setText(R.string.message_waiting);
                tv_status.setBackgroundColor(getContext().getResources().getColor(R.color.grey));
                break;
        }

        // Show delete button if current user is the creator of a waiting message
        if (message.getUserId().equals(FirebaseAuthHelper.getUserId()))
            bn_delete.setVisibility(View.VISIBLE);

        bn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog confirmDialog = new Dialog(getContext());
                confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                confirmDialog.setContentView(R.layout.dialog_confirm);
                confirmDialog.findViewById(R.id.bn_delete_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmDialog.dismiss();
                    }
                });
                confirmDialog.findViewById(R.id.bn_delete_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmDialog.dismiss();
                        presenter.onDeletemessageButtonClicked(message);
                    }
                });
                confirmDialog.show();
            }
        });
        final ImageView iv_image = v.findViewById(R.id.iv_message_image);
        final ProgressBar pb_loading = v.findViewById(R.id.pb_image);
        tv_title.setText(message.getTitle());
        tv_desc.setText(message.getDescription());

        final String reply = message.getReply();
        if (reply != null && !reply.equals("")) {
            v.findViewById(R.id.container_reply).setVisibility(View.VISIBLE);
            tv_reply.setText(reply);
        }

        // Time formatting: Created on: date, hour
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());
        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getContext());
        Date date = new Date(message.getTimestamp());
        String completeDate = dateFormat.format(date) + ", " + timeFormat.format(date);
        tv_date.setText(getString(R.string.message_date, message.getUserName(), completeDate));
        pb_loading.setVisibility(View.VISIBLE);

        // Map creating
        if (message.getPosition() != null) {
            mapView.setVisibility(View.VISIBLE);
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    LatLng position = new LatLng(message.getPosition().getLatitude(), message.getPosition().getLongitude());
                    mMap.addMarker(new MarkerOptions().position(position));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17f));
                }
            });
        }

        // Image loading from storage with Glide
        GlideApp.with(this)
                .load(message.getImageReference(message.IMAGE_FULL))
                .placeholder(R.drawable.ic_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        pb_loading.setVisibility(View.GONE);
                        iv_image.setImageResource(R.drawable.ic_no_image);
                        return true;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pb_loading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(iv_image);
        return v;
    }

    /** {@inheritDoc} */
    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.newInstance(R.string.message_deleting);
        progressDialog.show(getFragmentManager(), "progress");
    }

    /** {@inheritDoc} */
    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    /** {@inheritDoc} */
    @Override
    public void dismissDialog() {
        this.dismiss();
    }

    /** {@inheritDoc} */
    @Override
    public void notifyDeletemessageError() {
        Toast.makeText(getContext(), R.string.message_deleting_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachmessageDialogView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachmessageDialogView();
    }
}
