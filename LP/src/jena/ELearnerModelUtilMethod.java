/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import algorithm.datastructure.LinkedEConcept;
import java.util.ArrayList;
import ontology.EConcept;
import ontology.EPerformance;

/**
 *
 * @author Administrator
 */
public class ELearnerModelUtilMethod {

    public static boolean isInPerformance(ArrayList<EPerformance> performs, EConcept con) {
        for (EPerformance perform : performs) {
            if (perform.getConcept().equals(con)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInLinkedConcepts(ArrayList<LinkedEConcept> lcons, EConcept con) {
        for (LinkedEConcept lcon : lcons) {
            if (lcon.getConcept().equals(con)) {
                return true;
            }
        }
        return false;
    }
}
