package com.jmhqmc.demo.protocol.pdu;

import java.io.UnsupportedEncodingException;

import com.jmhqmc.demo.protocol.util.ByteBuffer;
import com.jmhqmc.demo.protocol.util.Data;
import com.jmhqmc.demo.protocol.util.NotEnoughDataInByteBufferException;
import com.jmhqmc.demo.protocol.util.TerminatingZeroNotFoundException;

class DemoBody extends ByteData {
	private String content;
	private PDUHeader header;

	public DemoBody(PDUHeader header) {
		this.header = header;
	}
	
	public DemoBody(PDUHeader header,String content) {
		this.header = header;
		this.content = content;
	}

	@Override
	public void setData(ByteBuffer buffer) throws PDUException, NotEnoughDataInByteBufferException,
			TerminatingZeroNotFoundException, UnsupportedEncodingException {
		content = buffer.removeString(header.getLength(), Data.ENC_UTF8);
		header.setLength(content.length());

		buffer = null;
	}

	@Override
	public ByteBuffer getData() throws ValueNotSetException, UnsupportedEncodingException {
		ByteBuffer buffer = new ByteBuffer();
		buffer.appendString(getContent(), Data.ENC_UTF8);

		return buffer;
	}

	public PDUHeader getHeader() {
		return header;
	}

	public void setHeader(PDUHeader header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof DemoBody))
			return false;
		DemoBody ch = (DemoBody) obj;
		return ch.hashCode() == this.hashCode();
	}

	public int hashCode() {
		return this.toString().hashCode() * 2000;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\ncontent:" + this.getContent());

		return sb.toString();
	}
}
