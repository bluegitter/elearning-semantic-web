package ms;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.AllValuesFromRestriction;
import com.hp.hpl.jena.ontology.ComplementClass;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.SomeValuesFromRestriction;
import com.hp.hpl.jena.ontology.UnionClass;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.vocabulary.RDFS;

// Class ( a:sheep subClassOf 
//          	restriction(a:eats allValuesFrom grass)
//				a:animal
//       )
//
// Class ( a:grass subClassOf a:plant )
//
// DisjiontClasses( 
//					UnionOf ( 
// 							  restriction(a:part_of someValuesFrom a:animal
//							  a:animal
//                          ) ,
//                  UnionOf (
//                            restriction(a:part_of someValuesFrom a:plant
//                            a:plant
//                          )
//                 )
//
// Class ( a:vegetarian equivalent 
//             restriction ( a:eats allValuesFrom 
//                             complementOf (
//                                             UnionOf ( 
// 														 restriction(a:part_of someValuesFrom a:animal
//														 a:animal
//													   )
//                                          )
//       )
//
// Conclusion : a:sheep subClassOf vegetarian;

public class Example3 {
	public static String NS = "http://example.org/example3/";
	
	public static void main(String args[]) {
		// Create the Ontlogy Model;
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		// Create the Classes : animal, plant, sheep, grass, vegetarian;
		OntClass animal = model.createClass(NS + "Animal");
		OntClass plant = model.createClass(NS + "Plant");
		OntClass sheep = model.createClass(NS + "Sheep");
		OntClass grass = model.createClass(NS + "Grass");
		OntClass vegetarian = model.createClass(NS + "Vegetarian");
		
		// Set sheep as subClass of animal, set grass as subClass of plant;
		animal.addSubClass(sheep);
		plant.addSubClass(grass);
		
		// Create the object property eat and part_of (there domain and range are owl:Thing); 
		ObjectProperty eat = model.createObjectProperty(NS + "eat");
		ObjectProperty partOf = model.createObjectProperty(NS + "partOf");
		
		// Create Restriction : eatAllGrass, set sheep as its subclass;
		AllValuesFromRestriction avr = model.createAllValuesFromRestriction(null, eat, grass);
		avr.addSubClass(sheep);
		
		// Create Restriction : partofSomePlant; partofSomeAnimal;
		SomeValuesFromRestriction plantPart = model.createSomeValuesFromRestriction(null, partOf, plant);
		SomeValuesFromRestriction animalPart = model.createSomeValuesFromRestriction(null, partOf, animal);
		
		// Create the Union Class meat : (animal, partofSomeAnimal);
		RDFNode[] nodes1 = {animal, animalPart};
		RDFList meatList = model.createList(nodes1);
		UnionClass meat = model.createUnionClass(null, meatList);
		
		// Create the Union Class vegetable : (plant, partofSomePlant):
		RDFNode[] nodes2 = {plant, plantPart};
		RDFList vegetableList = model.createList(nodes2);
		UnionClass vegetable = model.createUnionClass(null, vegetableList);
		
		// Set meat and vegetable as disjoint class;
		meat.addDisjointWith(vegetable);
		
		// Create the Complement Class of meat;
		ComplementClass nonMeat = model.createComplementClass(null, meat);
		
		// Create the Restriction : eatAllNonMeat;
		AllValuesFromRestriction vegeEater = model.createAllValuesFromRestriction(null, eat, nonMeat);
		
		// Set vegetarian as equivalent class of eatAllNonMeat;
		vegetarian.setEquivalentClass(vegeEater);
		
		// Output the super classe of sheep;
		for (StmtIterator sit = sheep.listProperties(RDFS.subClassOf);  sit.hasNext(); ) {
			System.out.println("Super Class: " + sit.nextStatement().getObject().as(Resource.class).getURI());
		}
		System.out.println("***************************************");
		
		// Get the Pellet Reasoner;
		Reasoner reasoner = PelletReasonerFactory.theInstance().create();
		// Create the inference model with Pellet Reasoner;
		InfModel infModel = ModelFactory.createInfModel(reasoner, model);
		
		// Output the super classes of sheep in the inference model;
		Resource infSheep = infModel.getResource(NS + "Sheep");
		for (StmtIterator sit = infSheep.listProperties(RDFS.subClassOf); sit.hasNext(); ) {
			System.out.println("Super Class: " + sit.nextStatement().getObject().as(Resource.class).getURI());
		}
		System.out.println("***************************************");
 	}
}
