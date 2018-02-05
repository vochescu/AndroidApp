package ro.ase.eu.proiect.busWayBucharest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.proiect.Manifest;
import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.util.Constants;
import ro.ase.eu.proiect.util.FavoriteLine;
import ro.ase.eu.proiect.util.Line;
import ro.ase.eu.proiect.util.LineAdapter;
import ro.ase.eu.proiect.util.Stop;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Switch mapSwitch;
    Stop s1, s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapSwitch = (Switch) findViewById(R.id.map_switch);
        s1 = new Stop();
        s2 = new Stop();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mapSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // the isChecked will be true if the switch is in the On position
                if(isChecked){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
                else{
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });
    }
    public void goToSearchLine(View view){
        Intent intent=new Intent(this,SearchLineActivity.class);
        startActivityForResult(intent,Constants.MAP_LINE_REQUEST_CODE);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Set start map focused on Bucharest
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Constants.BUCHAREST_LAT, Constants.BUCHAREST_LNG), 10.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.MAP_LINE_REQUEST_CODE && resultCode==RESULT_OK && data!=null) {
            Stop s1 = data.getParcelableExtra(Constants.MAP_LINE_KEY2);
            Stop s2 = data.getParcelableExtra(Constants.MAP_LINE_KEY1);
            if (s1 != null && s2 != null) {
                LatLng end = new LatLng(Double.parseDouble(s1.getLatitude()), Double.parseDouble(s1.getLongitude()));
                LatLng start = new LatLng(Double.parseDouble(s2.getLatitude()), Double.parseDouble(s2.getLongitude()));


                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(R.color.chart_blue)
                        .add(start)
                        .add(end);
                mMap.addPolyline(polylineOptions);

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(end)
                        .icon(bitmap(getApplicationContext(),R.drawable.ic_directions_bus_black_24dp))
                        .title(s1.getName()));
                marker.showInfoWindow();

                Marker marker2 = mMap.addMarker(new MarkerOptions()
                        .position(start)
                        .icon(bitmap(getApplicationContext(),R.drawable.ic_directions_bus_black_24dp))
                        .title(s2.getName()));
                marker2.showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,13.0f));

            }

        }
    }

    private BitmapDescriptor bitmap(Context context, int vectorResId) {
        Drawable dr = ContextCompat.getDrawable(context, vectorResId);
        Bitmap bitmap = Bitmap.createBitmap(dr.getIntrinsicWidth(), dr.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
        Canvas canvas = new Canvas(bitmap);
        dr.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
