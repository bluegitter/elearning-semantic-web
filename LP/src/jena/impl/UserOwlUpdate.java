/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena.impl;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import org.jdom.Document;
import org.jdom.Element;
import util.Constant;

/**
 *
 * @author t
 */
public class UserOwlUpdate {

    private Document srcDoc;
    private Document newDoc;
    private ArrayList<Element> els;

    public static File downloadFile(ELearner el){
        return null;
    }

    
    public void createNewDocWithEMI(ELearnerModelImpl emi, ELearner el) {
        BufferedReader b = null;
        try {
            Individual elIndi = emi.getOntModel().getIndividual(Constant.NS + el.getId());
            ELearnerModelImpl newEmi = new ELearnerModelImpl(new File("files/owl/update_template2.owl"));
            DatatypeProperty idP = emi.getOntModel().getDatatypeProperty(Constant.NS + "id");
            DatatypeProperty nameP = emi.getOntModel().getDatatypeProperty(Constant.NS + "name");
            DatatypeProperty gradeP = emi.getOntModel().getDatatypeProperty(Constant.NS + "grade");
            DatatypeProperty genderP = emi.getOntModel().getDatatypeProperty(Constant.NS + "gender");
            DatatypeProperty emailP = emi.getOntModel().getDatatypeProperty(Constant.NS + "email");
            DatatypeProperty addressP = emi.getOntModel().getDatatypeProperty(Constant.NS + "address");
            newEmi.getOntModel().add(elIndi, idP, elIndi.getPropertyValue(idP));
            newEmi.getOntModel().add(elIndi, nameP, elIndi.getPropertyValue(nameP));
            newEmi.getOntModel().add(elIndi, gradeP, elIndi.getPropertyValue(gradeP));
            newEmi.getOntModel().add(elIndi, genderP, elIndi.getPropertyValue(genderP));
            newEmi.getOntModel().add(elIndi, emailP, elIndi.getPropertyValue(emailP));
            newEmi.getOntModel().add(elIndi, addressP, elIndi.getPropertyValue(addressP));
            ArrayList<EInterest> ins = emi.getEInterests(el);
            ObjectProperty hasIn = emi.getOntModel().getObjectProperty(Constant.NS + "has_interest");
            ObjectProperty inverseHasIn = emi.getOntModel().getObjectProperty(Constant.NS + "inverse_of_has_interest");
            ObjectProperty isConceptOfI = emi.getOntModel().getObjectProperty(Constant.NS + "is_concept_of_I");
            ObjectProperty inverseIsConceptOfI = emi.getOntModel().getObjectProperty(Constant.NS + "inverse_of_is_concept_of_I");
            for (EInterest in : ins) {
                Individual inIndi = emi.getOntModel().getIndividual(Constant.NS + in.getId());
                Individual conIndi = emi.getOntModel().getIndividual(Constant.NS + in.getEConcept().getCid());
                newEmi.getOntModel().add(elIndi, hasIn, inIndi);
                newEmi.getOntModel().add(inIndi, inverseHasIn, elIndi);
                newEmi.getOntModel().add(inIndi, inverseIsConceptOfI, conIndi);
                newEmi.getOntModel().add(conIndi, isConceptOfI, inIndi);
            }
            ArrayList<EPerformance> performs = emi.getEPerformances(el);
            ObjectProperty hasPerform = emi.getOntModel().getObjectProperty(Constant.NS + "has_performance");
            ObjectProperty inverseHasPerform = emi.getOntModel().getObjectProperty(Constant.NS + "inverse_of_has_performance");
            ObjectProperty isConceptOfP = emi.getOntModel().getObjectProperty(Constant.NS + "is_concept_of_P");
            ObjectProperty inverseIsConceptOfP = emi.getOntModel().getObjectProperty(Constant.NS + "inverse_of_is_concept_of_P");
            for (EPerformance perform : performs) {
                Individual perIndi = emi.getOntModel().getIndividual(Constant.NS + perform.getId());
                Individual conIndi = emi.getOntModel().getIndividual(Constant.NS + perform.getEConcept().getCid());
                newEmi.getOntModel().add(elIndi, hasPerform, perIndi);
                newEmi.getOntModel().add(perIndi, inverseHasPerform, elIndi);
                newEmi.getOntModel().add(conIndi, isConceptOfP, perIndi);
                newEmi.getOntModel().add(perIndi, inverseIsConceptOfP, conIndi);
            }
            ArrayList<EPortfolio> ports = emi.getEPortfolios(el);
            ObjectProperty hasPort = emi.getOntModel().getObjectProperty(Constant.NS + "has_portfolio");
            ObjectProperty inverseHasPort = emi.getOntModel().getObjectProperty(Constant.NS + "inverse_of_has_portfolio");
            ObjectProperty isResourceOfP = emi.getOntModel().getObjectProperty(Constant.NS + "is_resource_of_P");
            ObjectProperty inverseIsResourceOfP = emi.getOntModel().getObjectProperty(Constant.NS + "inverse_of_is_resource_of_P");
            for (EPortfolio port : ports) {
                Individual portIndi = emi.getOntModel().getIndividual(Constant.NS + port.getId());
                Individual resIndi = emi.getOntModel().getIndividual(Constant.NS + port.getEResource().getRid());
                newEmi.getOntModel().add(elIndi, hasPort, portIndi);
                newEmi.getOntModel().add(portIndi, inverseHasPort, elIndi);
                newEmi.getOntModel().add(resIndi, isResourceOfP, portIndi);
                newEmi.getOntModel().add(portIndi, inverseIsResourceOfP, portIndi);
            }

            //文件写入write.owl并从中抽离必要信息储存
            File writeTo = new File("files/owl/write.owl");
            newEmi.writeToFile(writeTo);
            b = new BufferedReader(new FileReader(writeTo));
            boolean isHead = true;
            boolean isNamedIndividual = false;

            FileWriter fw = new FileWriter(new File("files/owl/" + el.getId() + ".owl"));
            while (true) {
                String s = b.readLine();
                if (s == null) {
                    break;
                }
                if (s.trim().startsWith("<owl:NamedIndividual")) {
                    isNamedIndividual = true;
                }
                if (isHead || isNamedIndividual) {
                    fw.write(s + "\n");
                    //  System.out.println(s);
                }
                if (s.trim().startsWith("<owl:Ontology")) {
                    isHead = false;
                }

                if (s.trim().equals("</rdf:RDF>")) {
                    break;
                }
            }
            fw.write("<!--Generated By ELearner Team (Version 1.0) Tsinghua-->");
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(UserOwlUpdate.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                b.close();
            } catch (IOException ex) {
                Logger.getLogger(UserOwlUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        ELearnerModelImpl emi = new ELearnerModelImpl(new File("files/owl/elearning_owl.owl"));
        ELearner el = emi.getELearner("el001");
        System.out.println("name:" + el.getName());
        UserOwlUpdate uou = new UserOwlUpdate();
        uou.createNewDocWithEMI(emi, el);
        //      UserOwlUpdate uou = new UserOwlUpdate();
//        uou.copyAndPasteDoc();

    }
}
