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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import lp.eresource.WebOperation;
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

    public void writeLog(int action, String data, String result, String status) {
        StringBuilder sb = new StringBuilder();
        sb.append("time");
        sb.append(",");
        sb.append(userId);
        sb.append(",");
        sb.append(ip);
        sb.append(",");
        sb.append(action);
        sb.append(",");
        sb.append(data);
        sb.append(",");
        sb.append(result);
        sb.append(",");
        sb.append("status");
        sb.append(";");
        try {
            output.write(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(LPLogger.class.getName()).log(Level.SEVERE, null, ex);
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
    public void sendLog(){
        String url = Constant.ISCBSERVER250+"uploadLogs.jsp?logs=";
    }
    public void close() {
        try {
            output.flush();
            output.close();


        } catch (IOException ex) {
            Logger.getLogger(LPLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getLog() {
        return mesList.toString();
    }

    public void addMessage(String message) {
        mesList.add(message);
    }

    public void writeLoginFailure() {
        writeMessage("用户登录失败,错误ID:" + userId + "\n");
    }

    public String getSysDate() {
        Date date = new Date(System.currentTimeMillis());
        return date.toString();
    }

    public static void main(String[] args) {
//        LPLogger lpl = new LPLogger();
//        lpl.setUserId("el001");
//       // lpl.addMessage(lpl.getSysDate());
//        lpl.writeLog("hello");
//        System.out.println(lpl.getLog());
    }

    /***************************************************
     * GET & SET  methods
     * @return
     */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                System.out.println("DisplayName:" + ni.getDisplayName());
                System.out.println("Name:" + ni.getName());
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    System.out.println("IP:"
                            + ips.nextElement().getHostAddress());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
