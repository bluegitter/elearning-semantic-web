/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena.impl;

import com.hp.hpl.jena.ontology.OntModel;
import java.io.File;
import java.io.IOException;
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
import ontology.EConcept;
import ontology.people.ELearner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class OwlUpdate {

    public static String[] optypes = {"AddELearner", "AddEConcept", "AddEResource", "AddEGoal", "AddInterest", "RemoveEConcept"};
    private ELearnerModelImpl emi;
    private File uf;
    private DocumentBuilder db;
    private HashMap op_types_map;
private Document doc;
    public OwlUpdate(ELearnerModelImpl emi, File uf) {
        try {
            this.uf = uf;
            this.emi = emi;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            op_types_map = new HashMap();
            for (int i = 0; i < optypes.length; i++) {
                op_types_map.put(optypes[i], (i + 1));
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OwlUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OntModel getModel() {
        return emi.getOntModel();
    }

    public Element getEConcpetElementByEConcpet(EConcept con) {
        Element el = doc.createElement("EConcept");
        Element id = doc.createElement("Id");
        Element name = doc.createElement("Name");
        Element difficulty = doc.createElement("Difficulty");        
        id.setTextContent(con.getCid());
        el.appendChild(id);
        el.appendChild(name);
        el.appendChild(difficulty);
        return el;
    }

    public EConcept getEConceptByElement(Element el) {
        EConcept con = new EConcept();
        if (el.getTagName().equals("EConcept")) {
            String id = ((Element) el.getElementsByTagName("Id").item(0)).getTextContent();
            String name = ((Element) el.getElementsByTagName("Name").item(0)).getTextContent();
            String diff = ((Element) el.getElementsByTagName("Difficulty").item(0)).getTextContent();
            con.setCid(id);
            con.setName(name);
            con.setDifficulty(diff);
        } else {
            return null;
        }
        return con;
    }

    public ELearner getELearnerByElement(Element el) {
        ELearner elearner = new ELearner();
        if (el.getTagName().equals("ELearner")) {
            String id = ((Element) el.getElementsByTagName("Id").item(0)).getTextContent();
            String name = ((Element) el.getElementsByTagName("Name").item(0)).getTextContent();
            String gender = ((Element) el.getElementsByTagName("Gender").item(0)).getTextContent();
            String grade = ((Element) el.getElementsByTagName("Grade").item(0)).getTextContent();
            String email = ((Element) el.getElementsByTagName("EMail").item(0)).getTextContent();
            String address = ((Element) el.getElementsByTagName("Address").item(0)).getTextContent();
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

    public boolean parseFile() {
        try {
            if (uf == null) {
                return false;
            }
            doc = db.parse(uf);
            Element root = doc.getDocumentElement();
            NodeList opl = root.getElementsByTagName("Operation");
            for (int i = 0; i < opl.getLength(); i++) {
                Element el = (Element) opl.item(i);
                String op_type = ((Element) el.getElementsByTagName("OPType").item(0)).getTextContent();
                int index = Integer.parseInt(op_types_map.get(op_type).toString());
                switch (index) {
                    case 1:
                        System.out.println("uu");
                        break;
                    default:
                        System.out.println("mam");
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

    public void writeFile(File toFile) {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File test = new File("files/owl/updates/update_write.xml");
            StreamResult result = new StreamResult(test);
          // StreamResult result = new StreamResult(toFile);
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(OwlUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        ELearnerModelImpl emi = new ELearnerModelImpl(new File("files/owl/el001.owl"));
        File f = new File("files/owl/updates/update.xml");
        OwlUpdate ou = new OwlUpdate(emi, f);
        ou.parseFile();
        ou.writeFile(null);
    }
}
