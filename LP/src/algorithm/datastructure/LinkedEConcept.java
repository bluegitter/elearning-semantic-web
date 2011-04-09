/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.datastructure;

import com.hp.hpl.jena.ontology.OntModel;
import java.util.ArrayList;
import ontology.EConcept;
import ontology.people.ELearner;

/**
 *
 * @author Administrator
 */
public class LinkedEConcept {

    private EConcept concept;
    private ArrayList<LinkedEConcept> pre;
    private ArrayList<LinkedEConcept> post;
    private boolean isLearnt;

    public LinkedEConcept(EConcept con) {
        concept = con;
        isLearnt = false;
        post = new ArrayList<LinkedEConcept>();
        pre = new ArrayList<LinkedEConcept>();
    }

    public boolean getIsLearnt() {
        return isLearnt;
    }

    public void setIsLearnt(boolean isLearnt) {
        this.isLearnt = isLearnt;
    }

    public EConcept getConcept() {
        return concept;
    }

    public void setConcept(EConcept concept) {
        this.concept = concept;
    }

    public ArrayList<LinkedEConcept> getPost() {
        return post;
    }

    public ArrayList<LinkedEConcept> getPre() {
        return pre;
    }

    public void addPre(LinkedEConcept lcon) {
        this.pre.add(lcon);
    }

    public void addPost(LinkedEConcept lcon) {
        this.post.add(lcon);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("pre size:"+pre.size());
        for(LinkedEConcept c:pre){
            sb.append(c.getConcept()+" "+c.getIsLearnt()+"\t");
        }
        sb.append("\npost size:"+post.size());
         for(LinkedEConcept c:post){
            sb.append(c.getConcept()+" "+c.getIsLearnt()+"\t");
        }
        sb.append("\n"+concept+" "+isLearnt);
        return sb.toString();
    }
}
