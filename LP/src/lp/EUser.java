package lp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontology.people.ELearner;
import util.Constant;

/**
 *
 * @author Shuaiguo
 */
public class EUser {

    public static void main(String[] args) {
        EUser us = new EUser("masheng");
        us.login("ma");
//        System.out.println("</body>");
    }
    public String username;
    public ELearner learner;

    public EUser(String username) {
        this.username = username;
    }

    public String login(String passwd) {
        try {
            //http://166.111.80.250/iscb/regUser.jsp?userName=el001&password=el&login=false
            StringBuilder sb = new StringBuilder();
            sb.append(Constant.ISCBSERVER250);
            sb.append("regUser.jsp?userName=");
            sb.append(username);
            sb.append("&password=");
            sb.append(passwd);
            sb.append("&login=false");
         //  System.out.println(sb.toString());
            URL url = new URL(sb.toString());
            try {
                String isChecked = sendRequest(url);
                if (isChecked.equals("true")) {
                    return null;
                } else {
                    return "登录失败！";
                }
            } catch (IOException ex) {
                Logger.getLogger(EUser.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(EUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "网络连接异常！";
    }

    public String sendRequest(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        String sCurrentLine = "";
        InputStream l_urlStream = connection.getInputStream();
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "gbk"));
        String temp = "";
        String result = "false";
        boolean getResult = false;
        String sTotalString = "";
        boolean flag = false;
        String name = "name";
        String email = "email";
        String company = "company";
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString += sCurrentLine + "\r\n";
            if (sCurrentLine.trim().equals("<body>")) {
                getResult = true;
                continue;
            }
            if (getResult) {
                if (!sCurrentLine.trim().equals("</body>")) {
                    if (!flag) {
                        temp = sCurrentLine.trim();
                        if (temp.equals("false")) {
                            result = "false";
                            return result;
                        } else {
                            result = "true";
                            flag = true;
                        }
                    }
                    String s = sCurrentLine.trim();
                    if (s.contains("name:")) {
                        name = s.substring(5);
                        LPApp.getApplication().user.learner.setName(name);
                        System.out.println("name:" + name);
                    }
                    if (s.contains("email:")) {
                        email = s.substring(6);
                        LPApp.getApplication().user.learner.setEmail(email);
                        System.out.println("email:" + email);
                    }
                    if (s.contains("company:")) {
                        company = s.substring(8);
                        LPApp.getApplication().user.learner.setAddress(company);
                        System.out.println("company:" + company);
                    }
                }
                if (sCurrentLine.trim().equals("</body>")) {
                    getResult = false;
                }
            }
        }
//        System.out.println(sTotalString);
        System.out.println("result:" + result);
        return result;
    }

}
