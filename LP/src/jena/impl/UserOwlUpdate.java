/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena.impl;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void main(String[] args) {
        ELearnerModelImpl emi = new ELearnerModelImpl();
        ELearner el = emi.getELearner("el001");
        emi.setCurrentGoal(el, "goal_0001");
        createNewDocWithEMI(emi, el);
    }

    public static void createNewDocWithEMI(ELearnerModelImpl emi, ELearner el) {
        BufferedReader b = null;
        try {
            //get user basic info
            Individual elIndi = emi.getOntModel().getIndividual(Constant.NS + el.getId());
            ELearnerModelImpl newEmi = new ELearnerModelImpl(new File("files/owl/update_template.owl"));
            newEmi.getOntModel().add(elIndi, emi.getOntModel().getDatatypeProperty(Constant.NS + "id"), newEmi.getOntModel().createLiteral(el.getId()));
            newEmi.getOntModel().add(elIndi, emi.getOntModel().getDatatypeProperty(Constant.NS + "name"), newEmi.getOntModel().createLiteral(el.getName()));
            newEmi.getOntModel().add(elIndi, emi.getOntModel().getDatatypeProperty(Constant.NS + "grade"), newEmi.getOntModel().createLiteral(el.getGrade()));
            newEmi.getOntModel().add(elIndi, emi.getOntModel().getDatatypeProperty(Constant.NS + "gender"), newEmi.getOntModel().createLiteral(el.getGender()));
            newEmi.getOntModel().add(elIndi, emi.getOntModel().getDatatypeProperty(Constant.NS + "email"), newEmi.getOntModel().createLiteral(el.getEmail()));
            newEmi.getOntModel().add(elIndi, emi.getOntModel().getDatatypeProperty(Constant.NS + "address"), newEmi.getOntModel().createLiteral(el.getAddress()));
            Property goalProperty = emi.getOntModel().getProperty(Constant.NS+"current_goal");
            RDFNode goalNode = elIndi.getPropertyValue(goalProperty);
            if(goalNode!=null){
                 newEmi.getOntModel().add(elIndi ,goalProperty,goalNode);
            }

            //get user interests
            DatatypeProperty valueProperty = emi.getOntModel().getDatatypeProperty(Constant.NS + "value");
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
                newEmi.getOntModel().add(inIndi, valueProperty, inIndi.getPropertyValue(valueProperty));
            } 
            //get user performance
            DatatypeProperty dateProperty = emi.getOntModel().getDatatypeProperty(Constant.NS + "date_time");
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
                float value = perIndi.getPropertyValue(valueProperty).asLiteral().getFloat();
                newEmi.getOntModel().add(perIndi, valueProperty, perIndi.getPropertyValue(valueProperty));
                if (value > 0) {
                    for (int i = 1; i < 7; i++) {
                        DatatypeProperty aProperty = emi.getOntModel().getDatatypeProperty(Constant.NS + "a" + i);
                        newEmi.getOntModel().add(perIndi, aProperty, perIndi.getPropertyValue(aProperty));
                    }
                }

                newEmi.getOntModel().add(perIndi, dateProperty, perIndi.getPropertyValue(dateProperty));
            }
            //get user portfolios
            ArrayList<EPortfolio> ports = emi.getEPortfolios(el);
            ObjectProperty hasPort = emi.getOntModel().getObjectProperty(Constant.NS + "has_portfolio");
            ObjectProperty inverseHasPort = emi.getOntModel().getObjectProperty(Constant.NS + "inverse_of_has_portfolio");
            ObjectProperty isResourceOfP = emi.getOntModel().getObjectProperty(Constant.NS + "is_resource_of_P");
            ObjectProperty inverseIsResourceOfP = emi.getOntModel().getObjectProperty(Constant.NS + "inverse_of_is_resource_of_P");
            DatatypeProperty rateProperty = emi.getOntModel().getDatatypeProperty(Constant.NS + "rate");
            DatatypeProperty rateStringProperty = emi.getOntModel().getDatatypeProperty(Constant.NS + "rateString");
            for (EPortfolio port : ports) {
                Individual portIndi = emi.getOntModel().getIndividual(Constant.NS + port.getId());
                Individual resIndi = emi.getOntModel().getIndividual(Constant.NS + port.getEResource().getRid());
                newEmi.getOntModel().add(elIndi, hasPort, portIndi);
                newEmi.getOntModel().add(portIndi, inverseHasPort, elIndi);
                newEmi.getOntModel().add(resIndi, isResourceOfP, portIndi);
                newEmi.getOntModel().add(portIndi, inverseIsResourceOfP, portIndi);
                newEmi.getOntModel().add(portIndi, valueProperty, portIndi.getPropertyValue(valueProperty));
                newEmi.getOntModel().add(portIndi, dateProperty, portIndi.getPropertyValue(dateProperty));
                newEmi.getOntModel().add(portIndi, rateProperty, portIndi.getPropertyValue(rateProperty));
                newEmi.getOntModel().add(portIndi, rateStringProperty, portIndi.getPropertyValue(rateStringProperty));
            }



            //文件写入write.owl并从中抽离必要信息储存(Properties )
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
            b.close();
            fw.write("<!--Generated By ELearner Team (Version 1.0) Tsinghua-->");
            fw.flush();
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(UserOwlUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
