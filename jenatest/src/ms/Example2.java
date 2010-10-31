package ms;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

// Individual (a:Spike rdf:type owl:Thing
//                     value (a:is_pet_of  a:Pete)
//            )
//
// Individual (a:Pete rdf:type owl:Thing)
//
// ObjectProperty (a:has_pet domain a:person
//                           range  a:animal
//                )
// ObjectProperty (a:is_pet_of inverseOf a:has_pet)
//
// Conclusion: Pete rdf:type a:person; Spike rdf:type a:animal;


public class Example2 {
	public static String NS = "http://example.org/example2/";
	
	public static void main(String args[]) {
		// Create the Ontology Model;
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		// Create Class : person, animal;
		OntClass person = model.createClass(NS + "Person");
		OntClass animal = model.createClass(NS + "Animal");
		
		// Create Object Property : has_pet, set its domain as person, set its range as animal;
		ObjectProperty hasPet = model.createObjectProperty(NS + "hasPet");
		hasPet.addDomain(person);
		hasPet.addRange(animal);
		
		// Create Object Property : is_pet_of;
		ObjectProperty isPetOf = model.createObjectProperty(NS + "isPetOf");
		// Set has_pet as the inverse property of is_pet_of;
		hasPet.setInverseOf(isPetOf);
		
		// Create Individual of owl:Thing : pete, spike;
		Individual pete = model.createIndividual(NS + "Pete", OWL.Thing);
		Individual spike = model.createIndividual(NS + "Spike", OWL.Thing);
		// Set spike as the pet of pete;
		spike.addProperty(isPetOf, pete);
		
		// Output the rdf:type information of pete and spike;
		for (StmtIterator it1 = pete.listProperties(RDF.type); it1.hasNext(); )
			System.out.println("Pete Types: " + it1.nextStatement().getObject().as(Resource.class).getURI());
		for (StmtIterator it2 = spike.listProperties(RDF.type); it2.hasNext(); )
			System.out.println("Spike Types: " + it2.nextStatement().getObject().as(Resource.class).getURI());
		
		System.out.println("**********************************************");
		
		// Get the Pellet Reasoner;
		Reasoner reasoner = PelletReasonerFactory.theInstance().create();
		// Create the inference model with Pellet Reasoner;
		InfModel infModel = ModelFactory.createInfModel(reasoner, model);
		
		Resource infPete = infModel.getResource(NS + "Pete");
		Resource infSpike = infModel.getResource(NS + "Spike");
		
		// Output the rdf:type information of pet and spike in the inference model;
		for (StmtIterator it1 = infPete.listProperties(RDF.type); it1.hasNext(); )
			System.out.println("Pete Types: " + it1.nextStatement().getObject().as(Resource.class).getURI());
		for (StmtIterator it2 = infSpike.listProperties(RDF.type); it2.hasNext(); )
			System.out.println("Spike Types: " + it2.nextStatement().getObject().as(Resource.class).getURI());
		
		System.out.println("**********************************************");
	}
}
