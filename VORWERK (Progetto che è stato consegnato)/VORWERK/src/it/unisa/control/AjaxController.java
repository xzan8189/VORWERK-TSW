package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.ProductModelDM;
import it.unisa.model.Robot;

@WebServlet("/AjaxController")
public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductModelDM modelRobot = new ProductModelDM();
	
    public AjaxController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		ArrayList<Robot> listRobot= new ArrayList<Robot>();
		ArrayList<Robot> robotNeed= new ArrayList<Robot>(); //sarebbero i robot di cui abbiamo bisogno
		
		StringBuffer packed= new StringBuffer();
		packed.append("<infoRobots>");
		
		String nameRobot = (String) request.getParameter("nameRobot");
		System.out.println(nameRobot);
		if (nameRobot!=null && !nameRobot.equals("")) {
			try {
				listRobot= modelRobot.doRetrieveAll("");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			for (Robot robot : listRobot)
				if (robot.getName().toUpperCase().startsWith(nameRobot.toUpperCase())) //ci metto i robot di cui ho bisogno
					robotNeed.add(robot);
			
			for (Robot robot : robotNeed) {
				packed.append("<codeRobot>"); //mi prendo i codici
				packed.append(robot.getCode());
				packed.append("</codeRobot>");
				packed.append("<nameRobot>"); //mi prendo i nomi che mostrerò
				packed.append(robot.getName());
				packed.append("</nameRobot>");
			}
		}
		
		packed.append("</infoRobots>");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		response.getWriter().write(packed.toString());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
