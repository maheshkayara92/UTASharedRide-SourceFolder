package com.sharedride.utasharedride;

import java.io.Serializable;

/**
 * Created by Mahesh Kayara on 4/23/16.
 */
public class RideView implements Serializable {

        RideDetails sride;
        UserDetails[] udetail;

        RideView(RideDetails sride, UserDetails[] udetail)
        {
            this.sride=sride;
            this.udetail=udetail;
        }

    public RideDetails getRideDetails()
    {
        return sride;
    }

    public UserDetails[] getCommuters()
    {
        return udetail;
    }
}
