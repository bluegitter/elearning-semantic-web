/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import exception.jena.IndividualNotExistException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import jena.impl.ELearnerModelImpl;
import ontology.EConcept;
import ontology.EGoal;
import ontology.people.ELearner;
import util.Constant;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class IsLeafConceptTest {

    public static void main2(String[] args) throws IOException, IndividualNotExistException {
        File owlFile = new File(Constant.OWLFile);
        ELearnerModelImpl emi = new ELearnerModelImpl(owlFile);
        ELearner el = emi.getELearner("el005");
//        EConcept con = emi.getEConcept("A_cid_0_e_1");
//        emi.addPropertyIsLeafConcept(con);
        ArrayList<EConcept> concepts = emi.getAllEConcepts();
        System.out.println("size:" + concepts.size());
        int i = 0;
        for (EConcept con : concepts) {
            String id = con.getCid();
            if (id.startsWith("A_")) {
                System.out.println("id:" + con.getCid());
                i++;
                emi.addPropertyIsLeafConcept(con, true);
            } else {
                emi.addPropertyIsLeafConcept(con, false);
            }
        }
        System.out.println("i:" + i);
        //        ArrayList<EGoal> goals = emi.getAllEGoals();
        // writeToTestFile(emi);
    }

    public static void writeToTestFile(ELearnerModelImpl emi) throws IOException {
        File f = new File("X:/elearning/temp_owl/test.owl");
        if (!f.exists()) {
            f.createNewFile();
        }
        jena.OwlOperation.writeRdfFile(emi.getOntModel(), f, null);
        jena.OwlOperation.writeOwlFileFromRdfFile(f, f);
    }

    public static void main(String[] args) {
        ELearnerModelImpl emi = new ELearnerModelImpl(new File(Constant.OWLFile));
        Set<EConcept> concepts = emi.getAllLeafEConcepts();
        System.out.println("size:" + concepts.size());
        Set<EConcept> concepts2 = emi.getAllBranchEConcepts();
        System.out.println("size:" + concepts2.size());
    }
}
