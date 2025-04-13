package com.example.safeharbor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView tvDistanceToBorder;
    private List<Polygon> boundaryPolygons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        tvDistanceToBorder = findViewById(R.id.tvDistanceToBorder);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        boundaryPolygons = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            enableMyLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Location permission is required to show your position",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getLastLocation();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                LatLng currentLocation = new LatLng(location.getLatitude(),
                                        location.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
                                updateDistanceToBorder(currentLocation);
                            }
                        }
                    });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Draw maritime boundaries
        drawMaritimeBoundaries();
    }

    private void drawMaritimeBoundaries() {
        // Indian Border
        PolygonOptions indianBorder = new PolygonOptions()
                .add(new LatLng(8.0, 77.0))
                .add(new LatLng(8.0, 78.0))
                .add(new LatLng(9.0, 78.0))
                .add(new LatLng(9.0, 77.0))
                .strokeColor(getResources().getColor(R.color.indian_border))
                .fillColor(getResources().getColor(R.color.indian_border_transparent));
        boundaryPolygons.add(mMap.addPolygon(indianBorder));

        // Sri Lanka Border
        PolygonOptions sriLankaBorder = new PolygonOptions()
                .add(new LatLng(6.0, 79.0))
                .add(new LatLng(6.0, 80.0))
                .add(new LatLng(7.0, 80.0))
                .add(new LatLng(7.0, 79.0))
                .strokeColor(getResources().getColor(R.color.sri_lanka))
                .fillColor(getResources().getColor(R.color.sri_lanka_transparent));
        boundaryPolygons.add(mMap.addPolygon(sriLankaBorder));

        // Maldives Border
        PolygonOptions maldivesBorder = new PolygonOptions()
                .add(new LatLng(4.0, 73.0))
                .add(new LatLng(4.0, 74.0))
                .add(new LatLng(5.0, 74.0))
                .add(new LatLng(5.0, 73.0))
                .strokeColor(getResources().getColor(R.color.maldives))
                .fillColor(getResources().getColor(R.color.maldives_transparent));
        boundaryPolygons.add(mMap.addPolygon(maldivesBorder));

        // Bangladesh Border
        PolygonOptions bangladeshBorder = new PolygonOptions()
                .add(new LatLng(21.0, 89.0))
                .add(new LatLng(21.0, 90.0))
                .add(new LatLng(22.0, 90.0))
                .add(new LatLng(22.0, 89.0))
                .strokeColor(getResources().getColor(R.color.bangladesh))
                .fillColor(getResources().getColor(R.color.bangladesh_transparent));
        boundaryPolygons.add(mMap.addPolygon(bangladeshBorder));
    }

    private void updateDistanceToBorder(LatLng currentLocation) {
        double minDistance = Double.MAX_VALUE;
        String nearestBorder = "";

        for (Polygon polygon : boundaryPolygons) {
            List<LatLng> points = polygon.getPoints();
            for (LatLng point : points) {
                float[] results = new float[1];
                Location.distanceBetween(currentLocation.latitude, currentLocation.longitude,
                        point.latitude, point.longitude, results);
                if (results[0] < minDistance) {
                    minDistance = results[0];
                    nearestBorder = getBorderName(polygon);
                }
            }
        }

        if (minDistance < 2000) { // 2 kilometers
            tvDistanceToBorder.setText(String.format("Warning: You are %.1f km from %s border",
                    minDistance / 1000, nearestBorder));
            tvDistanceToBorder.setTextColor(getResources().getColor(R.color.warning_red));
        } else {
            tvDistanceToBorder.setText(String.format("You are %.1f km from %s border",
                    minDistance / 1000, nearestBorder));
            tvDistanceToBorder.setTextColor(getResources().getColor(R.color.text_primary));
        }
    }

    private String getBorderName(Polygon polygon) {
        if (polygon == boundaryPolygons.get(0)) return "Indian";
        if (polygon == boundaryPolygons.get(1)) return "Sri Lankan";
        if (polygon == boundaryPolygons.get(2)) return "Maldives";
        if (polygon == boundaryPolygons.get(3)) return "Bangladesh";
        return "Unknown";
    }
} 