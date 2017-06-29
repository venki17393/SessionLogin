package com.session.helper;

public class Data {
	

	private JsonData user;

	@Override
	public String toString() {
		return "Data [user=" + user + "]";
	}

	public JsonData getUser() {
		return user;
	}

	public void setUser(JsonData user) {
		this.user = user;
	}

}
