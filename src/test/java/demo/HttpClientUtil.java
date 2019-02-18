package demo;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {
	/**
	 * @param url
	 *            发送请求的 URL
	 * @param jsonStr
	 *            需要post的数据
	 * @param headers
	 *            请求头
	 * @return 所代表远程资源的响应结果
	 */
	public static String postParam(String url, String jsonStr, Map<String, String> headers) {
		HttpPost post = new HttpPost();
		HttpResponse response = null;
		try {
			post.setURI(new URI(url));
			if (StringUtils.isNotEmpty(jsonStr)) {
				post.setEntity(new StringEntity(jsonStr, "UTF-8"));
			}
			if (MapUtils.isNotEmpty(headers)) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					post.addHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpClient httpClient = HttpClients.createDefault();
			response = httpClient.execute(post);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("got an error from HTTP for url:" + url, e);
		} finally {
			if (response != null) {
				EntityUtils.consumeQuietly(response.getEntity());
			}
			post.releaseConnection();
		}
	}

	public static String getParam(String url, Map<String, String> headers) {
		HttpGet get = new HttpGet();
		HttpResponse response = null;
		try {
			get.setURI(new URI(url));
			// if (StringUtils.isNotEmpty(jsonStr)) {
			// get.setParams(params);
			// post.setEntity(new StringEntity(jsonStr, "UTF-8"));
			// }
			if (MapUtils.isNotEmpty(headers)) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					get.addHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpClient httpClient = HttpClients.createDefault();
			response = httpClient.execute(get);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("got an error from HTTP for url:" + url, e);
		} finally {
			if (response != null) {
				EntityUtils.consumeQuietly(response.getEntity());
			}
			get.releaseConnection();
		}
	}

	public static void main(String[] args) {

//		P_HBHQMC   6CFF1695053213C2
		// 请求头
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=utf-8");
		headers.put("accept", "application/json; charset=utf-8");
		// 进行http的post请求
		JSONObject js = JSONObject.parseObject(getParam(
				"https://api.pingan.com.cn/oauth/oauth2/access_token?client_id=P_HEXIAO001&grant_type=client_credentials&client_secret=VEy764Mf",
				headers));
		String token = ((JSONObject)js.get("data")).getString("access_token");
		System.out.println(token);

		String aeskey = "6CFF1695053213C2";
		JSONObject jo = new JSONObject();
		jo.put("couponNo", "972401229341");//972401229341
		jo.put("partnerOrderId", "1111111111123332");
		jo.put("outletId", "100000000175790");
		String data = jo.toString();
		data = AESUtils.encrypt(data, aeskey);
		System.err.println(data);

		String req = "https://api.pingan.com.cn/open/appsvr/property/partner/redemption?access_token="+token+"&request_id=P_HEXIAO001&userName=18040519142";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data",
				data);
		jsonObject.put("partnerId", "P_HBHQMC");
		jsonObject.put("timestamp", new Date().getTime());

		// 生成需要post的json字符串
		String jsonStr = jsonObject.toJSONString();

		String resp = postParam(req,jsonStr,headers);
		System.out.println(resp);




		// 请求头
//		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Content-Type", "application/json; charset=utf-8");
//		headers.put("accept", "application/json; charset=utf-8");
//		// 进行http的post请求
//		JSONObject js = JSONObject.parseObject(getParam(
//				"https://test-api.pingan.com.cn:20443/oauth/oauth2/access_token?client_id=P_OKAY001&grant_type=client_credentials&client_secret=VuVEh743",
//				 headers));
//		String token = ((JSONObject)js.get("data")).getString("access_token");
//		System.out.println(token);
//
//		String aeskey = "802CE4BBD34F1B65";
//		JSONObject jo = new JSONObject();
//		jo.put("couponNo", "972401229341");//972401229341
//		jo.put("partnerOrderId", "1111111111122");
//		jo.put("outletId", "719863470568243456");
//		String data = jo.toString();
//		data = AESUtils.encrypt(data, aeskey);
//		System.err.println(data);
//
//		String req = "https://test-api.pingan.com.cn:20443/open/appsvr/property/partner/redemption?access_token="+token+"&request_id=P_OKAY001&userName=13788888305";
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("data",
//				data);
//		jsonObject.put("partnerId", "P_HUANQIUMINGCHE");
//		jsonObject.put("timestamp", new Date().getTime());
//
//		// 生成需要post的json字符串
//		String jsonStr = jsonObject.toJSONString();
//
//		String resp = postParam(req,jsonStr,headers);
//		System.out.println(resp);

		// parent_id = P_HUANGQIUMINGCHE ,aesKey = 802CE4BBD34F1B65
		// String key = "802CE4BBD34F1B65";
		// String data = "{\"couponNo\":\"66666666\",\" partnerOrderId \":
		// \"777777\",\"outletId\": \"9999999\"}";
		// System.out.println(AESUtil.Encrypt(data, key, key));

	}

}