/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.log;

import java.io.BufferedReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import lp.eresource.WebOperation;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import util.Constant;
import util.MethodConstant;

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
    private StringBuilder sb;

    public LPLogger() {
        Date date = MethodConstant.getSysDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String s = dateFormat.format(date);
        logFile = new File(Constant.TestLogFile + s);

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException ex) {
            }
        }
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
        sb = new StringBuilder();
        try {
            output = new BufferedWriter(new FileWriter(logFile));
        } catch (IOException ioe) {
        }
    }

    public void writeLog(int action, String data, String result, String status) {
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
        sb.append(status);
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

    public void sendLog() throws MalformedURLException, IOException {
        String u1 = Constant.ISCBSERVER250 + "uploadLogs.jsp?logs=";
        URL url = new URL(u1 + sb.toString());
        System.out.println("URL:" + url);
        URLConnection connection = (URLConnection) url.openConnection();
        connection.setDoOutput(true);

        String sCurrentLine = "";
        InputStream l_urlStream = connection.getInputStream();
        // 传说中的三层包装阿！
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
        String result = "";
        boolean getResult = false;
        String sTotalString = "";
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString += sCurrentLine + "\r\n";
//            if(sCurrentLine.trim().equals("<body>")){
//                getResult = true;
//            }
//            if(getResult && !sCurrentLine.trim().equals("</body>")){
//               result =  sCurrentLine.trim();
//            }
//            if(getResult && sCurrentLine.trim().equals("</body>")){
//                getResult = false;
//            }
        }
        System.out.println(sTotalString);
        System.out.println("result:" + result);
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

    public static void main(String[] args) throws MalformedURLException, IOException {
        LPLogger lpl = new LPLogger();
        lpl.setUserId("el001");
        lpl.sendLog();
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
