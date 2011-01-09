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
import exception.jena.IndividualExistException;
import exception.jena.IndividualNotExistException;
import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;
import util.Constant;
import util.StringExchanger;
import jena.interfaces.ELearnerModelQueryInterface;
import jena.interfaces.ELearnerUserOperationInterface;

/**************************************************************************
 * Model Query with ontology Model
 * @author william
 *
 **************************************************************************/
public class ELearnerModelImpl extends ELearnerModel implements ELearnerModelQueryInterface, ELearnerUserOperationInterface {

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
        EConcept con = emi.getEConcept("Computer_Science");
        EConcept con2 = emi.getEConcept("CMP.cf.3");
//        ArrayList<EPerformance> ps = emi.getEPerformances(el);
//        System.out.println("size" + ps.size());
//        EPerformance ep = new EPerformance();
//        ep.setId("newId");
//        ep.setValue(1f);
//        ep.setConcept(con2);
//        ep.setElearner(el);
//        ep.setDatetime(new Date(System.currentTimeMillis()));
//        emi.addEPerfomance(ep);
//
//        EPerformance ep2 = emi.getEPerformance(el, con2);
//        System.out.println(ep2);
//        ArrayList<EPerformance> ps2 = emi.getEPerformances(el);
//        //    emi.writeToFile(new File("test\\owl\\ms.owl"));
//        System.out.println("size2:" + ps2.size());
//        System.out.println("end");
    }

    @Override
    public ArrayList<EConcept> getAllEConcepts() {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        OntClass concept = ontModel.getOntClass(Constant.NS + "E_Concept");
        Iterator<Individual> iter = ontModel.listIndividuals(concept);
        while (iter.hasNext()) {
            Individual indi = (Individual) iter.next();
            concepts.add(getEConcept(indi.getLocalName()));
        }
        return concepts;
    }

    @Override
    public ArrayList<ISCB_Resource> getAllEResources() {
        Resource resourceClass = ontModel.getResource(Constant.NS + "ISCB_Resource");
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NSRDF + "type"), resourceClass);
        StmtIterator iter = ontModel.listStatements(selector);
        ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
        while (iter.hasNext()) {
            Statement s = iter.nextStatement();
            Resource r = s.getSubject();
            ISCB_Resource er = getEResource(r.getLocalName());
            resources.add(er);
        }
        return resources;
    }

    @Override
    public EConcept getEConcept(String cid) {
        Individual indi = ontModel.getIndividual(Constant.NS + cid);
        String name = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString();
        return new EConcept(cid, name);
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
        RDFNode gender = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "gender"));
        if (gender == null) {
            elearner.setGender("secret");
        } else {
            elearner.setGender(gender.asLiteral().getString());
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
        Individual con = ontModel.getIndividual(Constant.NS + concept.getCid());
        Individual el = ontModel.getIndividual(Constant.NS + elearner.getId());
        SimpleSelector selector_el = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "inverse_of_has_performance"), el);
        StmtIterator iter_el = ontModel.listStatements(selector_el);
        while (iter_el.hasNext()) {
            Statement s = iter_el.nextStatement();
            Resource r = s.getSubject();
            SimpleSelector selector_con = new SimpleSelector(r, ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_P"), con);
            StmtIterator iter_con = ontModel.listStatements(selector_con);
            while (iter_con.hasNext()) {
                EPerformance performance = getEPerformance(r.getLocalName());
                performance.setElearner(elearner);
                performance.setConcept(concept);
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
                EPerformance performance = getEPerformance(r.getLocalName());
                performance.setElearner(elearner);
                performance.setConcept(concept);
                performances.add(performance);
            }
        }
        return performances;
    }

    @Override
    public EPortfolio getEPortfolio(ELearner elearner, ISCB_Resource resource) {
        Resource res = ontModel.getResource(Constant.NS + resource.getRid());
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector_con = new SimpleSelector(res, ontModel.getProperty(Constant.NS + "is_resource_of_P"), (RDFNode) null);
        StmtIterator iter_con = ontModel.listStatements(selector_con);
        while (iter_con.hasNext()) {
            SimpleSelector selector_el = new SimpleSelector(el, ontModel.getProperty(Constant.NS + "has_portfolio"), (RDFNode) null);
            StmtIterator iter_el = ontModel.listStatements(selector_el);
            while (iter_el.hasNext()) {
                Resource port = (Resource) iter_el.nextStatement().getObject();
                EPortfolio portfolio = getEPortfolio(port.getLocalName());
                portfolio.setElearner(elearner);
                portfolio.setEResource(resource);
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
            String dateString = portResource.getRequiredProperty(ontModel.getProperty(Constant.NS + "date_time")).asTriple().getObject().getLiteralValue().toString();
            Date datetime = StringExchanger.parseStringToDate(dateString);
            SimpleSelector res_selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_resource_of_P"), portResource);
            StmtIterator res_iter = ontModel.listStatements(res_selector);
            ISCB_Resource resource = null;
            while (res_iter.hasNext()) {
                Statement res_statement = res_iter.nextStatement();
                Resource resResource = res_statement.getSubject();
                String resId = resResource.getLocalName();
                resource = getEResource(resId);
            }
            EPortfolio port = getEPortfolio(portResource.getLocalName());
            port.setEResource(resource);
            port.setElearner(elearner);
            portfolios.add(port);
        }
        return portfolios;
    }

    @Override
    public ISCB_Resource getEResource(String rid) {
        ISCB_Resource resource = new ISCB_Resource(rid);
        Individual indi = ontModel.getIndividual(Constant.NS + rid);
        resource.setName(indi.getPropertyValue(ontModel.getProperty(Constant.NS + "name")).asLiteral().getString());
        resource.setFileLocation(indi.getPropertyValue(ontModel.getProperty(Constant.NS + "file_location")).asLiteral().getString());
        resource.setDifficulty(indi.getPropertyValue(ontModel.getProperty(Constant.NS + "difficulty")).asLiteral().getString());
        RDFNode rd = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "description"));
        if (rd != null) {
            resource.setResourceDescription(rd.asLiteral().getString());
        } else {
            resource.setResourceDescription("");
        }
        RDFNode rq = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "resource_quality"));
        if (rq != null) {
            resource.setResourceQuality(rq.asLiteral().getString());
        } else {
            resource.setResourceQuality("");
        }
        RDFNode rt = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "resource_type"));
        if (rt != null) {
            resource.setResourceType(rt.asLiteral().getString());
        } else {
            resource.setResourceType("");
        }
        RDFNode rdt = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "upload_time"));
        if (rdt != null) {
            resource.setUploadTime(StringExchanger.parseStringToDate(rdt.asLiteral().getString()));
        } else {
            resource.setUploadTime(new Date(System.currentTimeMillis()));
        }
        Resource fileFormat = indi.getPropertyResourceValue(ontModel.getProperty(Constant.NS + "has_postfix"));
        if (fileFormat != null) {
            resource.setPostfix(getPostFix(fileFormat));
        } else {
            resource.setPostfix("");
        }
        return resource;
    }

    @Override
    public ArrayList<ISCB_Resource> getEResourcesByEConcept(EConcept concept) {
        ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
        Resource con = ontModel.getResource(Constant.NS + concept.getCid());
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_resource_of_C"), con);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource indi = iter.nextStatement().getSubject();
            resources.add(getEResource(indi.getLocalName()));
        }
        return resources;
    }

    @Override
    public ArrayList<ISCB_Resource> getEResourcesByInterestEConcepts(
            ELearner elearner) {
        ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
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
                    ISCB_Resource resource = getEResource(indi.getLocalName());
                    resources.add(resource);
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
            concepts.add(getEConcept(r.getLocalName()));
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
            concepts.add(getEConcept(r.getLocalName()));
        }
        return concepts;
    }

    /*******************************************************************************************************
     * Update Data in model 
     * @throws IndividualNotExistException 
     *******************************************************************************************************/
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
    public boolean addEInterest(EInterest interest) throws IndividualExistException {
        if (containEInterest(interest.getId())) {
            throw new IndividualExistException("EInterest " + interest.getId() + " already exist in the model");
        }
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

    @Override
    public boolean updateEInterest(EInterest interest) throws IndividualNotExistException {
        Individual indi = ontModel.getIndividual(Constant.NS + interest.getId());
        if (indi == null) {
            throw new IndividualNotExistException("EInterest " + interest.getId() + " does not exist in the model");
        }
        Property p = ontModel.getProperty(Constant.NS + "value");
        indi.setPropertyValue(p, ontModel.createTypedLiteral(interest.getValue(), new XSDDatatype("float")));
        return true;
    }

    @Override
    public EPerformance getEPerformance(String pid) {
        Individual indi = ontModel.getIndividual(Constant.NS + pid);
        if (indi == null) {
            return null;
        }
        EPerformance performance = new EPerformance();
        performance.setId(indi.getLocalName());
        Statement valueNode = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "value"));
        if (valueNode != null) {
            float value = (Float) valueNode.getLiteral().getFloat();
            performance.setValue(value);
        }
        Statement dateNode = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "date_time"));
        if (dateNode != null) {
            String dateString = dateNode.getLiteral().getString();
            Date datetime = StringExchanger.parseStringToDate(dateString);
            performance.setDatetime(datetime);
        }
        return performance;
    }

    @Override
    public EPortfolio getEPortfolio(String pid) {
        Individual indi = ontModel.getIndividual(Constant.NS + pid);
        if (indi == null) {
            return null;
        }
        EPortfolio port = new EPortfolio();
        Statement valueNode = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "value"));
        if (valueNode != null) {
            float value = (Float) valueNode.getLiteral().getFloat();
            port.setValue(value);
        }
        Statement dateNode = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "date_time"));
        if (dateNode != null) {
            String dateString = dateNode.getLiteral().getString();
            Date datetime = StringExchanger.parseStringToDate(dateString);
            port.setDatetime(datetime);
        }
        return port;
    }
}
