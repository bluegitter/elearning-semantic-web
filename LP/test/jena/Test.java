/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import exception.jena.IndividualNotExistException;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    public static void main(String[] args) throws UnknownHostException {
        testPerformance();
    }
    public static void testNet() throws UnknownHostException{
        InetAddress me = InetAddress.getByName("localhost");
        System.out.println("localhost by name =" + me);
        InetAddress me2 = InetAddress.getLocalHost();
        System.out.println("localhost by getLocalHost =" + me2);
        InetAddress[] many = InetAddress.getAllByName("microsoft.com");
        for (int i = 0; i < many.length; i++) {
            System.out.println(many[i]);
        }
    }
    public static void testPerformance() {
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

    public static void testPortfolio() {
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
