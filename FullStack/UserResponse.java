package com.crud.demo.responses;

public class UserResponse {
	private String message;
	private Object object;

	public UserResponse(String message, Object object) {
		super();
		this.message = message;
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
