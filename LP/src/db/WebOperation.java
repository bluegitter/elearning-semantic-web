/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import lp.LPApp;
import ontology.people.ELearner;
import util.LogConstant;

/**
 *
 * @author student
 */
public class WebOperation {

    public static void main(String[] arsg) {
        //  runsBroswer("www.163.com");
        //uploadUserFile(new ELearner("el001"));
        downloadUserFile(new ELearner("el001"));
    }

    public static void registBroswer(String webSite) {
        runsBroswer(webSite);
        LPApp.lpLogs.writeLog(101, "新用户注册-" + webSite, "注册", LogConstant.STATUS101);
    }

    public static void searchMoreBrowser(String webSite) {
        LPApp.lpLogs.writeLog(110, "查询更多资源-" + webSite, "更多查询", LogConstant.STATUS110);
        runsBroswer(webSite);
    }

    public static void viewResourceBroswer(String webSite, String id, String result) {
        LPApp.lpLogs.writeLog(102, "浏览了资源-" + id, result, LogConstant.STATUS102);
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

    //将个人用户信息上传到服务器上
    public static void uploadUserFile(ELearner el) {
        UploaderComm uc = new UploaderComm(el);
        uc.uploadFiles(false);
    }

    public static void downloadUserFile(ELearner el) {
        runsBroswer(UploaderConstants.DOWNLOAD_URL_STRING + "?elearner_id=" + el.getId());
    }

}
