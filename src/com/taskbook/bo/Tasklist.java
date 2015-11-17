package com.taskbook.bo;

import java.sql.Timestamp;

public class Tasklist {
	private String owner;
	private int tasklistID;
	private String tasklistName;
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
	public String getTasklistName() {
		return tasklistName;
	}
	public void setTasklistName(String tasklistName) {
		this.tasklistName = tasklistName;
	}
	
}
