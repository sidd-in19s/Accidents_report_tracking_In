package com.example.accident_statistics_calculator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
public class MainActivity222 extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    public double latitude,longitude;
    private String selected_vehicle_str,selected_state_str,selected_cause_str;
    private String vehicle_number_1,vehicle_number_2,vehicle_number_3,vehicle_number_4;
    private Spinner vehicle_spinner, my_spinner, cause_spinner, abbreviation_spinner, district_codes_spinner;
    private EditText casualties, injuries, city_village, pin_code, district, landmark, cause, vehicle_registration_code, vehicle_4_number;
    private Button report_button;
    private CheckBox live_location;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("accident_reports");
    TextView tv1_testing;

    boolean matchFound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main222);


        tv1_testing = findViewById(R.id.tv1_testing);
        vehicle_4_number = findViewById(R.id.vehicle_4_number);
        vehicle_registration_code = findViewById(R.id.vehicle_registration_code);
        district_codes_spinner = findViewById(R.id.district_codes_spinner);
        abbreviation_spinner = findViewById(R.id.abbreviation_spinner);
        vehicle_spinner = findViewById(R.id.vehicle_spinner);
        my_spinner = findViewById(R.id.my_spinner);
        casualties = findViewById(R.id.casualties);
        injuries = findViewById(R.id.injuries);
        city_village = findViewById(R.id.city_village);
        pin_code = findViewById(R.id.pin_code);
        district = findViewById(R.id.district);
        landmark = findViewById(R.id.landmark);
        cause = findViewById(R.id.cause);
        report_button = findViewById(R.id.report_button);
        live_location = findViewById(R.id.live_location);
        cause_spinner = findViewById(R.id.cause_spinner);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
        vehicle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the spinner
                selected_vehicle_str = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        my_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the spinner
                selected_state_str = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        cause_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the spinner
                selected_cause_str = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vehicle_registration_code.getText().toString().isEmpty() || vehicle_4_number.getText().toString().isEmpty() || city_village.getText().toString().isEmpty() || district.getText().toString().isEmpty()) {
                    if (vehicle_registration_code.getText().toString().isEmpty()) {
                        vehicle_registration_code.setError("This field is required");
                    }
                    if (vehicle_4_number.getText().toString().isEmpty()) {
                        vehicle_4_number.setError("This field is required");
                    }
                    if (city_village.getText().toString().isEmpty()) {
                        city_village.setError("This field is required");
                    }
                    if (district.getText().toString().isEmpty()){
                        district.setError("This field is required");
                    }
                } else {


                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("accident_reports");


                    String vehicleNumber_double = vehicle_4_number.getText().toString().toUpperCase();
                    String vehicleRegistrationCode_double = vehicle_registration_code.getText().toString().toUpperCase();
                    String districtCode_double = district_codes_spinner.getSelectedItem().toString();
                    String abbreviation_double = abbreviation_spinner.getSelectedItem().toString();
                    String full_vehicle_number_double = abbreviation_double + districtCode_double + vehicleRegistrationCode_double + vehicleNumber_double;

                    String injuries_no_str_compare = injuries.getText().toString();
                    String city_OR_village_str_compare = city_village.getText().toString().toUpperCase();

                    usersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // looop through each user node
                            boolean matchFound = false;
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String compare_vehicle_number = ds.child("Vehicle_Number").getValue(String.class);
                                String compare_city = ds.child("City_OR_Village").getValue(String.class);
                                String injuries_count = ds.child("Injuries").getValue(String.class);
                                if (compare_vehicle_number.equals(full_vehicle_number_double) && compare_city.equals(city_OR_village_str_compare) && injuries_count.equals(injuries_no_str_compare)) {
                                    matchFound = true;


                                    Intent intent = new Intent(MainActivity222.this, AdviceActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(MainActivity222.this, "Report is already submitted by someone.", Toast.LENGTH_SHORT).show();

                                    break;

                                }
                            }

                            if (!matchFound) {


                                if (live_location.isChecked()) {
                                    if (ContextCompat.checkSelfPermission(MainActivity222.this, Manifest.permission.SEND_SMS)
                                            == PackageManager.PERMISSION_GRANTED) {
                                        sendSMS();
                                    } else {
                                        ActivityCompat.requestPermissions(MainActivity222.this, new String[]{Manifest.permission.SEND_SMS},
                                                100);
                                    }
                                    String live_location_link = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude;
                                    //tv1_testing.setText(live_location_link);
                                }
                                String vehicleNumber = vehicle_4_number.getText().toString().toUpperCase();
                                String vehicleRegistrationCode = vehicle_registration_code.getText().toString().toUpperCase();
                                String districtCode = district_codes_spinner.getSelectedItem().toString();
                                String abbreviation = abbreviation_spinner.getSelectedItem().toString();
                                String full_vehicle_number = abbreviation + districtCode + vehicleRegistrationCode + vehicleNumber;
                                //root.child("vehicle_type").setValue(selected_vehicle_str);
                                //String vehicle_no_str = vehicle_no.getText().toString();
                                String casualties_no_str = casualties.getText().toString();
                                String injuries_no_str = injuries.getText().toString();
                                String city_OR_village_str = city_village.getText().toString().toUpperCase().toUpperCase();
                                String postal_code_str = pin_code.getText().toString();
                                String district_str = district.getText().toString().toUpperCase();
                                String landmark_str = landmark.getText().toString();
                                String cause_str = cause.getText().toString();
                                HashMap<String, String> reportMap = new HashMap<>();
                                reportMap.put("Cause_of_accident", cause_str);
                                reportMap.put("Selected_Cause", selected_cause_str);
                                reportMap.put("Landmark", landmark_str);
                                reportMap.put("State", selected_state_str);
                                reportMap.put("District", district_str);
                                reportMap.put("Pin_OR_Postal Code", postal_code_str);
                                reportMap.put("City_OR_Village", city_OR_village_str);
                                reportMap.put("Injuries", injuries_no_str);
                                reportMap.put("Casualties", casualties_no_str);
                                reportMap.put("Vehicle_Number", full_vehicle_number);
                                reportMap.put("Vehicle_Type", selected_vehicle_str);
                                root.push().setValue(reportMap);
                                Toast.makeText(MainActivity222.this, "Report Submitted, Thank You!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity222.this, AdviceActivity.class);
                                startActivity(intent);

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(MainActivity222.this, "onCancelled: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sendSMS();
        } else {
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }
    private void sendSMS() {
        String phone = "7385052356";
        String message_2 = "I am Citizen, reporting to an accident in a city.";
        String message = message_2+"This is my live location = "+" "+"http://maps.google.com/maps?q=loc:" + latitude + "," + longitude;
        if (!phone.isEmpty() && !message.isEmpty()){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,message,null,null);
            Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Please enter phone and message", Toast.LENGTH_SHORT).show();
        }
    }
    private void requestLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(2000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        }, null);
    }

}