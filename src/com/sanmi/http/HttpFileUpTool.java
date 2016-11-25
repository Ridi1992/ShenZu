package com.sanmi.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

public class HttpFileUpTool {

	private Handler handler;
	public static void postsms(Context context,String params,
			Map<String, Bitmap> files,Myreturn myreturn) throws IOException {
		if(isNetworkAvailable(context)){
			Log.i("网络状态", "网络可用");
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		URL uri = new URL("http://zuwo.seotech.com.cn/app_api/publish.php?act=rent");
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(100 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false);// 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append(BOUNDARY);
		sb.append(LINEND);
		sb.append("Content-Disposition: form-data; name=\""
				+ "data" + "\"" + LINEND);// 请求的参数名
		sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
		sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
		sb.append(LINEND);
		sb.append(params);//请求的参数内容
		sb.append(LINEND);
		System.out.println(sb+"@@@@@@@@@@@@@@@@@@@@@@@@@@/////////////////////////");
		
		DataOutputStream outStream = new DataOutputStream(conn
				.getOutputStream());
		outStream.write(sb.toString().getBytes());
		InputStream in = null;
		// 发送文件数据
		if (files != null){
			for (Entry<String, Bitmap> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file[]\"; filename=\""
								+ file.getKey() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				System.out.println(sb1.toString());
				outStream.write(sb1.toString().getBytes());
				InputStream is = Bitmap2IS(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				is.close();
				outStream.write(LINEND.getBytes());
			}
		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// 得到响应码
		int res = conn.getResponseCode();
		if (res == 200) {
			in = conn.getInputStream();
			int ch;
			StringBuilder sb2 = new StringBuilder();
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
			System.out.println("发布返回"+sb2.toString());
			try {
				JSONObject jsonObject=new JSONObject(sb2.toString());
				if(jsonObject.getString("code").equals("1")){
					myreturn.result(1,"发布成功");
				}else{
					myreturn.result(0,jsonObject.getString("meesage"));
				}
			} catch (Exception e) {
				System.out.println(e.toString());
				myreturn.result(0,"发布失败");
			}
		}
		outStream.close();
		conn.disconnect();
		}
		}else{
			Log.i("网络状态", "网络不可用");
			myreturn.result(0,"发布失败");
		}
	}
    //提交认证信息
	public static void certify(Context context,String params,
			Map<String, Bitmap> files,Myreturn myreturn) throws IOException {
		if(isNetworkAvailable(context)){
			Log.i("网络状态", "网络可用");
			String BOUNDARY = java.util.UUID.randomUUID().toString();
			String PREFIX = "--", LINEND = "\r\n";
			String MULTIPART_FROM_DATA = "multipart/form-data";
			String CHARSET = "UTF-8";
			URL uri = new URL("http://zuwo.seotech.com.cn/app_api/userInfo.php?act=certify");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setReadTimeout(100 * 1000); // 缓存的最长时间
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);
			// 首先组拼文本类型的参数
			StringBuilder sb = new StringBuilder();
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ "data" + "\"" + LINEND);// 请求的参数名
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(params);//请求的参数内容
			sb.append(LINEND);
			System.out.println(params);
			
			DataOutputStream outStream = new DataOutputStream(conn
					.getOutputStream());
			outStream.write(sb.toString().getBytes());
			InputStream in = null;
			// 发送文件数据
			if (files != null){
				for (Entry<String, Bitmap> file : files.entrySet()) {
					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					sb1.append("Content-Disposition: form-data; name=\"file[]\"; filename=\""
							+ file.getKey() + "\"" + LINEND);
					sb1.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINEND);
					sb1.append(LINEND);
					System.out.println(sb1.toString());
					outStream.write(sb1.toString().getBytes());
					InputStream is = Bitmap2IS(file.getValue());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						outStream.write(buffer, 0, len);
					}
					is.close();
					outStream.write(LINEND.getBytes());
				}
				// 请求结束标志
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
				outStream.write(end_data);
				outStream.flush();
				// 得到响应码
				int res = conn.getResponseCode();
				if (res == 200) {
					in = conn.getInputStream();
					int ch;
					StringBuilder sb2 = new StringBuilder();
					while ((ch = in.read()) != -1) {
						sb2.append((char) ch);
					}
					System.out.println("发布返回"+sb2.toString());
					try {
						JSONObject jsonObject=new JSONObject(sb2.toString());
						if(jsonObject.getString("code").equals("1")){
							myreturn.result(1,jsonObject.getString("data"));
						}else{
							myreturn.result(0,jsonObject.getString("message"));
						}
					} catch (Exception e) {
						System.out.println(e.toString());
						myreturn.result(0,"提交失败");
					}
				}
				outStream.close();
				conn.disconnect();
			}
		}else{
			Log.i("网络状态", "网络不可用");
			myreturn.result(0,"提交失败");
		}
	}
