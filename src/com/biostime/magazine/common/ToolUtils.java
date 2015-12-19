package com.biostime.magazine.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * 工具类
 * 
 * @author xc 新增日期：2013-4-2下午05:30:06
 * @version 1.0
 */
public class ToolUtils {

	/**
	 * UUID生成
	 * 
	 * @return
	 * @author xc 新增日期：2013-4-2下午04:03:40
	 * @version 1.0
	 */
	public static String newRandomUUID() {
		String uuidRaw = UUID.randomUUID().toString();
		return uuidRaw.replaceAll("-", "");
	}

	/**
	 * 获取请求传输过来的字节流，转为String返回
	 * 
	 * @param request
	 * @return
	 * @author xc 新增日期：2013-4-9上午10:36:52
	 * @version 1.0
	 */
	public static String getStringByInputData(HttpServletRequest request) {
		try {
			InputStream stream = request.getInputStream();
			// 定义读取字节长度
			byte buffer[] = new byte[1024];
			// 定义输出流
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = 0;
			while ((read = stream.read(buffer)) != -1) {
				baos.write(buffer, 0, read);
			}
			stream.close();
			return new String(baos.toByteArray(), "UTF-8");
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * 对传入参数进行MD5加密
	 * 
	 * @param s
	 * @return 加密后的字符串
	 */
	public final static String encrypt(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}
