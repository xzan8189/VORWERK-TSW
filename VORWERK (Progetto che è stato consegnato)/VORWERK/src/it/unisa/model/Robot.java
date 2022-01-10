package it.unisa.model;

public class Robot {
	//variabili d'istanza
	private int code;
	private String name;
	private String description;
	private String price;
	private String quantity;
	
	//metodi costruttori
	public Robot() {
		this.code=-1;
		this.name="";
		this.description="";
		this.price="";
		this.quantity="";
	}
	
	public Robot(int code, String name, String description, String price, String quantity) {
		this.code= code;
		this.name=name;
		this.description=description;
		this.price=price;
		this.quantity=quantity;
	}

	//metodi d'accesso
	public int getCode() {
		return this.code;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPrice() {
		return price;
	}

	public String getQuantity() {
		return quantity;
	}

	//metodi modificatori
	public void setCode(int code) {
		this.code= code;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	//metodi sovrascritti
	public boolean equals(Object otherObject) {
		if (otherObject==null) return false;
		if (!(otherObject instanceof Robot))
			return false;
		Robot other= (Robot) otherObject;
		return this.code==other.getCode();
	}
	
	public String toString() {
		return "name: " + this.name + ", description: " + this.description + ", price: " + this.price + ", quantity: " + this.quantity;
	}
	
	
	
	
	
	
	
	
	
	
}//fine classe













