/*
 * LPApp.java
 */
package lp;

import db.WebOperation;
import jena.OwlOperation;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jena.impl.ELearnerModelImpl;
import lp.eresource.RadarPanel;
import lp.log.LPLogger;
import lp.log.PopCenterDialog;
import ontology.EConcept;
import ontology.EPerformance;
import ontology.people.ELearner;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import util.ColorConstant;
import util.Constant;

/**
 * The main class of the application.
 */
public class LPApp extends SingleFrameApplication {

    public EUser user;
    public LPView view;
    public LoginPanel loginPane;
    public static ELearnerModelImpl lpModel;
    public static LPLogger lpLogs;
    public static int TOOL_STATE = -1;
    public static final int MY_CONCEPT = 0;
    public static final int ALL_CONCEPT = 1;
    public static final int RECOMMEND = 2;
    public static final int SEARCH = 3;
    public static final int PROFILE = 4;
    public static final int ASSESSMENT = 5;
    public static final int NEW_MAP = 6;
    public static final int ADMIN = 7;
//    public static final int NAVIGATOR = 5;
//    public static final int REGIST = 6;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {

        String sepstr = File.separator;
        lpModel = null;
        user = null;
        view = new LPView(this);
        view.mainPanel.setLayout(new CentralLayout());
        loginPane = new LoginPanel();

        view.mainPanel.add(loginPane);
        ColorConstant.backgroundGrayColor = view.mainPanel.getBackground();
        view.lpToolBar.setVisible(false);
        view.getFrame().setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        show(view);

    }

    @Override
    protected void shutdown() {
        this.view.getFrame().setVisible(false);
        Date date = new Date(System.currentTimeMillis());
        File file = new File(Constant.OWLFile);
        String location = "";

        if (LPApp.getApplication().user != null && LPApp.getApplication().user.learner != null) {
            ELearner el = LPApp.getApplication().user.learner;
            getFile(el.getId() + ".owl", location);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                }
            }

            if (LPApp.lpModel != null) {
                //保存文件,发送日志
                System.out.println("");
                long t1 = System.currentTimeMillis();
                //  sendLogs();

                long t2 = System.currentTimeMillis();
                System.out.println("日志发送成功:耗时" + (t2 - t1) + "ms");
                saveToFile(file);
                long t3 = System.currentTimeMillis();
                System.out.println("保存文件成功:耗时" + (t3 - t2) + "ms");

            }
        }

        super.shutdown();
    }

    public void initModel() {
//        lpModel = new ELearnerModelImpl(new java.io.File(Constant.OWLFile));
//        ELearner el = LPApp.lpModel.getELearner(LPApp.getApplication().user.username);
//        if (el == null) {
//            LPApp.getApplication().user.learner = new ELearner(LPApp.getApplication().user.username);
//        } else {
//            LPApp.getApplication().user.learner = el;
//        }
        LPApp.getApplication().user.learner = new ELearner(LPApp.getApplication().user.username);
        lpLogs = new LPLogger();
        lpLogs.setUserId(user.username);
    }

    private void saveToFile(File file) {
        jena.impl.UserOwlUpdate.createNewDocWithEMI(LPApp.lpModel, LPApp.getApplication().user.learner);
        WebOperation.uploadUserFile(LPApp.getApplication().user.learner);
//        try {
//            OwlOperation.writeRdfFile(LPApp.lpModel.getOntModel(), new File(Constant.RDF_BAK_File), null);
//            OwlOperation.writeRdfFile(LPApp.lpModel.getOntModel(), file, null);
//            System.out.println("Complete saving the file before exiting the program.");
//            OwlOperation.writeOwlFileFromRdfFile(file, file);
//            System.out.println("Complete saving the backup model file in type of RDF before exiting the program.");
//
//        
//
//
//        } catch (IOException ex) {
//            Logger.getLogger(LPApp.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    private void sendLogs() {
        try {
            lpLogs.sendLogs();
            System.out.println("Logs Sent..");
        } catch (MalformedURLException ex) {
            Logger.getLogger(LPApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LPApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        lpLogs.close();

    }

    public File getFile(String name, String location) {
        return null;
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of LPApp
     */
    public static LPApp getApplication() {
        return Application.getInstance(LPApp.class);
    }

    public void popEPerformanceRadarDialog(EConcept con, ELearner el, lp.map.MapCallback cb) {

        EPerformance perform = LPApp.lpModel.getEPerformance(el, con);
        if (perform == null) {
            perform = new EPerformance();
            perform.setId("EPerformance_" + el.getId() + "_" + con.getCid());
            perform.setEConcept(con);
            perform.setELearner(el);
            perform.setValue(-1f);
        }
        perform.setDatetime(new Date(System.currentTimeMillis()));
        RadarPanel radar = new RadarPanel(perform);
        PopCenterDialog pcd = new PopCenterDialog("知识点自我评估", radar);
        cb.callback();
    }

    public void popEConceptViewDialog(EConcept con) {
        //  JDialog dialog = new JDialog(LPApp.getApplication().getMainFrame());
        ConceptPane cp = new ConceptPane();
        cp.initCon(con);
        PopCenterDialog dialog = new PopCenterDialog(cp);
        dialog.setTitle("知识点浏览");
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(LPApp.class, args);
        // new TestFrame();
    }
}
