package util;

import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/*********************************************************************************************
 * This class is used to parse the QuerySolution to create relative class.
 * @author william
 *
 ****************************************************************************************/
public class QuerySolutionParser {

    public static ISCB_Resource getEResource(String uri, InfModel model) {
        ISCB_Resource res = new ISCB_Resource();
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
        Individual indi = ontModel.getIndividual(uri);
        String id = indi.getPropertyValue(model.getProperty(Constant.NS + "id")).asLiteral().getString();
        String name = indi.getPropertyValue(model.getProperty(Constant.NS + "name")).asLiteral().getString();
        String difficulty = indi.getPropertyValue(model.getProperty(Constant.NS + "difficulty")).asLiteral().getString();
        String fileLocation = indi.getPropertyValue(model.getProperty(Constant.NS + "fileLocation")).asLiteral().getString();

        res.setRid(id);
        res.setName(name);
        res.setDifficulty(difficulty);
        res.setFileLocation(fileLocation);
        //System.out.println(res);
        return res;
    }

    public static ELearner getELearner(String uri, Model model) {
        ELearner el = new ELearner();
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
        Individual indi = ontModel.getIndividual(uri);

        String id = indi.getLocalName();
        String name = indi.getPropertyValue(model.getProperty(Constant.NS + "name")).asLiteral().getString();
        String grade = indi.getPropertyValue(model.getProperty(Constant.NS + "grade")).asLiteral().getString();
        String email = indi.getPropertyValue(model.getProperty(Constant.NS + "email")).asLiteral().getString();
        String address = indi.getPropertyValue(model.getProperty(Constant.NS + "address")).asLiteral().getString();

        el.setId(id);
        el.setName(name);
        el.setGrade(grade);
        el.setEmail(email);
        el.setAddress(address);
//		System.out.println(el);
        return el;
    }

    public static EConcept getEConcept(String uri, InfModel model) {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
        Individual indi = ontModel.getIndividual(uri);
        String id = indi.getPropertyValue(model.getProperty(Constant.NS + "id")).asLiteral().getString();
        String name = indi.getPropertyValue(model.getProperty(Constant.NS + "name")).asLiteral().getString();
        EConcept con = new EConcept();
        con.setCid(id);
        con.setName(name);
        return con;
    }

    public static EPortfolio getEPortfolio(QuerySolution qs, InfModel model) {
        String portURI = getURI(qs, "?portfolio");
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
        Individual indi = ontModel.getIndividual(portURI);
        String id = indi.getLocalName();
        String elURI = getURI(qs, "?elearner");
        String resURI = getURI(qs, "?resource");
        ELearner elearner = getELearner(elURI, model);
        ISCB_Resource resource = getEResource(resURI, model);
        float value = indi.getPropertyValue(model.getProperty("value")).asLiteral().getFloat();
        EPortfolio portfolio = new EPortfolio();
        portfolio.setId(id);
        portfolio.setELearner(elearner);
        portfolio.setEResource(resource);
        portfolio.setValue(value);
//		System.out.println(portfolio);
        return portfolio;
    }

    public static EPerformance getEPerformance(QuerySolution qs, InfModel model) {
        String conURI = QuerySolutionParser.getURI(qs, "?concept");
        String elURI = QuerySolutionParser.getURI(qs, "?elearner");
        String perURI = QuerySolutionParser.getURI(qs, "?performance");
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
        Individual indi = ontModel.getIndividual(perURI);

        EConcept con = QuerySolutionParser.getEConcept(conURI, model);
        ELearner el = QuerySolutionParser.getELearner(elURI, model);
        String pid = indi.getLocalName();
        String valueString = indi.getPropertyValue(model.getProperty(Constant.NS + "value")).toString();
        float value = Float.valueOf(valueString);

        EPerformance perf = new EPerformance();
        perf.setId(pid);
        perf.setELearner(el);
        perf.setEConcept(con);
        perf.setValue(value);
        return perf;
    }

    /****************************************************************************
     * THE ID of the ontology is the same as its URI
     * This method is used to exchange the standard uri to the id.
     * @param qs
     * @param model
     * @param column   eg:"?elearner"
     * @return id
     ***************************************************************************/
    public static String getIdByURI(QuerySolution qs, Model model, String column) {
        String uri = qs.get(column).toString();
        String id = model.getResource(uri).getLocalName();
        return id;
    }

    public static String getURI(QuerySolution qs, String column) {
        String uri = qs.get(column).toString().trim();
        return uri;

    }
}
