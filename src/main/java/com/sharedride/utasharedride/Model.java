package com.sharedride.utasharedride;

/**
 * Created by Mahesh Kayara on 4/16/16.
 */
public class Model {

    String Rideno;
    String Ridername;
    String Riderutaid;
    String Ridedate;
    int value;
    Model(String Rideno,String Ridername, String Riderutaid, String Ridedate){
        this.Rideno = Rideno;
        this.Ridername = Ridername;
        this.Riderutaid = Riderutaid;
        this.Ridedate = Ridedate;
    }
    public String getRideno(){
        return Rideno;
    }

    public String getRidername(){
        return Ridername;
    }

    public String getRiderutaid(){
        return Riderutaid;
    }

    public String getRidedate(){
        return Ridedate;
    }
}
