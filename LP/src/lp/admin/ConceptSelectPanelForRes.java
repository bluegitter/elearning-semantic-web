/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.admin;

import java.util.ArrayList;
import ontology.EConcept;

/**
 *
 * @author student
 */
public class ConceptSelectPanelForRes extends ConceptSelectPanel {

    public ConceptSelectPanelForRes() {
        super();
    }

    public ConceptSelectPanelForRes(ArrayList<EConcept> cons) {
        super();
        this.cons = cons;
    }
    @Override
    public void deleteAction(){
        System.out.println("delete Concept for res");
    }
}
