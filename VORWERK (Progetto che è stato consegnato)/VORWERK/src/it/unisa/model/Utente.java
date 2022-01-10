package it.unisa.model;

public class Utente {
	//variabili d'istanza
	private String username;
	private String password;
	private String points;
	private String role; //ACCOUNT oppure ADMINISTRATOR
	
	//metodi costruttori
	public Utente() {
		this.username="";
		this.password="";
		this.points="-1";
		this.role="";
	}
	
	public Utente(String username, String password, String points, String role) {
		this.username=(username==null? "":username);
		this.password=(password==null? "":password);
		this.points=(points==null? "":points);
		this.role=(role==null? "":role);
	}

	//metodi d'accesso
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPoints() {
		return points;
	}

	public String getRole() {
		return role;
	}

	//metodi modificatori
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public void setRole(String role) {
		this.role = role;
	}

	//metodi sovrascritti
	public String toString() {
		return this.getClass().getName() + "[username: " + this.username + ", password: " + this.password +
				", points: " + this.points + ", role: " + this.role + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
} //fine classe

















