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

    public boolean login(String passwd) {
      try {
            //        try {
            //            return db.DbOperation.login(username, passwd);
            //        } catch(Exception ex) {
            //            return false;
            //        }
            //http://166.111.80.250/iscb/regUser.jsp?userName=masheng&password=masheng&login=false
            StringBuilder sb = new StringBuilder();

            sb.append(Constant.LoginRequest1);
            sb.append(username);
            sb.append(Constant.LoginRequest2);
            sb.append(passwd);
            sb.append(Constant.LoginRequest3);
            System.out.println(sb.toString());
            URL url = new URL(sb.toString());
            try {
                String isChecked = sendRequest(url);
                if(isChecked.equals("true")){
                    return true;
                }else{
                    return false;
                }
            } catch (IOException ex) {
                Logger.getLogger(EUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(EUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    public String sendRequest(URL url) throws IOException{
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        String sCurrentLine = "";
//        String sTotalString = "";
        InputStream l_urlStream = connection.getInputStream();
        // 传说中的三层包装阿！
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
        String result = "";
        boolean getResult =false;
        while ((sCurrentLine = l_reader.readLine()) != null) {
//            sTotalString += sCurrentLine + "\r\n";
            if(sCurrentLine.trim().equals("<body>")){
                getResult = true;
            }
            if(getResult && !sCurrentLine.trim().equals("</body>")){
               result =  sCurrentLine.trim();
            }
            if(getResult && sCurrentLine.trim().equals("</body>")){
                getResult = false;
            }
        }
//        System.out.println(sTotalString);
        System.out.println("result:"+result);
        return result;
    }

    public boolean reg(String passwd) {
        try {
            return db.DbOperation.addELearner(new ELearner(username, passwd));
        } catch(Exception ex) {
            return false;
        }
    }

     public boolean regist(String passwd,String email,String address) {
        try {
            return db.DbOperation.addELearner(new ELearner(username, passwd , email,address));
        } catch(Exception ex) {
            return false;
        }
    }
}
