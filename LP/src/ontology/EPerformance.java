package ontology;

import java.util.Date;

import ontology.people.ELearner;

public class EPerformance {

    public EPerformance() {
    }

    public ELearner getElearner() {
        return elearner;
    }

    public void setElearner(ELearner elearner) {
        this.elearner = elearner;
    }

    public EConcept getConcept() {
        return concept;
    }

    public void setConcept(EConcept concept) {
        this.concept = concept;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
//        return id+"\t"+value+"\t"+elearner.getId()+"\t"+concept.getCid()+"\t"+datetime;
        return concept.getName() + valueString();
    }
    @Override
    public boolean equals(Object o ){
        if(o instanceof EPerformance){

            EPerformance perform = (EPerformance) o;
            if(!this.id.equals(perform.getId())){
                return false;
            }
            if(this.value !=perform.getValue()){
                return false;
            }
            if(!this.concept.equals(perform.getConcept())){
                return false;
            }
            if(!this.elearner.equals(perform.getElearner())){
                return false;
            }
            
            return true;
        }
        return false;
    }
    private String valueString() {
        if (value < 0) {
            return "(学习中)";
        } else {
            return "(成绩:" + value + ")";
        }
    }
    private ELearner elearner;
    private EConcept concept;
    private float value;
    private String id;
    private Date datetime;
}
