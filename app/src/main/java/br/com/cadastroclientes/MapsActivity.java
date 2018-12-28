package br.com.cadastroclientes;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        String cep = getIntent().getExtras().get("cepMaps").toString();

        Geocoder geocoder = new Geocoder(this);
        List<Address> enderecos = null;

        try {
            enderecos = geocoder.getFromLocationName(cep, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LatLng endereco = new LatLng(enderecos.get(0).getLatitude(), enderecos.get(0).getLongitude());
        mMap.addMarker(new MarkerOptions().position(endereco).title("Busca do CEP"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(endereco));
    }
}
