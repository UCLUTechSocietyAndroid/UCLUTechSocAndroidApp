package com.uclutech.techsocietyapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class EventsListFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View eventListView = inflater.inflate(R.layout.list, null, false);
		ListView eventList = (ListView) eventListView
				.findViewById(R.id.list_listview_list);

//		EventsArrayAdapter adapter = new EventsArrayAdapter(getActivity(),
//				R.layout.event_item, null);
//		eventList.setAdapter(adapter);

		return eventListView;

	}

}
