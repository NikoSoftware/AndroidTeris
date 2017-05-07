package com.example.Bmob;

import cn.bmob.v3.BmobObject;

public class PlayerData extends  BmobObject{

	private String name;
	private String time;
	private Integer score;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
