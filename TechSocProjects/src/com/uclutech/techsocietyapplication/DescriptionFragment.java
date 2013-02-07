package com.uclutech.techsocietyapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DescriptionFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.project_description, container, false);
		TextView textView = (TextView) view.findViewById(R.id.projectdescription_textview_description);
		textView.setText("Project Description");
		return view;
	}
}
