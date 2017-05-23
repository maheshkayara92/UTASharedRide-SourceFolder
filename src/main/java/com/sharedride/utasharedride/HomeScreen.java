package com.sharedride.utasharedride;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mahesh Kayara on 4/14/16.
 */
public class HomeScreen extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView mCategoryList;

    private ArrayList<Category> category_name = new ArrayList<Category>();
    private ArrayList<ArrayList<SubCategory>> subcategory_name = new ArrayList<ArrayList<SubCategory>>();
    private ArrayList<Integer> subcatcount = new ArrayList<Integer>();

    int previousGroup;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Intent i = getIntent();
        ((UTASharedRide) this.getApplication()).setusertype(getIntent().getExtras().getString("Usertype"));
        this.getCatData();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mCategoryList = (ExpandableListView) findViewById(R.id.left_drawer);
        mCategoryList.setAdapter(new expandableListViewAdapter(HomeScreen.this, category_name, subcategory_name, subcatcount));

        mCategoryList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    if (groupPosition != previousGroup) {
                        parent.collapseGroup(previousGroup);
                    }
                    previousGroup = groupPosition;
                    parent.expandGroup(groupPosition);
                }
                parent.smoothScrollToPosition(groupPosition);
                return true;
            }
        });

        mCategoryList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Intent intent=new Intent(HomeScreen.this,CatWiseSearchResults.class);
                ArrayList<SubCategory> tempList = new ArrayList<SubCategory>();
                tempList = subcategory_name.get(groupPosition);
                intent.putExtra("subcategory",tempList.get(childPosition).getSubCatCode());
                startActivity(intent);
                mDrawerLayout.closeDrawer(mCategoryList);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homemenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    public class expandableListViewAdapter extends BaseExpandableListAdapter {
        private LayoutInflater layoutInflater;
        private ArrayList<Category> categoryName = new ArrayList<Category>();
        private ArrayList<ArrayList<SubCategory>> subCategoryName = new ArrayList<ArrayList<SubCategory>>();
        ArrayList<Integer> subCategoryCount = new ArrayList<Integer>();
        int count;
        SubCategory singleChild = new SubCategory();

        public expandableListViewAdapter(Context context, ArrayList<Category> categoryName, ArrayList<ArrayList<SubCategory>> subCategoryName, ArrayList<Integer> subCategoryCount) {
            layoutInflater = LayoutInflater.from(context);
            this.categoryName = categoryName;
            this.subCategoryName = subCategoryName;
            this.subCategoryCount = subCategoryCount;
            this.count = categoryName.size();
        }

        public void onGroupCollapsed(int groupPosition) {
            super.onGroupCollapsed(groupPosition);
        }

        public void onGroupExpanded(int groupPosition) {
            super.onGroupExpanded(groupPosition);
        }

        public int getGroupCount() {
            return categoryName.size();
        }

        public int getChildrenCount(int i) {
            return (subCategoryCount.get(i));
        }

        public Object getGroup(int i) {
            return categoryName.get(i).getCatName();
        }

        public SubCategory getChild(int i, int i1) {
            ArrayList<SubCategory> tempList = new ArrayList<SubCategory>();
            tempList = subCategoryName.get(i);
            return tempList.get(i1);
        }

        public long getGroupId(int i) {
            return i;
        }

        public long getChildId(int i, int i1) {
            return i1;
        }

        public boolean hasStableIds() {
            return true;
        }

        public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.expandablelistcategory, viewGroup, false);
            }

            TextView textView = (TextView) view.findViewById(R.id.cat_desc_1);
            textView.setText(getGroup(i).toString());
            return view;
        }

        public View getChildView(int i, int i1, boolean isExpanded, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.expandablelistsubcat, viewGroup, false);

            }

            singleChild = getChild(i, i1);
            TextView childSubCategoryName = (TextView) view.findViewById(R.id.subcat_name);
            childSubCategoryName.setText(singleChild.getSubCatName());
            return view;
        }

        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        public boolean areAllItemsEnabled() {
            return true;
        }

    }

    public void getCatData() {
        category_name.clear();
        Category categoryDetails = new Category();
        categoryDetails.setCatCode(1);
        categoryDetails.setCatName("Profile Management");
        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(2);
        categoryDetails.setCatName("Ride Management");
        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(3);
        categoryDetails.setCatName("View Rides");
        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(4);
        categoryDetails.setCatName("Ratings&Feedback Management");
        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(5);
        categoryDetails.setCatName("View Booking Request");
        category_name.add(categoryDetails);

        subcategory_name.clear();

        ArrayList<SubCategory> subCategoryMatches = new ArrayList<SubCategory>();
        SubCategory subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Update Profile");
        subCategoryMatch.setSubCatCode("10");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subcatcount.add(subCategoryMatches.size());

        subCategoryMatches = new ArrayList<SubCategory>();
        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Book Ride");
        subCategoryMatch.setSubCatCode("11");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Update Ride");
        subCategoryMatch.setSubCatCode("12");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Join Ride");
        subCategoryMatch.setSubCatCode("13");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Provide Ride");
        subCategoryMatch.setSubCatCode("14");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Exit Ride");
        subCategoryMatch.setSubCatCode("15");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Cancel Ride");
        subCategoryMatch.setSubCatCode("16");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subcatcount.add(subCategoryMatches.size());

        subCategoryMatches = new ArrayList<SubCategory>();
        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Future Rides");
        subCategoryMatch.setSubCatCode("17");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Past Rides");
        subCategoryMatch.setSubCatCode("18");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subcatcount.add(subCategoryMatches.size());

        subCategoryMatches = new ArrayList<SubCategory>();
        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Submit Ratings");
        subCategoryMatch.setSubCatCode("19");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("View Ratings");
        subCategoryMatch.setSubCatCode("20");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subcatcount.add(subCategoryMatches.size());

    }
}
