package com.uclutech.rss_parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.uclutech.model.NewsFeedObject;

public class NewsFeedObjectHandler extends DefaultHandler {

	private static final String ENTRY = "entry";
	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String CONTENT = "content";
	private static final String DATE = "published";

	private List<NewsFeedObject> newsFeedObjects = new ArrayList<NewsFeedObject>();
	private String tempBuffer;
	private NewsFeedObject tempNewsFeedObj;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(ENTRY)) {
			tempNewsFeedObj = new NewsFeedObject();
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (tempNewsFeedObj != null) {
			if (qName.equalsIgnoreCase(ENTRY)) {
				newsFeedObjects.add(tempNewsFeedObj);
			} else if (qName.equalsIgnoreCase(TITLE)) {
				tempNewsFeedObj.setTitle(tempBuffer);
			} else if (qName.equalsIgnoreCase(DATE)) {
				tempNewsFeedObj.setDate(tempBuffer);
			} else if (qName.equalsIgnoreCase(CONTENT)) {
				tempNewsFeedObj.setContent(tempBuffer);
			} else if (qName.equalsIgnoreCase(ID)) {
				try {
					tempNewsFeedObj.setContentUrl(new URL(tempBuffer));
				} catch (MalformedURLException e) {
					tempNewsFeedObj.setContentUrl(null);
				}
			}
		}
	}

	@Override
	public void characters(char[] buffer, int start, int length) {
		tempBuffer = new String(buffer, start, length);
	}

	public List<NewsFeedObject> getAll() {
		return newsFeedObjects;
	}

}