//更新头像
	public static void uphead(Context context,String user_id,
			Map<String, Bitmap> files,Myreturn myreturn) throws IOException {
		if(isNetworkAvailable(context)){
			Log.i("网络状态", "网络可用");
			String BOUNDARY = java.util.UUID.randomUUID().toString();
			String PREFIX = "--", LINEND = "\r\n";
			String MULTIPART_FROM_DATA = "multipart/form-data";
			String CHARSET = "UTF-8";
			URL uri = new URL("http://zuwo.seotech.com.cn/app_api/userInfo.php?act=uphead");
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setReadTimeout(100 * 1000); // 缓存的最长时间
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);
			// 首先组拼文本类型的参数
			StringBuilder sb = new StringBuilder();
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ "user_id" + "\"" + LINEND);// 请求的参数名
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(user_id);//请求的参数内容
			sb.append(LINEND);
			System.out.println("aaa"+user_id);
			DataOutputStream outStream = new DataOutputStream(conn
					.getOutputStream());
			outStream.write(sb.toString().getBytes());
			InputStream in = null;
			// 发送文件数据
			if (files != null){
				for (Entry<String, Bitmap> file : files.entrySet()) {
					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					sb1.append("Content-Disposition: form-data; name=\"file[]\"; filename=\""
							+ file.getKey() + "\"" + LINEND);
					sb1.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINEND);
					sb1.append(LINEND);
					System.out.println(sb1.toString());
					outStream.write(sb1.toString().getBytes());
					InputStream is = Bitmap2IS(file.getValue());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						outStream.write(buffer, 0, len);
					}
					is.close();
					outStream.write(LINEND.getBytes());
				}
				// 请求结束标志
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
				outStream.write(end_data);
				outStream.flush();
				// 得到响应码
				int res = conn.getResponseCode();
				if (res == 200) {
					in = conn.getInputStream();
					int ch;
					StringBuilder sb2 = new StringBuilder();
					while ((ch = in.read()) != -1) {
						sb2.append((char) ch);
					}
					System.out.println("返回"+sb2.toString());
					try {
						JSONObject jsonObject=new JSONObject(sb2.toString());
						if(jsonObject.getString("code").equals("1")){
							myreturn.result(1,jsonObject.getString("data"));
						}else{
							myreturn.result(0,jsonObject.getString("meesage"));
						}
					} catch (Exception e) {
						System.out.println(e.toString());
						myreturn.result(0,"更新失败");
					}
				}
				outStream.close();
				conn.disconnect();
			}
		}else{
			Log.i("网络状态", "网络不可用");
			myreturn.result(0,"网络不可用");
		}
	}
		//寻租
	public static void seeking(Context context,String params,
			Map<String, Bitmap> files,Myreturn myreturn) throws IOException {
		if(isNetworkAvailable(context)){
			Log.i("网络状态", "网络可用");
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		URL uri = new URL("http://zuwo.seotech.com.cn/app_api/publish.php?act=seeking");
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(100 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append(BOUNDARY);
		sb.append(LINEND);
		sb.append("Content-Disposition: form-data; name=\""
				+ "data" + "\"" + LINEND);// 请求的参数名
		sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
		sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
		sb.append(LINEND);
		sb.append(params);//请求的参数内容
		sb.append(LINEND);
		System.out.println(params);
		
		DataOutputStream outStream = new DataOutputStream(conn
				.getOutputStream());
		outStream.write(sb.toString().getBytes());
		InputStream in = null;
		// 发送文件数据
		if (files != null){
			for (Entry<String, Bitmap> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file[]\"; filename=\""
								+ file.getKey() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				System.out.println(sb1.toString());
				outStream.write(sb1.toString().getBytes());
				
				
				InputStream is = Bitmap2IS(file.getValue());//将图片转换成流
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				is.close();
				outStream.write(LINEND.getBytes());
			}
		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// 得到响应码
		int res = conn.getResponseCode();
		if (res == 200) {
			in = conn.getInputStream();
			int ch;
			StringBuilder sb2 = new StringBuilder();
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
			System.out.println("发布返回"+sb2.toString());
			try {
				JSONObject jsonObject=new JSONObject(sb2.toString());
				if(jsonObject.getString("code").equals("1")){
					myreturn.result(1,"发布成功");
				}else{
					myreturn.result(0,jsonObject.getString("meesage"));
				}
			} catch (Exception e) {
				System.out.println(e.toString());
				myreturn.result(0,"发布失败");
			}
		}
		outStream.close();
		conn.disconnect();
		}
		}else{
			Log.i("网络状态", "网络不可用");
			myreturn.result(0,"网络不可用");
		}
	}
	public interface Myreturn{

		void result(int i, String string);
		
	}
	   /**
	 * 将位图转换为输入流
	 */
	private static InputStream  Bitmap2IS(Bitmap bm){  
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	            bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);  
	            InputStream sbs = new ByteArrayInputStream(baos.toByteArray());    
	            return sbs;  
	        }
	/**
	 * 本地获取图片转换成位图
	 * @param 图片路径
	 * @return 位图
	 */
	public Bitmap getbitmap(String path){
		File file = new File(path);
   	 if (file.exists()) {
   		 	Bitmap bm=BitmapFactory.decodeFile(path);
   			return bm;
   	 	}else{
   	 		return null;
   	 	}
	}
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) 
			{
				// 当前网络是连接的
				if (info.getState() == NetworkInfo.State.CONNECTED) 
				{
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}
}
