package rss_parser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import model.NewsFeedObject;

public class NewsFeedParser {

    /**
     * Derived from: http://developer.android.com/training/basics/network-ops/xml.html
     */

    private Logger mLogger = Logger.getLogger(NewsFeedParser.class.getName());

    public static final String CHANNEL_TAG = "channel";
    public static final String ITEM_TAG = "item";
    public static final String TITLE_TAG = "title";
    public static final String CONTENT_TAG = "content";
    public static final String DATE_TAG= "date";
    public static final String CONTENT_URL_TAG = "content_url";

    private static final String ns = null;
    private String newsFeed;
    private Map<String, String> mTags;

    public NewsFeedParser(String newsFeed, Map<String, String> tags) {
        this.newsFeed = newsFeed;
        this.mTags = tags;
    }

    public List<NewsFeedObject> getNewsFeeds() throws IOException, XmlPullParserException {
        InputStream in = new ByteArrayInputStream(newsFeed.getBytes());
        return parse(in);
    }

    public List<NewsFeedObject> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<NewsFeedObject> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<NewsFeedObject> newsFeedObjects = new ArrayList<NewsFeedObject>();

        while (!parser.getName().equals(mTags.get(CHANNEL_TAG))) {
            parser.nextTag();
        }
        parser.require(XmlPullParser.START_TAG, ns, mTags.get(CHANNEL_TAG));
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if (name.equals(mTags.get(ITEM_TAG))) {
                NewsFeedObject nfo = readEntry(parser);
                if (nfo != null) {
                    newsFeedObjects.add(nfo);
                }
            } else {
                skip(parser);
            }
        }
        for (NewsFeedObject nfo: newsFeedObjects) {
            mLogger.info(nfo.toString());
        }
        return newsFeedObjects;
    }

    private NewsFeedObject readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, mTags.get(ITEM_TAG));
        String title = null;
        String content = null;
        String date = null;
        URL contentURL = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            mLogger.info("Parsing Item");
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            mLogger.info("Parsing through: " + name);

            if (name.equals(mTags.get(TITLE_TAG))) {
                title = readTitle(parser);
            } else if (name.equals(mTags.get(CONTENT_TAG))) {
                content = readContent(parser);
            } else if (name.equals(mTags.get(DATE_TAG))) {
                date = readDate(parser);
            } else {
                skip(parser);
            }
        }

        if ((date == null) && (content==null) &&(title == null)) {
            return null;
        } else {
            return new NewsFeedObject(title,date,content,contentURL);
        }
    }

    // Processes title tags in the feed.
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, mTags.get(TITLE_TAG));
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, mTags.get(TITLE_TAG));
        return title;
    }

    // Processes link tags in the feed.
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (tag.equals("link")) {
            if (relType.equals("alternate")){
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    // Processes content tags in the feed.
    private String readContent(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, mTags.get(CONTENT_TAG));
        String content = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, mTags.get(CONTENT_TAG));
        return content;
    }

    private String readDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, mTags.get(DATE_TAG));
        String content = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, mTags.get(DATE_TAG));
        return content;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
