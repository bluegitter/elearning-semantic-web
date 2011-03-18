/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import db.WebOperation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ghh
 */
public class MethodConstant {

    public static Date getSysDate() {
        return new Date(System.currentTimeMillis());
    }

    public static String getSysDateString() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = dateFormat.format(date);
        return s;
    }

    public static void main(String[] args) throws MalformedURLException, IOException {
        String s = "http://e6.thss.tsinghua.edu.cn/iscb/uploadLogs.jsp?logs=2011-03-18 15:42:32,el001,192.168.1.1,120,data,result,1";
        s =new String(s.getBytes("UTF-8"));
        System.out.println("s:"+s);

         URL url = new URL(s);
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

        }
        System.out.println(sTotalString);
        System.out.println("result:" + result);
    }
}
