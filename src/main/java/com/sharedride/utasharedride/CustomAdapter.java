package com.sharedride.utasharedride;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by riju on 4/16/16.
 */
public class CustomAdapter extends ArrayAdapter {

    Model[] modelItems = null;
    Context context;
    Integer selected_position = -1;
    TextView name1;
    TextView name2;
    TextView name3;
    TextView name4;
    CheckBox cb;
    String rideno;
    RequestParams params;
    RideDetails selectedride;
    UserDetails[] commuters;
    boolean ischeck=false;
    RideView rvw;
    ProgressDialog prgDialog;

    public CustomAdapter(Context context, Model[] resource) {
        super(context, R.layout.row, resource);
        this.context = context;
        this.modelItems = resource;
        params = new RequestParams();
        prgDialog = new ProgressDialog(context);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row, parent, false);
        name1 = (TextView) convertView.findViewById(R.id.column_value1);
        name2 = (TextView) convertView.findViewById(R.id.column_value2);
        name3 = (TextView) convertView.findViewById(R.id.column_value3);
        name4 = (TextView) convertView.findViewById(R.id.column_value4);
        cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        name1.setText(modelItems[position].getRideno());
        name2.setText(modelItems[position].getRidername());
        name3.setText(modelItems[position].getRiderutaid());
        name4.setText(modelItems[position].getRidedate());
        if (position == selected_position) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selected_position = position;
                    ischeck=true;
                    rideno=modelItems[selected_position].getRideno();
                    params.put("rideNo", rideno);
                    invokeWS();
                } else {
                    selected_position = -1;
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public void invokeWS(){
        prgDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.4.100.9:8080/SharedRide/futurerides/doselectedride",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                prgDialog.hide();
                try{
                selectedride=new RideDetails();
                JSONObject obj = response.getJSONObject("Ride");
                selectedride.setrideNo(obj.getInt("RideNo"));
                selectedride.setriderName(obj.getString("RiderName"));
                selectedride.setriderUtaId(obj.getLong("RiderUTAId"));
                selectedride.setrideDate(obj.getString("RideDate"));
                selectedride.setpickPoint(obj.getString("PickPoint"));
                selectedride.setDestination(obj.getString("Destination"));
                selectedride.setvehicleName(obj.getString("VehicleName"));
                selectedride.setvehicleCapacity(obj.getString("VehicleCapacity"));
                selectedride.setpassengerCount(obj.getInt("PassengerCount"));
                selectedride.setchargeperMile(obj.getDouble("ChargePermile"));
                selectedride.setrideStatus(obj.getString("RideStatus"));
                selectedride.setComments(obj.optString("Comments"));
                selectedride.setridebookedDate(obj.getString("RideBookedDate"));
                selectedride.settotalDistance(obj.getDouble("TotalDistance"));
                selectedride.settotalCharge(obj.getDouble("TotalCharge"));
                selectedride.setchargeperHead(obj.getDouble("ChargePerhead"));
                selectedride.setnotifiedYN(obj.getString("NotifedYN"));
                JSONArray objarry= response.getJSONArray("Commuters");
                commuters= new UserDetails[objarry.length()];
                    for (int n = 0; n < objarry.length(); n++) {
                        JSONObject aryobj = objarry.getJSONObject(n);
                        commuters[n]=new UserDetails();
                        commuters[n].setfirstName(aryobj.getString("FirstName"));
                        commuters[n].setUtaid(aryobj.getLong("UTAId"));
                        commuters[n].setMobileno(aryobj.getLong("MobileNo"));
                        commuters[n].setUserid(aryobj.getString("EmailId"));
                    }
                    rvw = new RideView(selectedride,commuters);
                    Intent intent=new Intent(context,SelectedRideDetails.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("Rideview", rvw);
                    intent.putExtras(mBundle);
                    context.startActivity(intent);
                }catch (JSONException e) {
                    prgDialog.hide();
                    Toast.makeText(context, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                prgDialog.hide();
                if (i == 404) {
                    Toast.makeText(context, "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (i == 500) {
                    Toast.makeText(context, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
