package com.mariaincyberspace.lostandfound_1.presentation.ui.item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.services.CoordinatesToPlaceService;


public class MapsFragment extends Fragment {

    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;
    AddItemViewModel viewModel;
    com.mariaincyberspace.lostandfound_1.domain.model.Location location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity().getApplication());
        viewModel = new AddItemViewModel(requireActivity().getApplication());
        getCurrentLocation();

        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(googleMap -> {
            googleMap.setOnMapLoadedCallback(() -> {
                LatLng current = new LatLng(currentLat, currentLong);
                String title = CoordinatesToPlaceService.getPlace(currentLat, currentLong);
                googleMap.addMarker(new MarkerOptions().position(current).title(title));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));

            });


            googleMap.setOnMapClickListener(latLng -> {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                String title = CoordinatesToPlaceService.getPlace(latLng.latitude, latLng.longitude);
                markerOptions.title(title);
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        latLng, 10
                ));
                googleMap.addMarker(markerOptions);
                this.location = new com.mariaincyberspace.lostandfound_1.domain.model
                        .Location(latLng.latitude, latLng.longitude);
            });
        });

        return v;
    }


    public com.mariaincyberspace.lostandfound_1.domain.model.Location getLocation() {
        return location;
    }


    private void getCurrentLocation() {
        if (ActivityCompat
                .checkSelfPermission(requireActivity().getApplication(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireActivity().getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLat = location.getLatitude();
                currentLong = location.getLongitude();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
            // other 'case' lines to check for other
            // permissions this app might request
        }

    }


}