package com.uclutech.techsocietyapplication.dao.projects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uclutech.techsocietyapplication.models.projects.Project;

public class ProjectDAOMockImpl implements ProjectDAO {

	private final static int NUMBER_OF_PROJECTS = 5;
	private Map<Long, Project> projects = new HashMap<Long, Project>();

	@Override
	public List<Project> getAll() {
		return new ArrayList<Project>(projects.values());
	}

	@Override
	public Project getById(long id) {
		return projects.get(id);
	}

	@Override
	public void remove(long id) {
		projects.remove(id);
	}

	@Override
	public void add(Project project) {
		projects.put(project.getId(), project);
	}

	@Override
	public void connect() {

		for (int i = 0; i < NUMBER_OF_PROJECTS; i++) {
			Project project = new Project();
			project.setId(i);
			project.setName("Project #" + String.valueOf(i));
			project.setDescription("Description for Project#"
					+ String.valueOf(i));
			projects.put(project.getId(), project);
		}

	}

}
