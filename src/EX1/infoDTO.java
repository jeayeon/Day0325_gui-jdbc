package EX1;

public class infoDTO {
	private String id;
	private String name;
	private String product;
	private int Quantity;
	private int price;
	private int total;

	infoDTO() {

	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotal() {
		return total;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
