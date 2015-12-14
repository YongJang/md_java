package food;

public class LikedDTO {
	private int num;
	private String id;
	public LikedDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LikedDTO(int num, String id) {
		super();
		this.num = num;
		this.id = id;
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
}
