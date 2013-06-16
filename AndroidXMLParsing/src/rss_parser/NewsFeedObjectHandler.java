package rss_parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.uclutech.model.NewsFeedObject;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class NewsFeedObjectHandler extends DefaultHandler {

	private String newsFeedObjectTag;
	private String urlTag;
	private String titleTag;
	private String contentTag;
	private String dateTag;

	private List<NewsFeedObject> newsFeedObjects = new ArrayList<NewsFeedObject>();
	private StringBuilder tempBuffer = new StringBuilder();
	private NewsFeedObject tempNewsFeedObj;

	public NewsFeedObjectHandler() {
		this("item","pubDate","title","description","link");
	}

	public NewsFeedObjectHandler(String newsFeedObjectTag, String dateTag,
			String titleTag, String contentTag, String urlTag) {
		this.newsFeedObjectTag = newsFeedObjectTag;
		this.dateTag = dateTag;
		this.titleTag = titleTag;
		this.contentTag = contentTag;
		this.urlTag = urlTag;
	}

    private NewsFeedObject parseXML() {
        NewsFeedObject newsFeed = null;

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


        return newsFeed;
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
				tempNewsFeedObj.setTitle(tempBuffer.toString());
			} else if (qName.equalsIgnoreCase(dateTag.toString())) {
				tempNewsFeedObj.setDate(tempBuffer.toString());
			} else if (qName.equalsIgnoreCase(contentTag.toString())) {
				tempNewsFeedObj.setContent(tempBuffer.toString());
			} else if (qName.equalsIgnoreCase(urlTag)) {
				try {
					tempNewsFeedObj.setContentUrl(new URL(tempBuffer.toString()));
				} catch (MalformedURLException e) {
					tempNewsFeedObj.setContentUrl(null);
				}
			}
			tempBuffer.delete(0, tempBuffer.length());
		}
	}

	@Override
	public void characters(char[] buffer, int start, int length) {
		tempBuffer = tempBuffer.append(new String(buffer, start, length));
	}

	public List<NewsFeedObject> getAll() {
		return newsFeedObjects;
	}

}
