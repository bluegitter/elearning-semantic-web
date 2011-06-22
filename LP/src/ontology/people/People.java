package ontology.people;

public class People {

    public People() {
    	gender = "male";
    	name = "name";
    	email = "";
        gender ="";
        address="";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof People) {
            People el = (People) o;
            if (!this.id.equals(el.getId())) {
                return false;
            }
            if (!this.name.equals(el.getName())) {
                return false;
            }
             if (!this.email.equals(el.getEmail())) {
                return false;
            }
            if (!this.address.equals(el.getAddress())) {
                return false;
            }
            return true;
        }
        return false;
    }
    protected String id;
    protected String name;
    protected String gender;
    protected String email;
    protected String address;
}
