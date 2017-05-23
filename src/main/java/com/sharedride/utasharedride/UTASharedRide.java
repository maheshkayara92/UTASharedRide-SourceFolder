package com.sharedride.utasharedride;

import android.app.Application;

/**
 * Created by Mahesh Kayara on 4/26/16.
 */
public class UTASharedRide extends Application {

    private String loggeduserid;
    private String userpwd;
    private String usertype;
    private String classcontext;

    public String getloggeduserid() {
        return loggeduserid;
    }

    public void setloggeduserid(String loggeduserid) {
        this.loggeduserid = loggeduserid;
    }

    public String getuserpwd() {
        return userpwd;
    }

    public void setuserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getusertype() {
        return usertype;
    }

    public void setusertype(String usertype) {
        this.usertype = usertype;
    }

    public void setcontextclass(String classcontext){
        this.classcontext = classcontext;
    }

    public String getcontextclass(){
        return classcontext;
    }
}
