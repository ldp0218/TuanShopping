package com.ldp.tuanshopping.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 向服务端发送请求,得到请求的数据
 * @author Administrator
 *
 */
public class HttpUtils {
	public static String download(String uri) throws IOException {
		HttpURLConnection conn = null; //连接对象
		InputStream is = null;
		String resultData = "";
		try {
			URL url = new URL(uri); //URL对象
			conn = (HttpURLConnection)url.openConnection(); //使用URL打开一个链接
			conn.setDoInput(true); //允许输入流，即允许下载
			conn.setDoOutput(true); //允许输出流，即允许上传
			conn.setUseCaches(false); //不使用缓冲
			conn.setRequestMethod("GET"); //使用get请求
			is = conn.getInputStream();   //获取输入流，此时才真正建立链接
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bufferReader = new BufferedReader(isr);
			String inputLine  = "";
			while((inputLine = bufferReader.readLine()) != null){
				resultData += inputLine + "\n";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				conn.disconnect();
			}
		}
		return resultData;
	}

}
