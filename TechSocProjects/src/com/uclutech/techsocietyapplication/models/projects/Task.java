package com.uclutech.techsocietyapplication.models.projects;

import java.sql.Date;
import java.util.List;

import com.uclutech.techsocietyapplication.models.society.Member;

public class Task {

	private long id;
	private String description;
	private Date dueDate;
	private List<Member> assignedMembers;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public List<Member> getAssignedMembers() {
		return assignedMembers;
	}

	public void setAssignedMembers(List<Member> assignedMembers) {
		this.assignedMembers = assignedMembers;
	}

}
