package com.session.helper;

public class AnyWhereUser {
	private boolean ok;
	private Data data;
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "AnyWhereUser [ok=" + ok + ", data=" + data + "]";
	}
	

}
