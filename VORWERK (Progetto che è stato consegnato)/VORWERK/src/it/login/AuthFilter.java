package it.login;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/AuthFilter")
public class AuthFilter implements Filter {
    public AuthFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponse hresponse = (HttpServletResponse) response;
		
		String loginURI = hrequest.getContextPath() + "/home.jsp";

		System.out.println("Requested uri: " + hrequest.getRequestURI());
		boolean loginRequest = hrequest.getRequestURI().startsWith(loginURI); //controlli se vuoi accedere ad home.jsp

		if(loginRequest) {
			System.out.println("Check role in the session");
			//check the token from session
			HttpSession session = hrequest.getSession(false);
			boolean loggedIn = (session != null && session.getAttribute("admin") != null && (Boolean)session.getAttribute("admin") == true);

			/*if (session!=null)
				System.out.println("tutto ok sessione");
			else System.out.println("no ok sessione");
			
			if (session.getAttribute("adminFilterRoles") != null)
				System.out.println("tutto ok permessi");
			else System.out.println("no ok permessi");*/
			
			
			if(!loggedIn) {
				System.out.println("Access unauthorized " + session.getAttribute("user") +"\nRedirect to login form\n");
				hresponse.sendRedirect(hresponse.encodeRedirectURL(hrequest.getContextPath() + "/login.jsp"));
			} else {
				// admin resource
				System.out.println("Access authorized " + session.getAttribute("user") +"\nSei entrato come amministratore\n");
				chain.doFilter(request, response);
			}
		} else {
			hresponse.sendRedirect(hresponse.encodeRedirectURL(hrequest.getContextPath() + "/login.jsp"));
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
