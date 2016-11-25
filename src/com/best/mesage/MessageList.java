package com.best.mesage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3089215081575427911L;
	private ArrayList<MessageData> data;
	/**
	 * @return the data
	 */
	public List<MessageData> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void addData(MessageData data) {
		this.data .add(data) ;
	}
	
	public void init(){
		data =new ArrayList<MessageData>();
	}
}
