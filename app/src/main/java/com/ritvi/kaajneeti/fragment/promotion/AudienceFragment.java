package com.ritvi.kaajneeti.fragment.promotion;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.GPSTracker;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.activity.HomeActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AudienceFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.btn_continue)
    Button btn_continue;
    @BindView(R.id.scroll_audience)
    ScrollView scroll_audience;
    @BindView(R.id.dsb_radius)
    DiscreteSeekBar dsb_radius;

    WorkaroundMapFragment mapFragment;
    GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_audience, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    GPSTracker gps;
    double latitude = 0.0;
    double longitude = 0.0;

    public void getLocation() {
        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Log.d(TagUtils.getTag(), "location:-latitude:-" + latitude);
            Log.d(TagUtils.getTag(), "location:-longitude:-" + longitude);
            this.latitude = latitude;
            this.longitude = longitude;
//            Pref.SetStringPref(getgetApplicationContext(), StringUtils.CURRENT_LATITUDE, String.valueOf(latitude));
//            Pref.SetStringPref(getApplicationContext(), StringUtils.CURRENT_LONGITUDE, String.valueOf(longitude));
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLocation();
        mapFragment = ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.map));

        ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                .setListener(new WorkaroundMapFragment.OnTouchListener() {
                    @Override
                    public void onTouch() {
                        scroll_audience.requestDisallowInterceptTouchEvent(true);
                    }
                });
        mapFragment.getMapAsync(this);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    TitleAttachmentFragment titleAttachmentFragment = new TitleAttachmentFragment();
                    homeActivity.addFragmentinFrameHome(titleAttachmentFragment,"titleAttachmentFragment");
                }
            }
        });

        dsb_radius.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                if (googleMap != null && latLng != null && circleOptions != null&&circle!=null) {
//                    googleMap.addCircle(new CircleOptions()
//                            .center(latLng)
//                            .radius(value)
//                            .strokeWidth(0f)
//                            .fillColor(0x550000FF));
                    circle.remove();
                    circleOptions.center(latLng).radius(value*1000).strokeWidth(1).fillColor(0x550000FF);
                    circle=googleMap.addCircle(circleOptions);
                }
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
    }

    public void checkZoom(int range){
//        if(range)
    }

    public void setCameraZoom(int zoom){
        if(googleMap!=null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }
    }


    LatLng latLng;
    CircleOptions circleOptions = new CircleOptions();
    Circle circle;
    @Override
    public void onMapReady(GoogleMap map) {
//        map.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                        getActivity(), R.raw.map_style));

        googleMap = map;

        latLng = new LatLng(latitude, longitude);

        Log.d(TagUtils.getTag(), "location111:-latitude:-" + latitude);
        Log.d(TagUtils.getTag(), "location111:-longitude:-" + longitude);

        map.addMarker(new MarkerOptions()
                .position(latLng)
                .title("current location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
        circleOptions.center(latLng).radius(40000).strokeWidth(1).fillColor(0x550000FF);
        circle=map.addCircle(circleOptions);
//        map.addCircle(circle);
    }
}
