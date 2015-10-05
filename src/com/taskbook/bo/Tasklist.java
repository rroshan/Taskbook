package com.taskbook.bo;

import java.sql.Timestamp;

public class Tasklist {
	private String owner;
	private int tasklistID;
	private String taskName;
	private Timestamp createdDate;
	private Timestamp lastModifiedDate;
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getTasklistID() {
		return tasklistID;
	}
	public void setTasklistID(int tasklistID) {
		this.tasklistID = tasklistID;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
