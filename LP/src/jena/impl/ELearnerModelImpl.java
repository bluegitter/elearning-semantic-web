package jena.impl;

import algorithm.datastructure.LinkedEConcept;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;
import jena.OwlOperation;
import exception.jena.HasNoPropertyValueException;
import exception.jena.IndividualExistException;
import exception.jena.IndividualNotExistException;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jena.ELearnerModelUtilMethod;
import jena.OwlFactory;
import jena.interfaces.ELearnerModelOperationInterface;
import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;
import util.Constant;
import jena.interfaces.ELearnerUserOperationInterface;
import ontology.EGoal;
import ontology.EPerformanceAssessment;
import util.StringExchanger;

/**************************************************************************
 * Model Query with ontology Model
 * @author william
 *
 **************************************************************************/
public class ELearnerModelImpl implements ELearnerUserOperationInterface, ELearnerModelOperationInterface {

    public OntModel ontModel;

    public ELearnerModelImpl() {
        ontModel = OwlFactory.getOntModel();
    }

    public ELearnerModelImpl(File file) {
        this.ontModel = OwlFactory.getOntModel(file);
    }

    public ELearnerModelImpl(File file, String lang) {
        this.ontModel = OwlFactory.getOntModel(file, lang);
    }

    public ELearnerModelImpl(OntModel ontModel) {
        this.ontModel = ontModel;
    }

