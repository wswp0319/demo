package com.jmhqmc.demo.controller;

import com.jmhqmc.demo.Constants;
import com.jmhqmc.demo.Result;

public abstract class BaseController {
	private Result result = new Result(Constants.SUCCESS_MESSAGE);

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public void setResult(String result, String message) {
		getResult().setResult(result);
		getResult().setMessage(message);
	}

	public void setResult(String message) {
		getResult().setMessage(message);
	}

}
