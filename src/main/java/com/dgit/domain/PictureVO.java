package com.dgit.domain;

import java.util.Date;

public class PictureVO {

	private int pno;
	private String id;
	private String fullname;
	private Date regdate;

	public PictureVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "PictureVO [pno=" + pno + ", id=" + id + ", fullname=" + fullname + ", regdate=" + regdate + "]";
	}

}
