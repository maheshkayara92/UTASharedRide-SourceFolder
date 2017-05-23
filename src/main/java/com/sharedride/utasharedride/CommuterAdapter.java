package com.sharedride.utasharedride;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Mahesh Kayara on 4/23/16.
 */
public class CommuterAdapter extends ArrayAdapter<UserDetails> {

    UserDetails[] commuters = null;
    Context context;
    TextView comname;
    TextView comutaid;
    TextView commobile;
    TextView comemail;

    public CommuterAdapter(Context context, UserDetails[] commuters) {
        super(context, R.layout.commuterow, commuters);
        this.context = context;
        this.commuters = commuters;
    }

    @Override
    public View getView(int position, View commuterView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        commuterView = inflater.inflate(R.layout.commuterow, parent, false);
        comname = (TextView) commuterView.findViewById(R.id.commutername);
        comutaid = (TextView) commuterView.findViewById(R.id.commuterid);
        commobile = (TextView) commuterView.findViewById(R.id.commutermobile);
        comemail = (TextView) commuterView.findViewById(R.id.commuteremail);
        comname.setText(String.valueOf(commuters[position].getfirstName()));
        comutaid.setText(String.valueOf(commuters[position].getUtaId()));
        commobile.setText(String.valueOf(commuters[position].getMobileno()));
        comemail.setText(String.valueOf(commuters[position].getUserid()));
        return commuterView;
    }
}