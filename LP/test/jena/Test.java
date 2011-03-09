/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jena;

import exception.jena.IndividualNotExistException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import jena.impl.ELearnerModelImpl;
import ontology.EPerformance;
import ontology.EPerformanceAssessment;
import ontology.EPortfolio;
import util.Constant;

/**
 *
 * @author william
 */
public class Test {
    public static void main(String[] args) {

        File file = new File(Constant.OWLFile);

        ELearnerModelImpl emi = new ELearnerModelImpl(file);
        EPerformance perform = emi.getEPerformance("E_Performance_1_1");
        try {
            emi.updateEPerformanceAssessment(perform, new EPerformanceAssessment("60", "60", "60", "60", "60", "60"));
        } catch (IndividualNotExistException ex) {
            Logger.getLogger(ELearnerModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        perform = emi.getEPerformance("E_Performance_1_1");

    }
    public static void testPortfolio(){
          File file = new File(Constant.OWLFile);
        long init = System.currentTimeMillis();

        ELearnerModelImpl emi = new ELearnerModelImpl(file);
        EPortfolio port = emi.getEPortfolio("E_Portfolio_el001-2");
        port.setRate(2);
        try {
            emi.updateEPortfolio(port);
        } catch (IndividualNotExistException ex) {
            Logger.getLogger(ELearnerModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        port = emi.getEPortfolio("E_Portfolio_el001-2");
        System.out.println("port:" + port);
    }
}
