package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);
	static final String KEY_ALGORITHM = "AES";
	static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

	// 加密
	/*
	 * str 源字符串 key 秘钥 iv 向量秘钥
	 */

	public static String Encrypt(String str, String key, String iv) {
		String resultObj = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKey secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			// 使用加密模式初始化 密钥
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes("UTF-8")));
			// 按单部分操作加密或解密数据，或者结束一个多部分操作。
			byte[] encode = cipher.doFinal(str.getBytes("UTF-8"));
			resultObj = new BASE64Encoder().encode(encode);
		} catch (Exception e) {
			logger.error("AESUtil Encrypt1 : ", e);
		}
		return resultObj;
	}

	// 解密
	/*
	 * sSrc 源字符串 sKey 秘钥 ivStr 向量秘钥
	 */
	public static String Decrypt(String sSrc, String sKey, String ivStr) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				logger.error("AES解密的key为空");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				logger.error("AES解密的key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "UTF-8");
				// originalString= unEscapeChar(originalString);
				return originalString;
			} catch (Exception e) {
				logger.error("base64解密错误", e);
				return null;
			}
		} catch (Exception ex) {
			logger.error("AES解密错误", ex);
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String dataSecret = "1234567890abcdef";
		String dataSecretIV = "1234567890abcdef";
		String mw = "{\"province\": \"山东\",\"pageNo\": \"1\",\"pageSize\": \"2\"}";
		System.out.println(AESUtil.Encrypt(mw,dataSecret, dataSecretIV));
		
		String miw = "ffNySaPT0qQG9/TSxApnHgEq74DDaLc0dkgxR8AxHULaNw8unwi2hLN3+4bEzVu1yqG/fjO4y7r2J74Fh/ocrg==";
		System.out.println(AESUtil.Decrypt(miw,dataSecret, dataSecretIV));
		
		System.out.println(AESUtil.Encrypt("{\"couponNo\":\"66666666\",\" partnerOrderId \": \"777777\",\"outletId\": \"9999999\"}","5D2C8305C9B6FF24", ""));
		//d08PaSsa08TqWRluKtxXAxqVg20gcZFlrD/Fp5nQz9ZdnEh6tHIhDyTGnUBTvS46C/FY8x5KJ3drIBOObsblBg==
		//d08PaSsa08TqWRluKtxXAxqVg20gcZFlrD/Fp5nQz9ZdnEh6tHIhDyTGnUBTvS46C/FY8x5KJ3dr
		//IBOObsblBg==
		
		
	}
}