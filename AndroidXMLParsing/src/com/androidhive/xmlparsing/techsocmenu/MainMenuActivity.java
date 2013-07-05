package com.androidhive.xmlparsing.techsocmenu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidhive.xmlparsing.news.AndroidNewsFeedActivity;

import com.androidhive.xmlparsing.R;
import android.widget.AdapterView.OnItemClickListener;

import com.androidhive.xmlparsing.projects.AndroidProjectsMenuActivity;
import com.androidhive.xmlparsing.settings.NewsPreferencesActivity;

public class MainMenuActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";

    static final String[] items_text = new String[] {
            "News", "Events", "Projects", "Games" };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.title);
        item.setTitle("Menu");

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

        setContentView(R.layout.activity_main);

        checkAndUpdateSharedPreference();


        GridView gridview = (GridView) findViewById(R.id.gridview);

        gridview.setAdapter(new MenuItemsAdapter(this,items_text));

        gridview.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch(i) {

                    case 0: {
                        intent = new Intent(MainMenuActivity.this, AndroidNewsFeedActivity.class);
                        MainMenuActivity.this.startActivity(intent);
                    }
                    break;

                    case 2: {
                        intent = new Intent(MainMenuActivity.this, AndroidProjectsMenuActivity.class);
                        MainMenuActivity.this.startActivity(intent);
                    }

                }

            }


        });
    }

    private void checkAndUpdateSharedPreference() {

        // Restore preferences
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        boolean bbcSource = mySharedPreferences.getBoolean("checkbox_source_bbc", false);
        boolean redditSource = mySharedPreferences.getBoolean("checkbox_source_reddit", false);

        if ((bbcSource==false)&&(redditSource==false)) {

            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putBoolean("checkbox_source_bbc", true);
            editor.putBoolean("checkbox_source_reddit", true);

            // Commit the edits!
            editor.commit();

        }

    }

    
}
