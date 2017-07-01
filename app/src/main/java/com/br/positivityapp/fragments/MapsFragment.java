package com.br.positivityapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.positivityapp.R;
import com.br.positivityapp.dao.EventDiarySetDAO;
import com.br.positivityapp.models.EventDiary;
import com.br.positivityapp.models.EventDiarySet;
import com.br.positivityapp.positivityapp.MainActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;


    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ((MainActivity) getActivity()).setActionBarTitle("Positivity App");

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).tabLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<EventDiarySet> allPos = new EventDiarySetDAO(getActivity()).getAllEventDiarySet();

        List<Marker> listMarkers = new ArrayList<Marker>();

        // Adding markers for every eventDiary
        for (EventDiarySet eds : allPos) {

            for (EventDiary ed : eds.getEventsDiary()) {

                if (ed.getLatitude() != null && ed.getLongitude() != null && ed.getLatitude() != 0.0 && ed.getLongitude() != 0.0) {
                    LatLng latLng = new LatLng(ed.getLatitude(), ed.getLongitude());

                    // Creating a marker
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Setting the position for the marker
                    markerOptions.position(latLng);

                    String date = (ed.getDay() < 10 ? "0" + ed.getDay() : ed.getDay()) + "/" + (ed.getMonth() < 10 ? "0" + ed.getMonth() : ed.getMonth()) + "/" + ed.getYear();

                    // Setting the title for the marker.
                    // This will be displayed on taping the marker
                    markerOptions.title(ed.getDescription() + " - " + date + " - " + ed.getTime());

                    // Placing a marker on the touched position
                    listMarkers.add(mMap.addMarker(markerOptions));
                }
            }
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : listMarkers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        mMap.moveCamera(cu);

//        LatLng UCA = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(UCA).title("YOUR TITLE")).showInfoWindow();
//
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(UCA,17));
//
//
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                // Creating a marker
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker.
//                // This will be displayed on taping the marker
//                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//
//                // Clears the previously touched position
//                mMap.clear();
//
//                // Animating to the touched position
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                // Placing a marker on the touched position
//                mMap.addMarker(markerOptions);
//            }
//        });

    }

}
