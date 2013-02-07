package com.uclutech.techsocietyapplication.models.projects;

import java.util.List;

import com.uclutech.techsocietyapplication.models.society.Member;

public class Project {

	private long id;
	private String name;
	private String description;
	private List<Member> teamMembers;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Member> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(List<Member> teamMembers) {
		this.teamMembers = teamMembers;
	}

}
