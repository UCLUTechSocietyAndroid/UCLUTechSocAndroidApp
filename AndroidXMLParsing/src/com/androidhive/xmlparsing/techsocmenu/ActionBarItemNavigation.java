package com.androidhive.xmlparsing.techsocmenu;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import com.androidhive.xmlparsing.R;
import com.androidhive.xmlparsing.news.AndroidNewsFeedActivity;
import com.androidhive.xmlparsing.settings.NewsPreferencesActivity;

/**
 * Created by user on 6/28/13.
 */
public class ActionBarItemNavigation {


    public void sortSelectedItemOptions(MenuItem item,Activity sender) {

    Intent intent;
    switch (item.getItemId()) {
        case R.id.newsSettings:
            intent = new Intent(sender, NewsPreferencesActivity.class);
            //pass current object
            sender.startActivity(intent);
            break;

        case R.id.newsMenu:
            intent = new Intent(sender, AndroidNewsFeedActivity.class);
            //pass current object
            sender.startActivity(intent);
            break;

        // on pressing label(logo)
        case android.R.id.home:
            intent = new Intent(sender, MainMenuActivity.class);
            //pass current object
            sender.startActivity(intent);
            break;

    }
}

}
