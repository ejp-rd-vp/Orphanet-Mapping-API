
/*
 * Servlet : description
 *
 * Description   : this servlet generates an html web page that contains a set of information about the mapping service.
 * 
 * Version       : 1.0
 *
 * Date          : 23/11/2021
 * 
 * Copyright     : Boulares Ouchenne
 */

package webservices;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import metier.Infos;
public class description extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServletConfig cfg;
    private Gson gson = new Gson();

	  public void init(ServletConfig config) throws ServletException {
	     cfg = config;
	  }

	  public ServletConfig getServletConfig() {
	    return cfg;
	  }

	  

	  public void destroy() {
	  }

	  public void service (ServletRequest req,  ServletResponse res ) 
	  throws ServletException, IOException  {
		  PrintWriter out = res.getWriter();
		  res.setContentType("application/json");
		  res.setCharacterEncoding("UTF-8");
	      String infos = this.gson.toJson(new Infos());
	      //out.println( "<TITLE>Orphanet API Endpoint</TITLE>" );
		  out.print( infos);
		  out.flush();	 
		
		  
	  } 

}
