package com.best.mesage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MessageTool {

	public void adddata(SharedPreferences share,MessageData data){
		
		if(data!=null){
			 MessageList list = null;
			if(getObject(share)==null){
				list=new MessageList();
				list.init();
			}else {
				list=Getdatalist(share);
				if(list==null){
					list=new MessageList();
					list.init();
				}
			}
			list.addData(data);
			 Editor edit = share.edit();
				try {
					edit.putString("datalist", serialize(list));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.toString());
				}
				edit.commit();
				System.out.println("保存消息成功");
		}
	}
	/**
	 * 序列化对象
	 */
	private static String serialize(MessageList data) throws IOException {
		@SuppressWarnings("unused")
		long startTime = System.currentTimeMillis();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		objectOutputStream.writeObject(data);
		String serStr = byteArrayOutputStream.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		objectOutputStream.close();
		byteArrayOutputStream.close();
//		Log.i("serial", "serialize str =" + serStr);
//		long endTime = System.currentTimeMillis();
//		Log.i("serial", "序列化耗时为:" + (endTime - startTime));
		return serStr;
	}
	/**
	 * 获取数据
	 * @param share
	 * @return
	 */
	public static MessageList Getdatalist(SharedPreferences share)  {
		try {
			long startTime = System.currentTimeMillis();
			String redStr = java.net.URLDecoder.decode(getObject(share), "UTF-8");
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					redStr.getBytes("ISO-8859-1"));
			ObjectInputStream objectInputStream = new ObjectInputStream(
					byteArrayInputStream);
			MessageList data = (MessageList) objectInputStream.readObject();
			objectInputStream.close();
			byteArrayInputStream.close();
			long endTime = System.currentTimeMillis();
//			Log.i("serial", "反序列化耗时为:" + (endTime - startTime));
			return data;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@SuppressWarnings("unused")
	private static String getObject(SharedPreferences share) {
		return share.getString("datalist", null);
	}
}
