package jena.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.TransitiveProperty;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.vocabulary.RDF;
import db.OwlOperation;
import exception.jena.IndividualExistException;
import exception.jena.IndividualNotExistException;
import jena.OwlFactory;
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
        setInfModel(null, ontModel);
    }

    public ELearnerModelImpl(File file, String lang) {
        super(file, lang);
    }
    public InfModel infModel;

    public void setInfModel(Reasoner reasoner, OntModel model) {
        ontModel = ModelFactory.createOntologyModel(OntModelSpec.DAML_MEM_TRANS_INF, ontModel);
    }

    public static void main(String[] args) throws IndividualNotExistException, IOException, IndividualExistException {
        File file = new File("test\\owl\\conceptsAndresource_RDF-XML.owl");
        long init = System.currentTimeMillis();

        ELearnerModelImpl emi = new ELearnerModelImpl(file);
        System.out.println("intitime:" + (System.currentTimeMillis() - init) + "ms");
        ELearner el = emi.getELearner("el001");
        EConcept cid1 = emi.getEConcept("CMP.cf.2");
        EConcept root = emi.getEConcept("Computer_Science");
        EConcept cid2 = emi.getEConcept("CMP.cf");
        EConcept cid3 = emi.getEConcept("CMP");

        boolean b = emi.isPartOfEConcept(root, cid1);
        boolean b2 = emi.isPartOfEConcept(root, cid3);
        boolean b3 = emi.isPartOfEConcept(cid1, cid3);
        boolean b4 = emi.isPartOfEConcept(cid2, cid1);
        System.out.println(b + " " + b2 + " " + b3 + " " + b4);

//        ArrayList<ISCB_Resource> r = emi.getEResourcesByTypes("全部", "all", "全部");
//        for (ISCB_Resource res : r) {
//            emi.addPropertyIsResourceOfC(res, cid1);
//            emi.addPropertyIsResourceOfC(res, cid2);
//            emi.addPropertyIsResourceOfC(res, cid3);
//            emi.addPropertyIsResourceOfC(res, root);
//        }
//        EConcept con2 = emi.getEConcept("CMP.cf.3");
//        emi.getEResourcesByName("数据结构概念");
//        emi.getEResourcesByMeidaType("文本");
//        ArrayList<ISCB_Resource> r = emi.getEResourcesByTypes("全部", "all", "全部");
// add resourcess to the root concepts
//        System.out.println("1" + emi.getMemberConcepts(root).size());
//        ArrayList<EConcept> cons = emi.getAllEConcepts();
//        for (EConcept con : cons) {
//            ArrayList<EConcept> sons = emi.getSonConcepts(con);
//            for (EConcept c : sons) {
//                emi.addPropertyIsPartOf(con, c);
//            }
//        }
//        System.out.println("2" + emi.getMemberConcepts(root).size());
//        for (EConcept con : cons) {
//            ArrayList<EConcept> sons = emi.getMemberConcepts(con);
//            System.out.println("sons size:" + sons.size());
//            ArrayList<ISCB_Resource> ress = emi.getEResourcesByEConcept(con);
//            for (EConcept c : sons) {
//                ArrayList<ISCB_Resource> r = emi.getEResourcesByEConcept(c);
//                for (ISCB_Resource resource : r) {
//                    if (!ress.contains(resource)) {
//                        System.out.println("add  new resource" + resource);
//                        emi.addPropertyIsResourceOfC(resource, con);
//                    }
//                }
//            }
//        }
//        ArrayList<ISCB_Resource> all = emi.getEResourcesByEConcept(root);
//        System.out.println("size:" + all.size());

//        ArrayList<EPerformance> ps = emi.getEPerformances(el);
//        System.out.println("size" + ps.size());
//        EPerformance ep = new EPerformance();
//        ep.setId("newId");
//        ep.setValue(3f);
//        ep.setConcept(con2);
//        ep.setElearner(el);
//        ep.setDatetime(new Date(System.currentTimeMillis()));
//        emi.addEPerfomance(ep);
//
//        EPerformance ep2 = emi.getEPerformance(el, con2);
//        System.out.println(ep2);
//        ArrayList<EPerformance> ps2 = emi.getEPerformances(el);
//        System.out.println("size2:" + ps2.size());
        System.out.println("end");
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
                    performance.setElearner(elearner);
                    performance.setConcept(concept);
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
    public ArrayList<EConcept> getEConcepts(ISCB_Resource resource) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        Individual res = ontModel.getIndividual(Constant.NS + resource.getRid());
        SimpleSelector selector = new SimpleSelector(res, ontModel.getProperty(Constant.NS + "is_resource_of_C"), (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource con = (Resource) iter.nextStatement().getObject();
            concepts.add(getEConcept(con.getLocalName()));
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
        in.addLiteral(ontModel.getProperty(Constant.NS + "value"), interest.getValue());
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

    public boolean containEInterest(ELearner elearner, EConcept concept) throws IndividualNotExistException {
        if (!containELearner(elearner.getId())) {
            throw new IndividualNotExistException("the elearner " + elearner.getName() + " does not exist in the model");
        }
        if (containEConcept(concept.getCid())) {
            throw new IndividualNotExistException("the concept " + concept.getName() + " does not exist in the model");
        }
        //TODO:
        return false;
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
}
