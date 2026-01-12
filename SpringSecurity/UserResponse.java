package com.security.demo.responses;

public class UserResponse {

	private String message;
	private Object data;

	public UserResponse(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;       
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
