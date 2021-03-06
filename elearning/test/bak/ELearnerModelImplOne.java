package bak;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import jena.impl.ELearnerModel;
import jena.interfaces.ELearnerModelOperationInterface;
import jena.interfaces.ELearnerModelQueryInterface;
import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;
import util.Constant;
import util.QuerySolutionParser;
import util.StringExchanger;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/***********************************************
 * Operation on the model with the SPARQL language.
 * @author william
 *
 */
public class ELearnerModelImplOne extends ELearnerModel  {

    public ELearnerModelImplOne(File file) {
        super(file);
    }

    public ELearnerModelImplOne() {
        super();
    }

    public ELearnerModelImplOne(File file, String lang) {
        super(file, lang);
    }

    /*******************************************************************************************************
     * Add new Data Operations
     *******************************************************************************************************/
    @Override
    public boolean addELearner(ELearner elearner) {
        Resource el = ontModel.createResource(Constant.NS + elearner.getId(), ontModel.getResource(Constant.NS + "E_Learner"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "id"), elearner.getId());
        ontModel.add(el, ontModel.getProperty(Constant.NS + "name"), elearner.getName(), new XSDDatatype("string"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "grade"), elearner.getGrade(), new XSDDatatype("string"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "address"), elearner.getAddress(), new XSDDatatype("string"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "email"), elearner.getEmail(), new XSDDatatype("string"));
        return true;
    }

    @Override
    public boolean addEResource(EResource resource) {
        if (containEResource(resource.getRid())) {
            return false;
        }
        Resource re = ontModel.createResource(Constant.NS + resource.getRid(), ontModel.getResource(Constant.NS + "E_Resource"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "id"), resource.getRid());
        ontModel.add(re, ontModel.getProperty(Constant.NS + "name"), resource.getName(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "fileLocation"), resource.getFileLocation(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "difficulty"), resource.getDifficulty(), new XSDDatatype("string"));
        return true;
    }

    @Override
    public boolean addEPortfolio(EPortfolio portfolio) {
        ELearner el = portfolio.getElearner();
        EResource res = portfolio.getEResource();
        if (!containELearner(el.getId())) {
            return false;
        }
        if (!containEResource(res.getRid())) {
            return false;
        }
        Resource port = ontModel.createResource(Constant.NS + portfolio.getId(), ontModel.getResource(Constant.NS + "E_Portfolio"));
        ontModel.add(port, ontModel.getProperty(Constant.NS + "inverse_of_has_portfolio"), ontModel.getResource(Constant.NS + el.getId()));
        ontModel.add(port, ontModel.getProperty(Constant.NS + "inverse_of_is_resource_of_P"), ontModel.getResource(Constant.NS + res.getRid()));
        ontModel.addLiteral(port, ontModel.getProperty(Constant.NS + "value"), portfolio.getValue());
        ontModel.add(port, ontModel.getProperty(Constant.NS + "datetime"), StringExchanger.parseDateToString(portfolio.getDatetime()), new XSDDatatype("dateTime"));
        return true;
    }

    @Override
    public boolean addEConcept(EConcept concept) {
        if (containEConcept(concept.getCid())) {
            return false;
        }
        Resource con = ontModel.createResource(Constant.NS + concept.getCid(), ontModel.getResource(Constant.NS + "E_Concept"));
        ontModel.add(con, ontModel.getProperty(Constant.NS + "id"), concept.getCid());
        ontModel.add(con, ontModel.getProperty(Constant.NS + "name"), concept.getName(), new XSDDatatype("string"));
        return true;
    }

