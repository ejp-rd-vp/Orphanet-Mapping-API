/*
 * Class : Infos
 *
 * Description   : this class generates a set of information about the Orphanet Endpoint.
 * 
 * Version       : 1.0
 *
 * Date          : 23/11/2021
 * 
 * Copyright     : Boulares Ouchenne
 */
package metier;

import java.util.HashSet;
import java.util.Set;

public class Infos {
String name, description,apiVersion,sampleRequests;

public Infos() {
	this.name="Orphanet Mapping Web Services";
	this.description="The Orphanet Mapping Web Services.";
	this.apiVersion="v0.1";
	this.sampleRequests="/map?form={origin}&code={code}&to={destination} will return results based on the specified code.";
}


	
}
