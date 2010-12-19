package util;

import ontology.EConcept;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;

/*********************************************************************************************
 * This class is used to parse the QuerySolution to create relative class.
 * @author william
 *
 ****************************************************************************************/
public class QuerySolutionParser {
	/*
	public getEPerformance(QuerySolution qs){
		
	}
	*/
	public static EResource getEResource(QuerySolution qs,Model model){
		EResource res = new EResource();
		String uri = qs.get("?resource").toString().trim();
		String id = model.getResource(uri).getLocalName();
		String name = qs.get("?r_name").toString().trim();
		String difficulty = qs.get("?r_difficulty").toString().trim();
		String fileLocation = qs.get("?r_fileLocation").toString().trim();
		res.setRid(id);
		res.setName(StringExchanger.getCommonString(name));
		res.setDifficulty(StringExchanger.getCommonString(difficulty));
		res.setFileLocation(StringExchanger.getCommonString(fileLocation));
		return res;
	}
	public static ELearner getELearner(QuerySolution qs, Model model){
		ELearner el = new ELearner();
		String uri = qs.get("?elearner").toString().trim();
		String id = model.getResource(uri).getLocalName();
		String name = qs.get("?el_name").toString().trim();
		String grade = qs.get("?el_grade").toString().trim();
		String email = qs.get("?el_email").toString().trim();
		String address = qs.get("?el_address").toString().trim();
		el.setName(id);
		el.setName(StringExchanger.getCommonString(name));
		el.setGrade(StringExchanger.getCommonString(grade));
		el.setEmail(StringExchanger.getCommonString(email));
		el.setAddress(StringExchanger.getCommonString(address));
		return el;
	}
	public static EConcept getEConcept(QuerySolution qs, Model model){
		EConcept con = new EConcept();
		String uri = qs.get("?concept").toString().trim();
		String id = model.getResource(uri).getLocalName();
		String name = qs.get("?c_name").toString().trim();
		con.setCid(id);
		con.setName(StringExchanger.getCommonString(name));
		return con;
	}
	public static EPortfolio getEPortfolio(QuerySolution qs,Model model){
		EPortfolio portfolio = new EPortfolio();
		String id = getIdByURI(qs,model,"?portfolio");
		float value = 0;
		ELearner elearner = getELearner(qs,model);
		EResource resource = getEResource(qs,model);
		portfolio.setId(id);
		portfolio.setElearner(elearner);
		portfolio.setEResource(resource);
		portfolio.setValue(value);
		return portfolio;
	}
	/****************************************************************************
	 * THE ID of the ontology is the same as its URI
	 * This method is used to exchange the standard uri to the id. 
	 * @param qs
	 * @param model
	 * @param column   eg:"?elearner"
	 * @return id
	 */
	public static String getIdByURI(QuerySolution qs,Model model,String column){
		String uri = qs.get(column).toString();
		String id = model.getResource(uri).getLocalName();
		return id;
	}
	
}
