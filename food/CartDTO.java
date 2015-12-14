package food;

public class CartDTO {
	private int num;
	private String id;
	private int quantity;
	public CartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartDTO(int num, String id, int quantity) {
		super();
		this.num = num;
		this.id = id;
		this.quantity = quantity;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	public int getQuantity(){
		return quantity;
	}

}
