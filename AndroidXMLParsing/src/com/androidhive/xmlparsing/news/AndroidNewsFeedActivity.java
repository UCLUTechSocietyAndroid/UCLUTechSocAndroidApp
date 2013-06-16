package com.androidhive.xmlparsing.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidhive.xmlparsing.R;

import model.NewsFeedObject;
import rss_parser.NewsFeedsHandler;
import rss_parser.RedditNewsFeedsHandler;


public class AndroidNewsFeedActivity extends ListActivity {

    private Logger mLogger = Logger.getLogger(AndroidNewsFeedActivity.class.getName());
    private List<NewsFeedObject> newsFeeds = new ArrayList<NewsFeedObject>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        List<URL> urls = new ArrayList<URL>();

        try {
            urls.add(new URL("http://www.reddit.com/.rss"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            new NewsFeedRetrieve().execute(new URL("http://www.reddit.com/.rss"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public class NewsFeedAdapter extends ArrayAdapter<NewsFeedObject> {

        private Context mContext;
        private List<NewsFeedObject> mNewsFeeds;

        public NewsFeedAdapter(Context context, int textViewResourceId,
                               List<NewsFeedObject> newsFeeds) {
            super(context, textViewResourceId);
            mContext = context;
            mNewsFeeds = newsFeeds;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_item, parent, false);
            NewsFeedObject positionedNewsFeed = mNewsFeeds.get(position);
            TextView date = (TextView) row
                    .findViewById(R.id.listitem_textview_date);
            TextView title = (TextView) row
                    .findViewById(R.id.listitem_textview_desciption);
            TextView link = (TextView) row
                    .findViewById(R.id.listview_textview_link);

            date.setText(positionedNewsFeed.getDate());
            title.setText(positionedNewsFeed.getTitle());
            link.setText(positionedNewsFeed.getContentUrl().toString());

            return row;
        }

    }

    public class NewsFeedRetrieve extends AsyncTask<URL, Void, List<String>> {

        @Override
        protected List<String> doInBackground(URL... urls) {
            mLogger.info("Retrieving Feeds");
            List<String> result = new ArrayList<String>();
            for (URL url : urls) {
                result.add(getContentFromUrl(url));

            }
            return result;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            NewsFeedsHandler handler = new RedditNewsFeedsHandler();
            newsFeeds = handler.getNewsFeedObjects(strings.get(0));
        }

        @Override
        protected void onProgressUpdate(Void... voids) {
            ProgressDialog dialog = ProgressDialog.show(AndroidNewsFeedActivity.this, "",
                    "Loading. Please wait...", true);

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

    }

}
