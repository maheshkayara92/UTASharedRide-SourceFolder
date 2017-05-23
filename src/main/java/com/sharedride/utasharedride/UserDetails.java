package com.sharedride.utasharedride;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mahesh Kayara on 4/22/16.
 */
public class UserDetails implements Serializable {

    private int profileno;
    private String firstname;
    private String lastname;
    private long utaid;
    private long mobileno;
    private char gender;
    private String usertype;
    private String userstatus;
    private String vehicleno;
    private String vehiclename;
    private String vehiclecapacity;
    private int licenseno;
    private double chargepermile;
    private String profiledate;
    private String userid;

    public void setprofileNo(int profileno) {
        this.profileno = profileno;
    }

    public int getprofileNo() {
        return profileno;
    }

    public void setfirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getfirstName() {
        return firstname;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setlastName(String lastname) {
        this.lastname = lastname;
    }

    public String getlastName() {
        return lastname;
    }

    public void setUtaid(long utaid) {
        this.utaid = utaid;
    }

    public long getUtaId() {
        return utaid;
    }

    public void setMobileno(long mobileno) {
        this.mobileno = mobileno;
    }

    public long getMobileno() {
        return mobileno;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public char getGender() {
        return gender;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUserype() {
        return usertype;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehiclename(String vehiclename) {
        this.vehiclename = vehiclename;
    }

    public String getVehiclename() {
        return vehiclename;
    }

    public void setVehiclecapacity(String vehiclecapacity) {
        this.vehiclecapacity = vehiclecapacity;
    }

    public String getVehiclecapacity() {
        return vehiclecapacity;
    }

    public void setChargepermile(double chargepermile) {
        this.chargepermile = chargepermile;
    }

    public double getChargepermile() {
        return chargepermile;
    }

    public void setLicenseno(int licenseno) {
        this.licenseno = licenseno;
    }

    public int getLicenseno() {
        return licenseno;
    }

    public void setProfiledate(String profiledate) {
        this.profiledate = profiledate;
    }

    public String getProfiledate() {
        return profiledate;
    }
}
