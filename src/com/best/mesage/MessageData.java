package com.best.mesage;

import java.io.Serializable;

public class MessageData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6160628326190561973L;
	private String face;
	private String message;
	private String id;
	private int type;
	private String time;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
   
       
}
