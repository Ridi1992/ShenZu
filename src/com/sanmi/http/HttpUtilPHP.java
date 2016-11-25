package com.sanmi.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class HttpUtilPHP {
	
	
	/**
	 * 
	 * @param handler
	 * @param handlerTag
	 * @param invokeMethodName
	 * @param params
	 */
	public static void invokePost(
			Context context,Handler handler, int handlerTag,
			String invokeMethodName,String[][] params) {
			MyAsyncTask asyncTask = new MyAsyncTask(handler, handlerTag,
					invokeMethodName);
			if(isNetworkAvailable(context)){
				Log.i("网络状态", "网络可用");
				asyncTask.execute(params);
			}else{
				Log.i("网络状态", "网络不可用");
				Toast.makeText(context,"网络链接不可用，您已处于离线状态", 0).show();
				handler.sendMessage(handler.obtainMessage(404, "网络链接不可用，您已处于离线状态"));
			}
	}
	private static class MyAsyncTask extends AsyncTask<String[][], Integer, Void> {
		
		private String invokeMethodName;
		private Handler handler;
		private int handlerTag;

		public MyAsyncTask(Handler handler, int handlerTag, String invokeMethodName) {
			this.invokeMethodName = invokeMethodName;
			this.handlerTag = handlerTag;
			this.handler = handler;
		}

		@Override
		protected Void doInBackground(String[][]... params) {
			return taskLoad(this, handler,handlerTag, invokeMethodName, params);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

	}


	@SuppressWarnings("rawtypes")
	private static Void taskLoad(AsyncTask task, Handler handler,int handlerTag,
		String invokeMethodName,String[][]... params) {
	String result=null;
	Object object=null;
	try {
		String parmer="";
		if(params!=null){
			String[][] param=params[0];
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < param.length; i++) {
				buffer.append(param[i][1]).append("    ");
				parmer+=param[i][0]+"="+param[i][1]+"&";
			}
			if(param.length>0)parmer=parmer.substring(0, parmer.length()-1);
			Log.i("", "开始远程调用， 方法名  : " + invokeMethodName
					+ "\n参数为 : " + buffer);
		}
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);//超时时间
		HttpPost request = new HttpPost(invokeMethodName);
		if (!parmer.isEmpty()){
			StringEntity entity = new StringEntity(parmer,HTTP.UTF_8);
			entity.setContentType("application/x-www-form-urlencoded");
			request.setEntity(entity); 
		}
		HttpResponse response = client.execute(request);  
		HttpEntity entity = response.getEntity();
		byte[] data = EntityUtils.toByteArray(entity);
		result=new String(data,"UTF-8");
		Log.i("", "远程调用方法名  : " + invokeMethodName
				+ "\n返回数据 : " + result);
		handler.sendMessage(handler.obtainMessage(handlerTag, result));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		handler.sendMessage(handler.obtainMessage(404, "编码错误！"));
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		handler.sendMessage(handler.obtainMessage(404, "请求失败"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		handler.sendMessage(handler.obtainMessage(404, "网络链接不可用，您已处于离线状态"));
	} catch (Exception e) {
		e.printStackTrace();
		handler.sendMessage(handler.obtainMessage(404, "网络链接不可用，您已处于离线状态"));
	}
	return null;
	
}
	/**
	 * 检测当的网络（WLAN、3G/2G）状态
	 * @param context Context
	 * @return true 表示网络可用
	 */
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
