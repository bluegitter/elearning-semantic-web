/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.eresource;

/**
 *
 * @author t
 */
public class UriLabel {

    private String name;
    private String uri;

    public UriLabel() {
    }

    public UriLabel(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    public String toString(){
        return name;
    }
}
