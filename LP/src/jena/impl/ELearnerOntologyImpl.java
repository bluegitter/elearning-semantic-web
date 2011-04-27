/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jena.impl;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import util.Constant;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class ELearnerOntologyImpl {
    public OWLOntologyManager manager;
    public OWLOntology ontology;
    public IRI documentIRI;
    public ELearnerOntologyImpl(){
        try {
            manager = OWLManager.createOWLOntologyManager();
            ontology = manager.loadOntologyFromOntologyDocument(new File(Constant.OWLFile));
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(ELearnerOntologyImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ELearnerOntologyImpl(File owlFile){
        try {
            manager = OWLManager.createOWLOntologyManager();
            ontology = manager.loadOntologyFromOntologyDocument(owlFile);
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(ELearnerOntologyImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void saveOwlFile(){
        try {
            manager.saveOntology(ontology);
        } catch (OWLOntologyStorageException ex) {
            Logger.getLogger(ELearnerOntologyImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addEConcept(){
        
    }
}
