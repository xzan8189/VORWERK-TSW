package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.PremioModelDM;
import it.unisa.model.ProductModelDM;
import it.unisa.model.Robot;

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB     //@MultipartConfig ci vuole!!!! altrimenti non ti fa caricare nulla
@WebServlet("/AccountController")
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static ProductModelDM model = new ProductModelDM();

	public AccountController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			if (action != null && !action.equals("")) {
				if (action.equalsIgnoreCase("lastRobot")) {
					ArrayList<Robot> lastRobot= new ArrayList<Robot>();
					int code = model.doRetrieveHighest();
					
					Robot robot1= model.doRetrieveByKey(""+code);
					code--;
					Robot robot2= model.doRetrieveByKey(""+code);
					code--;
					Robot robot3= model.doRetrieveByKey(""+code);
					
					lastRobot.add(robot1);
					lastRobot.add(robot2);
					lastRobot.add(robot3);
					
					request.setAttribute("lastRobot", lastRobot);
					
				}  else if (action.equalsIgnoreCase("getPictureRobot")) {
					String idCode= (String)request.getParameter("code");
					byte[] photo=null;
					
					if (idCode!=null && !idCode.equals("")) {
						photo= ProductModelDM.load(idCode);
						
						if (photo!=null) {
							ServletOutputStream outStreamServlet= response.getOutputStream();
							response.setContentType("image/*");
							outStreamServlet.write(photo);
							outStreamServlet.close();
						}
					}
					return;
					
				} else if (action.equalsIgnoreCase("getPictureAward")) {
					String idName= (String)request.getParameter("name");
					byte[] photo=null;
					
					if (idName!=null && !idName.equals("")) {
						photo= PremioModelDM.load(idName);
						
						if (photo!=null) {
							ServletOutputStream outStreamServlet= response.getOutputStream();
							response.setContentType("image/*");
							outStreamServlet.write(photo);
							outStreamServlet.close();
						}
					}
					return;
					
				}
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher view= this.getServletContext().getRequestDispatcher("/account/account.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	
	
}// fine servlet










