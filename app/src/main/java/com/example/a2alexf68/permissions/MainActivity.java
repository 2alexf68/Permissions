package com.example.a2alexf68.permissions;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    LocationManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Button grant = (Button) findViewById(R.id.b1),
                start = (Button) findViewById(R.id.b2);

        grant.setOnClickListener(this);
        start.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.b1) {
            //handle the user clicking the 'grant' button
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        } else if (view.getId() == R.id.b2) {

            //handle the user clicking start button
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                //Request GPS updates
                //0,0 updates as frequently as possible
                mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } else {

                new AlertDialog.Builder(this).setPositiveButton("OK", null).
                        setMessage("No permission to read files!").show();
            }
        }
    }

        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantsResults) {
        switch (requestCode) {
            case 0:
                if (grantsResults.length >0 && grantsResults[0] == PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage("Now you can use the GPS!").show();
            }else{
                new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage("GPS permission denined").show();
            }
            break;
        }
    }

        //when we get a new GPS location
        public void onLocationChanged(Location loc){
            TextView lon=(TextView)findViewById(R.id.lon),
                   lat=(TextView)findViewById(R.id.lat);

            lat.setText(String.valueOf(loc.getLatitude()));
            lon.setText(String.valueOf(loc.getLongitude()));
        }
        //if gps is enabled
        public void onProviderEnabled(String provider){

        }

        //if GPS is disabled
        public void onProviderDisabled(String provider){

        }
        //if GPS signal is lost
        public void onStatusChanged(String provider, int status, Bundle b){

        }

}
