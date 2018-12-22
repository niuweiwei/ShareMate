package bean;

import java.util.Date;

public class FollowBean {
	private int followId;
	private UserBean userbean;
	private boolean status;
	private Date date;
	
	public FollowBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public int getFollowId() {
		return followId;
	}

	public void setFollowId(int followId) {
		this.followId = followId;
	}

	public UserBean getUserbean() {
		return userbean;
	}
	public void setUserbean(UserBean userbean) {
		this.userbean = userbean;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
