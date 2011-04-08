/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena.interfaces;

import java.util.ArrayList;
import ontology.EConcept;
import ontology.people.ELearner;

/**
 *
 * @author student
 */
public interface ELearnerUserQueryInterface {

    ArrayList<EConcept> getIgnoreConceptsByELearner(ELearner el);
}
