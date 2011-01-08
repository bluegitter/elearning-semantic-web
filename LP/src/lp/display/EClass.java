package lp.display;

import ontology.EConcept;
import ontology.EPerformance;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;


/**
 *
 * @author David
 */
public class EClass implements Comparable{
    private enum CType {learner, concept, resource, performance};

    private CType type;
    private Object object;

    public double rank = 0;
    public float[] r = new float[3];

    public EClass(ELearner l) {
        type = CType.learner;
        object = l;
    }

    public EClass(EConcept c) {
        type = CType.concept;
        object = c;
    }

    public EClass(ISCB_Resource r) {
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
                ts = "";
            case learner:
                ts = "学习者:";
            case resource:
                ts = "资源:";
            case performance:
                ts = "知识:";
        }
        return ts + object.toString();
    }

    @Override
    public int compareTo(Object o) {
        return (int) (((EClass) o).rank * 1000 - this.rank * 1000);
    }

}
