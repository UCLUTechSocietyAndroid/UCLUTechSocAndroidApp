package rss_parser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import model.NewsFeedObject;


/**
 * Created by Home on 14/06/2013.
 */
public class NewsFeedXmlParser {

    private String ns;
    private String entryTag;

    public NewsFeedXmlParser() {

    }

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    public List readFeed(XmlPullParser parser) throws IOException, XmlPullParserException {

        List entries = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, entryTag);
        List<NewsFeedObject> list = new ArrayList<NewsFeedObject>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

        }
        return list;
    }
}
