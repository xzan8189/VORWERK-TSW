package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.Carrello;
import it.unisa.model.CarrelloModelDM;
import it.unisa.model.Premio;
import it.unisa.model.PremioModelDM;
import it.unisa.model.ProductModelDM;
import it.unisa.model.Robot;
import it.unisa.model.Utente;
import it.unisa.model.UtenteModelDM;

/**
 * Servlet implementation class RegisteredAccountController
 */
@WebServlet("/RegisteredAccountController")
public class RegisteredAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static CarrelloModelDM modelCarrello = new CarrelloModelDM();
	static ProductModelDM modelRobot = new ProductModelDM();
	static PremioModelDM modelAward =  new PremioModelDM();
	static UtenteModelDM modelUtente = new UtenteModelDM();
	public static String idNameAward=null;
	public static String message=null;
	public static String errorMessage=null;

	public RegisteredAccountController() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession currentSession = request.getSession();
		String username = (String) currentSession.getAttribute("user");
		String codeRobot = request.getParameter("codeRobot");

		try {
			if (action != null && !action.equals("")) {
				if (action.equalsIgnoreCase("addCarrelloRobot")) {
					this.addCarrelloRobot(request, username);
					
				} else if (action.equalsIgnoreCase("buyAward")) {
					//da completare
					String codeAward = request.getParameter("codeAward");
					Utente utente= modelUtente.doRetrieveByKey(username);
					Premio premio= modelAward.doRetrieveByKey(codeAward);
					RegisteredAccountController.idNameAward= codeAward;
					int puntiUtente= Integer.parseInt(utente.getPoints());
					int puntiPremio= Integer.parseInt(premio.getPunti());
					
					//aggiorno sia la quantità del premio disponibile e poi i punti dell'utente
					if (puntiUtente>=puntiPremio && Integer.parseInt(premio.getQuantity())>0) {
						int quantity= Integer.parseInt(premio.getQuantity()) - 1;
						premio.setQuantity(""+quantity);
						System.out.println("quantity premio: " + quantity);
						modelAward.doUpdate(premio);
						
						int newPuntiUtente= puntiUtente - puntiPremio;
						System.out.println("codeAward :" + codeAward + "\nnewPuntiUtente: " + newPuntiUtente + "\npuntiUtente: " + puntiUtente + "\npuntiPremio: " + puntiPremio + "\n");
						utente.setPoints(""+newPuntiUtente);
						modelUtente.doUpdate(utente);

						request.setAttribute("message", "La compera del premio è andata a buon fine!");
						request.setAttribute("errorMessage", RegisteredAccountController.errorMessage);
						RegisteredAccountController.errorMessage=null;
						RegisteredAccountController.message=null;
					} else {
						request.setAttribute("errorMessage", "Non hai abbastanza <b>Punti</b>!");
						RegisteredAccountController.errorMessage=null;
						RegisteredAccountController.message=null;
					}

					request.removeAttribute("points");
					request.removeAttribute("listPremi");
					request.setAttribute("points", modelUtente.doRetrieveByKey(username).getPoints());
					request.setAttribute("listPremi", modelAward.doRetrieveAll(""));


					RequestDispatcher view = this.getServletContext().getRequestDispatcher("/account/premi.jsp");
					view.forward(request, response);
					return;
					
				} else if (action.equalsIgnoreCase("buyAll")) {
					ArrayList<Carrello> carrello = modelCarrello.doRetrieveAll(username);
					
					//calcolo i punti
					int somma=0;
					for (Carrello robot: carrello) {
						somma += 350 * robot.getQuantity();
					}
					
					//aggiorno i punti all'utente
					Utente utente= modelUtente.doRetrieveByKey(username);
					int points= Integer.parseInt(utente.getPoints()) + somma;
					utente.setPoints(""+points);
					modelUtente.doUpdate(utente);
					
					//elimino tutto dal carrello perchè ha comprato
					modelCarrello.doDeleteAll(username);
					
					request.setAttribute("message", "La compera è andata a buon fine!");
					request.setAttribute("errorMessage", RegisteredAccountController.errorMessage);
					RegisteredAccountController.errorMessage=null;
					RegisteredAccountController.message=null;
					
				} else if (action.equalsIgnoreCase("delete")) {
					Robot robot= modelRobot.doRetrieveByKey(request.getParameter("code"));
					Carrello productInCarrello = modelCarrello.doRetrieveByKeyRobot(username, robot.getCode());
					int quantity= Integer.parseInt(robot.getQuantity()) + productInCarrello.getQuantity();
					robot.setQuantity(""+quantity);

					modelRobot.doUpdate(robot);
					modelCarrello.doDeleteRobot(username, robot);

					request.setAttribute("message", RegisteredAccountController.message);
					request.setAttribute("errorMessage", RegisteredAccountController.errorMessage);
					RegisteredAccountController.errorMessage=null;
					RegisteredAccountController.message=null;
					
				} else if(action.equalsIgnoreCase("clear")) {
					ArrayList<Carrello> carrello= (ArrayList<Carrello>) currentSession.getAttribute("carrello");
					
					for(int i=0; i<carrello.size(); i++) {
						Robot robot= modelRobot.doRetrieveByKey(""+carrello.get(i).getCodeRobot());
						int quantity= Integer.parseInt(robot.getQuantity()) + carrello.get(i).getQuantity();
						robot.setQuantity(""+quantity);
						
						modelRobot.doUpdate(robot);
						modelCarrello.doDeleteAll(username);
					}
					
					request.setAttribute("message", RegisteredAccountController.message);
					request.setAttribute("errorMessage", RegisteredAccountController.errorMessage);
					RegisteredAccountController.errorMessage=null;
					RegisteredAccountController.message=null;
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		//carrello, punti e robot(cioè i robot che stanno nel carrello dell'utente)
		try {
			request.removeAttribute("carrello");
			request.removeAttribute("robots");
			request.removeAttribute("listPremi");
			request.removeAttribute("listRobot");
			
			ArrayList<Carrello> prova= modelCarrello.doRetrieveAll(username);
			ArrayList<Robot> robots= new ArrayList<Robot>();
			for (Carrello product : prova) { //sto prendendo i prodotti dal carrello per farmi avere la lista dei robot dell'utente
				robots.add(modelRobot.doRetrieveByKey(""+product.getCodeRobot()));
			}
			
			request.setAttribute("carrello", prova);
			request.setAttribute("robots", robots);
			request.setAttribute("listPremi", modelAward.doRetrieveAll(""));
			request.setAttribute("listRobot", modelRobot.doRetrieveAll(""));
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("nessun carrello");
		}
		
		//capire a chi fare il dispatch delle liste
		if (action!=null && !action.equalsIgnoreCase("")) {
			if (username!=null && action.equalsIgnoreCase("dispatchPremiLogged")) {
				try {
					request.removeAttribute("points");
					request.setAttribute("points", modelUtente.doRetrieveByKey(username).getPoints());
					
					RequestDispatcher view = this.getServletContext().getRequestDispatcher("/account/premi.jsp");
					view.forward(request, response);
					return;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (action.equalsIgnoreCase("dispatchPremi")) {
				RequestDispatcher view = this.getServletContext().getRequestDispatcher("/account/premi.jsp");
				view.forward(request, response);
				return;
				
			} else if (action.equalsIgnoreCase("dispatchRobot")) {
				RequestDispatcher view = this.getServletContext().getRequestDispatcher("/account/robot.jsp");
				view.forward(request, response);
				return;
				
			}
		}
		
		RequestDispatcher view = this.getServletContext().getRequestDispatcher("/account/carrello.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	
	//metodi d'appoggio
	private void addCarrelloRobot(ServletRequest request, String username) throws NumberFormatException, SQLException {
		String codeRobot = request.getParameter("codeRobot");
		
		System.out.println("add carrello robot");
		Carrello carrello = modelCarrello.doRetrieveByKeyRobot(username, Integer.parseInt(codeRobot));
		System.out.println("username del carrello:" + carrello.getUsername() + "\nusername della sessione:" + username);
		if (!carrello.getUsername().equalsIgnoreCase("")) {
			System.out.println("modificare il prodotto");
			// significa che il prodotto è già è nel carrello e quindi bisogna solo
			// aggiornare la quantità

			// aggiungo quantità +1 al prodotto nel carrello
			Robot robot = modelRobot.doRetrieveByKey("" + carrello.getCodeRobot());
			if (Integer.parseInt(robot.getQuantity()) > 0) {
				modelCarrello.doUpdateRobot(username, robot, carrello.getQuantity() + 1);

				// diminuisco la quantità di uno del prodotto nel negozio
				robot.setQuantity(String.valueOf(Integer.parseInt(robot.getQuantity()) - 1));
				modelRobot.doUpdate(robot);
				System.out.println("prodotto aggiornato anche nello store\n\n");
				
				request.setAttribute("message", RegisteredAccountController.message);
				request.setAttribute("errorMessage", RegisteredAccountController.errorMessage);
				RegisteredAccountController.errorMessage=null;
				RegisteredAccountController.message=null;
				
			} else { //non è possibile aggiungere il robot al carrello
				System.out.println("\n\n");
				request.setAttribute("message", RegisteredAccountController.message);
				request.setAttribute("errorMessage", "Il prodotto è esaurito");
				RegisteredAccountController.errorMessage=null;
				RegisteredAccountController.message=null;
			}
		} else {
			System.out.println("aggiungere completamente il prodotto");
			// significa che il prodotto non lo tiene nel carrello e quindi bisogna
			// aggiungerlo

			// aggiungo il nuovo prodotto acquistato
			Robot robot = modelRobot.doRetrieveByKey("" + codeRobot);
			System.out.println("codeRobot:" + robot.getCode());
			if (Integer.parseInt(robot.getQuantity()) > 0) {
				modelCarrello.doSaveRobot(username, robot);
				System.out.println("prodotto salvato nel carrello");
				
				// diminuisco la quantità di uno del prodotto nel negozio
				robot.setQuantity(String.valueOf(Integer.parseInt(robot.getQuantity()) - 1));
				modelRobot.doUpdate(robot);
				System.out.println("prodotto aggiornato anche nello store\n\n");
				
				request.setAttribute("message", RegisteredAccountController.message);
				request.setAttribute("errorMessage", RegisteredAccountController.errorMessage);
				RegisteredAccountController.errorMessage=null;
				RegisteredAccountController.message=null;
				
			} else { //non è possibile aggiungere il robot al carrello
				System.out.println("\n\n");
				request.setAttribute("message", RegisteredAccountController.message);
				request.setAttribute("errorMessage", "Il prodotto è esaurito");
				RegisteredAccountController.errorMessage=null;
				RegisteredAccountController.message=null;
			}
		}
		
	}
}
