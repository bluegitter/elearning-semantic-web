package ontology.resources;

import java.util.Date;

public class ISCB_Resource extends E_Resource {

    protected String resourceType;
    protected String resourceQuality;
    protected String appType;
    protected Date uploadTime;
    protected String difficulty;
    protected String fileLocation;
    protected String postfix;

    public String getIsLearntResult() {
        return "未学过";
    }

    public ISCB_Resource() {
        this.rid = "newRID";
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
        char cs[] = fileLocation.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : cs) {
            if (c == '\\') {
                sb.append('/');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
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

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    @Override
    public String toString() {
        return name;
    }
}
