package com.jmhqmc.demo;

import java.text.SimpleDateFormat;

public class Constants {
	public static final String ENCODING_UTF8 = "UTF-8";

	// user key
	public static final String USER = "user";
	public static final String RAND = "rand";

	public static final String ROWS = "rows";
	public static final String PAGE = "page";
	public static final String TOTAL = "total";
	// public static final String SEARCH_RESULT = "search_result";

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat(DATE_FORMAT);
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
	public static final SimpleDateFormat DATE_TIME_FORMATER = new SimpleDateFormat(DATE_TIME_FORMAT);

	public static final String LOGIN_PAGE = "/m!/e.htm";

	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final int DEFAULT_CURRENT_PAGE = 1;
	
	public static final String SUCCESS = "success";
	public static final String SUCCESS_MESSAGE = "操作成功！";
	public static final String FAILED = "failed";
	public static final String SUCCESS_FAILED = "操作失败！";
	
	public static final String UPLOAD_DIR = "upload";
	
	public static final String SPACE = "space";
	public static final String NULL = "null";
	
	//区别前台、后台访问标识
	public static final int FROM_PORTAL = 1;
	public static final int FROM_SYSTEM = 2;
}
