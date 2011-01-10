package ontology;

import ontology.people.ELearner;

public class EInterest {
	public EInterest(){
		
	}
	public EInterest(String id){
		this.id = id;
	}	
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
	public ELearner getELearner() {
		return elearner;
	}
	public void setELearner(ELearner elearner) {
		this.elearner = elearner;
	}
	public EConcept getEConcept() {
		return econcept;
	}
	public void setEConcept(EConcept econcept) {
		this.econcept = econcept;
	}
	public String getId() {
		return id;
	}
        public boolean equals(Object o){
            if(o instanceof EInterest){
               EInterest ei = (EInterest) o;
               if(!this.id.equals(ei.getId())){
                   return false;
               }
               if(this.value != ei.getValue()){
                   return false;
               }
               return true;
            }
            return false;
        }
	public void setId(String id) {
		this.id = id;
	}
        public String toString(){
            return id+"\t"+value+"\t"+elearner.getId()+"\t"+econcept.getName();
        }

	private String id;
	private float value;
	private ELearner elearner;
	private EConcept econcept;
	
}
