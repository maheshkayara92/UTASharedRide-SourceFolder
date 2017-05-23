package com.sharedride.utasharedride;

/**
 * Created by Mahesh Kayara on 4/8/16.
 */
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends Activity {

    ProgressDialog prgDialog;
    TextView errorMsg;
    EditText emailET;
    EditText pwdET;
    String usrtype;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle(R.string.app_name);
        errorMsg = (TextView)findViewById(R.id.login_error);
        emailET = (EditText)findViewById(R.id.loginEmail);
        pwdET = (EditText)findViewById(R.id.loginPassword);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        usrtype=null;
    }

    public void loginUser(View view){
        String email = emailET.getText().toString();
        String password = pwdET.getText().toString();
        ((UTASharedRide) this.getApplication()).setloggeduserid(email);
        ((UTASharedRide) this.getApplication()).setuserpwd(password);
        RequestParams params = new RequestParams();
        if(Utility.isNotNull(email) && Utility.isNotNull(password)){
            if(Utility.validate(email)){
                params.put("username", email);
                params.put("password", password);
                invokeWS(params);
            }
            else{
                errorMsg.setText("Please enter valid email");
                      //  Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        } else{
            errorMsg.setText("Please fill the form, don't leave any field blank");
           // Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }

    public void invokeWS(RequestParams params){
        prgDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHandle requestHandle = client.get("http://10.4.100.9:8080/SharedRide/login/dologin", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                prgDialog.hide();
                try {
                    String response = new String(bytes);
                    JSONObject obj = new JSONObject(response);
                    int userstatus=obj.getInt("userstatus");
                    usrtype=obj.getString("usertype");
                    if (obj.getBoolean("status")) {
                        if (userstatus == 1) {
                            Intent loginIntent = new Intent(getApplicationContext(),HomeScreen.class);
                            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            loginIntent.putExtra("Usertype",usrtype);
                            startActivity(loginIntent);
                        }
                        else if(userstatus == 0){
                            Intent contentIntent = new Intent(getApplicationContext(),CreateProfile.class);
                            contentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(contentIntent);
                        }
                    }
                    else{
                        if(userstatus == -1) {
                            errorMsg.setText("You are not Registered.Please Sign Up");
                        }
                        else if(userstatus==-2){
                            errorMsg.setText("Invalid Password");
                        }
                        else{
                            errorMsg.setText("Error Occured");
                        }
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

    public void navigatetoRegisterActivity(View view){
        Intent loginIntent = new Intent(getApplicationContext(),RegisterActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }
}
