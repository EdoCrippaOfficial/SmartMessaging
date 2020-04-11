package inc.elevati.smartmessaging.utils;

import androidx.fragment.app.FragmentActivity;
import inc.elevati.smartmessaging.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/** Activity launched when user selects position during new message creation */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /** The starting position */
    private static final LatLng dummyPosition = new LatLng(45.646457, 9.607563);

    /** The button to select position */
    private Button bnSelect;

    /** The chosen position */
    private LatLng position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        position = getIntent().getParcelableExtra("position");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        bnSelect = findViewById(R.id.bn_position);
        bnSelect.setEnabled(false);
    }

    /**
     * Tells that the loaded map is ready
     * @param googleMap the loaded map
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        bnSelect.setEnabled(true);
        EspressoIdlingResource.decrement();
        if (position == null)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dummyPosition, 17f));
        else
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17f));

        bnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EspressoIdlingResource.increment();
                position = googleMap.getCameraPosition().target;
                Intent result = new Intent();
                result.putExtra("position", position);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
    }
}
