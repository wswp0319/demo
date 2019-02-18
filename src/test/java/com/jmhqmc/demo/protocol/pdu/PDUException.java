package com.jmhqmc.demo.protocol.pdu;

import com.jmhqmc.demo.protocol.ProtocolException;

public class PDUException extends ProtocolException {
	private static final long serialVersionUID = 1293599401770736306L;
	private transient PDU pdu = null;

	public PDUException() {
	}

	public PDUException(PDU pdu) {
		setPDU(pdu);
	}

	public PDUException(String s) {
		super(s);
	}

	public PDUException(PDU pdu, String s) {
		super(s);
		setPDU(pdu);
	}

	public PDUException(PDU pdu, Exception e) {
		super(e);
		setPDU(pdu);
	}

	public PDUException(PDU pdu, String s, Exception e) {
		super(s, e);
		setPDU(pdu);
	}

	public String toString() {
		String s = super.toString();
		return s;
	}

	public void setPDU(PDU pdu) {
		this.pdu = pdu;
	}

	public PDU getPDU() {
		return pdu;
	}

	public boolean hasPDU() {
		return pdu != null;
	}
}
