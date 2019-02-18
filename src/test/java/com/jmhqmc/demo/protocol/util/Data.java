package com.jmhqmc.demo.protocol.util;

public class Data {
	public static final int DEMO_HEADER_LEN = 5;
	//public static final int DEMO_BODY_LEN = 100;
	
	public static final String BOOL = "BOOL";
	public static final String INT = "INT";
	public static final String REAL = "REAL";

	// UPCPP Command Set
	public static final int RECEIVER = 0x00000001;
	public static final int SEND = 0x00000002;

	// Command_Status Error Codes
	public static final int ESME_ROK = 0x00000000;
	public static final int ESME_RINVMSGLEN = 0x00000001;

	// Interface_Version
	public static final byte PP_V1 = (byte) 0x10;

	// American Standard Code for Information Interchange
	public static final String ENC_ASCII = "ASCII";
	// Windows Latin-1
	public static final String ENC_CP1252 = "Cp1252";
	// ISO 8859-1, Latin alphabet No. 1
	public static final String ENC_ISO8859_1 = "ISO8859_1";
	// Sixteen-bit Unicode Transformation Format, big-endian byte order
	// with byte-order mark
	public static final String ENC_UTF16_BEM = "UnicodeBig";
	// Sixteen-bit Unicode Transformation Format, big-endian byte order
	public static final String ENC_UTF16_BE = "UnicodeBigUnmarked";
	// Sixteen-bit Unicode Transformation Format, little-endian byte order
	// with byte-order mark
	public static final String ENC_UTF16_LEM = "UnicodeLittle";
	// Sixteen-bit Unicode Transformation Format, little-endian byte order
	public static final String ENC_UTF16_LE = "UnicodeLittleUnmarked";
	// Eight-bit Unicode Transformation Format
	public static final String ENC_UTF8 = "UTF8";
	// Sixteen-bit Unicode Transformation Format, byte order specified by
	// a mandatory initial byte-order mark
	public static final String ENC_UTF16 = "UTF-16";
	// GSM 7-bit unpacked
	// Requires JVM 1.4 or later
	public static final String ENC_GSM7BIT = "X-Gsm7Bit";

}
