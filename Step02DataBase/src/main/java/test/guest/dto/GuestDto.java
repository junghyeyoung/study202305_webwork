package test.guest.dto;

import java.sql.Date;

public class GuestDto {
	private int num;
	private String writer;
	private String content;
	private String pwd;
	private java.sql.Date regdate;
	
	public GuestDto() {}

	public GuestDto(int num, String writer, String content, String pwd, Date regdate) {
		super();
		this.num = num;
		this.writer = writer;
		this.content = content;
		this.pwd = pwd;
		this.regdate = regdate;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public java.sql.Date getRegdate() {
		return regdate;
	}

	public void setRegdate(java.sql.Date regdate) {
		this.regdate = regdate;
	}

	
}
