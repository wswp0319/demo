package com.jmhqmc.demo.protocol;

public class ProtocolException extends Exception {
	private static final long serialVersionUID = 3108928509613380097L;

	public ProtocolException() {
		super();
	}

	public ProtocolException(Exception e) {
		super(e);
	}

	public ProtocolException(String s) {
		super(s);
	}

	public ProtocolException(String s, Exception e) {
		super(s, e);
	}
}
