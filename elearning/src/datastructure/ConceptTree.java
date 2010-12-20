package datastructure;

import java.util.ArrayList;

import ontology.EConcept;

public class ConceptTree {
	@SuppressWarnings("unused")
	private EConcept root;
	public ArrayList<EConcept> concepts;
	public ConceptTree(){
		root = new EConcept("root","root");
		concepts = new ArrayList<EConcept>();
	}
	public int getSize(){
		return concepts.size();
	}
	public boolean hasSon(String concept){
		for(EConcept con : concepts){
			if(con.getName().equals(concept)){
				return false;
			}
		}
		return true;
	}
	public boolean addFather(String concept){
		if(hasSon(concept)){
			return false;
		}else{
			int id = concepts.size()+1;
			EConcept newCon = new EConcept("cid"+id);
			newCon.setName(concept);
			return true;
		}
	}
	public boolean  addSon(){
		return false;
	}

}
