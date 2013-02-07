package com.uclutech.techsocietyapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.uclutech.techsocietyapplication.dao.projects.ProjectDAO;
import com.uclutech.techsocietyapplication.dao.projects.ProjectDAOMockImpl;

public class ProjectsListActivity extends Activity {

	private ProjectDAO projectsDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_list);

		projectsDAO = new ProjectDAOMockImpl();
		projectsDAO.connect();

		ListView projectsList = (ListView) findViewById(R.id.projectlist_listview_projects);
		ProjectArrayAdapter adapter = new ProjectArrayAdapter(this,
				R.layout.project_names, projectsDAO.getAll());
		projectsList.setAdapter(adapter);
	}

}
