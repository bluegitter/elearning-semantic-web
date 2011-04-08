package ontology;

public class EConcept {

    public EConcept() {
        cid = "tempCID";
        name = "name";
        difficulty = "diff";
    }

    public EConcept(String cid) {
        this.cid = cid;
        this.name = "name";
        difficulty = "diff";
    }

    public EConcept(String cid, String cname) {
        this.cid = cid;
        this.name = cname;
        difficulty = "diff";
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EConcept) {
            EConcept con = (EConcept) o;
            if (!this.cid.equals(con.getCid())) {
                return false;
            }
            if (!this.name.equals(con.getName())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.cid != null ? this.cid.hashCode() : 0);
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    private String cid;
    private String name;
    private String difficulty;
}
