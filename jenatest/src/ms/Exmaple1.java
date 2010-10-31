package ms;

import org.mindswap.pellet.jena.PelletReasonerFactory;
import com.hp.hpl.jena.ontology.IntersectionClass;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.SomeValuesFromRestriction;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.vocabulary.RDFS;

// Class ( a:bus_driver equivalent 
// 		       intersectionOf ( a:person , 
//                              restriction(a:drive someValuesFrom(a:bus)
//                            )
//        )
//
//
// Class ( a:driver equivalent
//				intersectionOf ( a:person ,
//                               restriction(a:drive someValuesFrom(a:vehicle)
//                             )
// 		 )
//
// Class (a:bus subClassOf a:vehicle)
//
// Conclusion: a:bus_driver subClassOf a:driver 
public class Exmaple1 {
	public static String NS = "http://example.org/example1/";
	
	public static void main(String args[]) {
		// Create Ontology Model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		// Create Class : person, driver, bus_driver, vehicle, bus;
		OntClass person = model.createClass(NS + "Person");
		OntClass driver = model.createClass(NS + "Driver");
		OntClass busDriver = model.createClass(NS + "BusDriver");
		OntClass vehicle = model.createClass(NS + "Vehicle");
		OntClass bus = model.createClass(NS + "Bus");
		
		// Add bus as subclass of vehicle; 
		vehicle.addSubClass(bus);
		
		// Create Object Property : drive, set its domain as person, set its range as vehicle;
		ObjectProperty drive = model.createObjectProperty(NS + "drive"); 
		drive.addDomain(person);
		drive.addRange(vehicle);
		
		// Create the Restriction : drive SomeValuesFrom Vehicle;
		SomeValuesFromRestriction driverRest = model.createSomeValuesFromRestriction(null, drive, vehicle);
		
		// Create the IntersectionClass : ( person and driveSomeVehicle);
		RDFNode[] nodes1 = {person, driverRest};
		RDFList driverList= model.createList(nodes1);
		IntersectionClass dinter = model.createIntersectionClass(null, driverList);
		
		// Set the IntersectionClass as equivalent class of driver;
		driver.addEquivalentClass(dinter);
		
		// Create the Restriction : drive SomeValuesFrom Bus;
		SomeValuesFromRestriction busDriverRest = model.createSomeValuesFromRestriction(null, drive, bus);
		
		// Create the IntersectionClass : (person and driveSomeBus);
		RDFNode[] nodes2 = {person, busDriverRest};
		RDFList busDriverList = model.createList(nodes2);
		IntersectionClass bdinter = model.createIntersectionClass(null, busDriverList);
		
		// Set the intersectionClass as equivalent class of bus_driver;
		busDriver.addEquivalentClass(bdinter);
		
		// Output the super classes of bus_driver;
		for (StmtIterator sit = busDriver.listProperties(RDFS.subClassOf);  sit.hasNext(); ) {
			System.out.println("Super Class: " + sit.nextStatement().getObject().as(Resource.class).getURI());
		}		
		System.out.println("***************************************");
		
		// Get the Pellet reasoner;
		Reasoner reasoner = PelletReasonerFactory.theInstance().create();
		// Create the inference model with Pellet reasoner; 
		InfModel infModel = ModelFactory.createInfModel(reasoner, model);
		
		// Get the bus_driver class in the inference model;
		Resource infBusDriver = infModel.getResource(NS + "BusDriver");
		// Output the super classes of bus_driver in the inference model;
		for (StmtIterator sit = infBusDriver.listProperties(RDFS.subClassOf); sit.hasNext(); ) {
			System.out.println("Super Class: " + sit.nextStatement().getObject().as(Resource.class).getURI());
		}
		System.out.println("***************************************");	
	}
}
