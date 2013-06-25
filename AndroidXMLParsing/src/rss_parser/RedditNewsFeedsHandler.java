package rss_parser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import model.NewsFeedObject;

/**
 * Created by Home on 16/06/2013.
 */
public class RedditNewsFeedsHandler implements NewsFeedsHandler {

    List<NewsFeedObject> data = null;



    @Override
    public List<NewsFeedObject> getNewsFeedObjects(String newsFeed) {
        Map<String,String> map = new HashMap<String, String>();
        map.put(NewsFeedParser.CHANNEL_TAG, "channel");
        map.put(NewsFeedParser.ITEM_TAG, "item");
        map.put(NewsFeedParser.TITLE_TAG, "title");
        map.put(NewsFeedParser.CONTENT_TAG, "description");
        map.put(NewsFeedParser.DATE_TAG, "pubDate");
        map.put(NewsFeedParser.CONTENT_URL_TAG,"link");

        NewsFeedParser parser = new NewsFeedParser(newsFeed, map);

        try {
            return parser.getNewsFeeds();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
    }
}
