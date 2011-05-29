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
            if (perform.getEConcept().equals(con)) {
                return true;
            }
        }
        return false;
    }
    //return true if id1 < id2
    //else return false

    public static boolean compareEConceptIds(String id1, String id2) {
        String s1[] = id1.split("_");
        String s2[] = id2.split("_");
        if (Integer.parseInt(s1[2]) < Integer.parseInt(s2[2])) {
            return true;
        } else if (Integer.parseInt(s1[2]) > Integer.parseInt(s2[2])) {
            return false;
        }
        if (s1[3].equals(s2[3])) {
            if (Integer.parseInt(s1[4]) < Integer.parseInt(s2[4])) {
                return true;
            } else {
                return false;
            }
        } else {
            if (s1[3].equals("h")) {
                return false;
            }
            if (s1[3].equals("m")) {
                if (s2[3].equals("h")) {
                    return true;
                }
            }
            if (s1[3].equals("e")) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<EConcept> sortRecommendEConcepts(ArrayList<EConcept> cons) {
        ArrayList<EConcept> sorted = new ArrayList<EConcept>();
        for (EConcept con : cons) {
            String id1 = con.getCid();
            if (sorted.isEmpty()) {
                sorted.add(con);
            } else {
                int size = sorted.size();
                boolean b = true;
                for (int i = 0; i < size; i++) {
                    EConcept con2 = sorted.get(i);
                    String id2 = con2.getCid();
                    if (compareEConceptIds(id1, id2)) {
                        sorted.add(i, con);
                        b = false;
                        break;
                    }
                }
                if (b) {
                    sorted.add(con);
                }
            }
        }
        return sorted;
    }

    public static boolean isInLinkedConcepts(ArrayList<LinkedEConcept> lcons, EConcept con) {
        for (LinkedEConcept lcon : lcons) {
            if (lcon.getConcept().equals(con)) {
                return true;
            }
        }
        return false;
    }

    public static void printEConcepts(ArrayList<EConcept> cons, String title) {
        System.out.println("=================" + title + "====================");
        System.out.println("cons size:" + cons.size());
        for (EConcept con : cons) {
            System.out.println("con:" + con + "\t" + con.getCid());
        }
        System.out.println("=====================================");
    }
}
