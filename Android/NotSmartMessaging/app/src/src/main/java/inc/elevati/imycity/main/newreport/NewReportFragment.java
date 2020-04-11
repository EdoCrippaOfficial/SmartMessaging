package inc.elevati.smartmessaging.main.newmessage;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import inc.elevati.smartmessaging.R;
import inc.elevati.smartmessaging.main.MainActivity;
import inc.elevati.smartmessaging.main.MainContracts;
import inc.elevati.smartmessaging.utils.EspressoIdlingResource;
import inc.elevati.smartmessaging.utils.MapsActivity;
import inc.elevati.smartmessaging.utils.GlideApp;
import inc.elevati.smartmessaging.utils.ProgressDialog;

import static android.app.Activity.RESULT_OK;

/** This fragment contains the message creating form */
public class NewmessageFragment extends Fragment implements MainContracts.NewmessageView {

    /** Constant representing the image pick intent request */
    private static final int PICK_IMAGE_REQUEST = 71;

    /** Constant representing the camera intent request */
    private static final int TAKE_PHOTO_REQUEST = 45;

    /** Constant representing the map intent request */
    private static final int PICK_POSITION_REQUEST = 33;

    /** The presenter associated to this view */
    private MainContracts.NewmessagePresenter presenter;

    /** Dialog displayed during database and storage sending */
    private ProgressDialog progressDialog;

    /** Global toast reference to avoid toasts accumulation */
    private Toast toast;

    /** ImageView for the new message image */
    private ImageView imageView;

    /** {@link TextInputEditText}s for message data */
    private TextInputEditText textInputTitle, textInputDesc;

    /** {@link TextInputLayout}s for message data */
    private TextInputLayout textLayoutTitle, textLayoutDesc;

    /** ProgressBar shown during image loading */
    private ProgressBar imageProgressBar;

    /** Uri referencing the image chosen or taken by the user */
    private Uri imageUri;

    /** The view containing Google map */
    private MapView mapView;

    /** The chosen position */
    private LatLng position;

    /** Image that acts as a button to add position */
    private ImageView ivAddPosition;

    /**
     * Static method that returns an instance of this fragment,
     * should be called only by the ViewPager adapter
     * @return a NewmessageFragment instance
     */
    public static NewmessageFragment newInstance() {
        return new NewmessageFragment();
    }

