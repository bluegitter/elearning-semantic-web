package ontology.resources;

import java.util.Date;

public class ISCB_Resource extends E_Resource{

    protected String resourceType;
    protected String resourceQuality;
    protected Date uploadTime;
    protected String difficulty;
    protected String fileLocation;
    protected String postfix;

    public ISCB_Resource() {
    }

    public ISCB_Resource(String rid) {
        this.rid = rid;
    }

    public String getResourceQuality() {
        return resourceQuality;
    }

    public void setResourceQuality(String resourceQuality) {
        this.resourceQuality = resourceQuality;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    @Override
    public String toString() {
        return rid + "\t" + name + "\t" + difficulty + "\t" + fileLocation;
    }
}
