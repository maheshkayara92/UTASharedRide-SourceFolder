package com.sharedride.utasharedride;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Created by Mahesh Kayara on 4/24/16.
 */
public class BookRide extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener,OnItemSelectedListener {

    ProgressDialog prgDialog;
    TextView bridernamevw;
    Spinner bridernamespinner;
    TextView briderutaidvw;
    TextView briderutaidvalvw;
    TextView bridedatevw;
    EditText bridedatevaltxt;
    EditText bridetimevaltxt;
    TextView bpickpointvw;
    EditText bpickpointvaltxt;
    TextView bdestinationvw;
    EditText bdestinationvaltxt;
    TextView bvehiclenamevw;
    TextView bvehiclenamevalvw;
    TextView bvehiclecapacityvw;
    TextView bvehiclecapacityvalvw;
    TextView bcommentsvw;
    EditText bcommentsvaltxt;
    Calendar myCalendar;
    List<String> riders;
    ArrayAdapter<String> dataAdapter;
    RequestParams params;
    String textname;
    String colored="*";
    int start,end;
    SpannableStringBuilder builder;
    String item;
    String utaid,rdate,ppoint,dest,vname,vcapacity,comments, rtime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookride);
        riders = new ArrayList<String>();
        bridernamevw = (TextView)findViewById(R.id.bridername);
        textname="RiderName:";
        builder = new SpannableStringBuilder();
        builder.append(textname);
        start = builder.length();
        builder.append(colored);
        end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bridernamevw.setText(builder);
        briderutaidvw = (TextView)findViewById(R.id.briderutaid);
        briderutaidvalvw = (TextView)findViewById(R.id.briderutaidval);
        bridedatevw = (TextView)findViewById(R.id.bridedate);
        textname="RideDate:";
        builder = new SpannableStringBuilder();
        builder.append(textname);
        start = builder.length();
        builder.append(colored);
        end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bridedatevw.setText(builder);
        bpickpointvw = (TextView)findViewById(R.id.bpickpoint);
        textname="PickupPoint:";
        builder = new SpannableStringBuilder();
        builder.append(textname);
        start = builder.length();
        builder.append(colored);
        end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bpickpointvw.setText(builder);
        bdestinationvw = (TextView)findViewById(R.id.bdestination);
        textname="Destination:";
        builder = new SpannableStringBuilder();
        builder.append(textname);
        start = builder.length();
        builder.append(colored);
        end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bdestinationvw.setText(builder);
        bvehiclenamevw = (TextView)findViewById(R.id.bvehiclename);
        bvehiclenamevalvw = (TextView)findViewById(R.id.bvehiclenameval);
        bvehiclecapacityvw = (TextView)findViewById(R.id.bvehiclecapacity);
        bvehiclecapacityvalvw = (TextView)findViewById(R.id.bvehiclecapacityval);
        bcommentsvw = (TextView)findViewById(R.id.bcomments);
        bpickpointvaltxt = (EditText)findViewById(R.id.bpickpointval);
        bdestinationvaltxt = (EditText)findViewById(R.id.bdestinationval);
        bridedatevaltxt = (EditText)findViewById(R.id.bridedateval);
        bridetimevaltxt = (EditText)findViewById(R.id.bridetimeval);
        bcommentsvaltxt = (EditText)findViewById(R.id.bcommentsval);
        bridernamespinner = (Spinner) findViewById(R.id.briderspinner);
        bridernamespinner.setOnItemSelectedListener(this);
        myCalendar = Calendar.getInstance();
        bridedatevaltxt.setOnClickListener(this);
        bridetimevaltxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookRide.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String min=Integer.toString(selectedMinute),hr=Integer.toString(selectedHour);
                        if (selectedMinute < 10)
                            min="0"+selectedMinute;
                        if (selectedHour < 10)
                            hr="0"+selectedHour;
                        bridetimevaltxt.setText( hr + "," +min + "," +"00");
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        fetchRiderNames();
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, riders);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bridernamespinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        bridernamespinner.setSelection(position);
        item = parent.getItemAtPosition(position).toString();
        String[] names = item.split(",");
        String firstname=names[1];
        String lastname=names[0];
        params = new RequestParams();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.4.100.9:8080/SharedRide/riderlist/doriderdet", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                prgDialog.hide();
                try {
                    briderutaidvalvw.setText(String.valueOf(response.getString("riderutaid")));
                    bvehiclenamevalvw.setText(String.valueOf(response.getString("vehiclename")));
                    bvehiclecapacityvalvw.setText(String.valueOf(response.getString("vehiclecapacity")));
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
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //String myFormat = "MMddyyyy";
        String mnth=Integer.toString(monthOfYear), day=Integer.toString(dayOfMonth);
        //SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        if(monthOfYear<10)
            mnth="0"+monthOfYear;
        if(dayOfMonth<10)
            day="0"+dayOfMonth;
        bridedatevaltxt.setText(mnth+","+day+","+year);
    }


    @Override
    public void onClick(View v) {
        new DatePickerDialog(this, this, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void fetchRiderNames(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.4.100.9:8080/SharedRide/riderlist/doriders", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                prgDialog.hide();
                try {
                    String objcheck = response.getString(0);
                    if (!objcheck.equals("-100")) {
                        for (int n = 0; n < response.length(); n++) {
                            String obj = response.getString(n);
                            riders.add(obj);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry No Riders are Available", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    prgDialog.hide();
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                dataAdapter.notifyDataSetChanged();
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

    public void Ridebook(View view)
    {
        if(bridedatevaltxt.getText().toString().trim().equals("")) {
            bridedatevaltxt.setError("Ride Date is required!");
        }
        else if(bpickpointvaltxt.getText().toString().trim().equals(""))
        {
            bpickpointvaltxt.setError("Pickup Point is required!");
        }
        else if(bdestinationvaltxt.getText().toString().trim().equals(""))
        {
            bdestinationvaltxt.setError("Destination is required!");
        }
        else
        {
            utaid=briderutaidvalvw.getText().toString();
            rdate=bridedatevaltxt.getText().toString();
            rtime=bridetimevaltxt.getText().toString();
            rdate=rdate+","+rtime;
            ppoint=bpickpointvaltxt.getText().toString();
            dest=bdestinationvaltxt.getText().toString();
            vname=bvehiclenamevalvw.getText().toString();
            vcapacity=bvehiclecapacityvalvw.getText().toString();
            comments=bcommentsvaltxt.getText().toString();
            params = new RequestParams();
            params.put("Ridername", item);
            params.put("Utaid", utaid);
            params.put("Ridedate", rdate);
            params.put("Pickpoint", ppoint);
            params.put("Destination", dest);
            params.put("Vname", vname);
            params.put("Vcapacity", vcapacity);
            params.put("Comments", comments);
            params.put("Commuterid",((UTASharedRide) this.getApplication()).getloggeduserid());
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://10.4.100.9:8080/SharedRide/bookride/dobooking", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        prgDialog.hide();
                        commutername = response.getString("CommuterName");
                        rideremail = response.getString("RiderEmailId");
                        ridermobileno = response.getString("RiderMobileNo");
                        boolean result = sendConfirmationMessageToRider(commutername, ridermobileno);
                        if (result) {
                            Toast.makeText(getApplicationContext(), "Ride has been booked Successfully. Message has been sent to Rider to Respond!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Ride has been booked Successfully. Message could not be sent to Rider!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
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

    public boolean sendConfirmationMessageToRider(String commutername, String ridermobileno) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(ridermobileno, null, "Hello, You have a ride booked by" + commutername + ".Please Accept or reject the ride by using app.", null, null);
        return true;
    }

}
