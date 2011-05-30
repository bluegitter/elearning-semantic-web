/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena.impl;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import lp.admin.AdminOperatorPane;
import ontology.EConcept;
import ontology.EGoal;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.Constant;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class OwlUpdate {

    public static String[] optypes = {"AddELearner", "AddEConcept", "AddEResource", "AddEGoal",
        "AddEInterest", "AddEPerformance", "AddEPortfolio", "RemoveIndividual",
        "AddObjectProperty", "RemoveObjectProperty", "AddDataProperty", "RemoveDataProperty"};
    public static int ADDELEARNER = 0;
    public static int ADDECONCEPT = 1;
    public static int ADDERESOURCE = 2;
    public static int ADDEGOAL = 3;
    public static int ADDEINTEREST = 4;
    public static int ADDEPERFORMANCE = 5;
    public static int ADDEPORTFOLIO = 6;
    public static int REMOVEINDIVIDUAL = 7;
    public static int ADDOBJECTPROPERTY = 8;
    public static int REMOVEOBJECTPROPERTY = 9;
    public static int ADDDATAPROPERTY = 10;
    public static int REMOVEdATAPROPERTY = 11;
    private ELearnerModelImpl emi;
    private File inFile;
    private File toFile;
    private ArrayList<Element> toFileElements;
    private DocumentBuilder db;
    private HashMap op_types_map;
    private Document doc;

    public OwlUpdate(ELearnerModelImpl emi, File inFile) {
        try {
            this.inFile = inFile;
            this.inFile = new File("files/owl/updates/update_write.xml");
            this.toFile = new File("files/owl/updates/update_write.xml");
            toFileElements = new ArrayList<Element>();

            this.emi = emi;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.newDocument();
            op_types_map = new HashMap();
            for (int i = 0; i < optypes.length; i++) {
                op_types_map.put(optypes[i], i);
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OwlUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OntModel getModel() {
        return emi.getOntModel();
    }

    public EConcept getEConceptByElement(Element element) {
        EConcept con = new EConcept();
        if (element.getTagName().equals("E_Concept")) {
            String id = ((Element) element.getElementsByTagName("Id").item(0)).getTextContent();
            String name = ((Element) element.getElementsByTagName("Name").item(0)).getTextContent();
            String diff = ((Element) element.getElementsByTagName("Difficulty").item(0)).getTextContent();
            con.setCid(id);
            con.setName(name);
            con.setDifficulty(diff);
        } else {
            return null;
        }
        return con;
    }

    public ELearner getELearnerByElement(Element element) {
        ELearner elearner = new ELearner();
        if (element.getTagName().equals("E_Learner")) {
            String id = ((Element) element.getElementsByTagName("Id").item(0)).getTextContent();
            String name = ((Element) element.getElementsByTagName("Name").item(0)).getTextContent();
            String gender = ((Element) element.getElementsByTagName("Gender").item(0)).getTextContent();
            String grade = ((Element) element.getElementsByTagName("Grade").item(0)).getTextContent();
            String email = ((Element) element.getElementsByTagName("Email").item(0)).getTextContent();
            String address = ((Element) element.getElementsByTagName("Address").item(0)).getTextContent();
            elearner.setId(id);
            elearner.setName(name);
            elearner.setGender(gender);
            elearner.setGrade(grade);
            elearner.setEmail(email);
            elearner.setAddress(address);
        } else {
            return null;
        }
        return elearner;
    }

    public EGoal getEGoalByElement(Element element) {
        EGoal goal = new EGoal();
        if (element.getTagName().equals("E_Goal")) {
            String id = ((Element) element.getElementsByTagName("Id").item(0)).getTextContent();
            String name = ((Element) element.getElementsByTagName("Name").item(0)).getTextContent();
            goal.setGid(id);
            goal.setName(name);
        } else {
            return null;
        }
        return goal;
    }

    public ISCB_Resource getEResourceByElement(Element element) {
        ISCB_Resource res = new ISCB_Resource();
        if (element.getTagName().equals("ISCB_Resource")) {
            String id = ((Element) element.getElementsByTagName("Id").item(0)).getTextContent();
            String name = ((Element) element.getElementsByTagName("Name").item(0)).getTextContent();
            String difficulty = ((Element) element.getElementsByTagName("Difficulty").item(0)).getTextContent();
            String appType = ((Element) element.getElementsByTagName("AppType").item(0)).getTextContent();
            String mediaType = ((Element) element.getElementsByTagName("MediaType").item(0)).getTextContent();
            String description = ((Element) element.getElementsByTagName("Description").item(0)).getTextContent();
            String fileLocation = ((Element) element.getElementsByTagName("FileLocation").item(0)).getTextContent();

            res.setRid(id);
            res.setName(name);
            res.setDifficulty(difficulty);
            res.setAppType(appType);
            res.setResourceType(mediaType);
            res.setResourceDescription(description);
            res.setFileLocation(fileLocation);
        } else {
            return null;
        }
        return res;
    }

    public EInterest getEInterestByElement(Element element) {
        EInterest in = new EInterest();
        if (element.getTagName().equals("E_Learner")) {
            String id = ((Element) element.getElementsByTagName("Id").item(0)).getTextContent();
            String eid = ((Element) element.getElementsByTagName("ELearnerId").item(0)).getTextContent();
            String cid = ((Element) element.getElementsByTagName("EConceptId").item(0)).getTextContent();
            String value = ((Element) element.getElementsByTagName("Value").item(0)).getTextContent();
            in.setId(id);
            in.setELearner(emi.getELearner(eid));
            in.setEConcept(emi.getEConcept(cid));
            in.setValue(Float.parseFloat(value));
        } else {
            return null;
        }
        return in;
    }

    public EPerformance getEPerformanceByElement(Element element) {
        EPerformance perf = new EPerformance();
        if (element.getTagName().equals("E_Performance")) {
            String id = ((Element) element.getElementsByTagName("Id").item(0)).getTextContent();
            String eid = ((Element) element.getElementsByTagName("ELearnerId").item(0)).getTextContent();
            String cid = ((Element) element.getElementsByTagName("EConceptId").item(0)).getTextContent();
            String value = ((Element) element.getElementsByTagName("Value").item(0)).getTextContent();
            perf.setId(id);
            perf.setELearner(emi.getELearner(eid));
            perf.setEConcept(emi.getEConcept(cid));
            perf.setValue(Float.parseFloat(value));
        } else {
            return null;
        }
        return perf;
    }

    public EPortfolio getEPortfolioByElement(Element element) {
        EPortfolio port = new EPortfolio();
        if (element.getTagName().equals("E_Portfolio")) {
            String id = ((Element) element.getElementsByTagName("Id").item(0)).getTextContent();
            String eid = ((Element) element.getElementsByTagName("ELearnerId").item(0)).getTextContent();
            String rid = ((Element) element.getElementsByTagName("EResourceId").item(0)).getTextContent();
            String value = ((Element) element.getElementsByTagName("Value").item(0)).getTextContent();
            port.setId(id);
            port.setELearner(emi.getELearner(eid));
            port.setEResource(emi.getEResource(rid));
            port.setValue(Float.parseFloat(value));
        } else {
            return null;
        }
        return port;
    }

    public Element createEConcpetElementByEConcpet(EConcept con) {
        Element el = doc.createElement("E_Concept");
        Element id = doc.createElement("Id");
        Element name = doc.createElement("Name");
        Element difficulty = doc.createElement("Difficulty");
        id.setTextContent(con.getCid());
        name.setTextContent(con.getName());
        difficulty.setTextContent(con.getDifficulty());
        el.appendChild(id);
        el.appendChild(name);
        el.appendChild(difficulty);
        return el;
    }

    public Element createELearnerElementByELearner(ELearner el) {
        Element element = doc.createElement("E_Learner");
        Element id = doc.createElement("Id");
        Element name = doc.createElement("Name");
        Element gender = doc.createElement("Gender");
        Element grade = doc.createElement("Grade");
        Element email = doc.createElement("Email");
        Element address = doc.createElement("Address");
        id.setTextContent(el.getId());
        name.setTextContent(el.getName());
        gender.setTextContent(el.getGender());
        grade.setTextContent(el.getGrade());
        email.setTextContent(el.getEmail());
        address.setTextContent(el.getAddress());
        element.appendChild(id);
        element.appendChild(name);
        element.appendChild(gender);
        element.appendChild(grade);
        element.appendChild(email);
        element.appendChild(address);
        return element;
    }

    public Element createEGoalElementByEGoal(EGoal goal) {
        Element element = doc.createElement("E_Goal");
        Element id = doc.createElement("Id");
        Element name = doc.createElement("Name");
        id.setTextContent(goal.getGid());
        name.setTextContent(goal.getName());
        element.appendChild(id);
        element.appendChild(name);
        return element;
    }

    public Element createRemoveElement(String id) {
        Element element = doc.createElement("Id");
        element.setTextContent(id);
        return element;
    }

    public Element createEResourceElementByEResource(ISCB_Resource res) {
        Element element = doc.createElement("ISCB_Resource");
        Element id = doc.createElement("Id");
        Element name = doc.createElement("Name");
        Element diff = doc.createElement("Difficulty");
        Element appType = doc.createElement("AppType");
        Element mediaType = doc.createElement("MediaType");
        Element description = doc.createElement("Description");
        Element fileLocation = doc.createElement("FileLocation");
        id.setTextContent(res.getRid());
        name.setTextContent(res.getName());
        diff.setTextContent(res.getDifficulty());
        appType.setTextContent(res.getAppType());
        mediaType.setTextContent(res.getResourceType());
        description.setTextContent(res.getResourceDescription());
        fileLocation.setTextContent(res.getFileLocation());
        element.appendChild(id);
        element.appendChild(name);
        element.appendChild(diff);
        element.appendChild(appType);
        element.appendChild(mediaType);
        element.appendChild(description);
        element.appendChild(fileLocation);
        return element;
    }

    public Element createEInterestElementByEInterest(EInterest in) {
        Element element = doc.createElement("E_Interest");
        Element id = doc.createElement("Id");
        Element eid = doc.createElement("ELearnerId");
        Element cid = doc.createElement("EConceptId");
        Element value = doc.createElement("Value");
        id.setTextContent(in.getId());
        eid.setTextContent(in.getELearner().getId());
        cid.setTextContent(in.getEConcept().getCid());
        value.setTextContent(String.valueOf(in.getValue()));
        element.appendChild(id);
        element.appendChild(eid);
        element.appendChild(cid);
        element.appendChild(value);
        return element;
    }

    public Element createEPerformanceElementByEPerformance(EPerformance perf) {
        Element element = doc.createElement("E_Performance");
        Element id = doc.createElement("Id");
        Element eid = doc.createElement("ELearnerId");
        Element cid = doc.createElement("EConceptId");
        Element value = doc.createElement("Value");
        id.setTextContent(perf.getId());
        eid.setTextContent(perf.getELearner().getId());
        cid.setTextContent(perf.getEConcept().getCid());
        value.setTextContent(String.valueOf(perf.getValue()));
        element.appendChild(id);
        element.appendChild(eid);
        element.appendChild(cid);
        element.appendChild(value);
        return element;
    }

    public Element createEPortfolioElementByEPortfolio(EPortfolio port) {
        Element element = doc.createElement("E_Portfolio");
        Element id = doc.createElement("Id");
        Element eid = doc.createElement("ELearnerId");
        Element rid = doc.createElement("EResourceId");
        Element value = doc.createElement("Value");
        id.setTextContent(port.getId());
        eid.setTextContent(port.getELearner().getId());
        rid.setTextContent(port.getEResource().getRid());
        value.setTextContent(String.valueOf(port.getValue()));
        element.appendChild(id);
        element.appendChild(eid);
        element.appendChild(rid);
        element.appendChild(value);
        return element;
    }

    public boolean parseFile() {
        try {
            if (inFile == null) {
                return false;
            }
            doc = db.parse(inFile);
            Element root = doc.getDocumentElement();
            NodeList opl = root.getElementsByTagName("Operation");
            for (int i = 0; i < opl.getLength(); i++) {
                Element el = (Element) opl.item(i);
                String op_type = ((Element) el.getElementsByTagName("OPType").item(0)).getTextContent();
                int index = Integer.parseInt(op_types_map.get(op_type).toString());
                switch (index) {
                    case 0:
                        System.out.println("AddELearner");
                        Element elElement = (Element) el.getElementsByTagName("E_Learner").item(0);
                        ELearner newEl = getELearnerByElement(elElement);
                        emi.addELearner(newEl);
                        break;
                    case 1:
                        System.out.println("AddEConcept");
                        Element conElement = (Element) el.getElementsByTagName("E_Concept").item(0);
                        EConcept newCon = getEConceptByElement(conElement);
                        emi.addEConcept(newCon);
                        break;
                    case 2:
                        System.out.println("AddEResource");
                        Element resElement = (Element) el.getElementsByTagName("ISCB_Resource").item(0);
                        ISCB_Resource newRes = getEResourceByElement(resElement);
                        emi.addEResource(newRes);
                        break;
                    case 3:
                        System.out.println("AddEGoal");
                        Element goalElement = (Element) el.getElementsByTagName("E_Goal").item(0);
                        EGoal newGoal = getEGoalByElement(goalElement);
                        emi.addEGoal(newGoal);
                        break;
                    case 4:
                        System.out.println("AddEInterest");
                        Element inElement = (Element) el.getElementsByTagName("E_Interest").item(0);
                        EInterest newInterest = getEInterestByElement(inElement);
                        emi.addEInterest(newInterest);
                        break;
                    case 5:
                        System.out.println("AddEPerformance");
                        break;
                    case 6:
                        System.out.println("AddEPortfolio");
                        break;
                    case 7:
                        System.out.println("RemoveIndividual");
                        String id = ((Element) el.getElementsByTagName("Id").item(0)).getTextContent();
                        Individual indi = emi.getOntModel().getIndividual(Constant.NS + id);
                        if (indi != null) {
                            indi.remove();
                        }
                        break;
                    case 8:
                        System.out.println("AddObjectProperty");

                        break;
                    case 9:
                        System.out.println("RemoveObjectProperty");

                        break;
                    case 10:
                        System.out.println("AddDataProperty");
                        break;
                    case 11:
                        System.out.println("RemoveDataProperty");
                        break;
                    default:
                        System.out.println("no op type selected");
                        break;
                }
                System.out.println("el:" + el.getTagName());
            }

            //     NodeList stl = root.getElementsByTagName("Statement");
        } catch (SAXException ex) {
            Logger.getLogger(OwlUpdate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OwlUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public void initToFile(String updateType, String verson) {
        Element root = doc.createElement("OwlUpdate");
        Element updateTypeElement = doc.createElement("UpdateType");
        updateTypeElement.setTextContent(updateType);
        Element versonElement = doc.createElement("ModelVersion");
        versonElement.setTextContent(verson);
        Element updates = doc.createElement("Updates");
        System.out.println("size:" + toFileElements.size());
        for (Element e : toFileElements) {
            updates.appendChild(e);
        }
        root.appendChild(updateTypeElement);
        root.appendChild(versonElement);
        root.appendChild(updates);
        doc.appendChild(root);
    }

    public void addUpdate(Element element) {
        toFileElements.add(element);
    }

    public void writeToFile(Document writeDoc) {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(writeDoc);
            StreamResult result = new StreamResult(toFile);
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(OwlUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public static void main(String[] args) {
        ELearnerModelImpl emi = new ELearnerModelImpl(new File("files/owl/el001.owl"));
        File f = new File("files/owl/updates/update.xml");
        AdminOperatorPane aop = new AdminOperatorPane();
    }

    public Element getOperationElement(String opType, Element element) {
        Element op = doc.createElement("Operation");
        Element type = doc.createElement("OPType");
        type.setTextContent(opType);
        op.appendChild(type);
        op.appendChild(element);
        return op;
    }

    public Element getObjectPropertyOperationElement(String opType,String subject, String property, String object) {
        Element element = doc.createElement("Operation");
        Element type = doc.createElement("OPType");
        type.setTextContent(opType);
        Element subElement = doc.createElement("Subject");
        subElement.setTextContent(subject);
        Element propElement = doc.createElement("Property");
        propElement.setTextContent(property);
        Element objElement = doc.createElement("Object");
        objElement.setTextContent(object);
        element.appendChild(type);
        element.appendChild(subElement);
        element.appendChild(propElement);
        element.appendChild(objElement);
        return element;
    }
    public Element getDataPropertyOperationElement(String opType,String subject,String property,String data,String dataType){
         Element element = doc.createElement("Operation");
        Element type = doc.createElement("OPType");
        type.setTextContent(opType);
        Element subElement = doc.createElement("Subject");
        subElement.setTextContent(subject);
        Element propElement = doc.createElement("Property");
        propElement.setTextContent(property);
        Element dataElement = doc.createElement("Data");
        dataElement.setTextContent(data);
        Element dataTypeElement = doc.createElement("DataType");
        dataTypeElement.setTextContent(dataType);
        element.appendChild(type);
        element.appendChild(subElement);
        element.appendChild(propElement);
        element.appendChild(dataElement);
        element.appendChild(dataTypeElement);
        return element;
    }
}
