package lp.display;

import ontology.EConcept;
import ontology.EPerformance;
import ontology.people.ELearner;
import ontology.resources.E_Resource;

/**
 *
 * @author David
 */
public class EClass implements Comparable {

    private enum CType {

        learner, concept, resource, performance
    };
    private CType type;
    public Object object;
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

    public EClass(E_Resource r) {
        type = CType.resource;
        object = r;
    }

    public EClass(EPerformance p) {
        type = CType.performance;
        object = p;
    }

    public boolean isPerformance() {
        return type == CType.performance;
    }

    public boolean isLearner() {
        return type == CType.learner;
    }

    public boolean isResource() {
        return type == CType.resource;
    }

    public String getIconStr() {
        String str = null;
        switch (type) {
            case learner:
                str = "/lp/resources/learner.png";
                break;
            case concept:
                str = "/lp/resources/concept.png";
                break;
            case resource:
                str = "/lp/resources/resource.png";
                break;
            case performance:
                if (((EPerformance) object).getValue() < 0) {
                    str = "/lp/resources/performance.png";
                } else {
                    str = "/lp/resources/performance_done.png";
                }
                break;
        }
        return str;
    }

    public int getColorIndex() {
        if(r[0] > r[1]) {
            if(r[0] > r[2])
                return 0;
            else
                return 2;
        } else {
            if(r[1] > r[2])
                return 1;
            else
                return 2;
        }
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
