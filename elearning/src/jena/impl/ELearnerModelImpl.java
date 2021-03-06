package jena.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import exception.jena.IndividualNotExistException;
import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;
import util.Constant;
import util.StringExchanger;
import jena.interfaces.ELearnerModelQueryInterface;
import jena.interfaces.ELearnerUserOperationInterface;

/**************************************************************************
 * Model Query with ontology Model
 * @author william
 *
 **************************************************************************/
public class ELearnerModelImpl extends ELearnerModel implements ELearnerModelQueryInterface,ELearnerUserOperationInterface
         {

    public ELearnerModelImpl() {
        super();
    }

    public ELearnerModelImpl(File file) {
        super(file);
    }

    public ELearnerModelImpl(File file, String lang) {
        super(file, lang);
    }

    public static void main(String[] args) throws IndividualNotExistException, IOException {
        File file = new File("test\\owl\\conceptsAndresource_RDF-XML.owl");
        long init = System.currentTimeMillis();
        ELearnerModelImpl emi = new ELearnerModelImpl(file);
        System.out.println("intitime:" + (System.currentTimeMillis() - init) + "ms");
        ELearner el = emi.getELearner("el001");
        EConcept con = emi.getEConcept("cid38");
        long t1 = System.currentTimeMillis();
        ArrayList<EConcept> inte = emi.getInterestConcepts(el);
        System.out.println("time:" + (System.currentTimeMillis() - t1) + "ms\tintests: " + inte.size());
        long t2 = System.currentTimeMillis();
        ArrayList<EConcept> all = emi.getAllEConcepts();
        System.out.println("time:" + (System.currentTimeMillis() - t2) + "ms\talls: " + all.size());
        long t3 = System.currentTimeMillis();
        ArrayList<EConcept> unin = emi.getUnInterestConcepts(el);
        System.out.println("time:" + (System.currentTimeMillis() - t2) + "ms\tunins: " + unin.size());

    }

    @Override
    public ArrayList<EConcept> getAllEConcepts() {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        OntClass concept = ontModel.getOntClass(Constant.NS + "E_Concept");
        Iterator<Individual> iter = ontModel.listIndividuals(concept);
        while (iter.hasNext()) {
            Individual indi = (Individual) iter.next();
            String id = indi.getLocalName();
            String name = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "name")).asLiteral().getString();
            EConcept con = new EConcept(id, name);
            concepts.add(con);
        }
        return concepts;
    }

    public ArrayList<EConcept> getAllEConceptsTwo() {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        OntClass concept = ontModel.getOntClass(Constant.NS + "E_Concept");
        Iterator<Individual> iter = ontModel.listIndividuals(concept);
        while (iter.hasNext()) {
            Individual indi = (Individual) iter.next();
            String id = indi.getLocalName();
            concepts.add(getEConcept(id));
        }
        return concepts;
    }

    @Override
    public ArrayList<EResource> getAllEResources() {
        Resource resourceClass = ontModel.getResource(Constant.NS + "E_Resource");
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NSRDF + "type"), resourceClass);
        StmtIterator iter = ontModel.listStatements(selector);

        ArrayList<EResource> resources = new ArrayList<EResource>();
        while (iter.hasNext()) {
            Statement s = iter.nextStatement();
            Resource r = s.getSubject();
            EResource er = new EResource(r.getLocalName());
            String name = r.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).toString();
            String difficulty = r.getRequiredProperty(ontModel.getProperty(Constant.NS + "difficulty")).toString();
            String fileLocation = r.getRequiredProperty(ontModel.getProperty(Constant.NS + "fileLocation")).toString();
            er.setName(name);
            er.setDifficulty(difficulty);
            er.setFileLocation(fileLocation);
            resources.add(er);
        }
        return resources;
    }

    @Override
    public EConcept getEConcept(String cid) {
        EConcept concept = new EConcept(cid);
        Individual indi = ontModel.getIndividual(Constant.NS + cid);
        concept.setName(indi.getPropertyValue(ontModel.getProperty(Constant.NS + "name")).asLiteral().getString());
        return concept;
    }

    @Override
    public ELearner getELearner(String eid) {
        ELearner elearner = new ELearner(eid);
        Individual indi = ontModel.getIndividual(Constant.NS + eid);
        elearner.setName(indi.getPropertyValue(ontModel.getProperty(Constant.NS + "name")).asLiteral().getString());
        RDFNode email = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "email"));
        if (email == null) {
            elearner.setEmail(" ");
        } else {
            elearner.setEmail(email.asLiteral().getString());
        }
        RDFNode address = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "address"));
        if (address == null) {
            elearner.setAddress(" ");
        } else {
            elearner.setAddress(address.asLiteral().getString());
        }
        RDFNode grade = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "grade"));
        if (grade == null) {
            elearner.setGrade(" ");
        } else {
            elearner.setGrade(grade.asLiteral().getString());
        }
        return elearner;
    }

    @Override
    public EPerformance getEPerformance(ELearner elearner, EConcept concept) {
        Resource con = ontModel.getResource(Constant.NS + concept.getCid());
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector_el = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "inverse_of_has_performance"), el);
        StmtIterator iter_el = ontModel.listStatements(selector_el);
        while (iter_el.hasNext()) {
            Statement s = iter_el.nextStatement();
            Resource r = s.getSubject();
            SimpleSelector selector_con = new SimpleSelector(r, ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_P"), con);
            StmtIterator iter_con = ontModel.listStatements(selector_con);
            while (iter_con.hasNext()) {
                float value = (Float) r.getRequiredProperty(ontModel.getProperty(Constant.NS + "value")).getLiteral().getFloat();
                String dateString = r.getRequiredProperty(ontModel.getProperty(Constant.NS + "datetime")).getLiteral().getString();
                Date datetime = StringExchanger.parseStringToDate(dateString);
                EPerformance performance = new EPerformance();
                performance.setElearner(elearner);
                performance.setConcept(concept);
                performance.setDatetime(datetime);
                performance.setId(r.getLocalName());
                performance.setValue(value);
                return performance;
            }
        }
        return null;
    }

    @Override
    public ArrayList<EPerformance> getEPerformances(ELearner elearner) {
        ArrayList<EPerformance> performances = new ArrayList<EPerformance>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector_el = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "inverse_of_has_performance"), el);
        StmtIterator iter_el = ontModel.listStatements(selector_el);
        while (iter_el.hasNext()) {
            Resource r = iter_el.nextStatement().getSubject();
            SimpleSelector selector_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_concept_of_P"), r);
            StmtIterator iter_con = ontModel.listStatements(selector_con);
            while (iter_con.hasNext()) {
                Resource conResource = iter_con.nextStatement().getSubject();
                EConcept concept = getEConcept(conResource.getLocalName());
                float value = (Float) r.getRequiredProperty(ontModel.getProperty(Constant.NS + "value")).getLiteral().getFloat();
                String dateString = r.getRequiredProperty(ontModel.getProperty(Constant.NS + "datetime")).getLiteral().getString();
                Date datetime = StringExchanger.parseStringToDate(dateString);

                EPerformance performance = new EPerformance();
                performance.setElearner(elearner);
                performance.setConcept(concept);
                performance.setDatetime(datetime);
                performance.setId(r.getLocalName());
                performance.setValue(value);
                performances.add(performance);
            }
        }
        return performances;
    }

    @Override
    public EPortfolio getEPortfolio(ELearner elearner, EResource resource) {
        Resource res = ontModel.getResource(Constant.NS + resource.getRid());
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector_con = new SimpleSelector(res, ontModel.getProperty(Constant.NS + "is_resource_of_P"), (RDFNode) null);
        StmtIterator iter_con = ontModel.listStatements(selector_con);
        while (iter_con.hasNext()) {
            SimpleSelector selector_el = new SimpleSelector(el, ontModel.getProperty(Constant.NS + "has_portfolio"), (RDFNode) null);
            StmtIterator iter_el = ontModel.listStatements(selector_el);
            while (iter_el.hasNext()) {
                Resource port = (Resource) iter_el.nextStatement().getObject();
                float value = (Float) port.getRequiredProperty(ontModel.getProperty(Constant.NS + "value")).getLiteral().getFloat();
                String dateString = port.getRequiredProperty(ontModel.getProperty(Constant.NS + "datetime")).getLiteral().getString();
                Date datetime = StringExchanger.parseStringToDate(dateString);

                EPortfolio portfolio = new EPortfolio();
                portfolio.setId(port.getLocalName());
                portfolio.setElearner(elearner);
                portfolio.setEResource(resource);
                portfolio.setValue(value);
                portfolio.setDatetime(datetime);
                return portfolio;
            }
        }
        return null;
    }

    @Override
    public ArrayList<EPortfolio> getEPortfolios(ELearner elearner) {
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

    @Override
    public EResource getEResource(String rid) {
        EResource resource = new EResource(rid);
        Individual indi = ontModel.getIndividual(Constant.NS + rid);
        resource.setName(indi.getPropertyValue(ontModel.getProperty(Constant.NS + "name")).asLiteral().getString());
        resource.setFileLocation(indi.getPropertyValue(ontModel.getProperty(Constant.NS + "fileLocation")).asLiteral().getString());
        resource.setDifficulty(indi.getPropertyValue(ontModel.getProperty(Constant.NS + "difficulty")).asLiteral().getString());
        return resource;
    }

    @Override
    public ArrayList<EResource> getEResourcesByEConcept(EConcept concept) {
        ArrayList<EResource> resources = new ArrayList<EResource>();
        Resource con = ontModel.getResource(Constant.NS + concept.getCid());
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_resource_of_C"), con);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource indi = iter.nextStatement().getSubject();
            EResource resource = new EResource(indi.getLocalName());
            resource.setName(indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString());
            resource.setFileLocation(indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "fileLocation")).getLiteral().getString());
            resource.setDifficulty(indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "difficulty")).getLiteral().getString());
            resources.add(resource);
            //resources.add(getEResource(indi.getLocalName()));
        }
        return resources;
    }

    @Override
    public ArrayList<EResource> getEResourcesByInterestEConcepts(
            ELearner elearner) {
        ArrayList<EResource> resources = new ArrayList<EResource>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector_interest = new SimpleSelector(el, ontModel.getProperty(Constant.NS + "has_interest"), (RDFNode) null);
        StmtIterator iter_interest = ontModel.listStatements(selector_interest);
        while (iter_interest.hasNext()) {
            Resource interest = (Resource) iter_interest.nextStatement().getObject();
            SimpleSelector selector_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_concept_of_I"), interest);
            StmtIterator iter_con = ontModel.listStatements(selector_con);
            while (iter_con.hasNext()) {
                Resource con = iter_con.nextStatement().getSubject();
                SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_resource_of_C"), con);
                StmtIterator iter = ontModel.listStatements(selector);
                while (iter.hasNext()) {
                    Resource indi = iter.nextStatement().getSubject();
                    EResource resource = new EResource(indi.getLocalName());
                    resource.setName(indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString());
                    resource.setFileLocation(indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "fileLocation")).getLiteral().getString());
                    resource.setDifficulty(indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "difficulty")).getLiteral().getString());
                    resources.add(resource);
                    //resources.add(getEResource(indi.getLocalName()));
                }
            }
        }
        return resources;
    }

    @Override
    public ArrayList<EConcept> getInterestConcepts(ELearner elearner) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector_interest = new SimpleSelector(el, ontModel.getProperty(Constant.NS + "has_interest"), (RDFNode) null);
        StmtIterator iter_interest = ontModel.listStatements(selector_interest);
        while (iter_interest.hasNext()) {
            Resource interest = (Resource) iter_interest.nextStatement().getObject();
            SimpleSelector selector_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_concept_of_I"), interest);
            StmtIterator iter_con = ontModel.listStatements(selector_con);
            while (iter_con.hasNext()) {
                Resource con = iter_con.nextStatement().getSubject();
                String name = con.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString();
                concepts.add(new EConcept(con.getLocalName(), name));
            }
        }
        return concepts;
    }

    @Override
    public ArrayList<EConcept> getSonConcepts(EConcept econcept) {
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

    public ArrayList<EConcept> getMemberConcept(EConcept concept) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();

        Resource con = ontModel.getResource(Constant.NS + concept.getCid());
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_part_of"), con);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Statement s = iter.nextStatement();
            Resource r = s.getSubject();
            EConcept newCon = getEConcept(r.getLocalName());
            concepts.add(newCon);
        }
        return concepts;
    }



    /*******************************************************************************************************
     * Update Data in model 
     * @throws IndividualNotExistException 
     *******************************************************************************************************/
    @Override
    public boolean updateEConcept(EConcept concept) throws IndividualNotExistException {
        if (!containEConcept(concept.getCid())) {
            throw new IndividualNotExistException("EConcept " + concept.getCid() + "  does not exist");
        }
        Individual indi = ontModel.getIndividual(Constant.NS + concept.getCid());
        Property p = ontModel.getProperty(Constant.NS + "name");
        ontModel.remove(indi, p, indi.getPropertyValue(p));
        ontModel.add(indi, p, concept.getName(), new XSDDatatype("string"));
        return false;
    }

    @Override
    public boolean updateELearner(ELearner elearner) throws IndividualNotExistException {
        if (!(containELearner(elearner.getId()))) {
            throw new IndividualNotExistException("elearner " + elearner.getId() + " does not exist");
        }
        ELearner el = getELearner(elearner.getId());
        Individual indi = ontModel.getIndividual(Constant.NS + elearner.getId());
        if (!elearner.getName().equals(el.getName())) {
//			SimpleSelector selector = new SimpleSelector(indi, ontModel.getProperty(Constant.NS+"name"), (RDFNode)null);
//			StmtIterator iter = infModel.listStatements(selector);
//			ontModel.remove(iter);
            Property p = ontModel.getProperty(Constant.NS + "name");
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, elearner.getName(), new XSDDatatype("string"));
        }
        if (!elearner.getAddress().equals(el.getAddress())) {
            Property p = ontModel.getProperty(Constant.NS + "address");
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, elearner.getAddress(), new XSDDatatype("string"));
        }
        if (!elearner.getEmail().equals(el.getEmail())) {
            Property p = ontModel.getProperty(Constant.NS + "email");
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, elearner.getEmail(), new XSDDatatype("string"));
        }
        if (!elearner.getGrade().equals(el.getGrade())) {
            Property p = ontModel.getProperty(Constant.NS + "grade");
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, elearner.getGrade(), new XSDDatatype("string"));
        }
        return true;
    }

    /*******************************************************************************************
     * update the value of the EPerformance
     *****************************************************************************************/
    @Override
    public boolean updateEPerfomance(EPerformance performance) throws IndividualNotExistException {
        if (!containEPerformance(performance.getId())) {
            throw new IndividualNotExistException("EPerformance " + performance.getId() + " does not exist");
        }
        Individual indi = ontModel.getIndividual(Constant.NS + performance.getId());
        float newValue = performance.getValue();
        Property p = ontModel.getProperty(Constant.NS + "value");
        ontModel.remove(indi, p, indi.getPropertyValue(p));
        ontModel.add(indi, p, String.valueOf(newValue), new XSDDatatype("float"));
        return true;
    }

    /*******************************************************************************************
     * update the value of the EPortfolio
     *****************************************************************************************/
    @Override
    public boolean updateEPortfolio(EPortfolio portfolio) throws IndividualNotExistException {
        if (!containEPortfolio(portfolio.getId())) {
            throw new IndividualNotExistException("EPortfolio " + portfolio.getId() + " does not exist");
        }
        Individual indi = ontModel.getIndividual(Constant.NS + portfolio.getId());
        float newValue = portfolio.getValue();
        Property p = ontModel.getProperty(Constant.NS + "value");
        ontModel.remove(indi, p, indi.getPropertyValue(p));
        ontModel.add(indi, p, String.valueOf(newValue), new XSDDatatype("float"));
        return true;
    }

    @Override
    public boolean updateEResource(EResource resource) throws IndividualNotExistException {
        if (!(containEResource(resource.getRid()))) {
            throw new IndividualNotExistException("EResource " + resource.getRid() + " does not exist");
        }
        EResource res = getEResource(resource.getRid());
        Individual indi = ontModel.getIndividual(Constant.NS + resource.getRid());
        if (!resource.getName().equals(res.getName())) {
            Property p = ontModel.getProperty(Constant.NS + "name");
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getName(), new XSDDatatype("string"));
        }
        if (!resource.getFileLocation().equals(res.getFileLocation())) {
            Property p = ontModel.getProperty(Constant.NS + "fileLocation");
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getFileLocation(), new XSDDatatype("string"));
        }
        if (!resource.getDifficulty().equals(res.getDifficulty())) {
            Property p = ontModel.getProperty(Constant.NS + "difficulty");
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getDifficulty(), new XSDDatatype("string"));
        }
        return true;
    }

    
    @Override
    public EInterest getEInterest(ELearner elearner, EConcept concept) {
        Individual el = ontModel.getIndividual(Constant.NS + elearner.getId());
        Individual con = ontModel.getIndividual(Constant.NS + concept.getCid());
        Property p1 = ontModel.getProperty(Constant.NS + "inverse_of_has_interest");
        Property p2 = ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_I");
        SimpleSelector selector = new SimpleSelector(null, p1, el);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource in = iter.nextStatement().getSubject();
            SimpleSelector selector2 = new SimpleSelector(in, p2, con);
            StmtIterator iter2 = ontModel.listStatements(selector2);
            while (iter2.hasNext()) {
                String id = in.getLocalName();
                EInterest interest = new EInterest(id);
                interest.setEConcept(concept);
                interest.setELearner(elearner);
                float value = in.getRequiredProperty(ontModel.getProperty(Constant.NS + "value")).getLiteral().getFloat();
                interest.setValue(value);
                return interest;
            }
        }
        return null;
    }

    public ArrayList<EInterest> getEInterests(ELearner elearner) {
        Individual el = ontModel.getIndividual(Constant.NS + elearner.getId());
        Property p1 = ontModel.getProperty(Constant.NS + "inverse_of_has_interest");
        Property p2 = ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_I");
        SimpleSelector selector = new SimpleSelector(null, p1, el);
        StmtIterator iter = ontModel.listStatements(selector);
        ArrayList<EInterest> interests = new ArrayList<EInterest>();
        while (iter.hasNext()) {
            Resource in = iter.nextStatement().getSubject();
            SimpleSelector selector2 = new SimpleSelector(in, p2, (RDFNode) null);
            StmtIterator iter2 = ontModel.listStatements(selector2);
            while (iter2.hasNext()) {
                Resource con = (Resource) iter2.nextStatement().getObject();
                String id = in.getLocalName();
                EInterest interest = new EInterest(id);
                interest.setEConcept(getEConcept(con.getLocalName()));
                interest.setELearner(elearner);
                float value = in.getRequiredProperty(ontModel.getProperty(Constant.NS + "value")).getLiteral().getFloat();
                interest.setValue(value);
                interests.add(interest);
            }
        }
        return interests;
    }

    @Override
    public ArrayList<EConcept> getUnInterestConcepts(ELearner elearner) {
        ArrayList<EConcept> interestConcepts = getInterestConcepts(elearner);
        ArrayList<EConcept> uninterest = new ArrayList<EConcept>();
        ArrayList<EConcept> allConcepts = getAllEConcepts();
        for (EConcept con : allConcepts) {
            if (!interestConcepts.contains(con)) {
                uninterest.add(con);
            }
        }
        return uninterest;
    }
    /***************************************************************************************
     * 
     */

    @Override
    public boolean addEInterest(EInterest interest) {
        ELearner el = interest.getELearner();
        EConcept con = interest.getEConcept();
        Resource in = ontModel.createResource(Constant.NS + interest.getId(), ontModel.getResource(Constant.NS + "E_Interest"));
        in.addProperty(ontModel.getProperty(Constant.NS + "inverse_of_has_interest"), ontModel.getResource(Constant.NS + el.getId()));
        in.addProperty(ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_I"), ontModel.getResource(Constant.NS + con.getCid()));
        in.addProperty(ontModel.getProperty(Constant.NS + "value"), String.valueOf(interest.getValue()), new XSDDatatype("string"));
        return true;
    }
	@Override
    public boolean removeEInterest(EInterest interest)
            throws IndividualNotExistException {
        if (!containEInterest(interest.getId())) {
            throw new IndividualNotExistException("EInterest " + interest.getId() + " does not exist in the model");
        }
        Individual in = ontModel.getIndividual(Constant.NS + interest.getId());
        in.remove();
        return true;
    }

    @Override
    public boolean removeEInterest(ELearner el, EConcept con)
            throws IndividualNotExistException {
        EInterest in = getEInterest(el, con);
        if (in == null) {
            throw new IndividualNotExistException("This EInterest does not exist in the model");
        }
        Individual interest = ontModel.getIndividual(Constant.NS + in.getId());
        interest.remove();
        return true;
    }


}
