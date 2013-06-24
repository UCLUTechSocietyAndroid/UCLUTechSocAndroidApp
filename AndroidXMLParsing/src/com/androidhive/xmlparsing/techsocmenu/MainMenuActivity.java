package com.androidhive.xmlparsing.techsocmenu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidhive.xmlparsing.news.AndroidNewsFeedActivity;

import com.androidhive.xmlparsing.R;
import android.widget.AdapterView.OnItemClickListener;

public class MainMenuActivity extends Activity {

    static final String[] items_text = new String[] {
            "News", "Events", "Projects", "Games" };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        ActionBar actionBar = getActionBar();
        actionBar.setTitle(Html.fromHtml("<h1>techSoc Menu</h1>"));

        GridView gridview = (GridView) findViewById(R.id.gridview);

        gridview.setAdapter(new MenuItemsAdapter(this,items_text));

        gridview.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {

                    case 0: {
                        Intent intent = new Intent(MainMenuActivity.this, AndroidNewsFeedActivity.class);
                        MainMenuActivity.this.startActivity(intent);
                    }
                    break;

                }

            }


        });
    }



    
}
