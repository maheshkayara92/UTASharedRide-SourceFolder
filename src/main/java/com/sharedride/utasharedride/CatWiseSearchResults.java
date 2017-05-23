package com.sharedride.utasharedride;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Mahesh Kayara on 4/16/16.
 */
public class CatWiseSearchResults extends Activity {

    String subCatCode;
    ProgressDialog prgDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subCatCode = getIntent().getExtras().getString("subcategory");
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        if(subCatCode.equals("11"))
        {
            Intent intent=new Intent(CatWiseSearchResults.this,BookRide.class);
            startActivity(intent);
        }
        if(subCatCode.equals("17"))
        {
            Intent intent=new Intent(CatWiseSearchResults.this,FutureRideDetails.class);
            startActivity(intent);
        }
        if(subCatCode.equals("10"))
        {
            Intent intent=new Intent(CatWiseSearchResults.this,UpdateProfile.class);
            startActivity(intent);
        }
		if(subCatCode.equals("13"))
        {
            Intent intent=new Intent(CatWiseSearchResults.this,JoinRide.class);
            startActivity(intent);
        }
        if(subCatCode.equals("18"))
        {
            Intent intent=new Intent(CatWiseSearchResults.this,PastRideDetails.class);
            startActivity(intent);
        }
    }
}