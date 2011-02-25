/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.log;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author student
 */
public class LPLogger {

    private ArrayList<String> mesList;
    private String userId;
    private String ip;

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

    public LPLogger() {
        mesList = new ArrayList<String>();
    }

    public String getLog() {
        return mesList.toString();
    }

    public void addMessage(String message) {
        mesList.add(message);
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
}
