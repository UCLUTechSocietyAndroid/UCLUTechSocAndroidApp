package com.androidhive.xmlparsing.projects;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidhive.xmlparsing.R;
import com.androidhive.xmlparsing.news.AndroidNewsFeedActivity;
import com.androidhive.xmlparsing.techsocmenu.ActionBarItemNavigation;
import com.androidhive.xmlparsing.techsocmenu.MenuItemsAdapter;

/**
 * Created by user on 7/5/13.
 */
public class AndroidProjectsMenuActivity extends Activity {

    static final String[] items_text = new String[] {
            "Robots", "Mobile Apps", "RepRap", "Raspberry Python" };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.title);
        item.setTitle("Projects");

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(Html.fromHtml(""));
        actionBar.setDisplayHomeAsUpEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // goes through all action bar ids, to do something relatively to the item pressed
        ActionBarItemNavigation itemNavigation = new ActionBarItemNavigation();
        itemNavigation.sortSelectedItemOptions(item, this);

        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.projects_main);



        GridView gridview = (GridView) findViewById(R.id.gridview_projects);

        gridview.setAdapter(new ProjectsMenuItemsAdapter(this,items_text));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch(i) {


                }

            }


        });
    }


}
