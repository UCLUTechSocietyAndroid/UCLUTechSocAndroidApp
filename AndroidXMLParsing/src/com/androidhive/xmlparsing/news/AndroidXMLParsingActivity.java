package com.androidhive.xmlparsing.news;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.androidhive.xmlparsing.R;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class AndroidXMLParsingActivity extends ListActivity {

	// All static variables
	String URL;
	static final String URL0 = "http://feeds.bbci.co.uk/news/technology/rss.xml";
	static final String URL1 = "http://www.geek.com/feed/";

	// XML node keys
	String KEY_DATE_GLOBAL;
	String KEY_LINK_GLOBAL;
	String KEY_INTENT_GLOBAL;

	static final String KEY_ITEM0 = "item"; // parent node
	static final String KEY_DATE0 = "pubDate";
	static final String KEY_LINK0 = "link";
	static final String KEY_DESC0 = "description";
	static final String KEY_INTENT0 = KEY_DESC0;

	static final String KEY_ITEM1 = "item"; // parent node
	static final String KEY_DATE1 = "pubDate";
	static final String KEY_LINK1 = "link";
	static final String KEY_DESC1 = "title";
	static final String KEY_INTENT1 = "description";

    String xml, xml0, xml1;

    Document doc, doc0, doc1;
    NodeList nl0;
    NodeList nl1;
    NodeList nl;

    ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);



		XMLParser parser0 = new XMLParser();
		XMLParser parser1 = new XMLParser();
		XMLParser parser = new XMLParser();




		// xml = parser.getXmlFromUrl(URL); // getting XML

         new RetreiveFeedTask().execute(parser0, parser1, parser);




		// nl = nl1;
		// parser=parser1;
		// for (int i = 0; i < nl.getLength(); i++) {

		// if (i%2!=0)
		// genericXMLParser(menuItems, nl, i, parser, KEY_ITEM1, KEY_DATE1,
		// KEY_LINK1, KEY_DESC1, KEY_INTENT1); // then its another site turn

		// }
	}



	public void genericXMLParser(ArrayList<HashMap<String, String>> menuItems,
			NodeList nl, int i, XMLParser parser, String KEY_ITEM,
			String KEY_DATE, String KEY_LINK, String KEY_DESC, String KEY_INTENT) {

		KEY_DATE_GLOBAL = KEY_DATE;
		KEY_LINK_GLOBAL = KEY_LINK;
		KEY_INTENT_GLOBAL = KEY_INTENT;

		// creating new HashMap
		HashMap<String, String> map = new HashMap<String, String>();
		Element e = (Element) nl.item(i);
		// adding each child node to HashMap key => value
		// map.put(KEY_ID, parser.getValue(e, KEY_ID));
		String checkDate = parser.getValue(e, KEY_DATE);
		map.put(KEY_DATE, checkDate);

		String checkLink = parser.getValue(e, KEY_LINK);
		map.put(KEY_LINK, checkLink);

		String checkDesc = parser.getValue(e, KEY_DESC);
		map.put(KEY_DESC, checkDesc);

		map.put(KEY_INTENT, parser.getValue(e, KEY_INTENT));
		// adding HashList to ArrayList
		menuItems.add(map);

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item,
				new String[] { KEY_DATE, KEY_DESC, KEY_LINK }, new int[] {
						R.id.listitem_textview_date,
						R.id.listitem_textview_desciption,
						R.id.listview_textview_link });

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				// View view;

				String date = ((TextView) view.findViewById(R.id.listitem_textview_date))
						.getText().toString();
				String link = ((TextView) view.findViewById(R.id.link))
						.getText().toString();
				String description = ((TextView) view
						.findViewById(R.id.listitem_textview_desciption)).getText().toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						SingleMenuItemActivity.class);
				in.putExtra(KEY_DATE_GLOBAL, date);
				in.putExtra(KEY_LINK_GLOBAL, link);
				in.putExtra(KEY_INTENT_GLOBAL, description);
				startActivity(in);

			}
		});
	}




    private class RetreiveFeedTask extends AsyncTask<XMLParser, Void, Void> {

        private Exception exception;


        @Override
        protected Void doInBackground(XMLParser... xmlParsers) {

            XMLParser parser0 = xmlParsers[0];
            XMLParser parser1 = xmlParsers[1];
            XMLParser parser = xmlParsers[2];

            xml0 = parser0.getXmlFromUrl(URL0); // getting XML
            xml1 = parser1.getXmlFromUrl(URL1); // getting XML

            doc0 = parser0.getDomElement(xml0); // getting DOM element
            doc1 = parser1.getDomElement(xml1); // getting DOM element

            nl0 = doc0.getElementsByTagName("item");
            nl1 = doc1.getElementsByTagName("item");
            nl = nl0;

            parser = parser0;
            nl = nl0;

            // looping through all item nodes <item

            parser = parser0;
            for (int i = 0; i < 11; i++) {

                // if (i%2==0)
                nl = nl1;
                parser = parser1;
                genericXMLParser(menuItems, nl, i, parser, KEY_ITEM1, KEY_DATE1,
                        KEY_LINK1, KEY_DESC1, KEY_INTENT1); // then its another site
                // turn

                nl = nl0;
                parser = parser0;
                genericXMLParser(menuItems, nl, i, parser, KEY_ITEM0, KEY_DATE0,
                        KEY_LINK0, KEY_DESC0, KEY_INTENT0); // then its bbc tur
            }

            return null;
        }


    }





}



