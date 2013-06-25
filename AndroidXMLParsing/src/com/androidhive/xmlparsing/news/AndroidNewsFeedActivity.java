package com.androidhive.xmlparsing.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidhive.xmlparsing.R;

import model.NewsFeedObject;
import rss_parser.BBCNewsFeedsHandler;
import rss_parser.NewsFeedsHandler;
import rss_parser.RedditNewsFeedsHandler;


public class AndroidNewsFeedActivity extends ListActivity {

    private Logger mLogger = Logger.getLogger(AndroidNewsFeedActivity.class.getName());
    private static List<NewsFeedObject> newsFeeds = new ArrayList<NewsFeedObject>();
    private NewsFeedAdapter adapter;

    // assign handlers for each news source
    NewsFeedsHandler handler_reddit = new RedditNewsFeedsHandler();
    NewsFeedsHandler handler_bbc = new BBCNewsFeedsHandler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(Html.fromHtml("<h1>techSoc News</h1>"));

        List<URL> urls = new ArrayList<URL>();

        //static final String URL0 = "http://feeds.bbci.co.uk/news/technology/rss.xml";



        try {
            new NewsFeedRetrieve(this).execute(new URL("http://www.reddit.com/.rss"),
                    new URL("http://feeds.bbci.co.uk/news/technology/rss.xml"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        adapter = new NewsFeedAdapter(this, R.layout.list_item, newsFeeds);
        setListAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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

        List<NewsFeedObject> nfos = null;

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

            List<NewsFeedObject> handlerList = null;

            //List<NewsFeedObject> url_xml = new HashMap<String,String>();

            // takes news sources and returns objects in right order
            handlerList = sortNewsFeed(urls);


            return handlerList;
        }

        @Override
        protected void onPostExecute(List<NewsFeedObject> nfos) {


               for (NewsFeedObject nfo : nfos) {
                  AndroidNewsFeedActivity.newsFeeds.add(nfo);
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

            //geting news feed objects from news sources
            handlerList1 = getNewsFeed(urls,0);
            handlerList2 = getNewsFeed(urls,1);


            // count determine which news to choose
            while (count != -1) {


                if ((sourcePlace1>=handlerList1.size()) && (sourcePlace2>=handlerList2.size()) )
                    count=-1;


                else if ((count==1)&&(handlerList1!=null)) {


                    if (sourcePlace1<handlerList1.size()) {
                        handlerList.add(mainFeedPlace,handlerList1.get(sourcePlace1));
                        sourcePlace1++;
                        mainFeedPlace++;
                    }

                    count++;
                }


                else if ((count==2)&&(handlerList2!=null))  {


                    if (sourcePlace2<handlerList2.size()) {
                       handlerList.add(mainFeedPlace,handlerList2.get(sourcePlace2));
                       sourcePlace2++;
                       mainFeedPlace++;
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
