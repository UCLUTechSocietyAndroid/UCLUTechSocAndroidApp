package com.androidhive.xmlparsing.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.androidhive.xmlparsing.news.AndroidNewsFeedActivity;

import java.util.List;

/**
* Created by user on 6/25/13.
*/

public class NewsPreferencesActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();

    }


    // when leave news settings menu, update the news feed
    @Override
    public void onStop (){

        Intent intent = new Intent(this, AndroidNewsFeedActivity.class);
        this.startActivity(intent);
        super.onStop();
    }




}


