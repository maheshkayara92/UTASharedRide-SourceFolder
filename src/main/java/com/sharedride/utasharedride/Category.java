package com.sharedride.utasharedride;

/**
 * Created by Mahesh Kayara on 4/15/16.
 */
public class Category {

    private String cat_name;
    private int cat_code;

    public void setCatName(String cat_name)
    {
     this.cat_name=cat_name;
    }

    public String getCatName()
    {
        return cat_name;
    }

    public void setCatCode(int cat_code)
    {
        this.cat_code=cat_code;
    }

    public int getCatCode()
    {
        return cat_code;
    }
}
