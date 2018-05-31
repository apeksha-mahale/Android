package com.example.myambulanceapplication;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION=2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    String id = "0";

    GPSTracker gps;
    TextView location;
    Button stateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            if (ActivityCompat.checkSelfPermission(this,mPermission)!= MockPackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{mPermission},REQUEST_CODE_PERMISSION);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        stateButton = (Button) findViewById(R.id.stateButton);
        btnShowLocation=(Button) findViewById(R.id.button);
        stateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference("AmbulanceList");
                DatabaseReference zero = myRef1.child(id);
                DatabaseReference zero1 = zero.child("State");
                zero1.setValue("0");
                Toast.makeText(getApplicationContext(), "Freed",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps=new GPSTracker(MainActivity.this);
                location=(TextView) findViewById(R.id.locationText);
                if(gps.canGetLocation()){
                    double latitude=gps.getLatitude();
                    double longitude=gps.getLongitude();
                    location.setText("Your location is " + latitude+""+longitude);
                    DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference("AmbulanceList");
                    DatabaseReference zero = myRef1.child(id);
                    DatabaseReference zero1 = zero.child("Location");
                    zero1.setValue(latitude+","+longitude);
                }
                else{
                    gps.showSettingsAlert();
                }
            }
        });

    }
}
