package com.br.positivityapp.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.br.positivityapp.BuildConfig;
import com.br.positivityapp.R;
import com.br.positivityapp.dao.EventDiarySetDAO;
import com.br.positivityapp.models.EventDiary;
import com.br.positivityapp.positivityapp.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewPosFragment extends Fragment implements OnMapReadyCallback {

    private Button btnSave;
    private CheckBox checkBoxAddLocation;
    private EditText editDescription;
    private CalendarDay daySelected;
    public MainActivity.SectionsPagerAdapter.CalendarPageListener listener;

    private GoogleMap mMap;
    private Double latitude = 0d;
    private Double longitude = 0d;

    public AddNewPosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_new_pos, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Add New Pos");
        ((MainActivity) getActivity()).mViewPager.setPagingEnabled(false);

        btnSave = (Button) root.findViewById(R.id.button);
        checkBoxAddLocation = (CheckBox) root.findViewById(R.id.checkBoxAddLocation);
        editDescription = (EditText) root.findViewById(R.id.editText);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapAdd);
        mapFragment.getMapAsync(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });

        boolean gotPermission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (gotPermission) {
            if (BuildConfig.DEBUG) Log.v("Location", "Got the permission");

        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        checkBoxAddLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ActivityCompat.checkSelfPermission(buttonView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(buttonView.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(isChecked);

                if (checkBoxAddLocation.isChecked()) {
                    Location location = getLastKnownLocation();
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                    if (location != null) {
                        Log.e("TAG", "GPS is on");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                    else{
                        //This is what you need:
                        latitude = userLocation.latitude;
                        longitude = userLocation.longitude;
                    }



                    // Creating a marker
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Setting the position for the marker
                    markerOptions.position(userLocation);

                    // Setting the title for the marker.
                    // This will be displayed on taping the marker
                    markerOptions.title("Localizacão do Evento: " + userLocation.latitude + " : " + userLocation.longitude);

                    // Clears the previously touched position
                    mMap.clear();

                    // Animating to the touched position
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(userLocation));

                    // Placing a marker on the touched position
                    mMap.addMarker(markerOptions);

                    // Listener to place a marker when the map is clicked.
                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            // Creating a marker
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Setting the position for the marker
                            markerOptions.position(latLng);

                            // Setting the title for the marker.
                            // This will be displayed on taping the marker
                            markerOptions.title("Localizacão do Evento: " + latLng.latitude + " : " + latLng.longitude);

                            // Clears the previously touched position
                            mMap.clear();

                            // Animating to the touched position
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                            // Placing a marker on the touched position
                            mMap.addMarker(markerOptions);

                            latitude = latLng.latitude;
                            longitude = latLng.longitude;
                        }
                    });
                    Toast.makeText(buttonView.getContext(), "Clique no mapa para posicionar o pino", Toast.LENGTH_SHORT).show();
                } else {
                    mMap.setOnMapClickListener(null);
                    mMap.clear();
                }

            }
        });

        return root;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).tabLayout.setVisibility(View.GONE);
    }

    public void onSaveClicked() {

        int day = daySelected.getDay();
        int month = daySelected.getMonth();
        int year = daySelected.getYear();
        String currentTimeString = DateFormat.getTimeInstance().format(new Date());
        String description = editDescription.getText().toString();
        EventDiary eventDiary;
        if (checkBoxAddLocation.isChecked())
            eventDiary = new EventDiary(description, day, month, year, currentTimeString, latitude, longitude);
        else
            eventDiary = new EventDiary(description, day, month, year, currentTimeString);
        EventDiarySetDAO dao = new EventDiarySetDAO(getActivity());
        dao.insertEventDiary(eventDiary, getActivity());

        Toast.makeText(getActivity(), "Event saved!", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();

    }

    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public CalendarDay getDaySelected() {
        return daySelected;
    }

    public void setDaySelected(CalendarDay daySelected) {
        this.daySelected = daySelected;
    }

}
