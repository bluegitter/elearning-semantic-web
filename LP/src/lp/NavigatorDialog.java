/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lp;

import java.util.ArrayList;
import javax.swing.JDialog;
import ontology.EInterest;

/**
 *
 * @author ghh
 */
public class NavigatorDialog extends JDialog{
    public ArrayList<EInterest> nodes = new ArrayList<EInterest>();
    public javax.swing.JPanel[] panels = {new RegistPane(this), new NavigatorPane(this),new NavigatorFinishPane(nodes,this),new Navigator_MyConcept1(this), new Navigator_AllConcept1(this),new Navigator_AllConcept2(this),new Navigator_Recommend(this)};
    private int panel_index = 0;

    public NavigatorDialog(javax.swing.JFrame f) {
        super(f);
        this.setContentPane(panels[panel_index]);
        this.setResizable(false);
    }

    public void setNext() {
        this.panel_index++;
        if(panel_index == 2)
        {
            panels[2] = new NavigatorFinishPane(nodes,this);
        }
        this.setContentPane(panels[panel_index]);
        this.pack();
    }
/*
    public void setNext(ArrayList<EInterest> nodes2)
    {
        this.nodes = nodes2;
        panels[2] = new NavigatorFinishPane(nodes,this);
        this.panel_index++;
        this.setContentPane(panels[panel_index]);
        this.pack();
    }
 
 */

    public void setPrevious() {
        this.panel_index--;
        this.setContentPane(panels[panel_index]);
        if(panel_index == 1)
        {
            panels[1] = new NavigatorPane(this);
          // ((NavigatorPane)this.getContentPane()).selectedNodes = new ArrayList();
        }
        this.pack();
    }

}
