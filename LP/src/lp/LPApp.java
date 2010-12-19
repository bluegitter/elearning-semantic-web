/*
 * LPApp.java
 */

package lp;

import jena.ELearnerModelImpl;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class LPApp extends SingleFrameApplication {

    public EUser user;
    public LPView view;

    public static ELearnerModelImpl lpModel;

    public static int TOOL_STATE = -1;

    public static final int MY_CONCEPT = 0;
    public static final int ALL_CONCEPT = 1;
    public static final int RECOMMEND = 2;
    public static final int SEARCH = 3;
    public static final int PROFILE = 4;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        lpModel = new ELearnerModelImpl();

        user = null;
        view = new LPView(this);
        view.mainPanel.setLayout(new CentralLayout());
        view.mainPanel.add(new LoginPanel());
        view.lpToolBar.setVisible(false);
        show(view);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of LPApp
     */
    public static LPApp getApplication() {
        return Application.getInstance(LPApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(LPApp.class, args);
    }
}