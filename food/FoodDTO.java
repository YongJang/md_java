package food;

public class FoodDTO {
	private int num;
	private String name;
	private String cost;
	private String img;
	private String filepath;
	private String type;
	private String region;
	private int coupon;
	private int rate;
	private String regdate;
	public FoodDTO() {
		super();
	}
	public FoodDTO(int num, String name, String cost, String img,
			String filepath, String type, String region, int coupon, int rate,
			String regdate) {
		super();
		this.num = num;
		this.name = name;
		this.cost = cost;
		this.img = img;
		this.filepath = filepath;
		this.type = type;
		this.region = region;
		this.coupon = coupon;
		this.rate = rate;
		this.regdate = regdate;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getCoupon() {
		return coupon;
	}
	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
}
