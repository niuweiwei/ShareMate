package bean;

public class TitleBean {
	private UserBean user;
	private TypeBean type;
	public TitleBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TitleBean(UserBean user, TypeBean type) {
		super();
		this.user = user;
		this.type = type;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public TypeBean getType() {
		return type;
	}
	public void setType(TypeBean type) {
		this.type = type;
	}
	
}
