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

import it.unisa.model.Premio;
import it.unisa.model.PremioModelDM;

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB     //@MultipartConfig ci vuole!!!! altrimenti non ti fa caricare nulla
@WebServlet("/AwardsController")
public class AwardsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //variabili d'istanza
	static PremioModelDM model= new PremioModelDM();
	public static String message=null;
	public static String errorMessage=null;
	public static String idNameAward=null;
	static String SAVE_DIR="/uploadTemp";
	
    public AwardsController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String order= request.getParameter("order");
		String action= request.getParameter("action");
		
		if (action!=null && !action.equals("")) {
			try {
				if (action.equalsIgnoreCase("details")) {
					Premio premio= model.doRetrieveByKey(request.getParameter("name"));
					request.removeAttribute("premio");
					request.setAttribute("premio", premio);

					request.getServletContext().setAttribute("list", "awardsList");//serve solo a far vedere in primo piano la lista dei premi
					
				} else if (action.equalsIgnoreCase("getPicture")) {
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
					
				} else if(action.equalsIgnoreCase("delete")) {
					Premio premio= model.doRetrieveByKey(request.getParameter("name"));
					
					model.doDelete(premio);
	
					request.setAttribute("message", AwardsController.message);
					request.setAttribute("errorMessage", AwardsController.errorMessage);
					AwardsController.errorMessage=null;
					AwardsController.message=null;

					request.getServletContext().setAttribute("list", "awardsList");//serve solo a far vedere in primo piano la lista dei premi
					
				} else if(action.equalsIgnoreCase("insert")) {
					Premio premio= new Premio();
					
					premio.setName(request.getParameter("nameAward"));
					premio.setDescription(request.getParameter("descriptionAward"));
					premio.setPunti(request.getParameter("pointsAward"));
					premio.setQuantity(request.getParameter("quantityAward"));
					
					model.doSave(premio);

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
								PremioModelDM.uploadPhoto(request.getParameter("nameAward"), savePath + File.separator + fileName);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}	
					
					request.setAttribute("message", AwardsController.message);
					request.setAttribute("errorMessage", AwardsController.errorMessage);
					AwardsController.errorMessage=null;
					AwardsController.message=null;

					request.getServletContext().setAttribute("list", "awardsList");//serve solo a mettere in primo piano la lista dei premi
					
				} else if (action.equalsIgnoreCase("updateAward")) {
					AwardsController.idNameAward= request.getParameter("idNameAward");
					
					String name= request.getParameter("updateNameAward");
					String description = request.getParameter("updateDescriptionAward");
					String points = request.getParameter("updatePointsAward");
					String quantity = request.getParameter("updateQuantityAward");	
					
					Premio premio= new Premio();
					premio.setName(name);
					premio.setDescription(description);
					premio.setPunti(points);
					premio.setQuantity(quantity);
					
					model.doUpdate(premio);

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
								PremioModelDM.uploadPhoto(name, savePath + File.separator + fileName);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}	
					
					request.setAttribute("message", Controller.message);
					request.setAttribute("errorMessage", Controller.errorMessage);
					Controller.errorMessage=null;
					Controller.message=null;

					request.getServletContext().setAttribute("list", "awardsList");//serve solo a mettere in primo piano la lista dei premi
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			request.getServletContext().removeAttribute("listPremi"); //prima era request.removeAttribute("listRobot");
			request.getServletContext().setAttribute("listPremi", model.doRetrieveAll(order)); //prima era request.setAttribute("listRobot", model.doRetrieveAll(order));
			
			request.getServletContext().setAttribute("list", "awardsList");//serve solo a mettere in primo pianola lista dei premi
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		RequestDispatcher view= this.getServletContext().getRequestDispatcher("/home.jsp");
		view.forward(request, response);
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
