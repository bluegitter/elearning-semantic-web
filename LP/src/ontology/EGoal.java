/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontology;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class EGoal {

    public EGoal() {
        name = "goal name";
        cons = new ArrayList<EConcept>();
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public ArrayList<EConcept> getCons() {
        return cons;
    }

    public void setCons(ArrayList<EConcept> cons) {
        this.cons = cons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private String gid;
    private String name;
    private ArrayList<EConcept> cons;
}
