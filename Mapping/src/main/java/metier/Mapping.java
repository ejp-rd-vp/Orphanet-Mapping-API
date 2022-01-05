/*
 * Class : Location
 *
 * Description   : this class generate a set of information about a Mapping.
 * 
 * Version       : 1.0
 *
 * Date          : 23/11/2021
 * 
 * Copyright     : Boulares Ouchenne
 */
package metier;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.memory.MemoryStore;

import com.google.gson.JsonElement;

public class Mapping {
	public Set<String> exactMatch;
	public Set<String> nTBT;
	public Set<String> bTNT;

	
public Mapping() {
		
		exactMatch =new HashSet<String>();
		nTBT=new HashSet<String>();
		bTNT=new HashSet<String>();
	}

/**
 *  This method returns the result of mapping form an Orpha Code 
 *   */
public Mapping(String Code) throws RepositoryException, RDFParseException, IOException
{
	    exactMatch =new HashSet<String>();
	    nTBT=new HashSet<String>();
	    bTNT=new HashSet<String>();
	    Repository BlazeGraphServer = new HTTPRepository("http://155.133.131.171:8080/blazegraph/namespace/mapper/sparql");  
	    BlazeGraphServer.initialize();
	   
	    
		//On ouvre une connexion au repository pour envoyer toute les requêtes
		 RepositoryConnection connection = BlazeGraphServer.getConnection();
		 //Création de la Query SELECT
		 //On initialise la query.	 
		 String requeteSPARQL = "select  * where {<http://www.orpha.net/ORDO/Orphanet_"+Code+"> ?p ?o}";

				 
		 //System.out.println(requeteSPARQL);	
		 // Création de la Requête 	         
		 org.openrdf.query.TupleQuery selectQuery = null;
		try {
			selectQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL,requeteSPARQL);
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 // on l'exécute			
		 TupleQueryResult selectQueryResult = null;
		try {
			selectQueryResult = selectQuery.evaluate();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		 // Récupération Affichage des résultats
		 
		 try {
			while(selectQueryResult.hasNext()) 
				{  
				   // chaque ligne du résultat est un BindingSet  
				
				BindingSet aBinding = selectQueryResult.next();				    
				String p=aBinding.getBinding("p").getValue().stringValue();
				String o=aBinding.getBinding("o").getValue().stringValue();
				if (p.contains("exactMatch"))this.exactMatch.add(o);
				if (p.contains("btnt"))this.bTNT.add(o);      				
				if (p.contains("ntbt"))this.nTBT.add(o);
				
				

				}
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
/**
 *  This method returns the result of mapping form a code to an Orpha code 
 *   */
public Mapping(String Code,int y) throws RepositoryException, RDFParseException, IOException
{
	    exactMatch =new HashSet<String>();
	    nTBT=new HashSet<String>();
	    bTNT=new HashSet<String>();
	    Repository BlazeGraphServer = new HTTPRepository("http://155.133.131.171:8080/blazegraph/namespace/mapper/sparql");  
	    BlazeGraphServer.initialize();
	   //On ouvre une connexion au repository pour envoyer toute les requêtes
		 RepositoryConnection connection = BlazeGraphServer.getConnection();
		 //Création de la Query SELECT
		 //On initialise la query.	 
		 String requeteSPARQL = "select  * where {?s ?p \""+Code+"\"^^xsd:string }";

				 
		 //System.out.println(requeteSPARQL);	
		 // Création de la Requête 	         
		 org.openrdf.query.TupleQuery selectQuery = null;
		try {
			selectQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL,requeteSPARQL);
		} catch (MalformedQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 // on l'exécute			
		 TupleQueryResult selectQueryResult = null;
		try {
			selectQueryResult = selectQuery.evaluate();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		 // Récupération Affichage des résultats
		 
		 try {
			while(selectQueryResult.hasNext()) 
				{  
				   // chaque ligne du résultat est un BindingSet  
				
				BindingSet aBinding = selectQueryResult.next();				    
				String p=aBinding.getBinding("p").getValue().stringValue();
				String o=aBinding.getBinding("s").getValue().stringValue();
				if (p.contains("exactMatch"))this.exactMatch.add(o);
				if (p.contains("btnt"))this.bTNT.add(o);      				
				if (p.contains("ntbt"))this.nTBT.add(o);
				
				

				}
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}



public Set<String> getExactMatch() {
	return exactMatch;
}
public void setExactMatch(Set<String> exactMatch) {
	this.exactMatch = exactMatch;
}
public Set<String> getnTBT() {
	return nTBT;
}
public void setnTBT(Set<String> nTBT) {
	this.nTBT = nTBT;
}
public Set<String> getbTNT() {
	return bTNT;
}
public void setbTNT(Set<String> bTNT) {
	this.bTNT = bTNT;
}

public int isEmpty() {
	
	if((this.getExactMatch().isEmpty())&&(this.getbTNT().isEmpty())&&(this.getnTBT().isEmpty())) return 0;
return 1;
}

/**
 *  This method returns the codes according to the specified levels 
 *   */
public Object applyFilterLevel(Set<String> Level) {
	if(Level.isEmpty()) return this;
	Set<String>Result = new HashSet<String>(); ;	
	
	if (Level.contains("exactmatch"))Result.addAll(exactMatch);
	if (Level.contains("ntbt"))Result.addAll(nTBT);
	if (Level.contains("btnt"))Result.addAll(bTNT);
	return Result;
	
}


/**
 *  This method returns the codes according to the desired codification 
 *   */
public void applyFilterTo(Set<String> To) {
if(To.isEmpty())return;
Set<String> Temp;	
Set<String>codeFilter = new HashSet<String>(); ;	

 if (To.contains("omim"))
 codeFilter.add("omim.org");
 
if (To.contains("icd"))
codeFilter.add("org/icd");


if (To.contains("meddra"))
codeFilter.add("/MEDDRA");


if (To.contains("umls"))
codeFilter.add("medgen/");


if (To.contains("mesh"))
codeFilter.add("mesh/");


if (To.contains("orphanet"))
codeFilter.add("Orphanet_");
		 

System.out.println(codeFilter);	 
	 Temp=new HashSet<String>();
 for (String c:exactMatch)
	 for (String p:codeFilter)
	 if (c.contains(p))Temp.add(c);	
 this.setExactMatch(Temp);
 
 Temp=new HashSet<String>();
 for (String c:nTBT)
	 for (String p:codeFilter)
		 if (c.contains(p))Temp.add(c);	
	 this.setnTBT(Temp);
	 
	 Temp=new HashSet<String>();
	 for (String c:bTNT)
		 for (String p:codeFilter)
			 if (c.contains(p))Temp.add(c);
	 this.setbTNT(Temp);
	 
 
	
}


public void AddMapFromOrphaCodes(Set<String> Codes) throws RepositoryException {
	   if(Codes.isEmpty())return;

	Repository BlazeGraphServer = new HTTPRepository("http://155.133.131.171:8080/blazegraph/namespace/mapper/sparql");  
    BlazeGraphServer.initialize();
    
	//On ouvre une connexion au repository pour envoyer toute les requêtes
	 RepositoryConnection connection = BlazeGraphServer.getConnection();
	 //Création de la Query SELECT
	 //On initialise la query.	 
	 String requeteSPARQL = "select  * where {";
	 for (String s:Codes)
		 requeteSPARQL=requeteSPARQL+"{<http://www.orpha.net/ORDO/Orphanet_"+s+"> ?p ?o}UNION";
	  requeteSPARQL=requeteSPARQL+"{}}";
	  requeteSPARQL=requeteSPARQL.replace("UNION{}", "");		 
	 System.out.println(requeteSPARQL);
	 // Création de la Requête 	         
	 org.openrdf.query.TupleQuery selectQuery = null;
	try {
		selectQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL,requeteSPARQL);
	} catch (MalformedQueryException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	 // on l'exécute			
	 TupleQueryResult selectQueryResult = null;
	try {
		selectQueryResult = selectQuery.evaluate();
	} catch (QueryEvaluationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	 
	 // Récupération Affichage des résultats
	 
	 try {
		while(selectQueryResult.hasNext()) 
			{  
			   // chaque ligne du résultat est un BindingSet  
			
			BindingSet aBinding = selectQueryResult.next();				    
			String p=aBinding.getBinding("p").getValue().stringValue();
			String o=aBinding.getBinding("o").getValue().stringValue();
			if (p.contains("exactMatch"))this.exactMatch.add(o);
			if (p.contains("btnt"))this.bTNT.add(o);      				
			if (p.contains("ntbt"))this.nTBT.add(o);
			
			

			}
	} catch (QueryEvaluationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
}


public void AddMapToOrphaCodes(Set<String> Codes) throws RepositoryException {
	Repository BlazeGraphServer = new HTTPRepository("http://155.133.131.171:8080/blazegraph/namespace/mapper/sparql");  
    BlazeGraphServer.initialize();
   //On ouvre une connexion au repository pour envoyer toute les requêtes
	 RepositoryConnection connection = BlazeGraphServer.getConnection();
	 //Création de la Query SELECT
	 //On initialise la query.	 
	 
	 String requeteSPARQL = "select  * where {";
	 for (String s:Codes)
		 requeteSPARQL=requeteSPARQL+"{?s ?p \""+s+"\"^^xsd:string }UNION";
	  requeteSPARQL=requeteSPARQL+"{}}";
	  requeteSPARQL=requeteSPARQL.replace("UNION{}", "");		 
	
			 
	 System.out.println(requeteSPARQL);	
	 // Création de la Requête 	         
	 org.openrdf.query.TupleQuery selectQuery = null;
	try {
		selectQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL,requeteSPARQL);
	} catch (MalformedQueryException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	 // on l'exécute			
	 TupleQueryResult selectQueryResult = null;
	try {
		selectQueryResult = selectQuery.evaluate();
	} catch (QueryEvaluationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	 
	 // Récupération Affichage des résultats
	 
	 try {
		while(selectQueryResult.hasNext()) 
			{  
			   // chaque ligne du résultat est un BindingSet  
			
			BindingSet aBinding = selectQueryResult.next();				    
			String p=aBinding.getBinding("p").getValue().stringValue();
			String o=aBinding.getBinding("s").getValue().stringValue();
			if (p.contains("exactMatch"))this.exactMatch.add(o);
			if (p.contains("btnt"))this.bTNT.add(o);      				
			if (p.contains("ntbt"))this.nTBT.add(o);
			
			

			}
	} catch (QueryEvaluationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

//for test purpose
public void Accurate() {
	this.nTBT.removeAll(this.exactMatch);
	//this.nTBT.removeAll(this.bTNT);
	this.bTNT.removeAll(this.exactMatch);
	this.bTNT.removeAll(this.nTBT);
	
}





}
