package com.jmhqmc.demo.protocol.util;

import com.jmhqmc.demo.protocol.ProtocolException;

public class TerminatingZeroNotFoundException extends ProtocolException {
	private static final long serialVersionUID = 7028315742573472677L;

	public TerminatingZeroNotFoundException() {
		super("Terminating zero not found in buffer.");
	}

	public TerminatingZeroNotFoundException(String s) {
		super(s);
	}

}