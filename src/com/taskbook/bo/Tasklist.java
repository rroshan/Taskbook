package com.taskbook.bo;

public class Tasklist {
	private String owner;
	private int tasklistID;
	private String taskName;
	
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
}
