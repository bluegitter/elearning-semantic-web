/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.eresource;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author student
 */
public class WebOperation {

    public static void main(String[] arsg) {
        runBroswer("www.163.com");
    }

    public static void runBroswer(String webSite) {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri = new URI(webSite);
                desktop.browse(uri);
            }
        } catch (IOException ex) {
            System.out.println("io exception :" + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.out.println("URISyntaxException :" + ex.getMessage());
        }
    }
}
