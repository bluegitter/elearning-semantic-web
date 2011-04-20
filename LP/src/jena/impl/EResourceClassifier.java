/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena.impl;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import java.util.ArrayList;
import ontology.EConcept;
import ontology.EGoal;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;
import util.Constant;

/**
 *
 * @author t
 */
public class EResourceClassifier {

    public static void main(String[] args) {
        ELearnerModelImpl emi = new ELearnerModelImpl();
        ELearner el = emi.getELearner("el005");
        System.out.println("el:" + el);
        EConcept con = emi.getEConcept("A_cid_1_4");

        ArrayList<ISCB_Resource> res = emi.getEResourcesByMeidaType("程序");
        System.out.println(res.size());
    }
    public static void changeConcept(ISCB_Resource res,EConcept from,EConcept to){
        
    }
    
}
