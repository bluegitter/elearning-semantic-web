/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
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
        //  runsBroswer("www.163.com");
        //uploadUserFile(new ELearner("el001"));
        downloadUserFile(new ELearner("el008"));
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

        ELearner elearner = LPApp.getApplication().user.learner;
        String pid = "E_Portfolio_" + elearner.getId() + "_" + id;
        if (!LPApp.lpModel.containEPortfolio(pid)) {
            ISCB_Resource resource = LPApp.lpModel.getEResource(id);
            float value = 1;
            Date datetime = new Date(System.currentTimeMillis());
            EPortfolio port = new EPortfolio(pid, elearner, resource, value, datetime);
            LPApp.lpModel.addEPortfolio(port);
        }
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

    public static boolean downloadUserFile(ELearner el) {
        String url = UploaderConstants.DOWNLOAD_URL_STRING + "?elearner_id=" + el.getId();
        File f = new File("files/owl/" + el.getId() + ".owl");
        System.out.println(url);
//        runsBroswer(UploaderConstants.DOWNLOAD_URL_STRING + "?elearner_id=" + el.getId());
        byte[] buffer = new byte[8 * 1024];
        URLConnection connection = null;
        try {
            URL u = new URL(url);
            connection = u.openConnection();
        } catch (Exception e) {
            System.out.println("ERR:" + url);
        }
        connection.setReadTimeout(100000);
        InputStream is = null;
        FileOutputStream fos = null;
        
        try {
            f.createNewFile();
            is = connection.getInputStream();
            fos = new FileOutputStream(f);
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }

        } catch (Exception e) {
            f.delete();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        buffer = null;
        if(f.exists()){
            return true;
        }return false;
        // System.gc();
    }
}
