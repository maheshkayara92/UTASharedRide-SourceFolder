package com.sharedride.utasharedride;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mahesh Kayara on 4/22/16.
 */
public class SelectedRideDetails extends AppCompatActivity {

    TextView ridenovw;
    TextView ridernamevw;
    TextView riderutaidvw;
    TextView ridedatevw;
    TextView pickpointvw;
    TextView destinationvw;
    TextView vehiclenamevw;
    TextView vehiclecapacityvw;
    TextView passengercountvw;
    TextView chargepermilevw;
    TextView ridestatusvw;
    TextView commentsvw;
    TextView ridebookeddatevw;
    TextView totaldistancevw;
    TextView totalchargevw;
    TextView chargeperheadvw;
    TextView notifiedynvw;
    ListView commutervew;
    CommuterAdapter Comadapter;
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        RideView ridevw = (RideView) i.getSerializableExtra("Rideview");
        if (((UTASharedRide) this.getApplication()).getcontextclass().equals("JoinRide"))
            setContentView(R.layout.joinedride);
        else
            setContentView(R.layout.selectedride);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        commutervew = (ListView) findViewById(R.id.commuterlist);
        ridenovw = (TextView)findViewById(R.id.ridenoval);
        ridernamevw = (TextView)findViewById(R.id.ridernameval);
        riderutaidvw = (TextView)findViewById(R.id.riderutaidval);
        ridedatevw = (TextView)findViewById(R.id.ridedateval);
        pickpointvw = (TextView)findViewById(R.id.pickpointval);
        destinationvw = (TextView)findViewById(R.id.destinationval);
        vehiclenamevw = (TextView)findViewById(R.id.vehiclenameval);
        vehiclecapacityvw = (TextView)findViewById(R.id.vehiclecapacityval);
        passengercountvw = (TextView)findViewById(R.id.passengercountval);
        chargepermilevw = (TextView)findViewById(R.id.chargepermileval);
        ridestatusvw = (TextView)findViewById(R.id.ridestatusval);
        commentsvw = (TextView)findViewById(R.id.commentsval);
        ridebookeddatevw = (TextView)findViewById(R.id.ridebookeddateval);
        totaldistancevw = (TextView)findViewById(R.id.totaldistanceval);
        totalchargevw = (TextView)findViewById(R.id.totalchargeval);
        chargeperheadvw = (TextView)findViewById(R.id.chargeperheadval);
        notifiedynvw = (TextView)findViewById(R.id.notifiedynval);
        ridenovw.setText(String.valueOf(ridevw.getRideDetails().getrideNo()));
        ridernamevw.setText(String.valueOf(ridevw.getRideDetails().getriderName()));
        riderutaidvw.setText(String.valueOf(ridevw.getRideDetails().getriderUtaId()));
        ridedatevw.setText(String.valueOf(ridevw.getRideDetails().getrideDate()));
        pickpointvw.setText(String.valueOf(ridevw.getRideDetails().getpickPoint()));
        destinationvw.setText(String.valueOf(ridevw.getRideDetails().getDestination()));
        vehiclenamevw.setText(String.valueOf(ridevw.getRideDetails().getvehicleName()));
        vehiclecapacityvw.setText(String.valueOf(ridevw.getRideDetails().getvehicleCapacity()));
        passengercountvw.setText(String.valueOf(ridevw.getRideDetails().getpassengerCount()));
        chargepermilevw.setText(String.valueOf(ridevw.getRideDetails().getchargeperMile()));
        ridestatusvw.setText(String.valueOf(ridevw.getRideDetails().getrideStatus()));
        commentsvw.setText(String.valueOf(ridevw.getRideDetails().getComments()));
        ridebookeddatevw.setText(String.valueOf(ridevw.getRideDetails().getridebookedDate()));
        totaldistancevw.setText(String.valueOf(ridevw.getRideDetails().gettotalDistance()));
        totalchargevw.setText(String.valueOf(ridevw.getRideDetails().gettotalCharge()));
        chargeperheadvw.setText(String.valueOf(ridevw.getRideDetails().getchargeperHead()));
        notifiedynvw.setText(String.valueOf(ridevw.getRideDetails().getnotifiedYN()));
        Comadapter = new CommuterAdapter(this, ridevw.getCommuters());
        commutervew.setAdapter(Comadapter);
        Helper.getListViewSize(commutervew);
    }

    public void sendJoinAckMethod(View view) {
        RequestParams params = new RequestParams();
        params.put("RideNo", ridenovw.getText().toString());
        params.put("UserId", ((UTASharedRide) this.getApplication()).getloggeduserid());

        AsyncHttpClient client = new AsyncHttpClient();
        RequestHandle requestHandle = client.get("http://10.4.100.9:8080/SharedRide/joinrides/dojoinride", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                prgDialog.hide();
                try {
                    String response = new String(bytes);
                    JSONObject obj = new JSONObject(response);
                    int userstatus = obj.getInt("userstatus");

                    if (obj.getBoolean("status")) {
                        Toast.makeText(getApplicationContext(),"Joined Ride Successfully!",Toast.LENGTH_LONG).show();
                        Intent loginIntent = new Intent(getApplicationContext(), JoinRide.class);
                        startActivity(loginIntent);

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
}
