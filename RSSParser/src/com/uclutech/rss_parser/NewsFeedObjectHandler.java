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

	private String newsFeedObjectTag;
	private String urlTag;
	private String titleTag;
	private String contentTag;
	private String dateTag;

	private List<NewsFeedObject> newsFeedObjects = new ArrayList<NewsFeedObject>();
	private String tempBuffer;
	private NewsFeedObject tempNewsFeedObj;

	public NewsFeedObjectHandler() {
		this("entry","","","","");
	}

	public NewsFeedObjectHandler(String newsFeedObjectTag, String dateTag,
			String titleTag, String contentTag, String urlTag) {
		this.newsFeedObjectTag = newsFeedObjectTag;
		this.dateTag = dateTag;
		this.titleTag = titleTag;
		this.contentTag = contentTag;
		this.urlTag = urlTag;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(newsFeedObjectTag)) {
			tempNewsFeedObj = new NewsFeedObject();
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (tempNewsFeedObj != null) {
			if (qName.equalsIgnoreCase(newsFeedObjectTag)) {
				newsFeedObjects.add(tempNewsFeedObj);
			} else if (qName.equalsIgnoreCase(titleTag)) {
				tempNewsFeedObj.setTitle(tempBuffer);
			} else if (qName.equalsIgnoreCase(dateTag)) {
				tempNewsFeedObj.setDate(tempBuffer);
			} else if (qName.equalsIgnoreCase(contentTag)) {
				tempNewsFeedObj.setContent(tempBuffer);
			} else if (qName.equalsIgnoreCase(urlTag)) {
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
