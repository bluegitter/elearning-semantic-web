package ontology.resources;

import java.util.Date;

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

    public String toString() {
        return rid + "\t" + name + "\t" + resourceDescription;
    }
}
