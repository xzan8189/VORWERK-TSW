package it.unisa.model;

public class Premio{
	//variabili d'istanza
	private String name;
	private String description;
	private String punti;
	private String quantity;
	
	//metodi costruttori
	public Premio() {
		this.name="-1";
		this.description="";
		this.punti="";
		this.quantity="";
	}
	
	public Premio(String name, String description, String punti, String quantity) {
		this.name=(name==null? "":name);
		this.description=(description==null? "":description);
		this.punti=(punti==null? "":punti);
		this.quantity=(quantity==null? "":quantity);
	}

	//metodi d'accesso
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPunti() {
		return punti;
	}

	public String getQuantity() {
		return quantity;
	}

	//metodi modificatori
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPunti(String punti) {
		this.punti = punti;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
	
	
	
}
