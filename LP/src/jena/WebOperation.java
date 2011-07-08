/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import lp.LPApp;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;
import util.LogConstant;

/**
 *
 * @author student
 */
public class WebOperation {

    public static void main(String[] arsg) {
  
    }

    public static void registBroswer(String webSite) {
        runsBroswer(webSite);
        LPApp.lpLogs.writeLog("新用户注册-" + webSite , LogConstant.STATUS1012);
    }

    public static void searchMoreBrowser(String webSite) {
        LPApp.lpLogs.writeLog( "查询更多资源(更多查询)-" + webSite, LogConstant.STATUS110);
        runsBroswer(webSite);
    }

    public static void viewResourceBroswer (String webSite, String id,String isLearnt) {
        ELearner elearner = LPApp.getApplication().user.learner;
        String pid = "E_Portfolio_" + elearner.getId() + "_" + id;
        if (!LPApp.lpModel.containEPortfolio(pid)) {
            ISCB_Resource resource = LPApp.lpModel.getEResource(id);
            float value = 1;
            Date datetime = new Date(System.currentTimeMillis());
            EPortfolio port = new EPortfolio(pid, elearner, resource, value, datetime);
            LPApp.lpModel.addEPortfolio(port);
        }
        LPApp.lpLogs.writeLog( "浏览了资源:" + id+" 资源是否被学过："+ isLearnt, LogConstant.STATUS102);
        runsBroswer(webSite);
    }

    public static void runsBroswer(String webSite) {
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
