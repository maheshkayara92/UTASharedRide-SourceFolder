package com.sharedride.utasharedride;

import java.io.Serializable;

/**
 * Created by Mahesh Kayara on 4/21/16.
 */

public class RideDetails implements Serializable {

    private int rideno;
    private String ridername;
    private long riderutaid;
    private String ridedate;
    private String pickpoint;
    private String destination;
    private String vehiclename;
    private String vehiclecapacity;
    private int passengercount;
    private double chargepermile;
    private String ridestatus;
    private String comments;
    private String ridebookeddate;
    private double totaldistance;
    private double totalcharge;
    private double chargeperhead;
    private String notifiedyn;
    private int riderprofilenumber;

    public void setrideNo(int rideno) {
        this.rideno = rideno;
    }

    public int getrideNo() {
        return rideno;
    }

    public void setriderName(String ridername) {
        this.ridername = ridername;
    }

    public String getriderName() {
        return ridername;
    }

    public void setrideDate(String ridedate) {
        this.ridedate = ridedate;
    }

    public String getrideDate() {
        return ridedate;
    }

    public void setriderUtaId(long riderutaid) {
        this.riderutaid = riderutaid;
    }

    public long getriderUtaId() {
        return riderutaid;
    }

    public void setpickPoint(String pickpoint) {
        this.pickpoint = pickpoint;
    }

    public String getpickPoint() {
        return pickpoint;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setvehicleName(String vehiclename) {
        this.vehiclename = vehiclename;
    }

    public String getvehicleName() {
        return vehiclename;
    }

    public void setvehicleCapacity(String vehiclecapacity) {
        this.vehiclecapacity = vehiclecapacity;
    }

    public String getvehicleCapacity() {
        return vehiclecapacity;
    }

    public void setchargeperMile(double chargepermile) {
        this.chargepermile = chargepermile;
    }

    public double getchargeperMile() {
        return chargepermile;
    }

    public void setpassengerCount(int passengercount) {
        this.passengercount = passengercount;
    }

    public int getpassengerCount() {
        return passengercount;
    }

    public void setrideStatus(String ridestatus) {
        this.ridestatus = ridestatus;
    }

    public String getrideStatus() {
        return ridestatus;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setridebookedDate(String ridebookeddate) {
        this.ridebookeddate = ridebookeddate;
    }

    public String getridebookedDate() {
        return ridebookeddate;
    }

    public void settotalDistance(double totaldistance) {
        this.totaldistance = totaldistance;
    }

    public double gettotalDistance() {
        return totaldistance;
    }

    public void settotalCharge(double totalcharge) {
        this.totalcharge = totalcharge;
    }

    public double gettotalCharge() {
        return totalcharge;
    }

    public void setchargeperHead(double chargeperhead) {
        this.chargeperhead = chargeperhead;
    }

    public double getchargeperHead() {
        return chargeperhead;
    }

    public void setnotifiedYN(String notifiedyn) {
        this.notifiedyn = notifiedyn;
    }

    public String getnotifiedYN() {
        return notifiedyn;
    }

    public void setriderprofileNumber(int riderprofilenumber) {
        this.riderprofilenumber = riderprofilenumber;
    }

    public int getriderprofileNumber() {
        return riderprofilenumber;
    }
}
