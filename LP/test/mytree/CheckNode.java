package mytree;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import ontology.EConcept;

public class CheckNode extends DefaultMutableTreeNode {
 public final static int SINGLE_SELECTION = 0;
   public final static int DIG_IN_SELECTION = 4;
   protected int selectionMode;
   protected boolean isSelected;

   public CheckNode() {
     this(null);
   }

   public CheckNode(Object userObject) {
     this(userObject, true, false);
   }

   public CheckNode(Object userObject, boolean allowsChildren
                                     , boolean isSelected) {
     super(userObject, allowsChildren);
     this.isSelected = isSelected;
     setSelectionMode(DIG_IN_SELECTION);
   }


   public void setSelectionMode(int mode) {
     selectionMode = mode;
   }

   public int getSelectionMode() {
     return selectionMode;
   }

   public void setSelected(boolean isSelected) {
     this.isSelected = isSelected;

     if ((selectionMode == DIG_IN_SELECTION)
         && (children != null)) {
       Enumeration enumOne = children.elements();
       while (enumOne.hasMoreElements()) {
         CheckNode node = (CheckNode)enumOne.nextElement();
         node.setSelected(isSelected);
       }
     }
   }

   public boolean isSelected() {
     return isSelected;
   }

    @Override
    public String toString() {
	if (userObject == null) {
	    return null;
	} else {
            EConcept ec = (EConcept)userObject;
	    return ec.getCid();
	}
    }
   // If you want to change "isSelected" by CellEditor,
   /*
   public void setUserObject(Object obj) {
     if (obj instanceof Boolean) {
       setSelected(((Boolean)obj).booleanValue());
     } else {
       super.setUserObject(obj);
     }
   }
   */
}

