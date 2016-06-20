package com.example.httputil;


public class CRecord {

	private String Cname;
	private String dateTime;
	private Integer Cscore;
	private int Rank;
	public int getRank() {
		return Rank;
	}
	public void setRank(int rank) {
		Rank = rank;
	}
	public String getCname() {
		return Cname;
	}
	public void setCname(String cname) {
		Cname = cname;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime=dateTime;
	}
	public Integer getCscore() {
		return Cscore;
	}
	public void setCscore(Integer cscore) {
		Cscore = cscore;
	}
	
	
}
