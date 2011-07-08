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
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.SwingUtilities;
import lp.LPApp;
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
        init();
    }

//    public LPLogger(File file) {
//        logFile = file;
//        init();
//    }
    private void createNewLogFileOnDisk() {
        try {
            Date date = MethodConstant.getSysDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String s = dateFormat.format(date);
            logFile = new File(Constant.LogFileDirectory + LPApp.getApplication().user.learner.getId() + "-" + s + ".log");
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            output = new BufferedWriter(new FileWriter(logFile));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void init() {
        mesList = new ArrayList<String>();
        userId = "userId";
        ip = "ip";
        sb = new StringBuilder();
        output = null;
        // createNewLogFileOnDisk();
    }

    public void writeLog(final String log, final String status) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                sendLog(log, status);
            }
        });

    }

    public void sendLog(String log, String status) {
        try {
            String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");
            data += "&" + URLEncoder.encode("log", "UTF-8") + "=" + URLEncoder.encode(log, "UTF-8");
            data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");

            // Send data
            URL url = new URL(Constant.LOG_PHP);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();
            // Get the response
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String head = br.readLine();
            if (head.trim().equals("success")) {
            //    System.out.println("send a log");
            } else {
              //  System.out.println("send failed");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void writeLog_(int action, String data, String result, String status) {
        String time = MethodConstant.getSysDateString();
        sb.append(time);
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
        sb.append(1);
        sb.append(";");
        try {
            if (output != null) {
                output.write(sb.toString());
            }

        } catch (IOException ex) {
            Logger.getLogger(LPLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//
//    public void sendLogs() {
//    }

    public void sendLogs_bak() throws MalformedURLException, IOException {
        //http://e6.thss.tsinghua.edu.cn/iscb/uploadLogs.jsp?logs=2010-03-18%2016:51:13,el001,192.168.1.1,120,data,result,1
        String u1 = Constant.ISCBSERVER250 + "uploadLogs.jsp?logs=";
        String urlString = u1 + URLEncoder.encode(sb.toString(), "UTF-8");
        URL url = new URL(urlString);
        // System.out.println("URL:" + url);
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
        //   System.out.println(sTotalString);
        //  System.out.println("result:" + result);
    }

    public void close() {
        try {
            if (output != null) {
                output.flush();
                output.close();
            }
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
        LPLogger lp = new LPLogger();
        lp.setUserId("el001");
        lp.writeLog("log", "test");
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
