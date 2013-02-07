package com.uclutech.techsocietyapplication;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uclutech.techsocietyapplication.models.projects.Project;

public class ProjectArrayAdapter extends ArrayAdapter<Project> implements
		OnClickListener {

	private List<Project> mProjects;
	private Context mContext;

	public ProjectArrayAdapter(Context context, int textViewResourceId,
			List<Project> projects) {
		super(context, textViewResourceId, projects);
		mContext = context;
		mProjects = projects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Project positionValue = mProjects.get(position);

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.project_names, parent, false);

		TextView nameView = (TextView) view
				.findViewById(R.id.projectnames_text_name);
		nameView.setText(positionValue.getName());
		TextView descriptionView = (TextView) view
				.findViewById(R.id.projectnames_text_description);
		descriptionView.setText(positionValue.getDescription());
		
		view.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(mContext, ProjectPager.class);
		mContext.startActivity(intent);
	}

}
