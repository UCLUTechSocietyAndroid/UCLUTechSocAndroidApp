package com.uclutech.techsocietyapplication.models.projects;

import java.sql.Time;
import java.util.List;

import com.uclutech.techsocietyapplication.models.society.Member;

public class Event {

	private String name;
	private Time time;
	private Location location;
	private List<Member> attendingMembers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Member> getAttendingMembers() {
		return attendingMembers;
	}

	public void setAttendingMembers(List<Member> attendingMembers) {
		this.attendingMembers = attendingMembers;
	}

	private Project project;

}
