package com.jmhqmc.demo.protocol.pdu;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import com.jmhqmc.demo.protocol.util.ByteBuffer;
import com.jmhqmc.demo.protocol.util.NotEnoughDataInByteBufferException;
import com.jmhqmc.demo.protocol.util.TerminatingZeroNotFoundException;

public abstract class ByteData extends ProtocolObject {

	private static final String SMPP_TIME_DATE_FORMAT = "yyyyMMddHHmmss";

	private static SimpleDateFormat dateFormatter;

	static {
		dateFormatter = new SimpleDateFormat(SMPP_TIME_DATE_FORMAT);
		dateFormatter.setLenient(false);
	}

	public abstract void setData(ByteBuffer buffer) throws PDUException,
			NotEnoughDataInByteBufferException,
			TerminatingZeroNotFoundException, UnsupportedEncodingException;

	public abstract ByteBuffer getData() throws ValueNotSetException,
			UnsupportedEncodingException;

	public ByteData() {
	}

	protected String appendSpace(String str, int length) {
		StringBuffer sb = null;
		if (str.length() < length) {
			sb = new StringBuffer(str);
			int i = length - str.length();
			for (int j = 0; j < i; j++) {
				sb.append(" ");
			}
		} else {
			sb = new StringBuffer(str.substring(0, length));
		}

		return sb.toString();
	}
}