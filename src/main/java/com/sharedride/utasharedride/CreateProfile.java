package com.sharedride.utasharedride;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by MaheshKayara on 4/24/2016.
 */
public class CreateProfile extends AppCompatActivity {
    ProgressDialog prgDialog;
    EditText FirstName, LastName, StudentID, MobileNumber, LicenseNumber, VehicleNumber, VehicleName, VehicleCapacity, ChargePerMile;
    TextView LicenseNumberTextView, VehicleNumberTextView, VehicleNameTextView, VehicleCapacityTextView, ChargePerMileTextView;
    RadioButton Male, Female, Commuter, Rider;
    RadioGroup radioGroupUser, radioGroupGender;
    Button SubmitAsCommuter, SubmitAsRider;
    String genderType,userTypeValue, firstNameValue, lastNameValue, studentIDValue, mobileNumberValue;
    String licenseNumberValue, vehicleNumberValue, vehicleNameValue, vehicleCapacityValue, chargePerMileValue;

    JSONObject jsonObject;
    RequestParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createprofile);
        FirstName = (EditText) findViewById(R.id.editTextFirstName);
        LastName = (EditText) findViewById(R.id.editTextLastName);
        StudentID = (EditText) findViewById(R.id.editTextStudentID);
        MobileNumber = (EditText) findViewById(R.id.editTextMobileNumber);
        LicenseNumber = (EditText) findViewById(R.id.editTextLicenseNumber);
        VehicleNumber = (EditText) findViewById(R.id.editTextVehicleNumber);
        VehicleName = (EditText) findViewById(R.id.editTextVehicleName);
        VehicleCapacity = (EditText) findViewById(R.id.editTextVehicleCapacity);
        ChargePerMile = (EditText) findViewById(R.id.editTextChargePerMile);

        LicenseNumberTextView = (TextView) findViewById(R.id.LicenseNumber);
        VehicleNumberTextView = (TextView) findViewById(R.id.VehicleNumber);
        VehicleNameTextView = (TextView) findViewById(R.id.VehicleName);
        VehicleCapacityTextView = (TextView) findViewById(R.id.VehicleCapacity);
        ChargePerMileTextView = (TextView) findViewById(R.id.ChargePerMile);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);

        Male = (RadioButton) findViewById(R.id.radioMale);
        Female = (RadioButton) findViewById(R.id.radioFemale);
        Commuter = (RadioButton) findViewById(R.id.radioCommuter);
        Rider = (RadioButton) findViewById(R.id.radioRider);
        SubmitAsCommuter = (Button) findViewById(R.id.buttonSubmitAsCommuter);
        SubmitAsRider = (Button) findViewById(R.id.buttonSubmitAsRider);

        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        radioGroupUser = (RadioGroup) findViewById(R.id.radioGroupUser);

        LicenseNumberTextView.setVisibility(View.GONE);
        VehicleNumberTextView.setVisibility(View.GONE);
        VehicleNameTextView.setVisibility(View.GONE);
        VehicleCapacityTextView.setVisibility(View.GONE);
        ChargePerMileTextView.setVisibility(View.GONE);
        LicenseNumber.setVisibility(View.GONE);
        VehicleNumber.setVisibility(View.GONE);
        VehicleName.setVisibility(View.GONE);
        VehicleCapacity.setVisibility(View.GONE);
        ChargePerMile.setVisibility(View.GONE);
        SubmitAsRider.setVisibility(View.GONE);

        genderType="Male";
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioMale) {
                    genderType = "Male";
                } else if (checkedId == R.id.radioFemale) {
                    genderType = "Female";
                }
            }
        });
        radioGroupUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioRider) {
                    SubmitAsCommuter.setVisibility(View.GONE);
                    LicenseNumberTextView.setVisibility(View.VISIBLE);
                    VehicleNumberTextView.setVisibility(View.VISIBLE);
                    VehicleNameTextView.setVisibility(View.VISIBLE);
                    VehicleCapacityTextView.setVisibility(View.VISIBLE);
                    ChargePerMileTextView.setVisibility(View.VISIBLE);
                    LicenseNumber.setVisibility(View.VISIBLE);
                    VehicleNumber.setVisibility(View.VISIBLE);
                    VehicleName.setVisibility(View.VISIBLE);
                    VehicleCapacity.setVisibility(View.VISIBLE);
                    ChargePerMile.setVisibility(View.VISIBLE);
                    SubmitAsRider.setVisibility(View.VISIBLE);
                    userTypeValue = "Rider";
                } else if (checkedId == R.id.radioCommuter) {
                    LicenseNumberTextView.setVisibility(View.GONE);
                    VehicleNumberTextView.setVisibility(View.GONE);
                    VehicleNameTextView.setVisibility(View.GONE);
                    VehicleCapacityTextView.setVisibility(View.GONE);
                    ChargePerMileTextView.setVisibility(View.GONE);
                    LicenseNumber.setVisibility(View.GONE);
                    VehicleNumber.setVisibility(View.GONE);
                    VehicleName.setVisibility(View.GONE);
                    VehicleCapacity.setVisibility(View.GONE);
                    ChargePerMile.setVisibility(View.GONE);
                    SubmitAsRider.setVisibility(View.GONE);
                    SubmitAsCommuter.setVisibility(View.VISIBLE);
                    userTypeValue = "Commuter";
                }
            }
        });
    }

    public void SubmitAsCommuterMethod(View view) {
        userTypeValue = "Commuter";
    //    setUserType(userTypeValue);
        params = new RequestParams();
        sendValues(getProfileValues(userTypeValue));
    }

    public void SubmitAsRiderMethod(View view) {
        userTypeValue = "Rider";
  //      setUserType(userTypeValue);
        params = new RequestParams();
        sendValues(getProfileValues(userTypeValue));
    }

    public JSONObject getProfileValues(String userValue) {
        jsonObject = new JSONObject();
        userTypeValue = userValue;
        try {
            if (userTypeValue == "Commuter") {
                firstNameValue = FirstName.getText().toString();
                lastNameValue = LastName.getText().toString();
                studentIDValue = StudentID.getText().toString();
                mobileNumberValue = MobileNumber.getText().toString();
                jsonObject.put("FirstName", firstNameValue);
                jsonObject.put("LastName", lastNameValue);
                jsonObject.put("StudentID", studentIDValue);
                jsonObject.put("MobileNumber", mobileNumberValue);
                jsonObject.put("Gender", genderType);
                jsonObject.put("UserType", userTypeValue);
                jsonObject.put("UserId",((UTASharedRide) this.getApplication()).getloggeduserid());
            } else if (userTypeValue == "Rider") {
                firstNameValue = FirstName.getText().toString();
                lastNameValue = LastName.getText().toString();
                studentIDValue = StudentID.getText().toString();
                mobileNumberValue = MobileNumber.getText().toString();
                licenseNumberValue = LicenseNumber.getText().toString();
                vehicleNumberValue = VehicleNumber.getText().toString();
                vehicleNameValue = VehicleName.getText().toString();
                vehicleCapacityValue = VehicleCapacity.getText().toString();
                chargePerMileValue = ChargePerMile.getText().toString();
                jsonObject.put("FirstName", firstNameValue);
                jsonObject.put("LastName", lastNameValue);
                jsonObject.put("StudentID", studentIDValue);
                jsonObject.put("MobileNumber", mobileNumberValue);
                jsonObject.put("Gender", genderType);
                jsonObject.put("UserType", userTypeValue);
                jsonObject.put("UserId",((UTASharedRide) this.getApplication()).getloggeduserid());
                jsonObject.put("LicenseNumber", licenseNumberValue);
                jsonObject.put("VehicleNumber", vehicleNumberValue);
                jsonObject.put("VehicleName", vehicleNameValue);
                jsonObject.put("VehicleCapacity", vehicleCapacityValue);
                jsonObject.put("ChargePerMile", chargePerMileValue);
            }
        } catch (JSONException jsonexception) {
            jsonexception.printStackTrace();
        }
        return jsonObject;
    }

    public void sendValues(final JSONObject jsonObject) {
        prgDialog.show();
        params.put("userprofile", jsonObject);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHandle requestHandle = client.get("http://10.4.100.9:8080/SharedRide/createprofile/docreateprofile", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                prgDialog.hide();
                try {
                    String response = new String(bytes);
                    JSONObject obj = new JSONObject(response);
                    int userstatus=obj.getInt("userstatus");
                    if (obj.getBoolean("status")) {
                        if (userstatus == 1) {
                            Toast.makeText(getApplicationContext(), "Profile Created Successfully!!!", Toast.LENGTH_LONG).show();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent loginIntent = new Intent(getApplicationContext(),HomeScreen.class);
                            loginIntent.putExtra("Usertype",userTypeValue);
                            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(loginIntent);
                        }
                    }
                    else{

                    }
                } catch (JSONException e) {
                    prgDialog.hide();
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                prgDialog.hide();
                if (i == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (i == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

   /* public void setUserType(String userValue){
        userTypeValue = userValue;
    }

    public String getUserType(){
        if (userTypeValue.equals("")){
            userTypeValue = "Commuter";
        }
        return userTypeValue;
    }*/
}