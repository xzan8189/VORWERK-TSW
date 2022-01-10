package it.login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.control.AwardsController;
import it.unisa.model.Utente;
import it.unisa.model.UtenteModelDM;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	// variabili d'istanza
	private static final long serialVersionUID = 1L;
	static UtenteModelDM model = new UtenteModelDM();

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String action = request.getParameter("action");
		HttpSession oldSession = request.getSession(false);

		try {
			if (action.equalsIgnoreCase("login")) {
				String permission = checkLogin(username, password);
				if (permission!=null) {
					if (permission.equalsIgnoreCase("administrator")) {
						// l'autenticazione è corretta ed è un ADMIN
	
						// recupero la sessione
						if (oldSession != null)
							oldSession.invalidate(); // invalida la sessione se già esiste
	
						HttpSession currentSession = request.getSession(); // crea una nuova sessione
						currentSession.setAttribute("user", username);
						currentSession.setAttribute("password", password);
						currentSession.setAttribute("admin", true);
						currentSession.setMaxInactiveInterval(5 * 60); // 5 minuti di inattività massima
	
						response.sendRedirect(request.getContextPath() + "/home.jsp");
						return;
	
					} else if (permission.equalsIgnoreCase("account")) {
						// l'autenticazione è corretta ed è un ACCOUNT
	
						// recupero la sessione
						if (oldSession != null)
							oldSession.invalidate(); // invalida la sessione se già esiste
	
						HttpSession currentSession = request.getSession(); // crea una nuova sessione
						currentSession.setAttribute("user", username);
						currentSession.setAttribute("password", password);
						currentSession.setAttribute("admin", false);
						currentSession.setAttribute("account", true);
						currentSession.setMaxInactiveInterval(5 * 60); // 5 minuti di inattività massima
	
						response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/account/account.jsp"));
						return;
	
					}
				} else {
					System.out.println("login sbagliato");
					request.getServletContext().setAttribute("errorMessage", "Inserisci <b>Username</b> e <b>Password</b> corretti");
				}
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		// se l'autenticazione fallisce
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.jsp"));
		
	}

	private String checkLogin(String username, String password) throws SQLException {
		Utente utente = model.doRetrieveByKey(username);
		System.out.println(utente);
		System.out.println("utente database: " + utente.getPassword() + "\nusername del sito: " + username + "\npassword del sito: " + password);
		
		if (utente.getUsername()!=null && utente.getPassword()!=null && username!=null && password!=null)
			if (!utente.getUsername().equals("") && !utente.getPassword().equals("") && !username.equals("") && !password.equals(""))
				if (utente.getPassword().equals(password) && utente.getUsername().equals(username))
					if (utente.getRole().equalsIgnoreCase("administrator"))
						return "administrator";
					else
						return "account";
		
		return null;
	}

}// fine servlet
