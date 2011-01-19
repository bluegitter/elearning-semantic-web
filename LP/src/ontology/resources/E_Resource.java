package ontology.resources;

public class E_Resource {

    protected String rid;
    protected String name;
    protected String resourceDescription;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof E_Resource) {
            E_Resource res = (E_Resource) o;
            if (!res.getRid().equals(this.rid)) {
                return false;
            } else if (!this.name.equals(res.getName())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.rid != null ? this.rid.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return rid + "\t" + name + "\t" + resourceDescription;
    }
}
