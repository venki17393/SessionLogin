package com.session.helper;

public class JsonData {

	@Override
	public String toString() {
		return "JsonData [id=" + id + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", accountId="
				+ accountId + ", login=" + login + ", firstName=" + firstName + ", lastName=" + lastName + ", status="
				+ status + "]";
	}
	private String id;
	private String createdAt;
	private String modifiedAt;
	private String accountId;
	private String login;
	private String firstName;
	private String lastName;
	private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
