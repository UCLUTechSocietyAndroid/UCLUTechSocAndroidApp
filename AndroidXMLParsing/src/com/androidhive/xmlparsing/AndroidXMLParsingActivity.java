package com.androidhive.xmlparsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.androidhive.xmlparsing.NewsFeedObject;


public class AndroidXMLParsingActivity extends ListActivity {

	// All static variables
	String URL;
	static final String URL0 = "http://feeds.bbci.co.uk/news/technology/rss.xml"; 
	static final String URL1 = "http://www.geek.com/feed/";
	
	private List<NewsFeedObject> newsFeedObjects = new ArrayList<NewsFeedObject>();
	private List<NewsFeedObject> sorted_newsFeedObjects = new ArrayList<NewsFeedObject>();
	
	private NewsFeedObject tempNewsFeedObj;
	private HashMapTranslator hashMapTranslator;
	
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
	
	ListView lv;

	ArrayList<String> newsTitlesArrayList = new ArrayList<String>();
	String[] news_articles_for_search;
	
	
	
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
			genericXMLParser(menuItems, nl, i, parser, KEY_ITEM1, KEY_DATE1, KEY_LINK1, KEY_DESC1, KEY_INTENT1, i); // then its another site turn
			
			nl = nl0;
			genericXMLParser( menuItems, nl, i, parser, KEY_ITEM0, KEY_DATE0, KEY_LINK0, KEY_DESC0, KEY_INTENT0, i); //then its bbc tur
			
		}		
		
		
		implementAdapter( newsFeedObjects);
		
		
		// Get a reference to the AutoCompleteTextView in the layout
	  AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_news_articles);
		// Get the string array
		news_articles_for_search = newsTitlesArrayList.toArray(new String[newsTitlesArrayList.size()]);;
		
		// Create the adapter and set it to the AutoCompleteTextView 
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, news_articles_for_search);

		// set adapter on textview
		 textView.setAdapter(adapter);
		
		// updates the ListView from TextBox suggestions through the adapter
		autoUpdateList(adapter, newsFeedObjects, textView);
		
		
		
	}
		
	
	
	// updates the ListView and TextBox suggestions through the adapter
	private void autoUpdateList(final ArrayAdapter<String> adapter, final List<NewsFeedObject> newsFeedObjects, 
			final AutoCompleteTextView textView) {
		
     adapter.registerDataSetObserver(new DataSetObserver() {
	
	 @Override
	    public void onChanged() {
	        super.onChanged();
	      
	      ArrayList<Integer> objectsNumbersInSortedList = new ArrayList<Integer>();
	     
	      String dataOnAdapter = "";
	      
	      objectsNumbersInSortedList.clear();
	    
	      
	        for (int counter =0; counter < adapter.getCount() ; counter++) 
	        {
	      
	        	Object item = adapter.getItem(counter);
	        
		     	dataOnAdapter = item.toString();
	        
		     	
		     	
			    int objectListSize = 0;
			    objectListSize = newsFeedObjects.size();
			
	      
			     for (int counter2 = 0; counter2 < objectListSize; counter2++) 
			     {
	            	 
	            	  tempNewsFeedObj = newsFeedObjects.get(counter2);
	            	  
	            	  if (tempNewsFeedObj.getTitle() == dataOnAdapter) {
	            		  objectsNumbersInSortedList.add(tempNewsFeedObj.getObjectNumber());
	            		counter2 =  objectListSize;  
	            	  }
	            	  
	            	  
	            	  
	              }
	        
	        
			     
	        
	        }
	        
	        // getting sorted news feed objects list from number array
	        // and if current input line is null, put original not-sorted list
	        // in addition because adapter works strange, we illuminate it under 1 text length 
	       if (textView.getText().length() > 1) getSortedNewsFeedObjects(objectsNumbersInSortedList);
	       else implementAdapter(newsFeedObjects);
	        
	       }
	
        });
     
 
		
	}
	
	
	
	private void  getSortedNewsFeedObjects(ArrayList<Integer> objectsNumbersInSortedList)
	{
		sorted_newsFeedObjects.clear();
		for (int counter = 0; counter < objectsNumbersInSortedList.size(); counter++) {
		
			
			for (int counter2 = 0;  counter2 < newsFeedObjects.size(); counter2++) {
			
			if (objectsNumbersInSortedList.get(counter) == newsFeedObjects.get(counter2).getObjectNumber() )	
				sorted_newsFeedObjects.add(counter, newsFeedObjects.get(counter2));
			
			}
			
		
		}
		
		implementAdapter(sorted_newsFeedObjects);
	}
	
	
	
	
	
	
	// Put all information from object into the list
	// implementing ListView adapter
	private void implementAdapter(List<NewsFeedObject> newsFeedObjects)
	{
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		int listSize = 0;
		listSize = newsFeedObjects.size();
		
		
	for (int counter=0; counter < listSize; counter++)
	
	{				
		tempNewsFeedObj = new NewsFeedObject();	
		hashMapTranslator = new HashMapTranslator();	
			
		tempNewsFeedObj = newsFeedObjects.get(counter);
		
		String title = tempNewsFeedObj.getTitle();
		String date = tempNewsFeedObj.getDate();
		String link = tempNewsFeedObj.getContentUrl();
		
		//getting hash map from the list of objects by using hashMapTranslator separate object
		ArrayList<Map<String, String>> hashMap =  hashMapTranslator.buildData(list, title, date, link);
		
		ListAdapter adapter = new SimpleAdapter(this, hashMap,
				R.layout.list_item,
				new String[] { "title", "date", "link" }, new int[] {
						R.id.desciption,
						R.id.date,
						R.id.link });
		
		// made up setAdapter function to return ListView with an adapter created
		setAdapter(adapter);	
	}
		
	
	
	}
		



// puts list view onto the view and implements onClickListener
private void setAdapter( ListAdapter adapter)
{
	setListAdapter(adapter);

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





	// puts information from XML to the list of objects List<NewsFeedObject>
	private void genericXMLParser(ArrayList<HashMap<String, String>> menuItems, NodeList nl, int i, XMLParser parser,
			String KEY_ITEM, String KEY_DATE, String KEY_LINK, String KEY_DESC, String KEY_INTENT, int objectNumber) 
	{
		
		tempNewsFeedObj = new NewsFeedObject();
		
		KEY_DATE_GLOBAL = KEY_DATE;
		KEY_LINK_GLOBAL = KEY_LINK;
		KEY_INTENT_GLOBAL = KEY_INTENT;
			
			Element e = (Element) nl.item(i);
		
			// adding each child node to the object
			tempNewsFeedObj.setObjectNumber(objectNumber);
			
			String putDate = parser.getValue(e, KEY_DATE);
			tempNewsFeedObj.setDate(putDate);
			
			String putLink = parser.getValue(e, KEY_LINK);
			tempNewsFeedObj.setContentUrl(putLink);
			
			String putTitle = parser.getValue(e, KEY_DESC);
			newsTitlesArrayList.add(putTitle);
			tempNewsFeedObj.setTitle(putTitle);
			
			
			String putBigTitle = parser.getValue(e, KEY_INTENT);
        	tempNewsFeedObj.setBigTitle(putBigTitle);
			
		    // adding object to the object list	
		    newsFeedObjects.add(tempNewsFeedObj);
			
		
	}
	
	
	
}