    //向模型中添加模型（合并模型）
    public boolean addNewModel(File file) {
        try {
            if (file == null) {
                System.out.println("File: " + file + " not found");
                return false;
            }
            java.io.InputStream in = new java.io.FileInputStream(file);
            ontModel.read(in, Constant.NS);
            Resource configuration = ontModel.createResource();
            configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ELearnerModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public void writeToFile(File file) {
        try {
            OwlOperation.writeRdfFile(ontModel, file, null);
            OwlOperation.writeOwlFileFromRdfFile(file, file);
        } catch (IOException ex) {
            Logger.getLogger(ELearnerModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeUserModelToFile(File file) {
        try {
            ELearnerModelImpl newEmi = new ELearnerModelImpl(new File("files/owl/el.owl"));
            ELearner newEL = getELearner("el001");
            newEmi.addELearner(newEL);

            //         newModel.add(newModel.createStatement(null, null, null))
            OwlOperation.writeRdfFile(newEmi.getOntModel(), file, null);
            OwlOperation.writeOwlFileFromRdfFile(file, file);
        } catch (IOException ex) {
            Logger.getLogger(ELearnerModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public EConcept getRootConcept() {
        EConcept rootConcept = new EConcept();
        rootConcept.setCid("Computer_Science");
        rootConcept.setName("软件工程");
        return rootConcept;
    }

    public OntModel getOntModel() {
        return ontModel;
    }

    /**************************************************************************************************
     * Check whether the individual exists in the model
     ***************************************************************************************************/
    public boolean containEConcept(String cid) {
        return ontModel.containsResource(ontModel.getResource(Constant.NS + cid));
    }

    public boolean containEInterest(ELearner elearner, EConcept concept) throws IndividualNotExistException {
        if (!containELearner(elearner.getId())) {
            throw new IndividualNotExistException("the elearner " + elearner.getName() + " does not exist in the model");
        }
        if (containEConcept(concept.getCid())) {
            throw new IndividualNotExistException("the concept " + concept.getName() + " does not exist in the model");
        }
        ArrayList<EInterest> ins = getEInterests(elearner);
        for (EInterest in : ins) {
            if (in.getEConcept().getCid().equals(concept.getCid())) {
                return true;
            }
        }
        return false;
    }

    public boolean containELearner(String eid) {
        return ontModel.containsResource(ontModel.getResource(Constant.NS + eid));
    }

    public boolean containEResource(String rid) {
        return ontModel.containsResource(ontModel.getResource(Constant.NS + rid));
    }

    public boolean containEPortfolio(String portId) {
        return ontModel.containsResource(ontModel.getResource(Constant.NS + portId));
    }

    public boolean containEPerformance(String performId) {
        return ontModel.containsResource(ontModel.getResource(Constant.NS + performId));
    }

    public boolean containEInterest(String interestId) {
        return ontModel.containsResource(ontModel.getResource(Constant.NS + interestId));
    }

    public boolean containEGoal(String goalId) {
        return ontModel.containsResource(ontModel.getResource(Constant.NS + goalId));
    }

    /*******************************************************************************************************
     * Add new Data Operations
     * @throws IndividualNotExistException
     *******************************************************************************************************/
    @Override
    public boolean addELearner(ELearner elearner) {
        if (containELearner(elearner.getId())) {
            System.out.println("elearner " + elearner.getId() + " has already existed in the model");
            return false;
            //          throw new IndividualExistException("elearner " + elearner.getId() + " has already existed in the model");
        }
        Resource el = ontModel.createResource(Constant.NS + elearner.getId(), ontModel.getResource(Constant.NS + "E_Learner"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "id"), elearner.getId());
        String name = elearner.getName();
        if (name != null) {
            ontModel.add(el, ontModel.getProperty(Constant.NS + "name"), name, new XSDDatatype("string"));
        }
        String gender = elearner.getGender();
        if (gender != null) {
            ontModel.add(el, ontModel.getProperty(Constant.NS + "gender"), gender, new XSDDatatype("string"));
        }
        String grade = elearner.getGrade();
        if (grade != null) {
            ontModel.add(el, ontModel.getProperty(Constant.NS + "grade"), grade, new XSDDatatype("string"));
        }
        String address = elearner.getAddress();
        if (address != null) {
            ontModel.add(el, ontModel.getProperty(Constant.NS + "address"), address, new XSDDatatype("string"));
        }
        String email = elearner.getEmail();
        if (email != null) {
            ontModel.add(el, ontModel.getProperty(Constant.NS + "email"), email, new XSDDatatype("string"));
        }
        return true;
    }

    @Override
    public boolean addEResource(ISCB_Resource resource) {
        if (containEResource(resource.getRid())) {
            System.out.println("EResource " + resource.getRid() + " has already existed in the model");
            return false;
//            throw new IndividualExistException("EResource " + resource.getRid() + " has already existed in the model");
        }
        Resource re = ontModel.createResource(Constant.NS + resource.getRid(), ontModel.getResource(Constant.NS + "ISCB_Resource"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "id"), resource.getRid());
        ontModel.add(re, ontModel.getProperty(Constant.NS + "name"), resource.getName(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "description"), resource.getResourceDescription(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "application_type"), resource.getAppType(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "resource_type"), resource.getResourceType(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "file_location"), resource.getFileLocation(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "difficulty"), resource.getDifficulty(), new XSDDatatype("string"));
        String rq = resource.getResourceQuality();
        if (rq != null) {
            ontModel.add(re, ontModel.getProperty(Constant.NS + "resource_quality"), rq, new XSDDatatype("string"));
        }
        Date ut = resource.getUploadTime();
        if (ut != null) {
            ontModel.add(re, ontModel.getProperty(Constant.NS + "upload_time"), StringExchanger.parseDateToString(ut), new XSDDatatype("date"));

        }
        String postfix = resource.getPostfix();
        if (postfix != null) {
            Individual indi = getFileFormat(postfix);
            if (indi != null) {
                Statement s = ontModel.createStatement(re, ontModel.getProperty(Constant.NS + "has_postfix"), indi);
                ontModel.add(s);
            }
        }

        return true;
    }

    @Override
    public boolean addEPortfolio(EPortfolio portfolio) throws IndividualExistException {
        if (containEPortfolio(portfolio.getId())) {
            throw new IndividualExistException("EPortfolio " + portfolio.getId() + " has already existed in the model");
        }
        Individual el = ontModel.getIndividual(Constant.NS + portfolio.getELearner().getId());
        Individual res = ontModel.getIndividual(Constant.NS + portfolio.getEResource().getRid());
        Individual port = ontModel.createIndividual(Constant.NS + portfolio.getId(), ontModel.getResource(Constant.NS + "E_Portfolio"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "has_portfolio"), port);
        ontModel.add(res, ontModel.getProperty(Constant.NS + "inverse_of_is_resource_of_P"), port);
//        ontModel.addLiteral(port, ontModel.getProperty(Constant.NS + "value"), portfolio.getValue());
        port.addLiteral(ontModel.getProperty(Constant.NS + "value"), portfolio.getValue());
        if (portfolio.getDatetime() != null) {
            ontModel.add(port, ontModel.getProperty(Constant.NS + "date_time"), StringExchanger.parseDateToString(portfolio.getDatetime()), new XSDDatatype("dateTime"));
        }
        return true;
    }

    @Override
    public boolean addEConcept(EConcept concept) {
        if (containEConcept(concept.getCid())) {
            System.out.println("EConcept " + concept.getCid() + "  has already existed in the model");
            return false;
//            throw new IndividualExistException("EConcept " + concept.getCid() + "  has already existed in the model");
        }
        Individual con = ontModel.createIndividual(Constant.NS + concept.getCid(), ontModel.getResource(Constant.NS + "E_Concept"));
        ontModel.add(con, ontModel.getProperty(Constant.NS + "id"), concept.getCid());
        XSDDatatype stringType = new XSDDatatype("string");
        ontModel.add(con, ontModel.getProperty(Constant.NS + "name"), concept.getName(), stringType);
        ontModel.add(con, ontModel.getProperty(Constant.NS + "difficulty"), concept.getDifficulty(), stringType);
        return true;
    }

    @Override
    public boolean addEInterest(EInterest interest) {
        if (containEInterest(interest.getId())) {
            System.out.println("EInterest " + interest.getId() + " already exist in the model");
            return false;
//            throw new IndividualExistException("EInterest " + interest.getId() + " already exist in the model");
        }
        ELearner el = interest.getELearner();
        EConcept con = interest.getEConcept();
        Resource in = ontModel.createResource(Constant.NS + interest.getId(), ontModel.getResource(Constant.NS + "E_Interest"));
        in.addProperty(ontModel.getProperty(Constant.NS + "inverse_of_has_interest"), ontModel.getResource(Constant.NS + el.getId()));
        in.addProperty(ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_I"), ontModel.getResource(Constant.NS + con.getCid()));
        in.addLiteral(ontModel.getProperty(Constant.NS + "value"), interest.getValue());
        return true;
    }

    @Override
    public boolean addEPerfomance(EPerformance performance) throws IndividualExistException {
        if (containEPerformance(performance.getId())) {
            throw new IndividualExistException("EPerformance " + performance.getId() + " has already existed in the model");
        }
        Individual el = ontModel.getIndividual(Constant.NS + performance.getELearner().getId());
        Individual con = ontModel.getIndividual(Constant.NS + performance.getEConcept().getCid());
        Individual perf = ontModel.createIndividual(Constant.NS + performance.getId(), ontModel.getOntClass(Constant.NS + "E_Performance"));
        perf.addProperty(ontModel.getProperty(Constant.NS + "inverse_of_has_performance"), el);
        perf.addProperty(ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_P"), con);
        perf.addLiteral(ontModel.getProperty(Constant.NS + "value"), performance.getValue());
        ontModel.add(el, ontModel.getProperty(Constant.NS + "has_performance"), perf);
        ontModel.add(con, ontModel.getProperty(Constant.NS + "is_concept_of_P"), perf);
        if (performance.getDatetime() != null) {
            ontModel.add(perf, ontModel.getProperty(Constant.NS + "date_time"), StringExchanger.parseDateToString(performance.getDatetime()), new XSDDatatype("dateTime"));
        }
        return true;
    }

    public boolean addEGoal(EGoal goal) {
        if (containEGoal(goal.getGid())) {
            System.out.println("the goal has already existed");
            return false;
            //  throw new IndividualExistException("the goal has already existed");
        }
        Individual goalIndi = ontModel.createIndividual(Constant.NS + goal.getGid(), ontModel.getOntClass(Constant.NS + "E_Goal"));
        ontModel.add(goalIndi, ontModel.getProperty(Constant.NS + "name"), goal.getName());
        return true;
    }

    @Override
    //non-transitive function
    public boolean addPropertyIsSonOf(EConcept fatherConcept, EConcept sonConcept) throws IndividualNotExistException {
        if (!containEConcept(fatherConcept.getCid())) {
            throw new IndividualNotExistException("father EConcept " + fatherConcept.getCid() + " does not exist in the model");
        }
        if (!containEConcept(sonConcept.getCid())) {
            throw new IndividualNotExistException("son EConcept " + sonConcept.getCid() + " does not exist in the model");
        }
        Resource son = ontModel.getResource(Constant.NS + sonConcept.getCid());
        Resource father = ontModel.getResource(Constant.NS + fatherConcept.getCid());
        ontModel.add(son, ontModel.getProperty(Constant.NS + "is_son_of"), father);
        return true;
    }

    public boolean addPropertyIgnoreEConcepts(ELearner el, EConcept con) {
        if (!containEConcept(el.getId())) {
            return false;
        }
        if (!containEConcept(con.getCid())) {
            return false;
        }
        Individual indiEl = ontModel.getIndividual(Constant.NS + el.getId());
        Individual indiCon = ontModel.getIndividual(Constant.NS + con.getCid());
        ontModel.add(indiEl, ontModel.getObjectProperty(Constant.NS + "ignore_concepts"), indiCon);
        return true;
    }
    //transitive function

    public boolean addPropertyIsPartOf(EConcept fatherConcept, EConcept sonConcept) throws IndividualNotExistException {
        if (!containEConcept(fatherConcept.getCid())) {
            throw new IndividualNotExistException("father EConcept " + fatherConcept.getCid() + " does not exist in the model");
        }
        if (!containEConcept(sonConcept.getCid())) {
            throw new IndividualNotExistException("son EConcept " + sonConcept.getCid() + " does not exist in the model");
        }
        Resource son = ontModel.getResource(Constant.NS + sonConcept.getCid());
        Resource father = ontModel.getResource(Constant.NS + fatherConcept.getCid());
        ontModel.add(son, ontModel.getProperty(Constant.NS + "is_part_of"), father);
        return true;
    }

    @Override
    public boolean addPropertyIsResourceOfC(ISCB_Resource resource, EConcept concept) {
        if (!containEResource(resource.getRid())) {
            // throw new IndividualNotExistException("ISCB_Resource " + resource.getRid() + " does not exist in the model");
            return false;
        }
        if (!containEConcept(concept.getCid())) {
            // throw new IndividualNotExistException("EConcept " + concept.getCid() + " does not exist in the model");
            return false;
        }
        Individual res = ontModel.getIndividual(Constant.NS + resource.getRid());
        Individual con = ontModel.getIndividual(Constant.NS + concept.getCid());
        ontModel.add(res, ontModel.getProperty(Constant.NS + "is_resource_of_C"), con);
        ontModel.add(con, ontModel.getProperty(Constant.NS + "inverse_of_is_resource_of_C"), res);
        return true;
    }

    public boolean addPropertyContainsConcept(EGoal goal, EConcept con) {
        if (!containEGoal(goal.getGid())) {
            return false;
        }
        if (!containEConcept(con.getCid())) {
            return false;
        }
        Individual g = ontModel.getIndividual(Constant.NS + goal.getGid());
        Individual c = ontModel.getIndividual(Constant.NS + con.getCid());
        ontModel.add(g, ontModel.getProperty(Constant.NS + "contain_concepts"), c);
        return true;
    }

    public boolean removePropertyIsResourceOfC(ISCB_Resource resource, EConcept concept) {
        if (!containEResource(resource.getRid())) {
            return false;
            // throw new IndividualNotExistException("ISCB_Resource " + resource.getRid() + " does not exist in the model");
        }
        if (!containEConcept(concept.getCid())) {
            return false;
            //  throw new IndividualNotExistException("EConcept " + concept.getCid() + " does not exist in the model");
        }
        Individual res = ontModel.getIndividual(Constant.NS + resource.getRid());
        Individual con = ontModel.getIndividual(Constant.NS + concept.getCid());
        ontModel.remove(res, ontModel.getProperty(Constant.NS + "is_resource_of_C"), con);
        ontModel.remove(con, ontModel.getProperty(Constant.NS + "inverse_of_is_resource_of_C"), res);
        return true;
    }

    public boolean removePropertyContainsConcept(EGoal goal, EConcept concept) {
        if (!containEGoal(goal.getGid())) {
            return false;
        }
        if (!containEConcept(concept.getCid())) {
            return false;
        }
        Individual g = ontModel.getIndividual(Constant.NS + goal.getGid());
        Individual con = ontModel.getIndividual(Constant.NS + concept.getCid());
        ontModel.remove(g, ontModel.getProperty(Constant.NS + "contain_concepts"), con);
        return true;
    }

    public void addPropertyIsLeafConcept(EConcept concept, boolean isLeaf) throws IndividualNotExistException {
        if (!containEConcept(concept.getCid())) {
            throw new IndividualNotExistException("EConcept " + concept.getCid() + " does not exist in the model");
        }
        Individual con = ontModel.getIndividual(Constant.NS + concept.getCid());
        Statement ls = ontModel.createLiteralStatement(con, ontModel.getProperty(Constant.NS + "is_leaf_concept"), isLeaf);
        ontModel.add(ls);
    }

    /*******************************************************************************************
     * UPDATE operations
     *****************************************************************************************/
    @Override
    public boolean updateEConcept(EConcept concept) throws IndividualNotExistException {
        if (!containEConcept(concept.getCid())) {
            throw new IndividualNotExistException("EConcept " + concept.getCid() + "  does not exist");
        }
        Individual indi = ontModel.getIndividual(Constant.NS + concept.getCid());
        Property p = ontModel.getProperty(Constant.NS + "name");
        Property p2 = ontModel.getDatatypeProperty(Constant.NS + "difficulty");
        ontModel.remove(indi, p, indi.getPropertyValue(p));
        ontModel.add(indi, p, concept.getName(), new XSDDatatype("string"));
        ontModel.remove(indi, p2, indi.getPropertyValue(p));
        ontModel.add(indi, p2, concept.getDifficulty(), new XSDDatatype("string"));
        return true;
    }

    @Override
    public boolean updateELearner(ELearner elearner) throws IndividualNotExistException {
        if (!(containELearner(elearner.getId()))) {
            throw new IndividualNotExistException("elearner " + elearner.getId() + " does not exist");
        }
        ELearner el = getELearner(elearner.getId());
        Individual indi = ontModel.getIndividual(Constant.NS + elearner.getId());
        if (!elearner.getName().equals(el.getName())) {
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

    public boolean updateEGoal(EGoal goal) {
        if (!containEGoal(goal.getGid())) {
            return false;
        }
        EGoal g = getEGoal(goal.getGid());
        Individual indi = ontModel.getIndividual(Constant.NS + goal.getGid());
        if (!g.getName().equals(goal.getName())) {
            Property p = ontModel.getProperty(Constant.NS + "name");
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, goal.getName(), new XSDDatatype("string"));
        }
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
    public boolean updateEPerfomance(EPerformance performance) throws IndividualNotExistException {
        if (!containEPerformance(performance.getId())) {
            throw new IndividualNotExistException("EPerformance " + performance.getId() + " does not exist");
        }
        Individual indi = ontModel.getIndividual(Constant.NS + performance.getId());

        float newValue = performance.getValue();
        Property p = ontModel.getProperty(Constant.NS + "value");
        indi.setPropertyValue(p, ontModel.createTypedLiteral(newValue, new XSDDatatype("float")));
        return true;
    }

    public boolean updateEPerformanceAssessment(EPerformance performance, EPerformanceAssessment assessment) throws IndividualNotExistException {
        if (!containEPerformance(performance.getId())) {
            throw new IndividualNotExistException("EPerformance " + performance.getId() + " does not exist");
        }
        Individual indi = ontModel.getIndividual(Constant.NS + performance.getId());
        Property p;
        p = ontModel.getProperty(Constant.NS + "a1");
        indi.setPropertyValue(p, ontModel.createTypedLiteral(assessment.a1, new XSDDatatype("string")));
        p = ontModel.getProperty(Constant.NS + "a2");
        indi.setPropertyValue(p, ontModel.createTypedLiteral(assessment.a2, new XSDDatatype("string")));
        p = ontModel.getProperty(Constant.NS + "a3");
        indi.setPropertyValue(p, ontModel.createTypedLiteral(assessment.a3, new XSDDatatype("string")));
        p = ontModel.getProperty(Constant.NS + "a4");
        indi.setPropertyValue(p, ontModel.createTypedLiteral(assessment.a4, new XSDDatatype("string")));
        p = ontModel.getProperty(Constant.NS + "a5");
        indi.setPropertyValue(p, ontModel.createTypedLiteral(assessment.a5, new XSDDatatype("string")));
        p = ontModel.getProperty(Constant.NS + "a6");
        indi.setPropertyValue(p, ontModel.createTypedLiteral(assessment.a6, new XSDDatatype("string")));

        return true;
    }

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

        Property p2 = ontModel.getProperty(Constant.NS + "rate");
        ontModel.remove(indi, p2, indi.getPropertyValue(p2));
        ontModel.add(indi, p2, String.valueOf(portfolio.getRate()), new XSDDatatype("int"));

        Property p3 = ontModel.getProperty(Constant.NS + "rateString");
        ontModel.remove(indi, p3, indi.getPropertyValue(p3));
        ontModel.add(indi, p3, String.valueOf(portfolio.getRateString()), new XSDDatatype("string"));
        System.out.println(portfolio.getId() + "has been updated\t" + portfolio);
        return true;
    }

    @Override
    public boolean updateEResource(ISCB_Resource resource) throws IndividualNotExistException {
        if (!(containEResource(resource.getRid()))) {
            throw new IndividualNotExistException("EResource " + resource.getRid() + " does not exist");
        }
        Individual indi = ontModel.getIndividual(Constant.NS + resource.getRid());
        Property p = ontModel.getProperty(Constant.NS + "name");
        String name = indi.getRequiredProperty(p).getLiteral().getString();
        if (!resource.getName().equals(name)) {
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getName(), new XSDDatatype("string"));
        }
        p = ontModel.getProperty(Constant.NS + "description");
        String description = indi.getRequiredProperty(p).getLiteral().getString();
        if (!resource.getResourceDescription().equals(description)) {
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getResourceDescription(), new XSDDatatype("string"));
        }
//        p = ontModel.getProperty(Constant.NS + "resource_quality");
//        String resource_quality = indi.getRequiredProperty(p).getLiteral().getString();
//        if (!resource.getResourceQuality().equals(resource_quality)) {
//            ontModel.remove(indi, p, indi.getPropertyValue(p));
//            ontModel.add(indi, p, resource.getResourceQuality(), new XSDDatatype("string"));
//        }
        p = ontModel.getProperty(Constant.NS + "resource_type");
        String resource_type = indi.getRequiredProperty(p).getLiteral().getString();
        if (!resource.getResourceType().equals(resource_type)) {
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getResourceType(), new XSDDatatype("string"));
        }
        p = ontModel.getProperty(Constant.NS + "application_type");
        String app_type = indi.getRequiredProperty(p).getLiteral().getString();
        if (!resource.getAppType().equals(app_type)) {
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getAppType(), new XSDDatatype("string"));
        }
        //upload time不应该随更新而改变
//        p = ontModel.getProperty(Constant.NS + "upload_time");
//        String upload_time = indi.getRequiredProperty(p).getLiteral().getString();
//        Date time = StringExchanger.parseStringToDate(upload_time);
//        if (!resource.getUploadTime().equals(time)) {
//            ontModel.remove(indi, p, indi.getPropertyValue(p));
//            ontModel.add(indi, p, StringExchanger.parseDateToString(resource.getUploadTime()), new XSDDatatype("date"));
//        }
        p = ontModel.getProperty(Constant.NS + "file_location");
        String fileLocation = indi.getRequiredProperty(p).getLiteral().getString();
        if (!resource.getFileLocation().equals(fileLocation)) {
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getFileLocation(), new XSDDatatype("string"));
        }

        p = ontModel.getProperty(Constant.NS + "difficulty");
        String difficulty = indi.getRequiredProperty(p).getLiteral().getString();
        if (!resource.getDifficulty().equals(difficulty)) {
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getDifficulty(), new XSDDatatype("string"));
        }
//        p = ontModel.getProperty(Constant.NS + "has_postfix");
//        Resource r = indi.getPropertyResourceValue(p);
//        String postfix = r.getRequiredProperty(ontModel.getProperty(Constant.NS + "file_postfix")).getLiteral().getString();
//        String newPostfixString = resource.getPostfix();
//        Individual newPostfix = getFileFormat(newPostfixString);
//        if (newPostfix != null) {
//            if (!newPostfixString.equals(postfix)) {
//                ontModel.remove(indi, p, indi.getPropertyResourceValue(p));
//                ontModel.add(ontModel.createStatement(indi, p, newPostfix));
//            }
//        }
        return true;
    }

    /***************************************************************************************
     * REMOVE operations
     ***************************************************************************************/
    @Override
    public boolean removeEConcept(String cid) {
        if (!containEConcept(cid)) {
            return false;
        }
        Individual indi = ontModel.getIndividual(Constant.NS + cid);
        indi.remove();
        return true;
    }

    public boolean removeEResource(String rid) {
        if (!containEConcept(rid)) {
            return false;
        }
        Individual indi = ontModel.getIndividual(Constant.NS + rid);
        indi.remove();
        return true;
    }

    public boolean removeEGoal(String gid) {
        if (!containEGoal(gid)) {
            return false;
        }
        Individual indi = ontModel.getIndividual(Constant.NS + gid);
        indi.remove();
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

    /****************************************************************************************************************************************/
    @Override
    public String getPostFix(Resource fileFormat) {
        Property p = ontModel.getProperty(Constant.NS + "file_postfix");
        return fileFormat.getRequiredProperty(p).getLiteral().getString();
    }

    @Override
    public Individual getFileFormat(String postfix) {
        OntClass concept = ontModel.getOntClass(Constant.NS + "File_Format");
        java.util.Iterator<Individual> iter = ontModel.listIndividuals(concept);
        while (iter.hasNext()) {
            Individual indi = (Individual) iter.next();
            String s = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "file_postfix")).getLiteral().getString();
            if (postfix.equals(s)) {
                return indi;
            }
        }
        return null;
    }

    @Override
    public EConcept getEConcept(String cid) {
        Individual indi = ontModel.getIndividual(Constant.NS + cid);
        String name = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString();
        EConcept con = new EConcept(cid, name);
        RDFNode difficulty = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "difficulty"));
        if (difficulty == null) {
            con.setDifficulty("diff");
        } else {
            con.setDifficulty(difficulty.asLiteral().getString());
        }
        return con;
    }

    public HashSet<EConcept> getPreEConcept(String cid) {
        Individual indi = ontModel.getIndividual(Constant.NS + cid);
        Property p = ontModel.getObjectProperty(Constant.NS + "is_post_concept_of");
        HashSet<EConcept> cons = new HashSet<EConcept>();
        StmtIterator iter = indi.listProperties(p);
        while (iter.hasNext()) {
            Resource r = (Resource) iter.next().getObject();
            cons.add(getEConcept(r.getLocalName()));
        }
        return cons;
    }

    public HashSet<EConcept> getPostEConcept(String cid) {
        Individual indi = ontModel.getIndividual(Constant.NS + cid);
        Property p = ontModel.getObjectProperty(Constant.NS + "is_pre_concept_of");
        HashSet<EConcept> cons = new HashSet<EConcept>();
        StmtIterator iter = indi.listProperties(p);
        while (iter.hasNext()) {
            Resource r = (Resource) iter.next().getObject();
            cons.add(getEConcept(r.getLocalName()));
        }
        return cons;
    }

    public HashSet<EConcept> getLearntEConcept(ELearner el) {
        HashSet<EConcept> cons = new HashSet<EConcept>();
        ArrayList<EPerformance> ps = getEPerformances(el);
        for (EPerformance p : ps) {
            cons.add(p.getEConcept());
        }
        return cons;
    }

    @Override
    public ELearner getELearner(String eid) {
        ELearner elearner = new ELearner(eid);
        Individual indi = ontModel.getIndividual(Constant.NS + eid);
        if (indi == null) {
            return null;
        }
        RDFNode nameNode = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "name"));
        if (nameNode == null) {
            elearner.setEmail(" ");
        } else {
            elearner.setName(nameNode.asLiteral().getString());
        }

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
    public ISCB_Resource getEResource(String rid) {
        ISCB_Resource resource = new ISCB_Resource(rid);
        Individual indi = ontModel.getIndividual(Constant.NS + rid);
        resource.setName(indi.getPropertyValue(ontModel.getProperty(Constant.NS + "name")).asLiteral().getString());
        RDFNode fl = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "file_location"));
        if (fl != null) {
            resource.setFileLocation(fl.asLiteral().getString());
        } else {
            resource.setFileLocation("");
        }
        RDFNode diff = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "difficulty"));
        if (diff != null) {
            resource.setDifficulty(diff.asLiteral().getString());
        } else {
            resource.setDifficulty("");
        }
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

        Resource appType = indi.getPropertyResourceValue(ontModel.getProperty(Constant.NS + "application_type"));
        if (appType != null) {
            resource.setAppType(appType.asLiteral().getString());
        } else {
            resource.setAppType("");
        }
        return resource;
    }

    @Override
    public EPerformance getEPerformance(String pid) {
        Individual indi = ontModel.getIndividual(Constant.NS + pid);
        if (indi == null) {
            return null;
        }
        EPerformance performance = new EPerformance();
        performance.setId(indi.getLocalName());
        if (indi.hasProperty(ontModel.getProperty(Constant.NS + "date_time"))) {
            Statement dateNode = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "date_time"));
            if (dateNode != null) {
                String dateString = dateNode.getLiteral().getString();
                Date datetime = StringExchanger.parseStringToDate(dateString);
                performance.setDatetime(datetime);
            }
        }
        EPerformanceAssessment ass = new EPerformanceAssessment();
        RDFNode a1Node = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "a1"));
        if (a1Node != null) {
            ass.a1 = a1Node.asLiteral().getString();
        }
        RDFNode a2Node = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "a2"));
        if (a2Node != null) {
            ass.a2 = a2Node.asLiteral().getString();
        }
        RDFNode a3Node = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "a3"));
        if (a3Node != null) {
            ass.a3 = a3Node.asLiteral().getString();
        }
        RDFNode a4Node = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "a4"));
        if (a4Node != null) {
            ass.a4 = a4Node.asLiteral().getString();
        }
        RDFNode a5Node = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "a5"));
        if (a5Node != null) {
            ass.a5 = a5Node.asLiteral().getString();
        }
        RDFNode a6Node = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "a6"));
        if (a6Node != null) {
            ass.a6 = a6Node.asLiteral().getString();
        }
        performance.assessment = ass;
