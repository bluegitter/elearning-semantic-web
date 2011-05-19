package jena;

import java.io.File;
import java.util.ArrayList;

import jena.impl.ELearnerModelImpl;
import ontology.EConcept;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;
import util.Constant;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.util.HashSet;
import ontology.EGoal;
import ontology.EPerformance;

/*************************************************************
 * Elearner Reasoner is the class used for reasoning.
 * The rules, ontology classes and the properties of the model are the param of this class.
 * 
 * @author William
 *
 */
public class ELearnerReasoner {

    public static void main2(String[] args) {
        File file = new File("files\\owl\\conceptsAndresource_RDF-XML.owl");
        long init = System.currentTimeMillis();
        ELearnerModelImpl emi = new ELearnerModelImpl(file);
        System.out.println("intitime:" + (System.currentTimeMillis() - init) + "ms");
        OntModel ontModel = emi.getOntModel();
        ELearner el = emi.getELearner("el001");
        ELearnerReasoner er = new ELearnerReasoner();
        ArrayList<EConcept> con1 = er.getRecommendEConcepts_1(ontModel, el);
        System.out.println("1:" + con1.size());
        con1 = er.getRecommendEConcepts_2(ontModel, el);
        System.out.println("2:" + con1.size());
        con1 = er.getRecommendEConcepts_3(ontModel, el);
        System.out.println("3:" + con1.size());
        con1 = er.getRecommendEConcepts_4(ontModel, el);
        System.out.println("4:" + con1.size());
    }

    public static EConcept getEConcept(OntModel ontModel, String cid) {
        EConcept concept = new EConcept(cid);
        Individual indi = ontModel.getIndividual(Constant.NS + cid);
        concept.setName(indi.getPropertyValue(ontModel.getProperty(Constant.NS + "name")).asLiteral().getString());
        return concept;
    }

    public static ArrayList<EConcept> getRecommendEConcepts(OntModel ontModel, ELearner elearner, int index) {
        switch (index) {
            case 1:
                return getRecommendEConcepts_1(ontModel, elearner);
            case 2:
                return getRecommendEConcepts_2(ontModel, elearner);
            case 3:
                return getRecommendEConcepts_3(ontModel, elearner);
            case 4:
                return getRecommendEConcepts_4(ontModel, elearner);
            default:
                return null;
        }
    }

