package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TreeHelper implements java.io.Serializable
{
private TreeNode root;
private List<TreeNode> tempNodeList;
public TreeHelper()
{
}
//public TreeHelper(List<TreeNode> treeNodeList)
//{
//   tempNodeList = treeNodeList;
//   generateTree();
//}
//
////public void generateTree()
////{
////   HashMap nodeMap = putNodesIntoMap();
////   putChildIntoParent(nodeMap);
////}
//
////protected HashMap putNodesIntoMap()
////{
////   HashMap nodeMap = new HashMap<String, TreeNode>();
////   Iterator it = tempNodeList.iterator();
////   while (it.hasNext())
////   {
////    TreeNode treeNode = (TreeNode) it.next();
////    int index = treeNode.getselfIndex();
////    String keyIndex= String.valueOf(index);
////    nodeMap.put(keyIndex, treeNode);
////   }
////   return nodeMap;
////}
//
//protected void putChildIntoParent(HashMap nodeMap)
//{
//   Iterator it = nodeMap.values().iterator();
//   while (it.hasNext())
//   {
//    TreeNode treeNode = (TreeNode) it.next();
//    int parentId = treeNode.getparentIndex();
//    if(parentId==0)
//    {
//     this.setRoot(treeNode);
//    }
//    else if(parentId!=treeNode.getselfIndex())
//    {
//     String parentKeyIndex = String.valueOf(parentId);
//     if (nodeMap.containsKey(parentKeyIndex))
//     {
//      TreeNode parentNode = (TreeNode) nodeMap.get(parentKeyIndex);
//      if (parentNode == null) {
//       return;
//      }
//      else
//      {
//       parentNode.addChildNode(treeNode);
//       System.out.println("childIndex: " +treeNode.getselfIndex()+" "+
//         "parentIndex: "+parentNode.getselfIndex());
//      }
//     }
//    }
//   }
//}
//
//protected void initTempNodeList()
//{
//   if (this.tempNodeList == null)
//   {
//    this.tempNodeList = new ArrayList<TreeNode>();
//   }
//}
//
//public void addTreeNode(TreeNode treeNode)
//{
//   initTempNodeList();
//   this.tempNodeList.add(treeNode);
//}
//
//public boolean insertTreeNode(TreeNode treeNode)
//{
//   boolean insertFlag = root.insertJuniorNode(treeNode);
//   return insertFlag;
//}
//public TreeNode getRoot()
//{
//   return root;
//}
//public void setRoot(TreeNode root)
//{
//   this.root = root;
//}
//public List<TreeNode> getTempNodeList()
//{
//   return tempNodeList;
//}
//public void setTempNodeList(List<TreeNode> tempNodeList)
//{
//   this.tempNodeList = tempNodeList;
//}

public static void main(String[] args) throws Exception
{
//	TreeNode tn = ConceptToTree.getRootNode();
//	ConceptToTree.addAllNodes (tn,1);
//	ConceptToTree.printTree();
	
//	String str2 = "MAA.T";
//	String str = "MAA.T.y";
//	String temp[] = null;
//	String temp2[] = null;
//	temp = str.split("\\.");
//	temp2 = str2.split("\\.");
//	
//	System.out.println(temp.length);
//	System.out.println(temp2.length);
	
	
//	String str = "MAA.T.y";
//	String str2 = ".MAA.T";
//	List<String> s = new ArrayList<String>();
//	s.add(str);
//	s.add(str2);
//	Pattern pattern1 = Pattern.compile("^([a-zA-Z0-9]+[\\.]{1}){"+2+"}[a-zA-Z0-9]+$");
//	Matcher matcher1 = pattern1.matcher(str2);
//	if(matcher1.find())
//	{
//		System.out.print(matcher1.group());
//	}
	
}
}