//        System.out.println("assessment:" + ass);

        Statement valueNode = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "value"));
        if (valueNode != null) {
            performance.setValue(ass.getValue());
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
        port.setId(pid);
        Statement valueNode = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "value"));
        if (valueNode != null) {
            float value = Float.valueOf(valueNode.getLiteral().getString());
            port.setValue(value);
        }
        Statement dateNode = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "date_time"));
        if (dateNode != null) {
            String dateString = dateNode.getLiteral().getString();
            Date datetime = StringExchanger.parseStringToDate(dateString);
            port.setDatetime(datetime);
        }
        Statement rateNode = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "rate"));
        if (rateNode != null) {
            int rateNodeInt = rateNode.getLiteral().getInt();
            port.setRate(rateNodeInt);
        } else {
            port.setRate(0);
        }
        Statement rateStringNode = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "rateString"));
        if (rateStringNode != null) {
            String rateStringNodeString = rateStringNode.getLiteral().getString();
            port.setRateString(rateStringNodeString);
        } else {
            port.setRateString("");
        }

        return port;
    }

    /**************************************************************************************************
     * AdminOperation 管理员权限
     **************************************************************************************************/
    public void changeConcept(ISCB_Resource res, EConcept from, EConcept to) {
        Individual resIndi = ontModel.getIndividual(Constant.NS + res.getRid());
        //    Individual fromI
    }

    public ISCB_Resource getEResourceByLocation(String location) {
        Iterator<Individual> iter = ontModel.listIndividuals(ontModel.getOntClass(Constant.NS + "ISCB_Resource"));
        DatatypeProperty dp = ontModel.getDatatypeProperty(Constant.NS + "file_location");
        while (iter.hasNext()) {
            Individual indi = (Individual) iter.next();
            if (indi.hasProperty(dp)) {
                Statement locationNode = indi.getRequiredProperty(dp);
                if (locationNode != null) {
                    String locationString = locationNode.getLiteral().getString();
                    if (locationString.equals(location)) {
                        ISCB_Resource res = getEResource(indi.getLocalName());
                        return res;
                    }
                }
            }

        }
        return null;
    }

    public boolean isSonOfEConcept(EConcept father, EConcept son) {
        Property p = ontModel.getProperty(Constant.NS + "is_son_of");
        Individual f = ontModel.getIndividual(Constant.NS + father.getCid());
        Individual s = ontModel.getIndividual(Constant.NS + son.getCid());
        SimpleSelector selector = new SimpleSelector(s, p, f);
        StmtIterator iter = ontModel.listStatements(selector);
        if (iter.hasNext()) {
            return true;
        }
        return false;
    }

    public boolean isPartOfEConcept(EConcept father, EConcept son) {
        Property p = ontModel.getProperty(Constant.NS + "inverse_of_is_part_of");
        Individual f = ontModel.getIndividual(Constant.NS + father.getCid());
        Resource s = ontModel.getIndividual(Constant.NS + son.getCid());
        while (true) {
            SimpleSelector selector = new SimpleSelector(null, p, s);
            StmtIterator iter = ontModel.listStatements(selector);
            if (iter.hasNext()) {
                Resource r = iter.nextStatement().getSubject();
                if (r.getLocalName().equals(f.getLocalName())) {
                    return true;
                } else {
                    if (r.getLocalName().equals(getRootConcept().getCid())) {
                        break;
                    }
                }
                s = r;
            }
        }
        return false;
    }

    @Override
    public Individual getFileFormatIndividualByFullName(String name) {
        Resource fileFormatClass = ontModel.getResource(Constant.NS + "File_Format");
        SimpleSelector selector = new SimpleSelector(null, RDF.type, fileFormatClass);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource r = iter.nextStatement().getSubject();
            String n = r.getProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString();
            if (n.equals(name)) {
                return ontModel.getIndividual(Constant.NS + r.getLocalName());
            }
        }
        return null;
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
    public HashSet<EConcept> getAllLeafEConcepts() {
        HashSet<EConcept> concepts = new HashSet<EConcept>();
        OntClass concept = ontModel.getOntClass(Constant.NS + "E_Concept");
        Iterator<Individual> iter = ontModel.listIndividuals(concept);
        while (iter.hasNext()) {
            Individual indi = (Individual) iter.next();
            RDFNode b = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "is_leaf_concept"));
            if (b == null) {
                try {
                    throw new HasNoPropertyValueException("the concept " + indi.getLocalName() + " has no value of the property is_leaf_concept");
                } catch (HasNoPropertyValueException ex) {
                    Logger.getLogger(ELearnerModelImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (b.asLiteral().getBoolean()) {
                    concepts.add(getEConcept(indi.getLocalName()));
                }
            }

        }
        return concepts;
    }

    public Set<EConcept> getAllBranchEConcepts() {
        Set<EConcept> concepts = new HashSet<EConcept>();
        OntClass concept = ontModel.getOntClass(Constant.NS + "E_Concept");
        Iterator<Individual> iter = ontModel.listIndividuals(concept);
        while (iter.hasNext()) {
            Individual indi = (Individual) iter.next();
            RDFNode b = indi.getPropertyValue(ontModel.getProperty(Constant.NS + "is_leaf_concept"));
            if (b == null) {
                try {
                    throw new HasNoPropertyValueException("the concept " + indi.getLocalName() + " has no value of the property is_leaf_concept");
                } catch (HasNoPropertyValueException ex) {
                    Logger.getLogger(ELearnerModelImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (!b.asLiteral().getBoolean()) {
                    concepts.add(getEConcept(indi.getLocalName()));
                }
            }

        }
        return concepts;
    }

    @Override
    public HashSet<ISCB_Resource> getAllEResources() {
        Resource resourceClass = ontModel.getResource(Constant.NS + "ISCB_Resource");
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NSRDF + "type"), resourceClass);
        StmtIterator iter = ontModel.listStatements(selector);
        HashSet<ISCB_Resource> resources = new HashSet<ISCB_Resource>();
        while (iter.hasNext()) {
            Statement s = iter.nextStatement();
            Resource r = s.getSubject();
            ISCB_Resource er = getEResource(r.getLocalName());
            resources.add(er);
        }
        return resources;
    }

    public HashSet<ISCB_Resource> getAllEReousrcesWithLeafConcept() {
        Resource resourceClass = ontModel.getResource(Constant.NS + "ISCB_Resource");
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NSRDF + "type"), resourceClass);
        StmtIterator iter = ontModel.listStatements(selector);
        HashSet<ISCB_Resource> resources = new HashSet<ISCB_Resource>();
        while (iter.hasNext()) {
            Statement s = iter.nextStatement();
            Resource r = s.getSubject();
            ISCB_Resource er = getEResource(r.getLocalName());
            HashSet<EConcept> cs = getLeafConcepts(er);
            if (!cs.isEmpty()) {
                resources.add(er);
            }
        }
        return resources;
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
            if (r != null) {
                SimpleSelector selector_con = new SimpleSelector(r, ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_P"), con);
                StmtIterator iter_con = ontModel.listStatements(selector_con);
                while (iter_con.hasNext()) {
                    EPerformance performance = getEPerformance(r.getLocalName());
                    performance.setELearner(elearner);
                    performance.setEConcept(concept);
                    return performance;
                }
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
                performance.setELearner(elearner);
                performance.setEConcept(concept);
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
                portfolio.setELearner(elearner);
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
            port.setELearner(elearner);
            portfolios.add(port);
        }
        return portfolios;
    }

    @Override
    public ArrayList<ISCB_Resource> getEResourcesByName(String name) {
//         elearner.getName(), new XSDDatatype("string")
        ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
        OntClass classRes = ontModel.getOntClass(Constant.NS + "ISCB_Resource");

        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "name"), name);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource res = iter.nextStatement().getSubject();
            String id = res.getLocalName();
            if (ontModel.contains(ontModel.createStatement(res, RDF.type, classRes))) {
                System.out.println("id2:" + id);
                resources.add(getEResource(id));
            }
