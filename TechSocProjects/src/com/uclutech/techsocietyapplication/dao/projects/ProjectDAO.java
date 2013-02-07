package com.uclutech.techsocietyapplication.dao.projects;

import java.util.List;

import com.uclutech.techsocietyapplication.models.projects.Project;

public interface ProjectDAO {

	public List<Project> getAll();

	public Project getById(long id);

	public void remove(long id);

	public void add(Project project);

	public void connect();
	
}
