package lp;

import ontology.people.ELearner;

/**
 *
 * @author Shuaiguo
 */
public class EUser {

    public String username;
    public ELearner learner;

    public EUser(String username) {
        this.username = username;
    }

    public boolean login(String passwd) {
//        try {
//            return db.DbOperation.login(username, passwd);
//        } catch(Exception ex) {
//            return false;
//        }
        return true;
    }

    public boolean reg(String passwd) {
        try {
            return db.DbOperation.addELearner(new ELearner(username, passwd));
        } catch(Exception ex) {
            return false;
        }
    }

     public boolean regist(String passwd,String email,String address) {
        try {
            return db.DbOperation.addELearner(new ELearner(username, passwd , email,address));
        } catch(Exception ex) {
            return false;
        }
    }
}
