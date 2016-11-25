package com.zuwo.data;

import java.io.Serializable;
import java.util.List;

public class Accept implements Serializable{
  /**
	 * 
	 */
	private static final long serialVersionUID = -3758396927776706189L;
private Boolean canAccept;
private List<data> Messagedata;


public List<data> getMessagedata() {
	return Messagedata;
}

public void setMessagedata(List<data> messagedata) {
	Messagedata = messagedata;
}

public Boolean getCanAccept() {
	return canAccept;
}

public void setCanAccept(Boolean canAccept) {
	this.canAccept = canAccept;
}
  
  
   public class data implements Serializable{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 6647118974022571322L;
	private String id;
        private String user_id;
        private String message_id;
        private int isAccept;
        private String message_userid;
        private String add_time;
        private int hasPay;
        private String pay_time;
        private String face;
        private String nickname;
        private int type;
        
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		public String getMessage_id() {
			return message_id;
		}
		public void setMessage_id(String message_id) {
			this.message_id = message_id;
		}
		public int getIsAccept() {
			return isAccept;
		}
		public void setIsAccept(int isAccept) {
			this.isAccept = isAccept;
		}
		public String getMessage_userid() {
			return message_userid;
		}
		public void setMessage_userid(String message_userid) {
			this.message_userid = message_userid;
		}
		public String getAdd_time() {
			return add_time;
		}
		public void setAdd_time(String add_time) {
			this.add_time = add_time;
		}
		public int getHasPay() {
			return hasPay;
		}
		public void setHasPay(int hasPay) {
			this.hasPay = hasPay;
		}
		public String getPay_time() {
			return pay_time;
		}
		public void setPay_time(String pay_time) {
			this.pay_time = pay_time;
		}
		public String getFace() {
			return face;
		}
		public void setFace(String face) {
			this.face = face;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
        
   }
}
