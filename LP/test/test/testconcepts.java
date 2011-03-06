/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import jena.impl.ELearnerModelImpl;
import ontology.EConcept;

/**
 *
 * @author ghh
 */
public class testconcepts {
    public static void main(String []args){
        ELearnerModelImpl emi = new ELearnerModelImpl();

String cid = "cid";
for(int i = 1;i<93;i++){
    EConcept con = emi.getEConcept(cid+i);
    System.out.println(con);
}
    }
}
