package util;

public class Constant {
	/*******************************************************************
	 * Jena Reasoning Config
	 *********************************************************************/
	public static String NS ="http://www.elearning.com/";
	public static String OWLFile ="D:\\EclipseWorkspace\\elearning\\protege\\elearning.owl";
	public static String RulesFile="D: \\EclipseWorkspace\\elearning\\src\\jena\\elearning.rules";
	
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
	public static String DBURL = "jdbc:mysql://localhost:3306/elearning";        
    public static String DBUSER = "root";
    public static String DBPASSWORD = "ms";
    
    
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
	public static String [] titles={"professor"};

}
