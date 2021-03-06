package util;

public class Constant {
	/*******************************************************************
	 * Jena Reasoning Config
	 *********************************************************************/
	public static String NS ="http://www.owl-ontologies.com/e-learning.owl#";
	public static String NSRDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static String OWLFile ="protege\\elearning.owl";
	//public static String OWLFile ="src\\db\\userOwlFile.owl";
	public static String RulesFile="src\\jena\\elearning2.rules";
	public static String userOwlFile = "src\\db\\userOwlFile.owl";
	/*********************************************************************
	 * SPARQL Prefix
	 ********************************************************************/
	public static String RDFPRE = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
	public static String BASEPRE = "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> ";
	public static String RDFSPRE = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "; 
	public static String XSDPRE = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "; 
	public static String FNPRE = "PREFIX fn: <http://www.w3.org/2005/xpath-functions#> "; 

	/********************************************************************
	 * Database Connection Config
	 ********************************************************************/
	//public static String DBURL = "jdbc:mysql://localhost:3306/elearning";
	public static String DBURL = "jdbc:mysql://192.168.8.86:3306/elearning"; //destop ip
	
	//public static String DBURL = "jdbc:mysql://192.168.9.161:3306/elearning"; 	//laptop ip
	 
	public static String DBUSER = "ms";
    public static String DBPASSWORD = "ms";
    public static String SERVERURL = "http://192.168.8.86:8080/myelearner";
    public static String SERVERTESTURL = "http://192.168.8.86:8080/myelearner/webapp/mylife.jpg";
    public static String OWLONSERVER = "http://localhost:8080/myelearner/owls/";
	/**********************************************************************
	 * To Be Used, but now useless....
	 **********************************************************************/
	public static String [] post={"post","associate professor","instructor"};
	public static String [] ERESOURCE_TYPES={
		"E_Resource_AUDIO","E_Resource_CODE","E_Resource_CAJ","E_Resource_DOC","E_Resource_PDF","E_Resource_TXT",
		"E_Resource_FLASH","E_Resource_IMAGE","E_Resource_PPT","E_Resource_VIDEO"};
	public static String [] EDUCATION_TYPES={
		"book","paper","lecture"
	};
	public static String [] RECOMMANDRULES = {
		
	};
	public static String [] titles={"professor"};

}
