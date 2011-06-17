package ontology;

import java.util.Date;

import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;

public class EPortfolio {

    private ELearner elearner;
    private ISCB_Resource resource;
    private float value;
    private String id;
    private Date datetime;
    private int rate;
    private String rateString;
    public EPortfolio() {
        
    }

    public EPortfolio(String id, ELearner elearner, ISCB_Resource resource, float value, Date datetime) {
        this.id = id;
        this.elearner = elearner;
        this.resource = resource;
        this.value = value;
        this.datetime = datetime;
        rate = 0;
        rateString ="";
    }

    public ELearner getELearner() {
        return elearner;
    }

    public void setELearner(ELearner elearner) {
        this.elearner = elearner;
    }

    public ISCB_Resource getEResource() {
        return resource;
    }

    public void setEResource(ISCB_Resource resource) {
        this.resource = resource;
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getRateString() {
        return rateString;
    }

    public void setRateString(String rateString) {
        this.rateString = rateString;
    }

    public String toString() {
        return id + "\t" 
                + elearner.getId() + "\t"
                + resource.getRid() + "\t"
                + rate + "\t"
                + datetime;
    }
}
