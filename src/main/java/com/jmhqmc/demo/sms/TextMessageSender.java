package com.jmhqmc.demo.sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class TextMessageSender {
	private static HttpClient client;
	private static TextMessageSender sender;
	private static final String URL = "https://dx.ipyy.net/ensms.ashx";
	private static final String USER_ID = "55946";// 用户ID。
	private static final String USER_NAME = "ZZ00248";// 用户账号名
	// 平台用户ZZ00248
	// 平台密码 ZZ0024801 
	// 平台地址 http://c.ipyy.net
	private static final String PASSWORD = "ZZ0024810";

	private TextMessageSender() {

	}

	public static TextMessageSender newInstance() {
		if (sender == null) {
			sender = new TextMessageSender();
		}
		if (client == null) {
			try {
				client = new SSLClient();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sender;
	}

	private HttpResponse sendMessage(String mobile, String content) throws Exception   {
		// 扩展号，没有请留空
		String ext = "";

		// 即时短信请留空，定时短信请指定，格式为：yyyy-MM-dd HH:mm:ss
		String sendTime = "";
		String stamp = new SimpleDateFormat("MMddHHmmss").format(new Date());
		String secret = MD5.GetMD5Code(PASSWORD + stamp).toUpperCase();

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("UserName", USER_NAME);
		jsonObj.put("Stamp", stamp);
		jsonObj.put("Secret", secret);
		jsonObj.put("Moblie", mobile);
		jsonObj.put("Text", content);
		jsonObj.put("Ext", ext);
		jsonObj.put("SendTime", sendTime);

		// Des加密，base64转码
		String text64 = DesHelper.Encrypt(jsonObj.toString(), PASSWORD);

		HttpPost post = new HttpPost(URL);
		post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("UserId", USER_ID));
		nvps.add(new BasicNameValuePair("Text64", text64));
		post.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response = client.execute(post);
		//System.out.println(response.getStatusLine());

		//HttpEntity entity = response.getEntity();
		//EntityUtils.toString(entity, "UTF-8");
		//System.out.println(returnString);
		//EntityUtils.consume(entity);
		
		return response;
	}

	/**
	 * 加密发送DEMO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// 目标手机号，多个以半角","分隔 17762396017,13581448730
		String mobile = "13581448730";

		// 信息内容
		String content = "环球出行验证码：{0}【环球出行】";

		try {
			//TextMessageSender.newInstance().sendMessage("13581448730", content.replace("{0}", "8776"));
			//TextMessageSender.newInstance().sendMessage("17762396017", content.replace("{0}", "9985"));
			HttpResponse response = TextMessageSender.newInstance().sendMessage(mobile, content.replace("{0}", "9985"));
			System.out.println(response.getStatusLine()+"\n"+response.getEntity());
			
			HttpEntity entity = response.getEntity();
			String returnString = EntityUtils.toString(entity, "UTF-8");
			System.out.println(returnString);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 即时短信请留空，定时短信请指定，格式为：yyyy-MM-dd HH:mm:ss
		/*
		 * String sendTime = ""; String stamp = new
		 * SimpleDateFormat("MMddHHmmss").format(new Date()); String secret =
		 * MD5.GetMD5Code(PASSWORD + stamp).toUpperCase();
		 * 
		 * try { JSONObject jsonObj = new JSONObject(); jsonObj.put("UserName",
		 * USER_NAME); jsonObj.put("Stamp", stamp); jsonObj.put("Secret", secret);
		 * jsonObj.put("Moblie", mobile); jsonObj.put("Text", content);
		 * jsonObj.put("Ext", ext); jsonObj.put("SendTime", sendTime);
		 * 
		 * // Des加密，base64转码 String text64 = DesHelper.Encrypt(jsonObj.toString(),
		 * PASSWORD);
		 * 
		 * client = new SSLClient(); HttpPost post = new HttpPost(URL);
		 * post.setHeader("Content-type",
		 * "application/x-www-form-urlencoded;charset=utf-8"); List<NameValuePair> nvps
		 * = new ArrayList<NameValuePair>(); nvps.add(new BasicNameValuePair("UserId",
		 * USER_ID)); nvps.add(new BasicNameValuePair("Text64", text64));
		 * post.setEntity(new UrlEncodedFormEntity(nvps)); HttpResponse response =
		 * client.execute(post); System.out.println(response.getStatusLine());
		 * 
		 * HttpEntity entity = response.getEntity(); String returnString =
		 * EntityUtils.toString(entity, "UTF-8"); System.out.println(returnString);
		 * EntityUtils.consume(entity); } catch (Exception ex) {
		 * System.out.println(ex.getMessage()); }
		 */
	}
}
