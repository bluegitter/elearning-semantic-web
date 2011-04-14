/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.datastructure;

import com.hp.hpl.jena.ontology.OntModel;
import java.util.ArrayList;
import lp.map.E_Castle;
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

    public int getConceptType() {
        String t = concept.getDifficulty();
        if (t.equals("初级")) {
            return E_Castle.EASY;
        } else if (t.equals("中级")) {
            return E_Castle.MEDIUM;
        } else if (t.equals("高级")) {
            return E_Castle.HARD;
        } else {
            return E_Castle.MEDIUM;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("pre size:").append(pre.size());

        for (LinkedEConcept c : pre) {
            sb.append(c.getConcept()).append(" ").append(c.getIsLearnt()).append("\t");
        }
        sb.append("\npost size:").append(post.size());
        for (LinkedEConcept c : post) {
            sb.append(c.getConcept()).append(" ").append(c.getIsLearnt()).append("\t");
        }
        sb.append("\n").append(concept).append(" ").append(isLearnt);
        return sb.toString();
    }
}
