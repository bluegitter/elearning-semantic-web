package ontology.people;

public class ELearner extends People {

    private String peopleURL;
    private String password;
    private String grade = "";

    public ELearner(String eid, String password) {
        this.id = eid;
        this.password = password;
    }

    public ELearner(String eid, String _password, String _email, String _address) {
        this.id = eid;
        this.password = _password;
        this.email = _email;
        this.address = _address;
    }

    public ELearner() {
        id = "user";
        password = "password";
    }

    public ELearner(String eid) {
        this.id = eid;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ELearner) {
            if(!super.equals((People)o)){
                return false;
            }
            ELearner el = (ELearner) o;
            if (!this.grade.equals(el.getGrade())) {
                return false;
            }
            return true;
        }
        return false;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPeopleURL() {
        return peopleURL;
    }

    public void setPeopleURL(String peopleURL) {
        this.peopleURL = peopleURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(id).append("\t").append(name).append("\t").append(grade).append("\t").append(password).append("\n");
//        sb.append(grade).append("\t").append(email).append("\t").append(address);
//        return sb.toString();

        return this.name + "(" + this.id + ")";
    }
}
