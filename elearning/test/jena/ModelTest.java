package jena;

import java.io.File;
import java.util.ArrayList;

import db.ResourceParser;

import jena.impl.ELearnerModel;
import jena.impl.ELearnerModelImpl;
import jena.impl.ELearnerModelImplTwo;
import ontology.EPerformance;
import ontology.people.ELearner;
import util.Constant;

public class ModelTest {
	public static void  contrastPerformance1(){
		for(int i =0;i<10000;i++){
			//start up the program
		}
		long time = System.currentTimeMillis();
		ELearnerModelImpl emi = new ELearnerModelImpl();
		ELearner el = new ELearner("el001");
		ArrayList<EPerformance> c = emi.getEPerformances(el);
		System.out.println("csize:"+c.size());
		long time1 = System.currentTimeMillis();
		ELearnerModelImplTwo emi2 = new ELearnerModelImplTwo();
		ArrayList<EPerformance> c2 = emi2.getEPerformances(el);
		System.out.println("csize:"+c2.size());
		long time2 = System.currentTimeMillis();
		System.out.println("time1"+(time1-time));
		System.out.println("time2"+(time2-time1));
	}
	public static void FileWritingPerformanceTest(){
		ResourceParser rp = new ResourceParser();
		rp.getBasicEConcepts();
		//rp.getDataStructureResrouces();
		System.out.println("Begin Writing ....");
		long time1 = System.currentTimeMillis();
		File file = new File( Constant.userOwlFile);
		rp.writeToFile(file);
		long time2 = System.currentTimeMillis();
		
		ELearnerModelImpl newEMI = new ELearnerModelImpl(file);
		
		long time3 = System.currentTimeMillis();
		
		System.out.println("write basic concepts results");
		
	}
	public static void main(String [] args){
		ResourceParser rp = new ResourceParser();
		rp.getDataStructureResrouces();
	}
}
