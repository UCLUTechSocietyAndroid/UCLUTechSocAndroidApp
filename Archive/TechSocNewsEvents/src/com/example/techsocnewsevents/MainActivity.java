package com.example.techsocnewsevents;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

	public class MainActivity extends Activity {

	 TextView twitterUpdate;
	 TextView newsUpdate1;
	 TextView newsUpdate2;
	 TextView newsUpdate3;
			
	 
	 HttpClient client;
	 JSONObject json;
	 	
	 
final static String URL = "http://api.twitter.com/1/statuses/user_timeline.json?screen_name=";
final static String URLNews1 = "https://ajax.googleapis.com/ajax/services/search/news?" +
                  "v=1.0&q=barack%20obama&userip=INSERT-USER-IP";

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	twitterUpdate = (TextView) findViewById(R.id.twitterUpdate);
	
	newsUpdate1 = (TextView) findViewById(R.id.news1);
	newsUpdate2 = (TextView) findViewById(R.id.news2);
	newsUpdate3 = (TextView) findViewById(R.id.news3);
	
	
	client = new DefaultHttpClient();
	
	
	new TweetPrint().execute("text");
	new NewsPrint1().execute("content");
	}

	
private JSONObject getJSON(StringBuilder url) throws ClientProtocolException, IOException, JSONException
	{
		
		   HttpGet get = new HttpGet(url.toString()); 
		   HttpResponse r = client.execute(get);
		  
		   int status = r.getStatusLine().getStatusCode();
		   
		   if ((status >= 200)&&(status<300)) 
		   {
			   
			 HttpEntity e = r.getEntity();
			 String data = EntityUtils.toString(e);
		     JSONArray timeline = new JSONArray(data);
		     JSONObject last = timeline.getJSONObject(0); 
		     return last;
		   
		   }else{
		  return null;
		   }
		   	
	}
	
	
	
private JSONObject lastTweet(String username ) throws ClientProtocolException, IOException, JSONException  {
	
	StringBuilder send_url = new StringBuilder(URL);
	send_url.append(username);
	
	JSONObject putOut = getJSON(send_url);
	
	return putOut;
	   
}



private JSONObject lastNews1() throws ClientProtocolException, IOException, JSONException  {
	
	StringBuilder send_url = new StringBuilder(URLNews1);
	//send_url.append(username);
	
	JSONObject putOut = getJSON(send_url);
	
	return putOut;
	   
}



	
public class TweetPrint extends AsyncTask<String,Integer,String> 
{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
			json = lastTweet("uclnews");
			return json.getString(params[0]);
			}catch (ClientProtocolException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}catch (JSONException e) {
				e.printStackTrace();				
			}
		
		return null;
		}
		   
		   
		@Override 
		protected void onPostExecute(String result) {
			twitterUpdate.setText(result);
		}
		
	}



public class NewsPrint1 extends AsyncTask<String,Integer,String> 
{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
		json = lastNews1();
		return json.getString(params[0]);
		}catch (ClientProtocolException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();//Log.e("log_tag", "Failed data was:\n" + result);		
		}
	
	return null;
	}
	   
	   
	@Override 
	protected void onPostExecute(String result) {
		newsUpdate1.setText(result);
	}
	
}


	


	}
