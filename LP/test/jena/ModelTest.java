package jena;

import java.io.File;
import java.util.ArrayList;

import db.ResourceParser;

import jena.impl.ELearnerModel;
import jena.impl.ELearnerModelImpl;
import jena.impl.ELearnerModelImplTwo;
import ontology.EPerformance;
import ontology.people.ELearner;
import util.ConstantForTest;

public class ModelTest {

    public static void contrastPerformance1() {
        for (int i = 0; i < 10000; i++) {
            //start up the program
        }
        long time = System.currentTimeMillis();
        ELearnerModelImpl emi = new ELearnerModelImpl();
        ELearner el = new ELearner("el001");
        ArrayList<EPerformance> c = emi.getEPerformances(el);
        System.out.println("csize:" + c.size());
        long time1 = System.currentTimeMillis();
        ELearnerModelImplTwo emi2 = new ELearnerModelImplTwo();
        ArrayList<EPerformance> c2 = emi2.getEPerformances(el);
        System.out.println("csize:" + c2.size());
        long time2 = System.currentTimeMillis();
        System.out.println("time1" + (time1 - time));
        System.out.println("time2" + (time2 - time1));
    }

    public static void FileWritingPerformanceTest() {
        ResourceParser rp = new ResourceParser();
        rp.getBasicEConcepts();
        //rp.getDataStructureResrouces();
        System.out.println("Begin Writing ....");
        long time1 = System.currentTimeMillis();
        File file = new File(ConstantForTest.TestOwlFileLocation + "basicconcpetsowl.owl");
        rp.writeToFile(file);
        long time2 = System.currentTimeMillis();
        System.out.println("complete writing basic concpets to the file");
        System.out.println("writing time: " + (time2 - time1));
        ELearnerModelImpl newEMI = new ELearnerModelImpl(file);
        long time3 = System.currentTimeMillis();
        System.out.println("reading time: " + (time3 - time2));

        System.out.println("complete reading basic concepts results");

    }

    public static void main(String[] args) {
        FileWritingPerformanceTest();
    }
}
