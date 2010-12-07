package jena;

import java.io.File;
import java.io.IOException;
import ontology.people.ELearner;
import ontology.resources.EResource;
import util.Constant;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import db.OwlOperator;

public class ELearnerModel {
	public ELearnerModel(){
		
	}
	public Model addELearner(Model model,ELearner elearner){
		Resource el = model.createResource(Constant.NS+elearner.getId(),model.getResource(Constant.NS+"E_Learner"));
		el.addProperty(model.getProperty(Constant.NS+"name"), elearner.getName());
		return model;
	}
	public Model addResource(Model model, EResource resource){
		
		return model;
	}
	public static void main(String [] args) throws IOException{
		ELearnerModel mo = new ELearnerModel();
		File file = new File("D:\\EclipseWorkspace\\elearning\\src\\db\\ms.owl");
		Model model = OwlFactory.getGenericRuleReasonerModel();
		ELearner el = new ELearner();
		el.setId("eltest");
		el.setName("testName");
		model = mo.addELearner(model, el);
		OwlOperator.updateOwlFile(model,file);
	}

	private Model model;
}
