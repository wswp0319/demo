package com.jmhqmc.demo.protocol.pdu;

import java.io.UnsupportedEncodingException;

import com.jmhqmc.demo.protocol.util.ByteBuffer;
import com.jmhqmc.demo.protocol.util.Data;
import com.jmhqmc.demo.protocol.util.NotEnoughDataInByteBufferException;
import com.jmhqmc.demo.protocol.util.TerminatingZeroNotFoundException;

public class DemoRequest extends Request {
	private byte tag = 0x00000001;
	private PDUHeader header = null;
	private DemoBody body = null;
	
	public DemoRequest() {
		
	}
	
	public DemoRequest(String content) {
		header = new PDUHeader();
		header.setTag(getTag());
		header.setLength(content.length());
		
		body = new DemoBody(header,content);
	}
	
	public byte getTag() {
		return tag;
	}

	public void setHeader(PDUHeader header) {
		this.header = header;
	}

	public PDUHeader getHeader() {
		return header;
	}

	public boolean isRequest() {
		return true;
	}

	public boolean isResponse() {
		return false;
	}

	public DemoBody getBody() {
		return body;
	}

	public void setBody(DemoBody body) {
		this.body = body;
	}

	public ByteBuffer getData() throws ValueNotSetException, UnsupportedEncodingException {
		ByteBuffer buffer = header.getData();
		buffer.appendBuffer(body.getData());
		return buffer;
	}

	public void setData(ByteBuffer buffer) throws InvalidPDUException, PDUException, NotEnoughDataInByteBufferException,
			UnsupportedEncodingException, TerminatingZeroNotFoundException {
		ByteBuffer headerBuffer = buffer.removeBuffer(Data.DEMO_HEADER_LEN);
		header = new PDUHeader();
		//header.setTag(tag);
		header.setData(headerBuffer);

		ByteBuffer chBuffer = buffer.removeBuffer(header.getLength());
		body = new DemoBody(header);
		body.setData(chBuffer);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(2048);
		sb.append("$$$$$$$$$$$$$$ Header Info $$$$$$$$$$$$$$$$");
		sb.append(header);
		sb.append("\n****************** Body Info *******************");
		sb.append(body);

		return sb.toString();
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, InvalidPDUException, PDUException, TerminatingZeroNotFoundException, NotEnoughDataInByteBufferException {
		DemoRequest request = new DemoRequest("{\"msg\":\"hello world!\"}");
		
		System.out.println(request.getData().getHexDump());
		
		System.out.println(request);
		
		String hexString = "01160000007b226d7367223a2268656c6c6f20776f726c6421227d";
		ByteBuffer buffer = new ByteBuffer(ByteBuffer.hexStringToBytes(hexString));
		DemoRequest dr = new DemoRequest();
		dr.setData(buffer);
		
		System.out.println(dr);
	}
}
