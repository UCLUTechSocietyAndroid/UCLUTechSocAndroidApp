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

public class TheVergeFeedsImpl implements Feeder {

	private final static String THE_VERGE_URL = "http://www.theverge.com/rss/index.xml";

	@Override
	public List<NewsFeedObject> getAll() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {

			SAXParser parser = factory.newSAXParser();
			NewsFeedObjectHandler handler = new NewsFeedObjectHandler();
			InputStream inputStream = new URL(THE_VERGE_URL).openStream();
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

	public static void main(String[] args) {
		System.out.println("Reddit Feed:");
		Feeder feed = new RedditFeedImpl();
		List<NewsFeedObject> nfObjs = feed.getAll();
		for (NewsFeedObject nfObj : nfObjs) {
			System.out.println(nfObj);
		}
	}

}
