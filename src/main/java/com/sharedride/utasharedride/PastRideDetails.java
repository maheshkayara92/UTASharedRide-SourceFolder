package com.sharedride.utasharedride;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mahesh Kayara on 4/29/16.
 */
public class PastRideDetails extends AppCompatActivity {

    ListView lv;
    Model[] modelItems;
    CustomAdapter adapter;
    ProgressDialog prgDialog;
    RequestParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.futureridedetails);
        lv = (ListView) findViewById(R.id.futureridelist);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        params = new RequestParams();
        params.put("userid",((UTASharedRide) this.getApplication()).getloggeduserid());
        invokeWS(this);
    }

    public void invokeWS(final Context c){
        prgDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.4.100.9:8080/SharedRide/pastrides/dopastrides", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                prgDialog.hide();
                try {
                    JSONObject objcheck = response.getJSONObject(0);
                    if (!objcheck.getString("RideNo").equals("-100")) {
                        modelItems = new Model[response.length()];
                        for (int n = 0; n < response.length(); n++) {
                            JSONObject obj = response.getJSONObject(n);
                            String ridenum = obj.getString("RideNo");
                            String ridername = obj.getString("RiderName");
                            String riderutaid = obj.getString("RiderUTAId");
                            String ridedate = obj.getString("RideDate");
                            Model ml = new Model(ridenum, ridername, riderutaid, ridedate);
                            modelItems[n] = ml;
                        }
                        adapter = new CustomAdapter(c, modelItems);
                        lv.setAdapter(adapter);
                        Helper.getListViewSize(lv);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry No Past Rides are Available", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    prgDialog.hide();
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
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
