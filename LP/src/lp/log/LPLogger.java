/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.log;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
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
import db.WebOperation;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import util.Constant;
import util.MethodConstant;
import util.StringExchanger;

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
        try {
            String time = MethodConstant.getSysDateString();
            sb.append(time);
            sb.append(",");
            sb.append(URLEncoder.encode(userId, "UTF-8"));
            sb.append(",");
            sb.append(ip);
            sb.append(",");
            sb.append(action);
            sb.append(",");
            sb.append(URLEncoder.encode(data, "UTF-8"));
            sb.append(",");
            sb.append(URLEncoder.encode(result, "UTF-8"));
            sb.append(",");
            sb.append(1);
            sb.append(";");
            try {
                output.write(sb.toString());
            } catch (IOException ex) {
                Logger.getLogger(LPLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LPLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void writeMessage(String mes) {
//        try {
//            output.write(userId + ":" + getSysDate() + ":");
//            output.write(mes);
//        } catch (IOException ex) {
//            Logger.getLogger(LPLogger.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void sendLogs() throws MalformedURLException, IOException {
        //http://e6.thss.tsinghua.edu.cn/iscb/uploadLogs.jsp?logs=2010-03-18%2016:51:13,el001,192.168.1.1,120,data,result,1
        String u1 = Constant.ISCBSERVER250 + "iscb/uploadLogs.jsp?logs=";
        String urlString = u1 + sb.toString();
        URL url = new URL(urlString);
        System.out.println("URL:" + url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        String sCurrentLine = "";
        InputStream l_urlStream = connection.getInputStream();
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "gbk"));
        String result = "";
        String sTotalString = "";
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString += sCurrentLine + "\r\n";

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
//
//    public void writeLoginFailure() {
//        writeMessage("用户登录失败,错误ID:" + userId + "\n");
//    }

    public static void main(String[] args) throws MalformedURLException, IOException {
        String u1 = Constant.ISCBSERVER250 + "iscb/uploadLogs.jsp?logs=";
        String urlString = "http://e6.thss.tsinghua.edu.cn/iscb/uploadLogs.jsp?logs=2011-03-18 19:30:37,el001,ip,101,el001,%E7%99%BB%E5%85%A5,1;";
        URL url = new URL(urlString);
        System.out.println("URL:" + url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        String sCurrentLine = "";
//        String response = connection.getResponseMessage();
//        System.out.println("res:"+response);
        InputStream l_urlStream = connection.getInputStream();
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
        String result = "";
        String sTotalString = "";
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString += sCurrentLine + "\r\n";

        }
        System.out.println(sTotalString);
        System.out.println("result:" + result);
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
