package com.taskbook.bo;

public class Subtask {
	private int sNo;
	private String status;
	private String description;
	
	public int getsNo() {
		return sNo;
	}
	
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