    /**
     * Method called when the View associated to this fragment is created (the first time this
     * fragment is shown, at orientation changes, at activity re-creations...); Here the layout
     * is inflated and all Views owned by this fragment are initialized
     * @param inflater the layout inflater
     * @param container this fragment parent view
     * @param savedInstanceState a Bundle containing saved data to be restored
     * @return the View initialized and inflated
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_message, container, false);
        imageView = v.findViewById(R.id.iv_new_message);
        textInputTitle = v.findViewById(R.id.text_input_edit_title);
        textInputDesc = v.findViewById(R.id.text_input_edit_desc);
        textLayoutTitle = v.findViewById(R.id.text_input_layout_title);
        textLayoutDesc = v.findViewById(R.id.text_input_layout_desc);
        imageProgressBar = v.findViewById(R.id.pb_image);
        mapView = v.findViewById(R.id.map_view);
        ivAddPosition = v.findViewById(R.id.iv_add_position);

        if (savedInstanceState != null) {

            // Restore the image uri if it was set
            imageUri = savedInstanceState.getParcelable("uri");
            if (imageUri != null) {

                // ProgressBar
                imageProgressBar.setVisibility(View.VISIBLE);

                // Reload the image
                GlideApp.with(this).load(imageUri)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                imageProgressBar.setVisibility(View.GONE);

                                // If error occurs during image restoring, delete the saved file reference
                                imageUri = null;
                                imageView.setImageResource(R.drawable.ic_add_image);
                                return true;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                imageProgressBar.setVisibility(View.GONE);

                                // If meanwhile imageUri is set to null (for example after executing a
                                // pending task from the presenter) restore the default image
                                if (imageUri == null) {
                                    imageView.setImageResource(R.drawable.ic_add_image);
                                    return true;
                                }
                                return false;
                            }
                        }).into(imageView);
            }

            // Restore the position if it was set
            position = savedInstanceState.getParcelable("position");
        }

        // Clear error when users provides input in TextInputEditTexts
        clearEditTextErrorOnInput();

        // ProgressDialog retrieval
        progressDialog = (ProgressDialog) getFragmentManager().findFragmentByTag("progress");

        // Presenter retrieval
        presenter = ((MainActivity) getActivity()).getPresenter().getNewmessagePresenter();

        // Fixed map creation if position already set
        if (position != null) createFixedMap(savedInstanceState);

        // Map listener that opens full-screen map
        ivAddPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EspressoIdlingResource.increment();
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivityForResult(intent, PICK_POSITION_REQUEST);
            }
        });

        // Click listener to pick or take a picture
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createImageDialog();
            }
        });

        // Send message button
        v.findViewById(R.id.bn_new_message_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textLayoutTitle.setError(null);
                textLayoutDesc.setError(null);
                textLayoutTitle.clearFocus();
                textLayoutDesc.clearFocus();
                String title = textInputTitle.getText().toString();
                String desc = textInputDesc.getText().toString();
                presenter.onSendButtonClicked(title, desc, getActivity().getApplicationContext(), imageUri, position);
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    /**
     * Method called by Android system when the state has to be saved
     * (e.g. during screen orientation changes)
     * @param outState the saved state
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the image Uri
        outState.putParcelable("uri", imageUri);

        // Save the position
        outState.putParcelable("position", position);
    }

    /**{@inheritDoc}*/
    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.newInstance(R.string.new_message_uploading);
        progressDialog.show(getFragmentManager(), "progress");
    }

    /**{@inheritDoc}*/
    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    /**{@inheritDoc}*/
    @Override
    public void notifyInvalidImage() {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(getContext(), R.string.new_message_no_picture, Toast.LENGTH_SHORT);
        toast.show();

        // Blink animation of the ImageView (the camera image)
        Animation animation = new AlphaAnimation(1f, 0.4f);
        animation.setDuration(350);
        animation.setRepeatCount(2);
        imageView.startAnimation(animation);
    }

    /**{@inheritDoc}*/
    @Override
    public void notifyInvalidTitle() {
        textLayoutTitle.setError(getString(R.string.new_message_no_title));
    }

    /**{@inheritDoc}*/
    @Override
    public void notifyInvalidDescription() {
        textLayoutDesc.setError(getString(R.string.new_message_no_description));
    }

    /**{@inheritDoc}*/
    @Override
    public void notifySendTaskCompleted(final MainContracts.SendTaskResult result) {
        if (getActivity() == null) return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (result) {
                    case RESULT_SEND_OK:
                        Toast.makeText(getContext(), R.string.new_message_ok, Toast.LENGTH_LONG).show();
                        imageView.setImageResource(R.drawable.ic_add_image);
                        imageUri = null;
                        textInputDesc.setText(null);
                        textInputTitle.setText(null);
                        break;
                    case RESULT_SEND_ERROR_DB:
                        Toast.makeText(getContext(), R.string.new_message_error_db, Toast.LENGTH_LONG).show();
                        break;
                    case RESULT_SEND_ERROR_IMAGE:
                        Toast.makeText(getContext(), R.string.new_message_error_image, Toast.LENGTH_LONG).show();
                        imageUri = null;
                        imageView.setBackgroundResource(R.drawable.ic_add_image);
                        break;
                }
            }
        });
    }

    /** Creates a dialog that asks user where to pick the image (gallery or camera) */
    private void createImageDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_pick);
        dialog.findViewById(R.id.bn_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.bn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /** Starts activity to pick image from gallery */
    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.new_message_select_picture)), PICK_IMAGE_REQUEST);
    }

    /** Creates a File object to receive photo from camera and then start camera activity */
    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            try {
                // Create the File where the photo should go
                File cameraFile = createImageFile();
                Uri photoURI = FileProvider.getUriForFile(getContext(), "inc.elevati.imycity.fileprovider", cameraFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST);
            } catch (IOException e) {
                Toast.makeText(getContext(), R.string.new_message_camera_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), R.string.new_message_camera_missing, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Called after the pick image or camera activity has completed
     * @param requestCode the request code, can be PICK_IMAGE_REQUEST or TAKE_PHOTO_REQUEST
     * @param resultCode the activity result code, it must be RESULT_OK to continue
     * @param data an Intent containing the returned data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getActivity() == null) return;

        // Retrieving image from storage
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // ProgressBar
            imageProgressBar.setVisibility(View.VISIBLE);

            imageUri = data.getData();
            GlideApp.with(this).load(imageUri).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    // Some file managers require read storage permission
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // Asking for read permission and then try again in onRequestPermissionsResult
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
                    } else {
                        // Unknown error
                        imageProgressBar.setVisibility(View.GONE);
                        imageView.setImageResource(R.drawable.ic_add_image);
                        Toast.makeText(getContext(), R.string.new_message_pick_error, Toast.LENGTH_LONG).show();
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imageProgressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(imageView);

        // Getting camera result
        } else if (requestCode == TAKE_PHOTO_REQUEST && resultCode == RESULT_OK) {

            // ProgressBar
            imageProgressBar.setVisibility(View.VISIBLE);

            GlideApp.with(this).load(imageUri)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageProgressBar.setVisibility(View.GONE);
                        imageView.setImageResource(R.drawable.ic_add_image);
                        Toast.makeText(getContext(), R.string.new_message_camera_error, Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imageProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        // Getting position result
        } else if (requestCode == PICK_POSITION_REQUEST && resultCode == RESULT_OK) {

            // Retrieve position
            position = data.getParcelableExtra("position");

            // Create fixed map
            createFixedMap(null);
        }
    }

    /**
     * Called when user has allowed or denied to grant READ_EXTERNAL_STORAGE permission, if
     * result is positive we try again to load image, otherwise error is displayed
     * @param requestCode the request code passed in requestPermissions
     * @param permissions the permissions requested
     * @param grantResults the request result
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // Permission is granted, try again to load image
            GlideApp.with(this).load(imageUri).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    imageProgressBar.setVisibility(View.GONE);

                    // Error occurred even with permissions, error message displayed
                    Toast.makeText(getContext(), R.string.new_message_pick_error, Toast.LENGTH_LONG).show();
                    imageView.setImageResource(R.drawable.ic_add_image);
                    return true;
                }
                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imageProgressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(imageView);
        } else {
            imageProgressBar.setVisibility(View.GONE);

            // Permission is denied, error message displayed
            imageView.setImageResource(R.drawable.ic_add_image);
            Toast.makeText(getContext(), R.string.new_message_permissions, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Creates a file used to receive the camera image
     * @return the File object created
     * @throws IOException exception occurred during file creation
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        imageUri = Uri.fromFile(image);
        return image;
    }

    /**
     * Method called to hide error when user provides new text in {@link TextInputEditText}s
     */
    private void clearEditTextErrorOnInput() {
        textInputTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textLayoutTitle.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        textInputDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textLayoutDesc.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    /** Loads a map with selected position into MapView */
    private void createFixedMap(Bundle savedInstanceState) {
        ivAddPosition.setVisibility(View.GONE);
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
                EspressoIdlingResource.decrement();
                mMap.getUiSettings().setAllGesturesEnabled(false);
                mMap.addMarker(new MarkerOptions().position(position));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17f));
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Intent intent = new Intent(getActivity(), MapsActivity.class);
                        intent.putExtra("position", position);
                        startActivityForResult(intent, PICK_POSITION_REQUEST);
                    }
                });
            }
        });
    }
}