package com.uclutech.techsocietyapplication;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.uclutech.techsocietyapplication.models.projects.Event;

public class EventsArrayAdapter extends ArrayAdapter<Event> {

	private List<Event> mEvents;
	private Context mContext;
	
	public EventsArrayAdapter(Context context, int textViewResourceId, List<Event> events) {
		super(context, textViewResourceId);
		mContext = context;
		mEvents = events;
	}
	
	

}
