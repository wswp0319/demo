package com.jmhqmc.demo.protocol.pdu;

import java.io.UnsupportedEncodingException;

import com.jmhqmc.demo.protocol.util.ByteBuffer;
import com.jmhqmc.demo.protocol.util.NotEnoughDataInByteBufferException;

public class PDUHeader extends ByteData {
	private byte tag;
	private int length;

	public ByteBuffer getData() throws UnsupportedEncodingException {
		ByteBuffer buffer = new ByteBuffer();
		buffer.appendByte(getTag());
		buffer.appendInt(getLength());

		return buffer;
	}

	public void setData(ByteBuffer buffer) throws NotEnoughDataInByteBufferException,
			NotEnoughDataInByteBufferException, UnsupportedEncodingException {
		tag = buffer.removeByte();
		length = buffer.removeInt();
		buffer = null;
	}

	public void setTag(byte tag) {
		this.tag = tag;
	}

	public byte getTag() {
		return tag;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof PDUHeader))
			return false;
		PDUHeader header = (PDUHeader) obj;

		return this.hashCode() == header.hashCode();
	}

	public int hashCode() {
		return this.getTag() * this.getLength();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\ntag:" + this.getTag() + " length:" + this.getLength());

		return sb.toString();
	}
}
