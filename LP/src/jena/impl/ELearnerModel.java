package jena.impl;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;
import util.Constant;
import util.StringExchanger;
import jena.OwlFactory;
import jena.interfaces.ELearnerModelOperationInterface;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import db.OwlOperation;
import exception.jena.IndividualExistException;
import exception.jena.IndividualNotExistException;
import java.util.Date;
import ontology.EPerformanceAssessment;

public class ELearnerModel implements ELearnerModelOperationInterface {

    protected OntModel ontModel;

    public ELearnerModel() {
        ontModel = OwlFactory.getOntModel();
    }

    public ELearnerModel(OntModel ontModel) {
        this.ontModel = ontModel;
    }

    public ELearnerModel(File file) {
        this.ontModel = OwlFactory.getOntModel(file);
    }

    public ELearnerModel(File file, String lang) {
        this.ontModel = OwlFactory.getOntModel(file, lang);
    }

    public boolean writeToFile(File file) {
        try {
            OwlOperation.writeOwlFile(ontModel, file);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ELearnerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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

    /*******************************************************************************************************
     * Add new Data Operations
     * @throws IndividualNotExistException 
     *******************************************************************************************************/
    @Override
    public boolean addELearner(ELearner elearner) throws IndividualExistException {
        if (containELearner(elearner.getId())) {
            throw new IndividualExistException("elearner " + elearner.getId() + " has already existed in the model");
        }
        Resource el = ontModel.createResource(Constant.NS + elearner.getId(), ontModel.getResource(Constant.NS + "E_Learner"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "id"), elearner.getId());
        ontModel.add(el, ontModel.getProperty(Constant.NS + "name"), elearner.getName(), new XSDDatatype("string"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "gender"), elearner.getGender(), new XSDDatatype("string"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "grade"), elearner.getGrade(), new XSDDatatype("string"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "address"), elearner.getAddress(), new XSDDatatype("string"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "email"), elearner.getEmail(), new XSDDatatype("string"));
        return true;
    }

    @Override
    public boolean addEResource(ISCB_Resource resource) throws IndividualExistException {
        if (containEResource(resource.getRid())) {
            throw new IndividualExistException("EResource " + resource.getRid() + " has already existed in the model");
        }
        Resource re = ontModel.createResource(Constant.NS + resource.getRid(), ontModel.getResource(Constant.NS + "ISCB_Resource"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "id"), resource.getRid());
        ontModel.add(re, ontModel.getProperty(Constant.NS + "name"), resource.getName(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "description"), resource.getResourceDescription(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "resource_quality"), resource.getResourceQuality(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "resource_type"), resource.getResourceType(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "upload_time"), StringExchanger.parseDateToString(resource.getUploadTime()), new XSDDatatype("date"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "file_location"), resource.getFileLocation(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "difficulty"), resource.getDifficulty(), new XSDDatatype("string"));
        String postfix = resource.getPostfix();
        Individual indi = getFileFormat(postfix);
        if (indi != null) {
            Statement s = ontModel.createStatement(re, ontModel.getProperty(Constant.NS + "has_postfix"), indi);
            ontModel.add(s);
        }
        return true;
    }

    @Override
    public boolean addEPortfolio(EPortfolio portfolio) throws IndividualExistException {
        if (containEPortfolio(portfolio.getId())) {
            throw new IndividualExistException("EPortfolio " + portfolio.getId() + " has already existed in the model");
        }
        Individual el = ontModel.getIndividual(Constant.NS + portfolio.getElearner().getId());
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
    public boolean addEConcept(EConcept concept) throws IndividualExistException {
        if (containEConcept(concept.getCid())) {
            throw new IndividualExistException("EConcept " + concept.getCid() + "  has already existed in the model");
        }
        Individual con = ontModel.createIndividual(Constant.NS + concept.getCid(), ontModel.getResource(Constant.NS + "E_Concept"));
        ontModel.add(con, ontModel.getProperty(Constant.NS + "id"), concept.getCid());
        ontModel.add(con, ontModel.getProperty(Constant.NS + "name"), concept.getName(), new XSDDatatype("string"));
        return true;
    }

    @Override
    public boolean addEPerfomance(EPerformance performance) throws IndividualExistException {
        if (containEPerformance(performance.getId())) {
            throw new IndividualExistException("EPerformance " + performance.getId() + " has already existed in the model");
        }
        Individual el = ontModel.getIndividual(Constant.NS + performance.getElearner().getId());
        Individual con = ontModel.getIndividual(Constant.NS + performance.getConcept().getCid());
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

    @Override
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
    public boolean addPropertyIsResourceOfC(ISCB_Resource resource, EConcept concept) throws IndividualNotExistException {
        if (!containEResource(resource.getRid())) {
            throw new IndividualNotExistException("ISCB_Resource " + resource.getRid() + " does not exist in the model");
        }
        if (!containEConcept(concept.getCid())) {
            throw new IndividualNotExistException("EConcept " + concept.getCid() + " does not exist in the model");
        }
        Individual res = ontModel.getIndividual(Constant.NS + resource.getRid());
        Individual con = ontModel.getIndividual(Constant.NS + concept.getCid());
        ontModel.add(res, ontModel.getProperty(Constant.NS + "is_resource_of_C"), con);
        ontModel.add(con, ontModel.getProperty(Constant.NS + "inverse_of_is_resource_of_C"), res);
        return true;
    }

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
        p = ontModel.getProperty(Constant.NS + "resource_quality");
        String resource_quality = indi.getRequiredProperty(p).getLiteral().getString();
        if (!resource.getResourceQuality().equals(resource_quality)) {
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getResourceQuality(), new XSDDatatype("string"));
        }
        p = ontModel.getProperty(Constant.NS + "resource_type");
        String resource_type = indi.getRequiredProperty(p).getLiteral().getString();
        if (!resource.getResourceType().equals(resource_type)) {
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, resource.getResourceType(), new XSDDatatype("string"));
        }
        p = ontModel.getProperty(Constant.NS + "upload_time");
        String upload_time = indi.getRequiredProperty(p).getLiteral().getString();
        Date time = StringExchanger.parseStringToDate(upload_time);
        if (!resource.getUploadTime().equals(time)) {
            ontModel.remove(indi, p, indi.getPropertyValue(p));
            ontModel.add(indi, p, StringExchanger.parseDateToString(resource.getUploadTime()), new XSDDatatype("date"));
        }
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
        p = ontModel.getProperty(Constant.NS + "has_postfix");
        Resource r = indi.getPropertyResourceValue(p);
        String postfix = r.getRequiredProperty(ontModel.getProperty(Constant.NS + "file_postfix")).getLiteral().getString();
        String newPostfixString = resource.getPostfix();
        Individual newPostfix = getFileFormat(newPostfixString);
        if (newPostfix != null) {
            if (!newPostfixString.equals(postfix)) {
                ontModel.remove(indi, p, indi.getPropertyResourceValue(p));
                ontModel.add(ontModel.createStatement(indi, p, newPostfix));
            }
        }
        return true;
    }

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
        EPerformanceAssessment ass = new EPerformanceAssessment();
        Statement a1Node = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "a1"));
        if (a1Node != null) {
            ass.a1 = a1Node.getLiteral().getString();
        }
        Statement a2Node = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "a2"));
        if (a2Node != null) {
            ass.a2 = a2Node.getLiteral().getString();
        }
        Statement a3Node = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "a3"));
        if (a3Node != null) {
            ass.a3 = a3Node.getLiteral().getString();
        }
        Statement a4Node = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "a4"));
        if (a4Node != null) {
            ass.a4 = a4Node.getLiteral().getString();
        }
        Statement a5Node = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "a5"));
        if (a5Node != null) {
            ass.a5 = a5Node.getLiteral().getString();
        }
        Statement a6Node = indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "a6"));
        if (a6Node != null) {
            ass.a6 = a6Node.getLiteral().getString();
        }
        performance.assessment = ass;
        System.out.println("assessment:" + ass);
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

    public static void main(String[] args) {
        ELearnerModel emi = new ELearnerModel();

        EPortfolio port = emi.getEPortfolio("E_Portfolio_el001-2");

    }
}
