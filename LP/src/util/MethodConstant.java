/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Date;

/**
 *
 * @author ghh
 */
public class MethodConstant {
    public static Date getSysDate(){
        return  new Date(System.currentTimeMillis());
    }
    public static String getSysDateString(){
         Date date = new Date(System.currentTimeMillis());
        return date.toString();
    }
}
