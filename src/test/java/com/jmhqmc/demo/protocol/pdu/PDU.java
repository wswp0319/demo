package com.jmhqmc.demo.protocol.pdu;

import java.io.UnsupportedEncodingException;

import com.jmhqmc.demo.protocol.util.NotEnoughDataInByteBufferException;


public abstract class PDU extends ByteData {

	public PDU() {

	}

	public abstract void setHeader(PDUHeader header)
			throws NotEnoughDataInByteBufferException,
			UnsupportedEncodingException;

	public abstract PDUHeader getHeader();

	public boolean canResponse() {
		return false;
	}

	public abstract boolean isRequest();

	public abstract boolean isResponse();
}