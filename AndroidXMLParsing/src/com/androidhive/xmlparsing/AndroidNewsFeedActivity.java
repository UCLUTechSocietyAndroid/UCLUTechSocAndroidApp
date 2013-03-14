package com.androidhive.xmlparsing;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uclutech.model.NewsFeedObject;
import com.uclutech.rss_parser.Feeder;
import com.uclutech.rss_parser.RedditFeedImpl;
import com.uclutech.rss_parser.TheVergeFeedsImpl;

public class AndroidNewsFeedActivity extends ListActivity {

	private List<NewsFeedObject> newsFeeds = new ArrayList<NewsFeedObject>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Feeder redditFeeder = new RedditFeedImpl();
		Feeder vergeFeeder = new TheVergeFeedsImpl();
		newsFeeds.addAll(redditFeeder.getAll());
		newsFeeds.addAll(vergeFeeder.getAll());

		NewsFeedAdapter adapter = new NewsFeedAdapter(this, R.layout.list_item,
				newsFeeds);
		setListAdapter(adapter);
	}

	public class NewsFeedAdapter extends ArrayAdapter<NewsFeedObject> {

		private Context mContext;
		private List<NewsFeedObject> mNewsFeeds;

		public NewsFeedAdapter(Context context, int textViewResourceId,
				List<NewsFeedObject> newsFeeds) {
			super(context, textViewResourceId);
			mContext = context;
			mNewsFeeds = newsFeeds;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.list_item, parent, false);
			NewsFeedObject positionedNewsFeed = mNewsFeeds.get(position);
			TextView date = (TextView) row
					.findViewById(R.id.listitem_textview_date);
			TextView title = (TextView) row
					.findViewById(R.id.listitem_textview_desciption);
			TextView link = (TextView) row
					.findViewById(R.id.listview_textview_link);

			date.setText(positionedNewsFeed.getDate());
			title.setText(positionedNewsFeed.getTitle());
			link.setText(positionedNewsFeed.getContentUrl().toString());

			return row;
		}

	}

}
