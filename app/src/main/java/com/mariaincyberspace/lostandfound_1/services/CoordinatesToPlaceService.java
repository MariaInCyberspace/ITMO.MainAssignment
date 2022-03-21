package com.mariaincyberspace.lostandfound_1.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import com.google.android.libraries.places.api.Places;
import com.mariaincyberspace.lostandfound_1.MyApp;
import com.mariaincyberspace.lostandfound_1.utils.Literals;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CoordinatesToPlaceService {
    private static double latitude;
    private static double longitude;


    public static String getPlace(double _latitude, double _longitude) {
        latitude = _latitude;
        longitude = _longitude;
        String api = Literals.Api.API;
        Context applicationContext = MyApp.getContext();
        String addressString = "";
        StringBuilder sb = new StringBuilder();

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, api);
        }

        Geocoder geocoder = new Geocoder(applicationContext, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);

                if (address.getSubAdminArea() != null) sb.append(address.getSubAdminArea()).append(", ");
                if (address.getAdminArea() != null) sb.append(address.getAdminArea()).append(", ");
                if (address.getCountryName() != null) sb.append(address.getCountryName());

                addressString = sb.toString();
            }
        } catch (IOException ex) {
            Log.d("CoordinatesToPlaceLog:\n", ex. getMessage());
        }

        return addressString;
    }
}
