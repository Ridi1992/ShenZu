package com.zuwo.data;

public class MeCallData {
	private String face;
	private String sex;
	private String age;
	private String nickname;
	private String appointment_id;
	private long appointment_time;
	private int isAccept;
	private int hasPay;
	private int hasConfirm;// 是否确认支付
	private Boolean isMy;// true我约了谁，false谁约了我
	private long pay_time;
	private String publisher_tel;
	private String tel;
	private String user_id;
	private String message_userid;
	

	private data message;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMessage_userid() {
		return message_userid;
	}

	public void setMessage_userid(String message_userid) {
		this.message_userid = message_userid;
	}

	public int getHasConfirm() {
		return hasConfirm;
	}

	public void setHasConfirm(int hasConfirm) {
		this.hasConfirm = hasConfirm;
	}

	public String getPublisher_tel() {
		return publisher_tel;
	}

	public void setPublisher_tel(String publisher_tel) {
		this.publisher_tel = publisher_tel;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Boolean getIsMy() {
		return isMy;
	}

	public void setIsMy(Boolean isMy) {
		this.isMy = isMy;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(String appointment_id) {
		this.appointment_id = appointment_id;
	}

	public long getAppointment_time() {
		return appointment_time;
	}

	public void setAppointment_time(long appointment_time) {
		this.appointment_time = appointment_time;
	}

	public int getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(int isAccept) {
		this.isAccept = isAccept;
	}

	public int getHasPay() {
		return hasPay;
	}

	public void setHasPay(int hasPay) {
		this.hasPay = hasPay;
	}

	public long getPay_time() {
		return pay_time;
	}

	public void setPay_time(long pay_time) {
		this.pay_time = pay_time;
	}

	public data getMessage() {
		return message;
	}

	public void setMessage(data message) {
		this.message = message;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public class data {
		private String id;
		private String user_id;
		private String schedule_start;
		private String schedule_end;
		private float rent;
		private String picture;
		private String skills;
		private String address;
		private long add_time;
		private int type;
		private String myrange;
		private String category;
		private String isshow;
		private String startdate;
		private String enddate;

		

		public String getStartdate() {
			return startdate;
		}

		public void setStartdate(String startdate) {
			this.startdate = startdate;
		}

		public String getEnddate() {
			return enddate;
		}

		public void setEnddate(String enddate) {
			this.enddate = enddate;
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

		public String getSchedule_start() {
			return schedule_start;
		}

		public void setSchedule_start(String schedule_start) {
			this.schedule_start = schedule_start;
		}

		public String getSchedule_end() {
			return schedule_end;
		}

		public void setSchedule_end(String schedule_end) {
			this.schedule_end = schedule_end;
		}

		public float getRent() {
			return rent;
		}

		public void setRent(float rent) {
			this.rent = rent;
		}

		public String getPicture() {
			return picture;
		}

		public void setPicture(String picture) {
			this.picture = picture;
		}

		public String getSkills() {
			return skills;
		}

		public void setSkills(String skills) {
			this.skills = skills;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public long getAdd_time() {
			return add_time;
		}

		public void setAdd_time(long add_time) {
			this.add_time = add_time;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getMyrange() {
			return myrange;
		}

		public void setMyrange(String myrange) {
			this.myrange = myrange;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getIsshow() {
			return isshow;
		}

		public void setIsshow(String isshow) {
			this.isshow = isshow;
		}

	}

}
