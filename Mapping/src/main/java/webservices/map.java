/*
 * Servlet : map
 *
 * Description   : this servlet generates an html web page that contains a set of informations according to a mapping query.
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFParseException;

import com.google.gson.Gson;

import metier.Erreur;
import metier.Infos;
import metier.Mapping;
import metier.Message;


public class map extends HttpServlet {
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
		  
		  Set<String> inputs = new HashSet<String>();
		  inputs.add("orphanet");
		  inputs.add("icd");
		  inputs.add("mesh");
		  inputs.add("meddra");
          inputs.add("umls");
          inputs.add("omim");
          inputs.add(null);

      	//recuperate and normalize the set of parameters from

		  String From = req.getParameter("from");
		  if((From==null))
		  {
			  Erreur R= new Erreur();
			  Message M=new Message ("Parameter 'from' is mandatory.");
              M.setCode("required Parameters.");
			  R.Add(M);
              String infos;
	          infos= this.gson.toJson(R);
       	      out.print( infos);
	          out.flush();
              return;
		  }
		  From=From.toLowerCase();

		  if(!(inputs.contains(From)))
		  {
			  	  Erreur R= new Erreur();
		              R.Add(new Message ("The value of Parameter 'from' must be one of :{orphanet, omim, umls, mesh, meddra, icd}."));
		              String infos;
			          infos= this.gson.toJson(R);
		       	      out.print( infos);
			          out.flush();
		              return;  
		
		  }
		  
          Set<String> Codes = new HashSet<String>();
		  
		  String[] CodesA = req.getParameterValues("code");
		  if(CodesA==null)
		  {
			  Erreur R= new Erreur();
			  Message M=new Message ("Parameters 'code'  is mandatory.");
              M.setCode("required Parameter.");
			  R.Add(M);
              String infos;
	          infos= this.gson.toJson(R);
       	      out.print( infos);
	          out.flush();
              return;
		  }
		  else
		  {
			  for(int i=0;i<CodesA.length;i++)
			  Codes.add(CodesA[i].toUpperCase()); 
		  }
		  
		  
			
	      	//recuperate and normalize the set of parameters To

		  Set<String> To = new HashSet<String>();
		 		  
		 		  String[] ToA = req.getParameterValues("to");
		 		  if(ToA!=null)
		 		  {
		 			  for(int i=0;i<ToA.length;i++)
		 			  To.add(ToA[i].toLowerCase()); 
		 		  }
		 		
		 		  if(!inputs.containsAll(To))
		 		  {
		 			  	  Erreur R= new Erreur();
		 		              R.Add(new Message ("The value of Parameters 'to' must be one or more of :{orphanet, omim, umls, mesh, meddra, icd}."));
		 		              String infos;
		 			          infos= this.gson.toJson(R);
		 		       	      out.print( infos);
		 			          out.flush();
		 		              return;  
		 		
		 		  }
		  
			      	//recuperate and normalize the set of parameters Levels

		  
		  
		  Set<String> levels = new HashSet<String>();
		  levels.add("exactmatch");
		  levels.add("ntbt");
		  levels.add("btnt");
		  levels.add(null);
		  
		   
		  
		  Set<String> Level = new HashSet<String>();
 		  
 		  String[] LevelA = req.getParameterValues("level");
 		  if(LevelA!=null)
 		  {
 			  for(int i=0;i<LevelA.length;i++)
 			  Level.add(LevelA[i].toLowerCase()); 
 		  }
 		
 		  if(!levels.containsAll(Level))
 		  {
 			  	      Erreur R= new Erreur();
 			  	      R.Add(new Message ("The value of Parameters 'Level' must be one or more of :{exactMatch, ntbt, btnt}."));
 			  	      String infos;
 			          infos= this.gson.toJson(R);
 		       	      out.print( infos);
 			          out.flush();
 		              return;  
 		
 		  }
 
		  
		  
		  
		 
 		 Mapping R = new Mapping();
		  if (From.equals("orphanet"))
		 {
			//call the mapping method from an Orphanet code
					try {
						R.AddMapFromOrphaCodes(Codes);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					String infos;
					
					//get codes according to the specified mapping levels
					R.applyFilterTo(To);
					infos= this.gson.toJson(R.applyFilterLevel(Level));//****
					
					out.print( infos);
			        out.flush();
		 }
		 else
		 {	
			 //add the specific prefix
			 String prefix="";
			 if(From.equals("icd"))
			 prefix="http://identifiers.org/icd/";
			 else if(From.equals("meddra"))
			 prefix="http://purl.bioontology.org/ontology/MEDDRA/";
			 else if(From.equals("umls"))
			 prefix="https://www.ncbi.nlm.nih.gov/medgen/";
			 else if(From.equals("mesh"))
			 prefix="http://id.nlm.nih.gov/mesh/";
			 else if(From.equals("omim"))
			 prefix="https://www.omim.org/entry/";
			  
			 Set<String> cdsprfix = new HashSet<String>();
			 for(String s:Codes)
			  {
				 cdsprfix.add(prefix+s);
			  }
			 Codes=new HashSet<String>();
			 Codes.addAll(cdsprfix);
			 
			 try {
				
				 //call the mapping method from the specific code to the other codes
				 R.AddMapToOrphaCodes(Codes);
				
			if(!((To.size()==1)&&(To.contains("orphanet"))))
				{
					
					Codes=new HashSet<String>();
					
					for(String s:R.getbTNT()) 
					Codes.add(s.substring(s.lastIndexOf("_")+1));
					 
					for(String s:R.getnTBT()) 
						Codes.add(s.substring(s.lastIndexOf("_")+1));
						
					for(String s:R.getExactMatch()) 
						Codes.add(s.substring(s.lastIndexOf("_")+1));
						
					 
					 R.AddMapFromOrphaCodes(Codes);
					 R.Accurate();
				}
				
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 	String infos;
			 	//get codes according to the desired codes
			 	R.applyFilterTo(To);///****
				infos= this.gson.toJson(R.applyFilterLevel(Level));//***
		      	
		      	out.print( infos);
		        out.flush();
				
		 }
          
     
          
          
          
          
          
         
         System.out.println("Codes= "+Codes);
		  System.out.println("From= "+From);
		  System.out.println("To= "+ To);
		  System.out.println("Levels= "+Level);
		  
        


		  
		              
			
		  
	  } 
       
	  /**
		 *  This method returns the numeric value of a text if it is valid otherwise it returns an error message
		 */
	
	  public static Object tryParse(String text) {
		  try {
		    return Integer.parseInt(text);
		  } catch (NumberFormatException e) {
		    return e.toString();
		  }
		}	
}
