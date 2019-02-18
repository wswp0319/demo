package com.jmhqmc.demo.protocol.pdu;

public class InvalidPDUException extends PDUException {
	private static final long serialVersionUID = 3098867436913161548L;
	private Exception underlyingException = null;

	public InvalidPDUException(PDU pdu, Exception e) {
		super(pdu, e);
		underlyingException = e;
	}

	public InvalidPDUException(PDU pdu, String s) {
		super(pdu, s);
	}

	public String toString() {
		String s = super.toString();
		if (underlyingException != null) {
			s += "\nUnderlying exception: " + underlyingException.toString();
		}
		return s;
	}

	public Exception getException() {
		return underlyingException;
	}
}