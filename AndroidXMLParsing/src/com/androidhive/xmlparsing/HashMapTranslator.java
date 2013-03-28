package com.androidhive.xmlparsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HashMapTranslator {
	

	public ArrayList<Map<String, String>> buildData(ArrayList<Map<String, String>> list, String title, String date, String link) {
		
	    list.add(putData(title, date, link));
	    
	    return list;
	  }

	  public HashMap<String, String> putData(String title, String date, String link) {
		  
	    HashMap<String, String> item = new HashMap<String, String>();
	    item.put("title", title);
	    item.put("date", date);
	    item.put("link", link);
	    
	    return item;
	  }
	  
	  
}


