/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.log;

import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Constant;

/**
 *
 * @author student
 */
public class LPLogger {

    private ArrayList<String> mesList;
    private String userId;
    private String ip;
    private File logFile;
    private BufferedWriter output;

    public LPLogger() {
        logFile = new File(Constant.TestLogFile);
        init();
    }

    public LPLogger(File file) {
        logFile = file;
        init();
    }

    private void init() {
        mesList = new ArrayList<String>();
        userId = "userId";
        ip = "ip";
        try {
            output = new BufferedWriter(new FileWriter(logFile));
        } catch (IOException ioe) {
        }
    }

    public void writeMessage(String mes) {
        try {
            output.write(userId + ":" + getSysDate() + ":");
            output.write(mes);
        } catch (IOException ex) {
            Logger.getLogger(LPLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            output.flush();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(LPLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void makeLog(String message) {
    }

    public String getLog() {
        return mesList.toString();
    }

    public void addMessage(String message) {
        mesList.add(message);
    }

    public void writeLoginSuccessful() {
        writeMessage("用户登录成功:");
    }

    public void writeLoginFailure() {
        writeMessage("用户登录失败,错误ID:" + userId);
    }

    public String getSysDate() {
        Date date = new Date(System.currentTimeMillis());
        return date.toString();
    }

    public static void main(String[] args) {
        LPLogger lpl = new LPLogger();
        lpl.setUserId("el001");
        lpl.addMessage(lpl.getSysDate());
        System.out.println(lpl.getLog());
    }

    /***************************************************
     * GET & SET  methods
     * @return
     */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
