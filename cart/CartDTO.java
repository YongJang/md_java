package cart;

public class CartDTO {
	private int memberNum;
	private String foodList;
	private int foodCost;
	private String totalCost;
	private int coupon;
	public CartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartDTO(int memberNum, String foodList, int foodCost, String totalCost, int coupon) {
		super();
		this.memberNum = memberNum;
		this.foodList = foodList;
		this.foodCost = foodCost;
		this.totalCost = totalCost;
		this.coupon = coupon;
	}
	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}
	public void setFoodList(String foodList) {
		this.foodList = foodList;
	}
	public void setFoodCost(int foodCost) {
		this.foodCost = foodCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}
	public int getMemberNum() {
		return memberNum;
	}
	public String getFoodList() {
		return foodList;
	}
	public int getFoodCost() {
		return foodCost;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public int getCoupon() {
		return coupon;
	}
	
	
}