//            System.out.println("id1:" + id);
        }
        return resources;
    }

    @Override
    public ArrayList<ISCB_Resource> getLearntEResources(ELearner elearner, EConcept concept) {
        ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
        Individual el = ontModel.getIndividual(Constant.NS + elearner.getId());
        Individual con = ontModel.getIndividual(Constant.NS + concept.getCid());
        Property p1 = ontModel.getProperty(Constant.NS + "has_portfolio");
        Property p2 = ontModel.getProperty(Constant.NS + "is_resource_of_P");
        Property p3 = ontModel.getProperty(Constant.NS + "is_resource_of_C");
        SimpleSelector selector_port = new SimpleSelector(el, p1, (RDFNode) null);
        StmtIterator iter_port = ontModel.listStatements(selector_port);
        while (iter_port.hasNext()) {
            Resource port = (Resource) iter_port.nextStatement().getObject();
            SimpleSelector selector_res = new SimpleSelector(null, p2, port);
            StmtIterator iter_res = ontModel.listStatements(selector_res);
            while (iter_res.hasNext()) {
                Resource res = iter_res.nextStatement().getSubject();
                SimpleSelector selector_con = new SimpleSelector(res, p3, con);
                StmtIterator iter_con = ontModel.listStatements(selector_con);
                if (iter_con.hasNext()) {
                    resources.add(getEResource(res.getLocalName()));
                }
            }
        }
        return resources;
    }

    @Override
    public ArrayList<ISCB_Resource> getEResourcesByTypes(String applicationType, String fileFormat, String mediaType) {
        ArrayList<ISCB_Resource> r1 = new ArrayList<ISCB_Resource>();
        ArrayList<ISCB_Resource> r2 = new ArrayList<ISCB_Resource>();
        ArrayList<ISCB_Resource> r3 = new ArrayList<ISCB_Resource>();
        if (applicationType == null) {
            System.out.println("applicationType is null");
        }
        if (mediaType == null) {
            System.out.println("mediaType is null");
        }
        r1 = getEResourcesByAppType(applicationType);
        r3 = getEResourcesByMeidaType(mediaType);
        System.out.println("r1:" + r1.size());
        System.out.println("r3:" + r3.size());
        if (r1.isEmpty()) {
            return r3;
        } else if (r3.isEmpty()) {
            return r1;
        } else {
            ArrayList<ISCB_Resource> in = new ArrayList<ISCB_Resource>();
            for (ISCB_Resource res : r1) {
                if (r3.contains(res)) {
                    in.add(res);
                }
            }
            return in;
        }
    }

    @Override
    public ArrayList<ISCB_Resource> getEResourcesByAppType(String applicationType) {
        ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
        OntClass classRes = ontModel.getOntClass(Constant.NS + "ISCB_Resource");
        SimpleSelector selector = new SimpleSelector(null, RDF.type, classRes);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource res = iter.nextStatement().getSubject();
            Statement s = res.getProperty(ontModel.getProperty(Constant.NS + "application_type"));
            if (s != null) {
                if (s.getLiteral().getString().equals(applicationType) || applicationType.equals("全部")) {
                    String id = res.getLocalName();
                    resources.add(getEResource(res.getLocalName()));
                }
            } else {
                System.out.println("the resource application type is null\t" + res.getLocalName());
            }
        }
        return resources;
    }

    @Override
    public ArrayList<ISCB_Resource> getEResourcesByMeidaType(String mediaType) {
        ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
        OntClass classRes = ontModel.getOntClass(Constant.NS + "ISCB_Resource");
        SimpleSelector selector = new SimpleSelector(null, RDF.type, classRes);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource res = iter.nextStatement().getSubject();
            Statement s = res.getProperty(ontModel.getProperty(Constant.NS + "resource_type"));
            if (s != null) {
                if (s.getLiteral().getString().equals(mediaType) || mediaType.equals("全部")) {
                    String id = res.getLocalName();
                    resources.add(getEResource(res.getLocalName()));
                }
            } else {
                System.out.println("the resource media type is null\t" + res.getLocalName());
            }
        }
        return resources;
    }

    @Override
    public ArrayList<ISCB_Resource> getEResourcesByFileFormat(String fileFormat) {
        Individual fileFormatIndi = getFileFormatIndividualByFullName(fileFormat);
        ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "has_postfix"), fileFormatIndi);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource res = iter.nextStatement().getSubject();
            if (res != null) {
                resources.add(getEResource(res.getLocalName()));
            } else {
                System.out.println("the resource is null\t");
            }
        }
        return resources;
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
    public HashSet<EConcept> getEConcepts(ISCB_Resource resource) {
        HashSet<EConcept> concepts = new HashSet<EConcept>();
        Individual res = ontModel.getIndividual(Constant.NS + resource.getRid());
        SimpleSelector selector = new SimpleSelector(res, ontModel.getProperty(Constant.NS + "is_resource_of_C"), (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource con = (Resource) iter.nextStatement().getObject();
            concepts.add(getEConcept(con.getLocalName()));
        }
        return concepts;
    }

    public HashSet<EConcept> getLeafConcepts(ISCB_Resource resource) {
        HashSet<EConcept> concepts = new HashSet<EConcept>();
        Individual res = ontModel.getIndividual(Constant.NS + resource.getRid());
        SimpleSelector selector = new SimpleSelector(res, ontModel.getProperty(Constant.NS + "is_resource_of_C"), (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource con = (Resource) iter.nextStatement().getObject();
            EConcept c = getEConcept(con.getLocalName());
            if (!c.getDifficulty().equals("diff")) {
                concepts.add(c);
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

    @Override
    public ArrayList<EConcept> getMemberConcepts(EConcept concept) {
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

    @Override
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

    //return empty list if there is no concepts meet the need
    //else return the list of concept ids whoes concept name is the given name.
    public ArrayList<String> getConceptIds(String conceptName) {
        ArrayList<String> cids = new ArrayList<String>();
        Property p = ontModel.getDatatypeProperty(Constant.NS + "name");
        SimpleSelector selector = new SimpleSelector(null, p, conceptName);
        StmtIterator iter = ontModel.listStatements(selector);
        OntClass conceptClass = ontModel.getOntClass(Constant.NS + "E_Concept");

        while (iter.hasNext()) {
            Resource con = iter.nextStatement().getSubject();
            SimpleSelector selector_con = new SimpleSelector(con, RDF.type, conceptClass);
            StmtIterator iter_con = ontModel.listStatements(selector_con);
            if (iter_con.hasNext()) {
                cids.add(con.getLocalName());
                System.out.println("cids:" + cids);
            }
        }
        return cids;
    }

    public EGoal getEGoal(String id) {
        EGoal goal = new EGoal();
        goal.setGid(id);
        Individual g = ontModel.getIndividual(Constant.NS + id);
        if (g == null) {
            //the id doesn't exist
            return null;
        }
        DatatypeProperty nameProperty = ontModel.getDatatypeProperty(Constant.NS + "name");
        RDFNode nameRDFNode = g.getPropertyValue(nameProperty);
        String name = "";
        if (nameRDFNode == null) {
            //the name doesn't exist
            return null;
        } else {
            name = nameRDFNode.asLiteral().getString();
        }
        goal.setName(name);
        Property p = ontModel.getObjectProperty(Constant.NS + "contain_concepts");
        SimpleSelector selector = new SimpleSelector(g, p, (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        ArrayList<EConcept> cons = new ArrayList();
        while (iter.hasNext()) {
            Resource con = (Resource) iter.nextStatement().getObject();
            EConcept c = getEConcept(con.getLocalName());
            cons.add(c);
        }
        goal.setCons(cons);
        return goal;
    }

    //获得用户的多个目标（此方法暂时无用，因为目标只允许有一个）
    public ArrayList<EGoal> getGoalsByELearner(ELearner el) {
        Individual elIndi = ontModel.getIndividual(Constant.NS + el.getId());
        if (elIndi == null) {
            return null;
        }
        Property hasGoalProperty = ontModel.getObjectProperty(Constant.NS + "has_goal");
        SimpleSelector selector = new SimpleSelector(elIndi, hasGoalProperty, (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        ArrayList<EGoal> goals = new ArrayList();
        while (iter.hasNext()) {
            Resource r = (Resource) iter.nextStatement().getObject();
            EGoal g = getEGoal(r.getLocalName());
            goals.add(g);
        }
        return goals;
    }

    public ArrayList<EConcept> getIgnoreConceptsByELearner(ELearner el) {
        ArrayList<EConcept> cons = new ArrayList<EConcept>();
        Individual elIndi = ontModel.getIndividual(Constant.NS + el.getId());
        if (elIndi == null) {
            return null;
        }
        Property p = ontModel.getObjectProperty(Constant.NS + "ignore_concepts");
        SimpleSelector selector = new SimpleSelector(elIndi, p, (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource c = (Resource) iter.nextStatement().getObject();
            EConcept con = getEConcept(c.getLocalName());
            cons.add(con);
        }
        return cons;
    }
    //不考虑has_goal这个ObjectProperty

    public String getCurrentGoal(ELearner el) {
        String goalId = null;
        Individual elIndi = ontModel.getIndividual(Constant.NS + el.getId());
        if (elIndi == null) {
            return null;
        }
        RDFNode currentGoal = elIndi.getPropertyValue(ontModel.getDatatypeProperty(Constant.NS + "current_goal"));
        //return null if the elearner has no goal
        if (currentGoal == null) {
            return null;
        } else {
            goalId = currentGoal.asLiteral().getString();
        }
        return goalId;
    }

    public boolean setCurrentGoal(ELearner el, String goal) {
        Individual elIndi = ontModel.getIndividual(Constant.NS + el.getId());
        if (elIndi == null) {
            return false;
        }
        elIndi.setPropertyValue(ontModel.getDatatypeProperty(Constant.NS + "current_goal"), ontModel.createTypedLiteral(goal, new XSDDatatype("string")));
        return true;
    }

    //返回某个知识点的前后继知识点，并为每个知识点标识是否为用户所学过。
    public LinkedEConcept getLinkedConceptsByEConcept(ELearner el, EConcept con) {
        ArrayList<EConcept> cons = new ArrayList<EConcept>();
        LinkedEConcept lcon = new LinkedEConcept(con);
        Individual elIndi = ontModel.getIndividual(Constant.NS + el.getId());
        if (elIndi == null) {
            return null;
        }
        //学过的知识点
        ArrayList<EPerformance> performs = getEPerformances(el);
        if (ELearnerModelUtilMethod.isInPerformance(performs, con)) {
            lcon.setIsLearnt(true);
        }
        Individual conIndi = ontModel.getIndividual(Constant.NS + con.getCid());

        Property p = ontModel.getObjectProperty(Constant.NS + "is_pre_concept_of");
        SimpleSelector selector = new SimpleSelector(null, p, conIndi);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource c = iter.nextStatement().getSubject();
            EConcept cc = getEConcept(c.getLocalName());
            LinkedEConcept preC = new LinkedEConcept(cc);
            if (ELearnerModelUtilMethod.isInPerformance(performs, cc)) {
                preC.setIsLearnt(true);
            }
            lcon.addPre(preC);
            cons.add(cc);
        }
        p = ontModel.getObjectProperty(Constant.NS + "is_post_concept_of");
        selector = new SimpleSelector(null, p, conIndi);
        iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource c = iter.nextStatement().getSubject();
            EConcept cc = getEConcept(c.getLocalName());
            LinkedEConcept postC = new LinkedEConcept(cc);
            if (ELearnerModelUtilMethod.isInPerformance(performs, cc)) {
                postC.setIsLearnt(true);
            }
            lcon.addPost(postC);
            cons.add(cc);
        }
        return lcon;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //有待改进~~
    //
    public ArrayList<LinkedEConcept> getConceptsByELearnerGoal(ELearner el, EGoal goal) {
        ArrayList<LinkedEConcept> lcons = new ArrayList<LinkedEConcept>();
        Individual elIndi = ontModel.getIndividual(Constant.NS + el.getId());
        if (elIndi == null) {
            return null;
        }
        //学过的知识点
        ArrayList<EPerformance> performs = getEPerformances(el);
        //目标的知识点
        ArrayList<EConcept> gCons = goal.getCons();
        //创建LinkedConcept,把目标中的知识点标识是否学习过.
        for (EConcept con : gCons) {
            LinkedEConcept lcon = new LinkedEConcept(con);
            if (ELearnerModelUtilMethod.isInPerformance(performs, con)) {
                lcon.setIsLearnt(true);
            } else {
                lcon.setIsLearnt(false);
            }
            lcons.add(lcon);
        }
        //标识知识点之间关系
        for (LinkedEConcept lcon : lcons) {
        }
        return lcons;
    }

    public HashSet<EConcept> getEConceptsByGoal(EGoal goal) {
        HashSet<EConcept> cons = new HashSet<EConcept>();
        Individual goalIndi = ontModel.getIndividual(Constant.NS + goal.getGid());
        StmtIterator iter = goalIndi.listProperties(ontModel.getObjectProperty(Constant.NS + "contain_concepts"));
        while (iter.hasNext()) {
            Resource r = (Resource) iter.next().getObject();
            cons.add(getEConcept(r.getLocalName()));
        }
        return cons;
    }

    public ArrayList<EGoal> getAllEGoals() {
        ArrayList<EGoal> goals = new ArrayList<EGoal>();
        OntClass goal = ontModel.getOntClass(Constant.NS + "E_Goal");
        Iterator<Individual> iter = ontModel.listIndividuals(goal);
        while (iter.hasNext()) {
            Individual indi = (Individual) iter.next();
            goals.add(getEGoal(indi.getLocalName()));
        }
        return goals;
    }

    public ArrayList<EConcept> getRecommendConceptsByGoal(ELearner el, EGoal goal) {
        //学过的知识点
        ArrayList<EPerformance> performs = getEPerformances(el);
        //目标的知识点
        ArrayList<EConcept> gCons = goal.getCons();
        //用户忽略的知识点
        ArrayList<EConcept> ignoreCons = getIgnoreConceptsByELearner(el);
        //去除目标知识点中已经学习过的
        for (EPerformance p : performs) {
            EConcept con = p.getEConcept();
            if (gCons.contains(con)) {
                gCons.remove(con);
            }
        }
        //去除目标知识点中用户忽略的
        for (EConcept c : ignoreCons) {
            if (gCons.contains(c)) {
                gCons.remove(c);
            }
        }
//        System.out.println("gCons:" + gCons.size());
        return gCons;
    }

    public static void addProperty(String rid, String cid) {
        File owlFile = new File("files/owl/elearning_owl.owl");
        ELearnerModelImpl emi = new ELearnerModelImpl(owlFile);
        //emi.addNewModel(el005f);
        boolean b = emi.containELearner("el001");

        ISCB_Resource res = emi.getEResource(rid);
        EConcept con = emi.getEConcept(cid);
        emi.addPropertyIsResourceOfC(res, con);
        System.out.println("add " + res.getFileLocation() + "\t" + con.getCid() + "," + con.getName());
        emi.writeToFile(owlFile);

    }

    public static void search(String cs) {
        File owlFile = new File("files/owl/elearning_owl.owl");
        ELearnerModelImpl emi = new ELearnerModelImpl(owlFile);
        //emi.addNewModel(el005f);
        boolean b = emi.containELearner("el001");
        HashSet<ISCB_Resource> rs = emi.getAllEResources();
        // String cs = "110401.asp";

        for (ISCB_Resource res : rs) {
            String loca = res.getFileLocation();
            if (loca.contains(cs)) {
                System.out.println("got:" + res.getRid() + "\t" + res.getName());
            }
        }
    }

    public static void main(String[] args) throws IndividualNotExistException, IndividualExistException {
        //files/owl/elearning_owl.owl
        File f1 = new File("files/owl/elearning_owl_1.owl");
        File el001f = new File("files/owl/el001.owl");
        File el005f = new File("files/owl/el005.owl");
        File writeTo = new File("files/owl/update_write.xml");
        search("060501");
        String rid = "rid000149";
        String rid2 = "rid000148";
        String r3 = "rid000300";
        String cid = "A_cid_3_m_2";
        //addProperty(rid, cid);
        //addProperty(rid2, cid);
        //  addProperty(r3,cid);
        // OntModel model = OwlFactory.getOntModel(el001f, f1);

//        StmtIterator nl = indi1.listProperties();
//        int i = 0;
//        while (nl.hasNext()) {
//            Statement st = nl.next();
//            i++;
//        }
//        System.out.println(" i:" + i);

//        HashSet<EConcept> cons = emi.getAllLeafEConcepts();
//        EConcept father = emi.getEConcept("CMP.cf.2");
//
//        System.out.println("cons:" + cons.size());
//        for (EConcept con : cons) {
//            emi.addPropertyIsPartOf(father, con);
//            emi.addPropertyIsSonOf(father, con);
//
//        }
        //    emi.writeToFile(writeTo);

        System.out.println("end");
    }
}
