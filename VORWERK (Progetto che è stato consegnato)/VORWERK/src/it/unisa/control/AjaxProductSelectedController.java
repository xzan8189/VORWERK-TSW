package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.ProductModelDM;
import it.unisa.model.Robot;

@WebServlet("/AjaxProductSelectedController")
public class AjaxProductSelectedController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductModelDM modelRobot = new ProductModelDM();

	public AjaxProductSelectedController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			if (action != null && !action.equalsIgnoreCase("")) {
				if (action.equalsIgnoreCase("details")) {
					String codeRobot = request.getParameter("codeRobot");
					String searchCodeRobot = request.getParameter("SearchRobot");

					if (codeRobot != null && !codeRobot.equalsIgnoreCase("")) {
						Robot robot = modelRobot.doRetrieveByKey(codeRobot);

						request.setAttribute("robot", robot);
					} else if (searchCodeRobot != null && !searchCodeRobot.equalsIgnoreCase("")) {
						ArrayList<Robot> listRobot = modelRobot.doRetrieveAll("");
						ArrayList<Robot> listSearchedRobot = new ArrayList<Robot>();
						
						for (Robot robot : listRobot)
							if (robot.getName().toUpperCase().startsWith(searchCodeRobot.toUpperCase()))
								listSearchedRobot.add(robot);
								
						request.setAttribute("listSearchedRobot", listSearchedRobot);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		RequestDispatcher view = this.getServletContext().getRequestDispatcher("/account/ajaxProductSelected.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
