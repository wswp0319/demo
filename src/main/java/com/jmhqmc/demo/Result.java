package com.jmhqmc.demo;

public class Result {
	private String message;
	private String result;

	public Result() {
	}

	public Result(String message) {
		super();
		this.message = message;
		this.result = Constants.SUCCESS;
	}

	public Result(String message, String result) {
		super();
		this.message = message;
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}

