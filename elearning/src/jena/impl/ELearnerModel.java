package jena.impl;

import java.io.File;
import java.io.IOException;

import jena.OwlFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;

import db.OwlOperation;

public class ELearnerModel {
	protected OntModel ontModel;
    protected InfModel infModel;

    public ELearnerModel() {
        ontModel = OwlFactory.getOntModel();
        init();
    }
    public ELearnerModel(OntModel ontModel) {
        this.ontModel = ontModel;
        init();
    }
    public ELearnerModel(File file){
    	this.ontModel = OwlFactory.getOntModel(file);
    	init();
    }
    public ELearnerModel(File file,String lang){
    	this.ontModel = OwlFactory.getOntModel(file, lang);
    	init();
    }
    protected void init(){
    	updateInfModel();
    }
    public boolean writeToFile(File file) {
        try {
        	updateInfModel();
            OwlOperation.writeOwlFile(infModel, file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void updateInfModel() {
        infModel = OwlFactory.getInfoModel(OwlFactory.getGenericRuleReasoner(), ontModel);
    }
    public OntModel getOntModel(){
    	return ontModel;
    }
    public InfModel getInfModel() {
        return infModel;
    }
}
