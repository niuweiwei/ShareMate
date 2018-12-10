package bean;

public class TypeBean {
	private int typeId;
	private String typeName;
	
	public TypeBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TypeBean(int typeId, String typeName) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
	}
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