    public boolean addEInterest(EInterest interest) {
        ELearner el = interest.getELearner();
        EConcept con = interest.getEConcept();
        if (!containELearner(el.getId())) {
            return false;
        }
        if (!containEConcept(con.getCid())) {
            return false;
        }
        Resource in = ontModel.createResource(Constant.NS + interest.getId(), ontModel.getResource(Constant.NS + "E_Interest"));
        in.addProperty(ontModel.getProperty(Constant.NS + "inverse_of_has_interest"), ontModel.getResource(Constant.NS + el.getId()));
        in.addProperty(ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_I"), ontModel.getResource(Constant.NS + con.getCid()));
        in.addProperty(ontModel.getProperty(Constant.NS + "value"), String.valueOf(interest.getValue()), new XSDDatatype("string"));
        return true;
    }

    @Override
    public boolean addEPerfomance(EPerformance performance) {
        ELearner el = performance.getElearner();
        EConcept con = performance.getConcept();
        if (!containELearner(el.getId())) {
            return false;
        }
        if (!containEConcept(con.getCid())) {
            return false;
        }
        Resource perf = ontModel.createResource(Constant.NS + performance.getId(), ontModel.getResource(Constant.NS + "E_Performance"));
        ontModel.add(perf, ontModel.getProperty(Constant.NS + "inverse_of_has_performance"), ontModel.getResource(Constant.NS + el.getId()));
        ontModel.add(perf, ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_P"), ontModel.getResource(Constant.NS + con.getCid()));
        ontModel.addLiteral(perf, ontModel.getProperty(Constant.NS + "value"), performance.getValue());
        ontModel.add(perf, ontModel.getProperty(Constant.NS + "datetime"), StringExchanger.parseDateToString(performance.getDatetime()), new XSDDatatype("dateTime"));
        return true;
    }

    /*******************************************************************************
     * Add Properties for the model
     ******************************************************************************/
    @Override
    public boolean addPropertyIsSonOf(EConcept fatherConcept, EConcept sonConcept) {
        Resource son = ontModel.getResource(Constant.NS + sonConcept.getCid());
        Resource father = ontModel.getResource(Constant.NS + fatherConcept.getCid());
        ontModel.add(son, ontModel.getProperty(Constant.NS + "is_son_of"), father);
        return true;
    }

    @Override
    public boolean addPropertyIsResourceOfC(EResource resource, EConcept concept) {
        Individual res = ontModel.getIndividual(Constant.NS + resource.getRid());
        Individual con = ontModel.getIndividual(Constant.NS + concept.getCid());
        ontModel.add(res, ontModel.getProperty(Constant.NS + "is_resource_of_C"), con);
        return true;
    }

    @Override
    public boolean containEConcept(String cid) {
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?concept "
                + "WHERE {"
                + "      ?concept rdf:type base:E_Concept . "
                + "      ?concept base:id \"" + cid + "\" . "
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            return true;
        }
        qe.close();
        return false;
    }

    @Override
    public boolean containELearner(String eid) {
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?elearner "
                + "WHERE {"
                + "      ?elearner rdf:type base:E_Learner . "
                + "      ?elearner base:id \"" + eid + "\". "
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            return true;
        }
        qe.close();
        return false;
    }

    @Override
    public boolean containEResource(String rid) {
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?resource "
                + "WHERE {"
                + "      ?resource rdf:type base:E_Resource . "
                + "      ?resource base:id \"" + rid + "\" . "
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();

        if (results.hasNext()) {
            return true;
        }
        qe.close();
        return false;
    }

