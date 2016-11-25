package com.zuwo.data;

public class Comment {
	
	private String comment_id;
	private String content;
	private String comment_rank;
	private String add_time;
	private String nickname;
	private String face;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getComment_rank() {
		return comment_rank;
	}
	public void setComment_rank(String comment_rank) {
		this.comment_rank = comment_rank;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

}
