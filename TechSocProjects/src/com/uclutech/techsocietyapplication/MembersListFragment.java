package com.uclutech.techsocietyapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MembersListFragment extends Fragment {

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View memberListView = inflater.inflate(R.layout.list, container);
		ListView memberList = (ListView) memberListView.findViewById(R.id.list_listview_list);
		
		MemberArrayAdapter adapter = new MemberArrayAdapter(getActivity(), R.layout.event_item, null);
		
		memberList.setAdapter(adapter);
		
		return memberListView;
	}
	
}
