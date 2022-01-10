package it.unisa.control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import it.unisa.model.ProductModelDM;
import it.unisa.model.Robot;

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB     //@MultipartConfig ci vuole!!!! altrimenti non ti fa caricare nulla
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //variabili d'istanza
	static ProductModelDM model= new ProductModelDM();
	public static String message=null;
	public static String errorMessage=null;
	static String SAVE_DIR="/uploadTemp";
	
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String order= request.getParameter("order");
		String action= request.getParameter("action");
		
		if (action!=null && !action.equals("")) {
			try {
				if (action.equalsIgnoreCase("details")) {
					Robot robot= model.doRetrieveByKey(request.getParameter("code"));
					request.removeAttribute("product");
					request.setAttribute("product", robot);

					request.getServletContext().setAttribute("list", "robotList");//serve solo a mettere in primo piano la lista dei robot
					
				} else if (action.equalsIgnoreCase("getPicture")) {
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
					
				} else if (action.equalsIgnoreCase("delete")) {
					Robot robot= model.doRetrieveByKey(request.getParameter("code"));
					
					model.doDelete(robot);
					
					request.setAttribute("message", Controller.message);
					request.setAttribute("errorMessage", Controller.errorMessage);
					Controller.errorMessage=null;
					Controller.message=null;

					request.getServletContext().setAttribute("list", "robotList");//serve solo a mettere in primo piano la lista dei robot
					
				} else if (action.equalsIgnoreCase("insert")) {
					Robot robot= new Robot();
					
					robot.setName(request.getParameter("name"));
					robot.setDescription(request.getParameter("description"));
					robot.setPrice(request.getParameter("price"));
					robot.setQuantity(request.getParameter("quantity"));

					model.doSave(robot);

					//Aggiornamento immagine
					Robot highestRobot= model.doRetrieveByKey(""+model.doRetrieveHighest());
					
					String appPath= request.getServletContext().getRealPath("");
					String savePath= appPath + File.separator + SAVE_DIR;
					
					File photoFile= new File(savePath);
					if (!photoFile.exists())
						photoFile.mkdir();
					
					for(Part part : request.getParts()) {
						String fileName= this.extractFileName(part);
						if (fileName!=null && !fileName.equals("")) {
							part.write(savePath + File.separator + fileName);
							try {
								ProductModelDM.uploadPhoto(highestRobot.getCode(), savePath + File.separator + fileName);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}	
					
					request.setAttribute("message", Controller.message);
					request.setAttribute("errorMessage", Controller.errorMessage);
					Controller.errorMessage=null;
					Controller.message=null;

					request.getServletContext().setAttribute("list", "robotList");//serve solo a mettere in primo piano la lista dei robot
					
				} else if (action.equalsIgnoreCase("updateRobot")) {
					String code= request.getParameter("codeRobot");
					String name= request.getParameter("updateNameRobot");
					String description = request.getParameter("updateDescriptionRobot");
					String price = request.getParameter("updatePriceRobot");
					String quantity = request.getParameter("updateQuantityRobot");	
					
					Robot robot = new Robot();
					robot.setCode(Integer.parseInt(code));
					robot.setName(name);
					robot.setDescription(description);
					robot.setPrice(price);
					robot.setQuantity(quantity);
					
					model.doUpdate(robot);
					
					//Aggiornamento immagine
					String appPath= request.getServletContext().getRealPath("");
					String savePath= appPath + File.separator + SAVE_DIR;
					
					File photoFile= new File(savePath);
					if (!photoFile.exists())
						photoFile.mkdir();
					
					for(Part part : request.getParts()) {
						String fileName= this.extractFileName(part);
						if (fileName!=null && !fileName.equals("")) {
							part.write(savePath + File.separator + fileName);
							try {
								ProductModelDM.uploadPhoto(Integer.parseInt(code), savePath + File.separator + fileName);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}	
					
					request.setAttribute("message", Controller.message);
					request.setAttribute("errorMessage", Controller.errorMessage);
					Controller.errorMessage=null;
					Controller.message=null;

					request.getServletContext().setAttribute("list", "robotList");//serve solo a mettere in primo piano la lista dei robot
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
			
			
		try {
			request.getServletContext().removeAttribute("listRobot"); //prima era request.removeAttribute("listRobot");
			request.getServletContext().setAttribute("listRobot", model.doRetrieveAll(order)); //prima era request.setAttribute("listRobot", model.doRetrieveAll(order));
			
			request.getServletContext().setAttribute("list", "robotList");//serve solo a mettere in primo piano la lista dei robot
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//capire se la richiesta della lista dei robot proviene da robot.jsp, e quindi fare il
		//dispatcher a chi l'ha richiesta
		if (action!=null && !action.equals(""))
			if (action.equalsIgnoreCase("dispatchRobot")) {
				RequestDispatcher view= this.getServletContext().getRequestDispatcher("/account/robot.jsp");
				view.forward(request, response);
				return;
			}

		RequestDispatcher view= this.getServletContext().getRequestDispatcher("/home.jsp");
		view.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
	    String[] items = contentDisp.split(";");
	    for (String s : items) {
	    	if (s.trim().startsWith("filename")) {
	    		return s.substring(s.indexOf("=") + 2, s.length()-1);
	    	}
	    }
	    return "";
	}
}
