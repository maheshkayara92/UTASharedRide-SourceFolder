package com.sharedride.utasharedride;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by MaheshKayara on 4/29/2016.
 */
public class JoinRide extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    ProgressDialog prgDialog;
    TextView bridernamevw;
    Spinner bridernamespinner;
    TextView briderutaidvw;
    TextView briderutaidvalvw;
    TextView bridedatevw;
    EditText bridedatevaltxt;
    Calendar myCalendar;
    List<String> riders;
    ArrayAdapter<String> dataAdapter;
    RequestParams params;
    String item;
    String utaid, rdate;
    int i=0;

    ListView lv;
    Model[] modelItems;
    CustomAdapter adapter;
    Button searchButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinridesearch);
        ((UTASharedRide) this.getApplication()).setcontextclass(this.getLocalClassName());
        riders = new ArrayList<String>();
        searchButton = (Button) findViewById(R.id.btnLinkTobookride);
        bridernamevw = (TextView) findViewById(R.id.bridername);
        briderutaidvw = (TextView) findViewById(R.id.briderutaid);
        briderutaidvalvw = (TextView) findViewById(R.id.briderutaidval);
        bridedatevw = (TextView) findViewById(R.id.bridedate);
        bridedatevaltxt = (EditText) findViewById(R.id.bridedateval);
        bridernamespinner = (Spinner) findViewById(R.id.briderspinner);
        bridernamespinner.setOnItemSelectedListener(this);
        myCalendar = Calendar.getInstance();
        bridedatevaltxt.setOnClickListener(this);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        fetchRiderNames();
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, riders);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bridernamespinner.setAdapter(dataAdapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = JoinRide.this;
                SearchRideMethod(c);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (i == 1) {
            bridernamespinner.setSelection(position);
            item = parent.getItemAtPosition(position).toString();
            String[] names = item.split(",");
            String firstname = names[1];
            String lastname = names[0];
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
        i=1;
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //String myFormat = "MMddyyyy";
        monthOfYear++;
        String mnth = Integer.toString(monthOfYear), day = Integer.toString(dayOfMonth);
        //SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        if (monthOfYear < 10)
            mnth = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        bridedatevaltxt.setText(mnth + "/" + day + "/" + year);
    }


    @Override
    public void onClick(View v) {
        new DatePickerDialog(this, this, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void fetchRiderNames() {
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

    public void SearchRideMethod(final Context context) {
        utaid = briderutaidvalvw.getText().toString();
        rdate = bridedatevaltxt.getText().toString();
        setContentView(R.layout.futureridedetails);
        lv = (ListView) findViewById(R.id.futureridelist);
        params = new RequestParams();
        params.put("RiderName", item);
        params.put("RiderUtaid", utaid);
        params.put("RideDate", rdate);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.4.100.9:8080/SharedRide/joinrides/dosearchrides", params, new JsonHttpResponseHandler() {
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
                        adapter = new CustomAdapter(context, modelItems);
                        lv.setAdapter(adapter);
                        Helper.getListViewSize(lv);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry No Future Rides are Available", Toast.LENGTH_LONG).show();
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

