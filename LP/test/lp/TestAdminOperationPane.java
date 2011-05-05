/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lp;

import java.util.HashSet;
import lp.admin.TableTemplate;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class TestAdminOperationPane {
    public static void main2(String[] args) {
        javax.swing.JFrame f = new javax.swing.JFrame();
        f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
       // AdminOperatorPane pane = new AdminOperatorPane();
        TableTemplate pane = new TableTemplate();
        HashSet<Object> num = new HashSet<Object>();
        for(int i = 0;i<25;i++){
            num.add(new Integer(i));
        }
        pane.setData(num);
        pane.initTable();
        f.add(pane);
        f.pack();
        f.setVisible(true);

    }
    
}
