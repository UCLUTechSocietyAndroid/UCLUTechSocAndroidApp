package com.androidhive.xmlparsing.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidhive.xmlparsing.R;
import com.androidhive.xmlparsing.settings.NewsPreferencesActivity;
import com.androidhive.xmlparsing.techsocmenu.ActionBarItemNavigation;
import com.androidhive.xmlparsing.techsocmenu.MainMenuActivity;

import model.NewsFeedObject;
import rss_parser.BBCNewsFeedsHandler;
import rss_parser.NewsFeedsHandler;
import rss_parser.RedditNewsFeedsHandler;


public class AndroidNewsFeedActivity extends ListActivity {

    private Logger mLogger = Logger.getLogger(AndroidNewsFeedActivity.class.getName());

    //any reason why it was static
    private List<NewsFeedObject> newsFeeds = new ArrayList<NewsFeedObject>();

    private NewsFeedAdapter adapter;
    SharedPreferences mySharedPreferences;

    // assign handlers for each news source
    NewsFeedsHandler handler_reddit = new RedditNewsFeedsHandler();
    NewsFeedsHandler handler_bbc = new BBCNewsFeedsHandler();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // goes through all action bar ids, to do something relatively to the item pressed
        ActionBarItemNavigation itemNavigation = new ActionBarItemNavigation();

        itemNavigation.sortSelectedItemOptions(item,this);

        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        // getting newsFeeds
        startNewsSelection();

        adapter = new NewsFeedAdapter(this, R.layout.list_item, newsFeeds);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.title);
        item.setTitle("News");

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(Html.fromHtml(""));
        actionBar.setDisplayHomeAsUpEnabled(true);

        return true;
    }


    public void startNewsSelection() {

        List<URL> urls = new ArrayList<URL>();

        mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
             new NewsFeedRetrieve(this).execute(new URL("http://www.reddit.com/.rss"),
             new URL("http://feeds.bbci.co.uk/news/technology/rss.xml"));

        } catch (MalformedURLException e) {
             e.printStackTrace();
        }

    }




    public class NewsFeedAdapter extends ArrayAdapter<NewsFeedObject> {

        private Context mContext;
        private List<NewsFeedObject> mNewsFeeds;

        public NewsFeedAdapter(Context context, int textViewResourceId,
                               List<NewsFeedObject> newsFeeds) {
            super(context, textViewResourceId, newsFeeds);
            mContext = context;
            mNewsFeeds = newsFeeds;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            NewsFeedObject positionedNewsFeed = mNewsFeeds.get(position);
            mLogger.info("Populated ");
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_item, parent, false);

            TextView date = (TextView) row
                    .findViewById(R.id.listitem_textview_date);
            TextView title = (TextView) row
                    .findViewById(R.id.listitem_textview_description);
            TextView link = (TextView) row
                    .findViewById(R.id.link);

            date.setText(positionedNewsFeed.getDate());
            title.setText(positionedNewsFeed.getTitle());
            link.setText(positionedNewsFeed.getContentUrl());
            return row;

        }

    }





    public class NewsFeedRetrieve extends AsyncTask<URL, Void, List<NewsFeedObject>> {

        private ProgressDialog dialog;

        int counter=0;


        public NewsFeedRetrieve(Context context) {
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading. Please Wait...");
            this.dialog.show();
        }

        @Override
        protected List<NewsFeedObject> doInBackground(URL... urls) {

            List<NewsFeedObject> handlerList = new ArrayList<NewsFeedObject>();

            // takes news sources and returns objects in right order
            handlerList = sortNewsFeed(urls);

            return handlerList;
        }

        @Override
        protected void onPostExecute(List<NewsFeedObject> nfos) {



               for (NewsFeedObject nfo : nfos) {
                  newsFeeds.add(nfo);//AndroidNewsFeedActivity.
               }


            adapter.notifyDataSetChanged();
            dialog.dismiss();
            mLogger.info("notified changes");
        }




        @Override
        protected void onProgressUpdate(Void... voids) {
        }

        private String getContentFromUrl(URL url) {

            String result = "";

            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    mLogger.info(inputLine);
                    result += inputLine;
                }

                mLogger.info("Content = " + result);

            } catch (IOException e) {
                mLogger.severe("Content problem");
            }
            return result;
        }



        private List<NewsFeedObject> sortNewsFeed(URL[] urls) {

            List<NewsFeedObject> handlerList = new ArrayList<NewsFeedObject>();

            //source1
            List<NewsFeedObject> handlerList1 = null;
            int sourcePlace1 = 0;

            //source2
            List<NewsFeedObject> handlerList2 = null;
            int sourcePlace2 = 0;

            int count = 1;
            int mainFeedPlace = 0;

            //get preference values
            boolean bbcSource = mySharedPreferences.getBoolean("checkbox_source_bbc", false);
            boolean redditSource = mySharedPreferences.getBoolean("checkbox_source_reddit", false);


            //geting news feed objects from news sources
            // Restore preferences
            int newsArticlesNumber=0;
            if (redditSource) {
                handlerList1 = getNewsFeed(urls,0);
                newsArticlesNumber+=handlerList1.size();
            }

            if (bbcSource) {
                handlerList2 = getNewsFeed(urls,1);
                newsArticlesNumber+=handlerList2.size();
            }

            // NEED TO REFACTOR CODE HERE THROUGH OBJECTS
            // count determine which news to choose
            while (count != -1) {


                if (mainFeedPlace>=newsArticlesNumber)
                    count=-1;



                else if ((count==1)) {


                    if (handlerList1!=null) {

                       if (sourcePlace1<handlerList1.size()) {
                          handlerList.add(mainFeedPlace,handlerList1.get(sourcePlace1));
                          sourcePlace1++;
                          mainFeedPlace++;
                       }
                    }

                    count++;
                }


                else if (count==2)  {


                    if (handlerList2!=null) {
                       if (sourcePlace2<handlerList2.size()) {
                         handlerList.add(mainFeedPlace,handlerList2.get(sourcePlace2));
                         sourcePlace2++;
                         mainFeedPlace++;
                       }
                    }

                    count=1;
                }



                else count=-1;

            }


            return handlerList;
        }


        private  List<NewsFeedObject> getNewsFeed(URL[] urls, int newsSource) {

                URL url = urls[newsSource];
                List<String> result = new ArrayList<String>();
                result.add(getContentFromUrl(url));


                if( url.toString().equals("http://www.reddit.com/.rss") )
                    return handler_reddit.getNewsFeedObjects(result.get(0));


                else if( url.toString().equals("http://feeds.bbci.co.uk/news/technology/rss.xml") )
                    return handler_bbc.getNewsFeedObjects(result.get(0)) ;

                else return null;

        }


    }

}