    public ArrayList<EConcept> getAllEConcepts() {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?concept "
                + "WHERE {"
                + "      ?concept rdf:type base:E_Concept . "
                + "      }";

        Query query = QueryFactory.create(queryString);
        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();
        // ResultSetFormatter.out(System.out, results, query);
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String conURI = QuerySolutionParser.getURI(qs, "?concept");
            EConcept con = QuerySolutionParser.getEConcept(conURI, ontModel);
            concepts.add(con);
        }
        qe.close();
        return concepts;
    }

    public ArrayList<EConcept> getSonConcepts(EConcept concept) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?concept "
                + "WHERE {"
                + "      ?concept rdf:type base:E_Concept . "
                + "      ?concept base:is_son_of ?fatherConcept . "
                + "      ?fatherConcept rdf:type base:E_Concept . "
                + "      ?fatherConcept base:id " + StringExchanger.getSparqlString(concept.getCid()) + " . "
                + "      }";

        Query query = QueryFactory.create(queryString);
        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String conURI = QuerySolutionParser.getURI(qs, "?concept");
            EConcept con = QuerySolutionParser.getEConcept(conURI, ontModel);
            concepts.add(con);
        }
        qe.close();
        return concepts;
    }

    public ArrayList<EConcept> getSonConceptsTwo(EConcept econcept) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        Resource concept = ontModel.getResource(Constant.NS + econcept.getCid());
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_son_of"), concept);

        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Statement s = iter.nextStatement();
            Resource r = s.getSubject();
            String nameString = r.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).asTriple().getObject().toString();
            String name = StringExchanger.getCommonString(nameString);
            concepts.add(new EConcept(s.getSubject().getLocalName(), name));
        }
        return concepts;
    }

    public ArrayList<EConcept> getInterestConcepts(ELearner elearner) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?concept "
                + "WHERE {"
                + "      ?concept rdf:type base:E_Concept . "
                + "      ?elearner base:id " + StringExchanger.getSparqlString(elearner.getId()) + " . "
                + "      ?concept base:is_concept_of_I ?interest . "
                + "      ?elearner base:has_interest ?interest . "
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String conURI = QuerySolutionParser.getURI(qs, "?concept");
            EConcept con = QuerySolutionParser.getEConcept(conURI, ontModel);
            concepts.add(con);
        }
        qe.close();
        return concepts;
    }

    //get a basic Concept by the concept id
    public EConcept getEConcept(String cid) {
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                + "SELECT ?concept  "
                + "WHERE {"
                + "      ?concept rdf:type base:E_Concept . "
                + "      ?concept base:id \"" + cid + "\" . "
                + "      }";
        Query query = QueryFactory.create(queryString);
        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();
        // Output query results
        EConcept con = null;
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String conURI = QuerySolutionParser.getURI(qs, "?concept");
            con = QuerySolutionParser.getEConcept(conURI, ontModel);
        }
        qe.close();
        return con;
    }
    //get a basic ELearner by the given elearner id

    public ELearner getELearner(String eid) {
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                + "SELECT ?elearner "
                + "WHERE {"
                + "      ?elearner rdf:type base:E_Learner . "
                + "      ?elearner base:id " + StringExchanger.getSparqlString(eid) + " . "
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();

        // Output query results
        ELearner el = null;
        if (results.hasNext()) {
            QuerySolution qs = results.next();
            String elURI = QuerySolutionParser.getURI(qs, "?elearner");
            el = QuerySolutionParser.getELearner(elURI, ontModel);
        }
        qe.close();
        return el;
    }

    public EResource getEResource(String rid) {
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                + "SELECT ?resource  "
                + "WHERE {"
                + "      ?resource rdf:type base:E_Resource . "
                + "      ?resource base:id " + StringExchanger.getSparqlString(rid) + " . "
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();
        // Output query results
        EResource res = null;
        if (results.hasNext()) {
            QuerySolution qs = results.next();
            String uri = QuerySolutionParser.getURI(qs, "?resource");
            res = QuerySolutionParser.getEResource(uri, ontModel);
        }
        qe.close();
        return res;
    }

    public ArrayList<EPerformance> getEPerformances(ELearner elearner) {
        ArrayList<EPerformance> ps = new ArrayList<EPerformance>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?concept ?performance "
                + "WHERE {"
                + "      ?performance rdf:type base:E_Performance . "
                + "      ?performance base:inverse_of_is_concept_of_P ?concept . "
                + "      ?performance base:inverse_of_has_performance ?elearner . "
                + "      ?elearner base:id " + StringExchanger.getSparqlString(elearner.getId()) + " . "
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String conURI = QuerySolutionParser.getURI(qs, "?concept");
            String perURI = QuerySolutionParser.getURI(qs, "?performance");
            Individual indi = ontModel.getIndividual(perURI);

            String pid = ontModel.getResource(perURI).getLocalName();
            float value = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "value")).asLiteral().getFloat();
            String dateString = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "datetime")).asLiteral().getString();

            EPerformance per = new EPerformance();
            per.setId(pid);
            per.setElearner(elearner);
            per.setConcept(QuerySolutionParser.getEConcept(conURI, ontModel));
            per.setValue(value);
            per.setDatetime(StringExchanger.parseStringToDate(dateString));
            //	System.out.println(per);
            ps.add(per);
        }
        qe.close();
        return ps;
    }

    public EPortfolio getEPortfolio(ELearner elearner, EResource resource) {
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?portfolio "
                + "WHERE {"
                + "      ?portfolio rdf:type base:E_Portfolio . "
                + "      ?portfolio base:inverse_of_is_resource_of_P ?resource . "
                + "      ?portfolio base:inverse_of_has_portfolio ?elearner . "
                + "      ?resource base:id " + StringExchanger.getSparqlString(resource.getRid()) + " . "
                + "      ?elearner base:id " + StringExchanger.getSparqlString(elearner.getId()) + " . "
                + "      }";
        Query query = QueryFactory.create(queryString);
        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();

        EPortfolio portfolio = null;
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String portURI = QuerySolutionParser.getURI(qs, "?portfolio");
            Individual indi = ontModel.getIndividual(portURI);
            String id = indi.getLocalName();
            float value = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "value")).asLiteral().getFloat();
            String dateString = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "datetime")).asLiteral().getString();
            Date datetime = StringExchanger.parseStringToDate(dateString);

            portfolio = new EPortfolio();
            portfolio.setId(id);
            portfolio.setValue(value);
            portfolio.setEResource(resource);
            portfolio.setElearner(elearner);
            portfolio.setDatetime(datetime);
            //System.out.println(portfolio);
        }
        qe.close();
        return portfolio;
    }

    public ArrayList<EPortfolio> getEPortfolios(ELearner elearner) {
        long all = System.currentTimeMillis();
        long qt = all;
        ArrayList<EPortfolio> portfolios = new ArrayList<EPortfolio>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?resource ?portfolio "
                + "WHERE {"
                + "      ?resource rdf:type base:E_Resource . "
                + "      ?elearner base:id " + StringExchanger.getSparqlString(elearner.getId()) + " . "
                + "      ?portfolio base:inverse_of_is_resource_of_P ?resource . "
                + "      ?elearner base:has_portfolio ?portfolio . "
                + "      }";
        Query query = QueryFactory.create(queryString);
        InfModel infModel = getInfModel();
        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, infModel);
        ResultSet results = qe.execSelect();
        System.out.println(System.currentTimeMillis() - qt + "qt");
        long wt = System.currentTimeMillis();
        while (results.hasNext()) {
            long t = System.currentTimeMillis();
            QuerySolution qs = results.next();
            String portURI = QuerySolutionParser.getURI(qs, "?portfolio");
            String resURI = QuerySolutionParser.getURI(qs, "?resource");
            Individual indi = ontModel.getIndividual(portURI);
            String pid = indi.getLocalName();
            float value = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "value")).asLiteral().getFloat();
            String dateString = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "datetime")).asLiteral().getString();
            Date datetime = StringExchanger.parseStringToDate(dateString);

            EResource resource = QuerySolutionParser.getEResource(resURI, ontModel);
            EPortfolio port = new EPortfolio();
            port.setId(pid);
            port.setEResource(resource);
            port.setElearner(elearner);
            port.setValue(value);
            port.setDatetime(datetime);
            //   System.out.println(port);
            portfolios.add(port);
            System.out.println("==resource==" + (System.currentTimeMillis() - t));
        }
        System.out.println(portfolios.size() + "size");
        System.out.println(System.currentTimeMillis() - wt + "while loop");

        qe.close();
        System.out.println("all" + (System.currentTimeMillis() - all));
        return portfolios;
    }

    public ArrayList<EPortfolio> getEPortfoliosTwo(ELearner elearner) {
        ArrayList<EPortfolio> portfolios = new ArrayList<EPortfolio>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector port_selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "inverse_of_has_portfolio"), el);

        StmtIterator port_iter = ontModel.listStatements(port_selector);
        while (port_iter.hasNext()) {
            Statement s = port_iter.nextStatement();
            Resource portResource = s.getSubject();
            float value = (Float) portResource.getRequiredProperty(ontModel.getProperty(Constant.NS + "value")).asTriple().getObject().getLiteralValue();
            String dateString = portResource.getRequiredProperty(ontModel.getProperty(Constant.NS + "datetime")).asTriple().getObject().getLiteralValue().toString();
            Date datetime = StringExchanger.parseStringToDate(dateString);
            SimpleSelector res_selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_resource_of_P"), portResource);
            StmtIterator res_iter = ontModel.listStatements(res_selector);
            EResource resource = null;
            while (res_iter.hasNext()) {
                Statement res_statement = res_iter.nextStatement();
                Resource resResource = res_statement.getSubject();
                String resId = resResource.getLocalName();
                resource = getEResource(resId);
            }
            EPortfolio port = new EPortfolio();
            port.setId(portResource.getLocalName());
            port.setEResource(resource);
            port.setElearner(elearner);
            port.setValue(value);
            port.setDatetime(datetime);
            portfolios.add(port);
        }
        return portfolios;
    }

    public ArrayList<EResource> getEResourcesByEConcept(EConcept concept) {
        ArrayList<EResource> resources = new ArrayList<EResource>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?resource "
                + "WHERE {"
                + "      ?resource rdf:type base:E_Resource . "
                + "      ?concept base:id " + StringExchanger.getSparqlString(concept.getCid()) + " . "
                + "      ?resource base:is_resource_of_C ?concept . "
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String uri = QuerySolutionParser.getURI(qs, "?resource");
            EResource res = QuerySolutionParser.getEResource(uri, ontModel);
            resources.add(res);
        }
        qe.close();
        return resources;
    }

    public ArrayList<EResource> getAllEResources() {
        ArrayList<EResource> resources = new ArrayList<EResource>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?resource  "
                + "WHERE {"
                + "      ?resource rdf:type base:E_Resource . "
                + "      }";

        Query query = QueryFactory.create(queryString);
        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String resURI = QuerySolutionParser.getURI(qs, "?resource");
            EResource res = QuerySolutionParser.getEResource(resURI, ontModel);
            resources.add(res);
        }
        qe.close();
        return resources;
    }

    public EPerformance getEPerformance(ELearner elearner, EConcept concept) {
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?performance "
                + "WHERE {"
                + "      ?performance rdf:type base:E_Performance . "
                + "      ?performance base:inverse_of_has_performance ?elearner . "
                + "      ?performance base:inverse_of_is_concept_of_P ?concept . "
                + "      ?concept base:id " + StringExchanger.getSparqlString(concept.getCid()) + " . "
                + "      ?elearner base:id " + StringExchanger.getSparqlString(elearner.getId()) + " . "
                + "      "
                + "      }";
        Query query = QueryFactory.create(queryString);
        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, ontModel);
        ResultSet results = qe.execSelect();

        EPerformance performance = null;
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String perURI = QuerySolutionParser.getURI(qs, "?performance");
            Individual indi = ontModel.getIndividual(perURI);

            String pid = ontModel.getResource(perURI).getLocalName();
            float value = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "value")).asLiteral().getFloat();
            String dateString = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "datetime")).asLiteral().getString();
            Date datetime = StringExchanger.parseStringToDate(dateString);
            performance = new EPerformance();
            performance.setId(pid);
            performance.setConcept(concept);
            performance.setElearner(elearner);
            performance.setValue(value);
            performance.setDatetime(datetime);
        }
        qe.close();
        return performance;
    }

    /*********************************************************************************
     * Methods which need rules
     ********************************************************************************/
    public ArrayList<EConcept> getMemberConcept(EConcept concept) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?concept "
                + "WHERE {"
                + "      ?concept rdf:type base:E_Concept . "
                + "      ?concept base:is_part_of ?fatherConcept . "
                + "      ?fatherConcept rdf:type base:E_Concept . "
                + "      ?fatherConcept base:id " + StringExchanger.getSparqlString(concept.getCid()) + " . "
                + "      }";

        Query query = QueryFactory.create(queryString);
        InfModel infModel = getInfModel();
        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, infModel);
        ResultSet results = qe.execSelect();
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String conURI = QuerySolutionParser.getURI(qs, "?concept");
            EConcept con = QuerySolutionParser.getEConcept(conURI, ontModel);
            concepts.add(con);
        }
        qe.close();
        return concepts;
    }

    public ArrayList<EConcept> getRecommendEConcepts(ELearner elearner, int rule) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        Individual el = ontModel.getIndividual(Constant.NS + elearner.getId());
        String ruleString = "is_recommend_of_c_" + rule;
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + ruleString), el);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Statement s = iter.nextStatement();
            Resource r = s.getSubject();
            String nameString = r.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).asTriple().getObject().toString();
            String name = StringExchanger.getCommonString(nameString);
            concepts.add(new EConcept(s.getSubject().getLocalName(), name));
        }
        return concepts;
    }

    public ArrayList<EConcept> getRecommendEConceptsTemp(ELearner elearner, int rule) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        String i = "is_recommend_of_c_" + rule;
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?concept "
                + "WHERE {"
                + "      ?concept rdf:type base:E_Concept . "
                + "      ?elearner base:id " + StringExchanger.getSparqlString(elearner.getId()) + " . "
                + "      ?concept base:" + i + " ?elearner . "
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        InfModel infModel = getInfModel();
        QueryExecution qe = QueryExecutionFactory.create(query, infModel);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String conURI = QuerySolutionParser.getURI(qs, "?concept");
            EConcept con = QuerySolutionParser.getEConcept(conURI, ontModel);
            concepts.add(con);
        }
        qe.close();
        return concepts;
    }

    public ArrayList<ELearner> getRecommendELearners(ELearner elearner, int rule) {
        ArrayList<ELearner> elearners = new ArrayList<ELearner>();
        String i = "is_recommend_of_L_" + rule + "";
        //this rule is used to recommend elearner to rec_el
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?elearner "
                + "WHERE {"
                + "      ?elearner rdf:type base:E_Learner . "
                + "      ?rec_el base:id " + StringExchanger.getSparqlString(elearner.getId()) + " . "
                + "      ?elearner base:" + i + " ?rec_el . "
                + "      }";
        Query query = QueryFactory.create(queryString);
        // Execute the query and obtain results
        InfModel infModel = getInfModel();
        QueryExecution qe = QueryExecutionFactory.create(query, infModel);
        ResultSet results = qe.execSelect();
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String elURI = QuerySolutionParser.getURI(qs, "?elearner");
            ELearner el = QuerySolutionParser.getELearner(elURI, ontModel);
            elearners.add(el);
        }
        qe.close();
        return elearners;
    }

    public ArrayList<EResource> getRecommendEResources(ELearner elearner,
            int rule) {
        ArrayList<EResource> resources = new ArrayList<EResource>();
        String i = "is_recommend_of_r_" + rule;
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?resource  "
                + "WHERE {"
                + "      ?resource rdf:type base:E_Resource . "
                + "      ?elearner base:id " + StringExchanger.getSparqlString(elearner.getId()) + " . "
                + "      ?resource base:" + i + " ?elearner . "
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        InfModel infModel = getInfModel();
        QueryExecution qe = QueryExecutionFactory.create(query, infModel);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String uri = QuerySolutionParser.getURI(qs, "?resource");
            EResource res = QuerySolutionParser.getEResource(uri, ontModel);
            resources.add(res);
        }
        qe.close();
        return resources;
    }

    public ArrayList<EResource> getEResourcesByInterestEConcepts(
            ELearner elearner) {
        ArrayList<EResource> resources = new ArrayList<EResource>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> "
                + "SELECT ?resource  "
                + "WHERE {"
                + "      ?resource rdf:type base:E_Resource . "
                + "      ?elearner base:id \"" + elearner.getId() + "\" . "
                + "      ?elearner base:has_interest ?interest ."
                + "      ?interest base:inverse_of_is_concept_of_I ?concept ."
                + "      ?resource base:is_resource_of_C ?concept ."
                + "      }";
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        InfModel infModel = getInfModel();
        QueryExecution qe = QueryExecutionFactory.create(query, infModel);
        ResultSet results = qe.execSelect();
        //   ResultSetFormatter.out(System.out, results, query);
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            String uri = QuerySolutionParser.getURI(qs, "?resource");
            EResource res = QuerySolutionParser.getEResource(uri, ontModel);
            resources.add(res);
        }
        qe.close();
        return resources;
    }

    public static void main(String[] args) {
        ELearnerModelImplOne emi = new ELearnerModelImplOne(new File("test\\owl\\conceptsAndresource_RDF-XML.owl"));

        ELearner elearner = emi.getELearner("el001");
        ArrayList<EConcept> cons = emi.getRecommendEConcepts(elearner, 1);
        ArrayList<EConcept> cons2 = emi.getRecommendEConcepts(elearner, 3);
        System.out.println(cons.size());
        System.out.println(cons);
        System.out.println(cons2);
        //	long t = System.currentTimeMillis();
        //ArrayList<EPortfolio> rs = emi.getEPortfoliosTwo(elearner);
        //System.out.println(System.currentTimeMillis()-t);
        //System.out.println(rs.size());
    }

   

    public ArrayList<EConcept> getUnInterestConcepts(ELearner elearner) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
