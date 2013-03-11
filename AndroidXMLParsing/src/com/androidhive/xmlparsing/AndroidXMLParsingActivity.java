package com.androidhive.xmlparsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.androidhive.xmlparsing.NewsFeedObject;

public class AndroidXMLParsingActivity extends ListActivity {

	// All static variables
	String URL;
	static final String URL0 = "http://feeds.bbci.co.uk/news/technology/rss.xml"; 
	static final String URL1 = "http://www.geek.com/feed/";
	
	private List<NewsFeedObject> newsFeedObjects = new ArrayList<NewsFeedObject>();
	private NewsFeedObject tempNewsFeedObj;
	
	
	// XML node keys
    String KEY_DATE_GLOBAL;
    String KEY_LINK_GLOBAL;
	String KEY_INTENT_GLOBAL;
	
	// bbc source
	static final String KEY_ITEM0 = "item"; 
	static final String KEY_DATE0 = "pubDate";
    static final String KEY_LINK0 = "link";
	static final String KEY_DESC0 = "description";
	static final String KEY_INTENT0 = KEY_DESC0;
	
	// geek.com source
	static final String KEY_ITEM1 = "item"; 
	static final String KEY_DATE1 = "pubDate";
    static final String KEY_LINK1 = "link";
	static final String KEY_DESC1 = "title";
	static final String KEY_INTENT1 = "description";
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
		
		XMLParser parser = new XMLParser();
		
		String xml0, xml1;
		Document doc0, doc1;
		
		// getting XML
		xml0 = parser.getXmlFromUrl(URL0); 
		xml1 = parser.getXmlFromUrl(URL1); 
		
		// getting DOM element
		doc0 = parser.getDomElement(xml0); 
		doc1 = parser.getDomElement(xml1); 
		
		NodeList nl0;
		NodeList nl1;
		
		nl0 = doc0.getElementsByTagName("item");
		nl1 = doc1.getElementsByTagName("item");
		NodeList nl = nl0;
		
		
		// looping through all nodes <item>
		nl = nl0;
		
		for (int i = 0; i<10; i++) {
	
	    	nl = nl1;	
			genericXMLParser(menuItems, nl, i, parser, KEY_ITEM1, KEY_DATE1, KEY_LINK1, KEY_DESC1, KEY_INTENT1); // then its another site turn
			
			nl = nl0;
			genericXMLParser( menuItems, nl, i, parser, KEY_ITEM0, KEY_DATE0, KEY_LINK0, KEY_DESC0, KEY_INTENT0); //then its bbc tur
			
		}		
		
		
	}
		
		

		
	private void genericXMLParser(ArrayList<HashMap<String, String>> menuItems, NodeList nl, int i, XMLParser parser,
			String KEY_ITEM, String KEY_DATE, String KEY_LINK, String KEY_DESC, String KEY_INTENT) 
	{
		
		tempNewsFeedObj = new NewsFeedObject();
		
		KEY_DATE_GLOBAL = KEY_DATE;
		KEY_LINK_GLOBAL = KEY_LINK;
		KEY_INTENT_GLOBAL = KEY_INTENT;
			
			Element e = (Element) nl.item(i);
		
			// adding each child node to the object
			
			String putDate = parser.getValue(e, KEY_DATE);
			tempNewsFeedObj.setDate(putDate);
			
			String putLink = parser.getValue(e, KEY_LINK);
			tempNewsFeedObj.setContentUrl(putLink);
			
			String putTitle = parser.getValue(e, KEY_DESC);
			tempNewsFeedObj.setTitle(putTitle);
			
			String putBigTitle = parser.getValue(e, KEY_INTENT);
        	tempNewsFeedObj.setBigTitle(putBigTitle);
			
		    // adding object to the object list	
		    newsFeedObjects.add(tempNewsFeedObj);
			
			
		//****************************************************************************************************************	
		// Old adapter part
	//	ListAdapter adapter = new SimpleAdapter(this, menuItems,
	//			R.layout.list_item,
	//			new String[] { KEY_DATE, KEY_LINK, KEY_DESC }, new int[] {
	//					R.id.date, R.id.link ,R.id.desciption });
	//		
	//	setListAdapter(adapter);
		
	  
		   
		//ListAdapter adapter = new SimpleAdapter() 
		//*******************************************************************************************************************
		
		   
				
		// selecting single ListView item
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() { 
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// getting values from selected ListItem to pass them onto details page
				String date = ((TextView) view.findViewById(R.id.date)).getText().toString();
				String link = ((TextView) view.findViewById(R.id.link)).getText().toString();
				String description = ((TextView) view.findViewById(R.id.desciption)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(KEY_DATE_GLOBAL, date);
				in.putExtra(KEY_LINK_GLOBAL, link);
				in.putExtra(KEY_INTENT_GLOBAL, description);
				startActivity(in);

			}
		});
	}
	
	
	
}









