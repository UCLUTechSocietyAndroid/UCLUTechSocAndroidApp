package com.uclutech.rss_parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.uclutech.model.NewsFeedObject;

public class RedditFeedImpl implements Feeder {

	public static final String REDDIT_URL = "http://www.reddit.com/r/technology/.rss";

	@Override
	public List<NewsFeedObject> getAll() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {

			SAXParser parser = factory.newSAXParser();
			NewsFeedObjectHandler handler = new NewsFeedObjectHandler("item",
					"pubDate", "title", "description", "guid");
			InputStream inputStream = new URL(REDDIT_URL).openStream();
			parser.parse(inputStream, handler);
			return handler.getAll();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
