package lp.display;

import ontology.EConcept;
import ontology.EPerformance;
import ontology.people.ELearner;
import ontology.resources.EResource;


/**
 *
 * @author David
 */
public class EClass {
    private enum CType {learner, concept, resource, performance};

    private CType type;
    private Object object;

    public EClass(ELearner l) {
        type = CType.learner;
        object = l;
    }

    public EClass(EConcept c) {
        type = CType.concept;
        object = c;
    }

    public EClass(EResource r) {
        type = CType.resource;
        object = r;
    }

    public EClass(EPerformance p) {
        type = CType.performance;
        object = p;
    }

    @Override
    public String toString() {
        String ts = "";
        switch(type) {
            case concept:
                ts = "知识:";
            case learner:
                ts = "学习者:";
            case resource:
                ts = "资源:";
            case performance:
                ts = "知识:";
        }
        return ts + object.toString();
    }


}
