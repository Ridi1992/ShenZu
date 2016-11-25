package com.zuwo.userinfo;

import java.io.Serializable;

public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4939825135961569907L;
	private String user_id;
    private String user_name;
    private float  user_money;
    private String user_rank;
    private String rank_id;
    public String getRank_id() {
		return rank_id;
	}
	public void setRank_id(String rank_id) {
		this.rank_id = rank_id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String nickname;
    private String face;
    private String age;
    private String height;
    private String work;
    private String signature;
    private String address;
    private String idCard;
    private String hasCertify;
	private int sex;
	private boolean islogin;
	
	public float getUser_money() {
		return user_money;
	}
	public void setUser_money(float user_money) {
		this.user_money = user_money;
	}
	public String getUser_rank() {
		return user_rank;
	}
	public void setUser_rank(String user_rank) {
		this.user_rank = user_rank;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getHasCertify() {
		return hasCertify;
	}
	public void setHasCertify(String hasCertify) {
		this.hasCertify = hasCertify;
	}
	public String getId() {
		return user_id;
	}
	public void setId(String id) {
		this.user_id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getNickname() {
		return nickname;
	}
	public boolean isIslogin() {
		return islogin;
	}
	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}

	

}