    /*****************************************************
     * [rule_c_1: (?learner_c_1 el:has_performance ?performance_c_1)
    (?concept_c_1_1 el:is_concept_of_P ?performance_c_1)
    (?concept_c_1_2 el:is_post_concept_of ?concept_c_1_1)
    ->(?concept_c_1_2 el:is_recommend_of_c_1 ?learner_c_1)]
     * @param ontModel
     * @param elearner
     * @return
     */
    public static ArrayList<EConcept> getRecommendEConcepts_1(OntModel ontModel, ELearner elearner) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "inverse_of_has_performance"), el);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource per = iter.nextStatement().getSubject();
            SimpleSelector per_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_concept_of_P"), per);
            StmtIterator iter2 = ontModel.listStatements(per_con);
            while (iter2.hasNext()) {
                Resource con = iter2.nextStatement().getSubject();
                SimpleSelector con_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_post_concept_of"), con);
                StmtIterator iter3 = ontModel.listStatements(con_con);
                while (iter3.hasNext()) {
                    Resource result = iter3.nextStatement().getSubject();
                    String id = result.getLocalName();
                    EConcept newCon = getEConcept(ontModel, id);
                    concepts.add(newCon);
                }
            }
        }
        return concepts;
    }

    /*****************************************************************
     *  * [rule_c_2: (?learner_c_2 el:has_performance ?performance_c_2)
    (?concept_c_2_1 el:is_concept_of_P ?performance_c_2)
    (?concept_c_2_2 el:is_son_of ?concept_c_2_1)
    ->(?concept_c_2_2 el:is_recommend_of_c_2 ?learner_c_2)]
     * @param ontModel
     * @param elearner
     * @return
     */
    public static ArrayList<EConcept> getRecommendEConcepts_2(OntModel ontModel, ELearner elearner) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector = new SimpleSelector(el, ontModel.getProperty(Constant.NS + "has_performance"), (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource perform = (Resource) iter.nextStatement().getObject();
            SimpleSelector selector_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_concept_of_P"), perform);
            StmtIterator iter_con = ontModel.listStatements(selector_con);
            while (iter_con.hasNext()) {
                Resource con = iter_con.nextStatement().getSubject();
                SimpleSelector selector_result = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_son_of"), con);
                StmtIterator iter_result = ontModel.listStatements(selector_result);
                while (iter_result.hasNext()) {
                    Resource result = iter_result.nextStatement().getSubject();
                    String name = result.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString();
                    concepts.add(new EConcept(result.getLocalName(), name));
                }
            }
        }
        return concepts;
    }

    /******************************************************************************
     * [rule_c_3:  (?concept_c_3 el:is_concept_of_I ?interest_c_3)
     *      (?learner_c_3 el:has_interest ?interest_c_3)
     *      ->(?concept_c_3 el:is_recommend_of_c_3 ?learner_c_3)]
     * @param ontModel
     * @param elearner
     * @return
     * 给用户推荐他感兴趣的知识点
     */
    public static ArrayList<EConcept> getRecommendEConcepts_3(OntModel ontModel, ELearner elearner) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector = new SimpleSelector(el, ontModel.getProperty(Constant.NS + "has_interest"), (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource interest = (Resource) iter.nextStatement().getObject();
            SimpleSelector selector_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_concept_of_I"), interest);
            StmtIterator iter_con = ontModel.listStatements(selector_con);
            while (iter_con.hasNext()) {
                Resource result = iter_con.nextStatement().getSubject();
                String name = result.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString();
                concepts.add(new EConcept(result.getLocalName(), name));
            }
        }
        return concepts;
    }

    /************************************************************************
     * [rule_c_4:  (?concept_c_4 el:is_concept_of_P ?performance_c_4)
     *      (?learner_c_4 el:has_performance ?performance_c_4)
     *      (?performance_c_4 el:value ?perValue_c_4)
     *      greaterThan(0.5,?perValue_c_4)
     *      greaterThan(?perValue_c_4,0) 
     *      ->(?concept_c_4 el:is_recommend_of_c_4 ?learner_c_4)]
     * @param ontModel
     * @param elearner
     * @return
     */
    public static ArrayList<EConcept> getRecommendEConcepts_4(OntModel ontModel, ELearner elearner) {
        ArrayList<EConcept> concepts = new ArrayList<EConcept>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector = new SimpleSelector(el, ontModel.getProperty(Constant.NS + "has_performance"), (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource perform = (Resource) iter.nextStatement().getObject();
            float value = perform.getRequiredProperty(ontModel.getProperty(Constant.NS + "value")).getLiteral().getFloat();
            if (value > 0 && value < 0.5) {
                SimpleSelector selector_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_concept_of_P"), perform);
                StmtIterator iter_con = ontModel.listStatements(selector_con);
                while (iter_con.hasNext()) {
                    Resource result = iter_con.nextStatement().getSubject();
                    String name = result.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString();
                    concepts.add(new EConcept(result.getLocalName(), name));
                }
            }
        }
        return concepts;
    }

    /**********************************************************************************************************
     * [rule_r_5:  (?concept_r_5 el:is_concept_of_P ?performance_r_5)
     *      (?learner_r_5 el:has_performance ?performance_c_5)
     *      (?performance_r_5 el:value ?perValue_r_5)
     *      greaterThan(0,?perValue_r_5)        
     *      (?resource_r_5 el:is_resource_of_C ?concept_r_5)
     *      ->(?resource_r_5 el:is_recommend_of_r_5 ?learner_r_5)]
     * @param ontModel
     * @param elearner
     * @return
     */
    public static ArrayList<ISCB_Resource> getRecommendEResource_5(OntModel ontModel, ELearner elearner) {
        ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector = new SimpleSelector(el, ontModel.getProperty(Constant.NS + "has_performance"), (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource perform = (Resource) iter.nextStatement().getObject();
            float value = perform.getRequiredProperty(ontModel.getProperty(Constant.NS + "value")).getLiteral().getFloat();
            if (value > 0) {
                SimpleSelector selector_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_concept_of_P"), perform);
                StmtIterator iter_con = ontModel.listStatements(selector_con);
                while (iter_con.hasNext()) {
                    Resource con = iter_con.nextStatement().getSubject();

                    SimpleSelector selector_res = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_resource_of_C"), con);
                    StmtIterator iter_res = ontModel.listStatements(selector_res);
                    while (iter_res.hasNext()) {
                        Resource indi = iter_res.nextStatement().getSubject();
                        ISCB_Resource resource = new ISCB_Resource(indi.getLocalName());
                        resource.setName(indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString());
                        resource.setFileLocation(indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "fileLocation")).getLiteral().getString());
                        resource.setDifficulty(indi.getRequiredProperty(ontModel.getProperty(Constant.NS + "difficulty")).getLiteral().getString());
                        resources.add(resource);
                    }
                }
            }
        }
        return resources;
    }

    /********************************************************************
     * [rule_r_6: (?resource_r_6_1 el:is_resource_of_P ?portfolio_r_6)
     *     (?learner_r_6 el:has_portfolio ?portfolio_r_6)
     *     (?resource_r_6_2 el:is_post_of_R ?resource_r_6_1)
     * ->(?resource_r_6_2 el:is_recommend_of_r_6 ?learner_r_6)]
     * @param ontModel
     * @param elearner
     * @return
     */
    public static ArrayList<ISCB_Resource> getRecommendEResource_6(OntModel ontModel, ELearner elearner) {
        ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector = new SimpleSelector(el, ontModel.getProperty(Constant.NS + "has_portfolio"), (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource port = (Resource) iter.nextStatement().getObject();
            SimpleSelector selector_res = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_resource_of_P"), port);
            StmtIterator iter_res = ontModel.listStatements(selector_res);
            while (iter_res.hasNext()) {
                Resource res = iter_res.nextStatement().getSubject();
                SimpleSelector selector_result = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_post_of_R"), res);
                StmtIterator iter_result = ontModel.listStatements(selector_result);
                while (iter_result.hasNext()) {
                    Resource result = iter_result.nextStatement().getSubject();
                    ISCB_Resource newRes = new ISCB_Resource(result.getLocalName());
                    newRes.setName(result.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString());
                    newRes.setFileLocation(result.getRequiredProperty(ontModel.getProperty(Constant.NS + "fileLocation")).getLiteral().getString());
                    newRes.setDifficulty(result.getRequiredProperty(ontModel.getProperty(Constant.NS + "difficulty")).getLiteral().getString());
                    resources.add(newRes);
                }
            }
        }
        return resources;
    }

    /****************************************************************************************
     * [rule_L_0:(?learner_L_0_1 el:has_interest ?interest_L_0_1)
     *    (?concept_L_0 el:is_concept_of_I ?interest_L_0_1)
     *    (?concept_L_0 el:is_concept_of_P ?performance_L_0)
     *    (?learner_L_0_2 el:has_performance ?performance_L_0)
     *    notEqual(?learner_L_0_2,?learner_L_0_1)
     *    ->(?learner_L_0_2 el:is_recommend_of_L_0 ?learner_L_0_1)]
     * @param ontModel
     * @param elearner
     * @return
     */
    public static ArrayList<ELearner> getRecommendELearner_0(OntModel ontModel, ELearner elearner) {
        ArrayList<ELearner> elearners = new ArrayList<ELearner>();
        Resource el = ontModel.getResource(Constant.NS + elearner.getId());
        SimpleSelector selector = new SimpleSelector(el, ontModel.getProperty(Constant.NS + "has_interest"), (RDFNode) null);
        StmtIterator iter = ontModel.listStatements(selector);
        while (iter.hasNext()) {
            Resource interest = (Resource) iter.nextStatement().getObject();
            SimpleSelector selector_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_concept_of_I"), interest);
            StmtIterator iter_con = ontModel.listStatements(selector_con);
            while (iter_con.hasNext()) {
                Resource concept = iter_con.nextStatement().getSubject();
                SimpleSelector selector_perform = new SimpleSelector(concept, ontModel.getProperty(Constant.NS + "is_concept_of_I"), (RDFNode) null);
                StmtIterator iter_perform = ontModel.listStatements(selector_perform);
                while (iter_perform.hasNext()) {
                    Resource perform = (Resource) iter_perform.nextStatement().getObject();
                    SimpleSelector selector_el = new SimpleSelector(null, ontModel.getProperty(Constant.NS + "is_concept_of_I"), perform);
                    StmtIterator iter_el = ontModel.listStatements(selector_el);
                    while (iter_el.hasNext()) {
                        Resource newEL = iter.nextStatement().getSubject();
                        if (!newEL.getLocalName().equals(elearner.getId())) {
                            ELearner recommendELearner = new ELearner(newEL.getLocalName());
                            recommendELearner.setName(newEL.getRequiredProperty(ontModel.getProperty(Constant.NS + "name")).getLiteral().getString());
                            Statement email = newEL.getRequiredProperty(ontModel.getProperty(Constant.NS + "email"));
                            if (email == null) {
                                recommendELearner.setEmail(" ");
                            } else {
                                recommendELearner.setEmail(email.getLiteral().getString());
                            }
                            Statement address = newEL.getRequiredProperty(ontModel.getProperty(Constant.NS + "address"));
                            if (address == null) {
                                recommendELearner.setAddress(" ");
                            } else {
                                recommendELearner.setAddress(address.getLiteral().getString());
                            }
                            Statement grade = newEL.getRequiredProperty(ontModel.getProperty(Constant.NS + "grade"));
                            if (grade == null) {
                                recommendELearner.setGrade(" ");
                            } else {
                                recommendELearner.setGrade(grade.getLiteral().getString());
                            }
                        }
                    }
                }
            }
        }
        return elearners;
    }

    public static ArrayList<EConcept> getRecommendEConcpetsInGoal(ELearnerModelImpl model, ELearner elearner, EGoal goal) {
        ArrayList<EConcept> cons = model.getEConceptsByGoal(goal);
        HashSet<EConcept> learnt = model.getLearntEConcept(elearner);
        cons.removeAll(learnt);
        ArrayList<EConcept> sorted = new ArrayList<EConcept>();
        for (int j = 0; j < cons.size(); j++) {
            EConcept con = cons.get(j);
            String id = con.getCid();
            if (sorted.isEmpty()) {
                sorted.add(con);
            } else {
                int size = sorted.size();
                boolean b = true;
                for (int i = 0; i < size; i++) {
                    EConcept con2 = sorted.get(i);
                    String id2 = con2.getCid();
                    if (ELearnerModelUtilMethod.compareEConceptIds(id, id2)) {
                        sorted.add(i, con);
                        b = false;
                        break;
                    }
                }
                if (b) {
                    sorted.add(con);
                }
            }
        }
        return sorted;
    }

    /***************************************************************************************************************
     * return null if the Recommend Concept list is empty
     * else return the concept C on the top of the sorted list if there is no more pre concept of C not learnt by user..
     * else there is any concept who is the pre concept of the given top concept, then return the pre concept first.
     * ***************************************************************************************************************/
    public static ArrayList<EConcept> getRecommendPreEConcpet(ELearnerModelImpl model, ELearner elearner, EGoal goal) {
        ArrayList<EConcept> cons = getRecommendEConcpetsInGoal(model, elearner, goal);
        if (cons.isEmpty()) {
            return null;
        }
        EConcept con = cons.get(0);
        System.out.println("rec con:" + con + "\t" + con.getCid());
        HashSet<EConcept> learnt = model.getLearntEConcept(elearner);
        ArrayList<EConcept> temp = new ArrayList<EConcept>();
        ArrayList<EConcept> pre = new ArrayList<EConcept>();
        pre.add(con);
        while (!pre.isEmpty()) {
            EConcept c = (EConcept) pre.toArray()[0];
            HashSet<EConcept> pre_temp = model.getPreEConcept(c.getCid());
            pre_temp.removeAll(learnt);
            if (pre_temp.isEmpty()) {
                temp.add(c);
                pre.remove(c);
            } else {
                pre.remove(c);
                pre.addAll(pre_temp);
            }
        }
        temp = ELearnerModelUtilMethod.sortRecommendEConcepts(temp);
        return temp;
    }

    public static void main(String[] args) {
        ELearnerModelImpl emi = new ELearnerModelImpl();
        EGoal goal = emi.getEGoal("goal_0001");
        ELearner el = emi.getELearner("el005");
        ArrayList<EConcept> cons = getRecommendEConcpetsInGoal(emi, el, goal);
        jena.ELearnerModelUtilMethod.printEConcepts(cons, "print recommend cons in goal");

        ArrayList<EConcept> preCons = getRecommendPreEConcpet(emi, el, goal);
        jena.ELearnerModelUtilMethod.printEConcepts(preCons, "print pre cons");
    }
}
