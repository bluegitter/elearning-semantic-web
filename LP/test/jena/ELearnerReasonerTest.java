/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import com.hp.hpl.jena.ontology.OntModel;
import java.io.File;
import java.util.ArrayList;
import jena.impl.ELearnerModelImpl;
import ontology.EConcept;
import ontology.people.ELearner;

/**
 *
 * @author william
 */
public class ELearnerReasonerTest {

    public static void main(String[] args) {
        File file = new File("test\\owl\\conceptsAndresource_RDF-XML.owl");
        long init = System.currentTimeMillis();
        ELearnerModelImpl emi = new ELearnerModelImpl(file);
        System.out.println("intitime:" + (System.currentTimeMillis() - init) + "ms");
        OntModel ontModel = emi.getOntModel();
        ELearner el = emi.getELearner("el001");
        ELearnerReasoner er = new ELearnerReasoner();
        ArrayList<EConcept> cons = er.getRecommendEConcepts_1(ontModel, el);
        System.out.println("cons size:" + cons.size());
        System.out.println(cons);
    }
}
