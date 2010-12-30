package protege;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import jena.impl.ELearnerModelImplOne;

import com.hp.hpl.jena.util.FileManager;

import util.Constant;
import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLOntology;
import edu.stanford.smi.protegex.owl.ProtegeOWL;


public class ProtegeOwlTest {
	public static void main(String [] args) throws OntologyLoadException, IOException{
		InputStream in = FileManager.get().open(Constant.OWLFile);
		long time1 = System.currentTimeMillis();
		OWLModel owlModel = ProtegeOWL.createJenaOWLModelFromInputStream(in);
		//OWLModel owlModel = ProtegeOWL.createJenaOWLModelFromURI(Constant.OWLONSERVER+"userOwlFile2.owl");
        long time2 = System.currentTimeMillis();
       ELearnerModelImplOne emi = new ELearnerModelImplOne(new File(Constant.OWLFile));
        long time3 = System.currentTimeMillis();
        System.out.println((time2-time1)+"ms Protege Reading Model Time");
        System.out.println((time3-time2)+"ms Jena Reading Model Time");
        
        
		owlModel.getNamespaceManager().setDefaultNamespace(Constant.NS);
      //  OWLNamedClass worldClass = owlModel.createOWLNamedClass("E_Learner");
        OWLNamedClass elearner = owlModel.getOWLNamedClass(Constant.NS+"E_Learner");
        OWLOntology ontology = owlModel.getDefaultOWLOntology();
        System.out.println( ontology.getRDFType());
        System.out.println(elearner.getLocalName());
        System.out.println(elearner.getPropertyValue(owlModel.getOWLProperty("id")));
        System.out.println("Class URI: " + elearner.getURI());

	}
}
