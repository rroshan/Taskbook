package com.taskbook.bo;

public class UserProfile {
	private String userId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private float karmaPointsTotal;
	private float karmaPointsBlocked;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public float getKarmaBalance() {
		return karmaPointsTotal - karmaPointsBlocked;
	}
	
	public void setKarmaPointsTotal(float karmaPointsTotal) {
		this.karmaPointsTotal = karmaPointsTotal;
	}
	
	public void setKarmaPointsBlocked(float karmaPointsBlocked) {
		this.karmaPointsBlocked = karmaPointsBlocked;
	}
	
	public float getKarmaPointsTotal() {
		return karmaPointsTotal;
	}

	public float getKarmaPointsBlocked() {
		return karmaPointsBlocked;
	}
}
