package lp.display;

import ontology.EConcept;
import ontology.EPerformance;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;

/**
 *
 * @author David
 */
public class EClass implements Comparable {

    private enum CType {

        learner, concept, resource, performance
    };
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

    public String getIconStr() {
        String str = null;
        switch (type) {
            case learner:
                str = "src/lp/resources/learner.png";
                break;
            case concept:
                str = "src/lp/resources/concept.png";
                break;
            case resource:
                str = "src/lp/resources/resource.png";
                break;
            case performance:
                str = "src/lp/resources/performance.png";
                break;
        }
        return str;
    }

    @Override
    public String toString() {
        return object.toString();
    }

    @Override
    public int compareTo(Object o) {
        return (int) (((EClass) o).rank * 1000 - this.rank * 1000);
    }
}
