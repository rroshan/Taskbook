package com.taskbook.bo;

public class MessageBean {
	private String message;
	private String type;
	
	public String getMessage() 
	{
		return message;
	}
	public void setMessage(String message) 
	{
		this.message = message;
	}
	public String getType() 
	{
		return type;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public void appendMessage(String message) {
		this.message = this.message + "<br>" + message;
	}
}
