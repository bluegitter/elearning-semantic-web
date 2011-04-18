/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owlapi.model.OWLOntology;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import jena.impl.ELearnerModelImpl;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import util.Constant;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class ELearnerOntology {

    public static void main(String[] args) {
        try {
            ELearnerModelImpl emi = new ELearnerModelImpl();
            writeOwlFile(emi.getOntModel(), new File(Constant.TEST_File));
            readAndWriteOWLAPIModel();
        } catch (IOException ex) {
            Logger.getLogger(ELearnerOntology.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void writeOwlFile(com.hp.hpl.jena.ontology.OntModel model, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        model.write(out);
        //model.write(out, "N3");
        out.flush();
        out.close();
        System.out.println("Owl File Update Success");
    }

    public static void readAndWriteOWLAPIModel() {
        try {
            File file = new File(Constant.newConceptFile);
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory factory = manager.getOWLDataFactory();
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
            manager.saveOntology(ontology);
        } catch (OWLOntologyStorageException ex) {
            Logger.getLogger(ELearnerOntology.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OWLOntologyCreationException ex) {
        }
    }
}
