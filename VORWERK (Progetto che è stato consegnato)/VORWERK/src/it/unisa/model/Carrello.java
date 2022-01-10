package it.unisa.model;

public class Carrello {
	//variabili d'istanza
	private String username;
	private int codeRobot;
	private int quantity;
	
	//metodi costruttori
	public Carrello() {
		this.username="";
		this.codeRobot=-1;
		this.quantity=-1;
	}
	
	public Carrello(String username, int codeRobot, int quantity) {
		this.username=username;
		this.codeRobot=codeRobot;
		this.quantity=quantity;
	}

	//metodi d'accesso
	public String getUsername() {
		return username;
	}

	public int getCodeRobot() {
		return codeRobot;
	}

	public int getQuantity() {
		return quantity;
	}
	
	//metodi modificatori
	public void setUsername(String username) {
		this.username = username;
	}

	public void setCodeRobot(int codeRobot) {
		this.codeRobot = codeRobot;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	//metodi sovrascritti
	public String toString() {
		return this.getClass().getName() + "[username: " + this.username + ", codeRobot: " + this.codeRobot + ", quantity: " + this.quantity + "]";
	}
	
	
	
	
	
	
	
}//fine classe














