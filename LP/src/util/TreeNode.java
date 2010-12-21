package util;

import java.util.List;
import java.util.ArrayList;
public class TreeNode implements java.io.Serializable
{
private int level;
protected String nodeName; 
protected String nodeId; 
protected TreeNode parentNode;
protected List<TreeNode> childList;
public TreeNode()
{
   initChildList();
}
public TreeNode(String nodeName,String nodeId,int level)
{
//   this.setParentNode(parentNode);
   this.setNodeName(nodeName);
   this.setNodeId(nodeId);

   initChildList();
}

public TreeNode(TreeNode parentNode,String nodeName,String nodeId)
{
   this.setParentNode(parentNode);
   this.setNodeName(nodeName);
   this.setNodeId(nodeId);
   this.setLevel(parentNode.getLevel()+1);
   initChildList();
}


public boolean isLeaf()
{
   if (childList == null)
   {
    return true;
   }
   else
   {
    if (childList.isEmpty())
    {
     return true;
    }
    else
    {
     return false;
    }
   }
}

public void addChildNode(TreeNode treeNode)
{
   initChildList();
   childList.add(treeNode);
   treeNode.setParentNode(this);
   
}

public void initChildList()
{
   if (childList == null)
    childList = new ArrayList<TreeNode>();
}


//public List<TreeNode> getElders()
//{
//	List<TreeNode> elderList=new ArrayList<TreeNode>();
//	TreeNode parentNode=this.getParentNode();
//	if(parentNode==null)
//	{
//	return elderList;
//	}
//	else
//	{
//	elderList.add(parentNode);
//	elderList.addAll(parentNode.getElders());
//	return elderList;
//	}
//}
//
//public List<TreeNode> getJuniors()
//{
//	List<TreeNode> juniorList=new ArrayList<TreeNode>();
//	List<TreeNode> childList=this.getChildList();
//	if(childList==null)
//	{
//	return juniorList;
//	}
//	else
//	{
//	int childNumber=childList.size();
//	for(int i=0;i<childNumber;i++)
//	{
//	TreeNode junior=childList.get(i);
//	juniorList.add(junior);
//	juniorList.addAll(junior.getJuniors());
//	}
//	return juniorList;
//	}
//}

public List<TreeNode> getChildList()
{
   return childList;
}

public void deleteNode()
{
   TreeNode parentNode = this.getParentNode();
   if (parentNode != null) {
    parentNode.deleteChildNode();
   }
}

public void deleteChildNode()
{
   List<TreeNode> childList = this.getChildList();
   int childNumber = childList.size();
   for (int i = 0; i < childNumber; i++) {
    TreeNode child = childList.get(i);
        childList.remove(i);
   }
}

//	public boolean insertJuniorNode(TreeNode treeNode) {
//		int juniorparentIndex = treeNode.getparentIndex();
//		if (this.parentIndex == juniorparentIndex) {
//			addChildNode(treeNode);
//			return true;
//		} else {
//			List<TreeNode> childList = this.getChildList();
//			int childNumber = childList.size();
//			boolean insertFlag;
//			for (int i = 0; i < childNumber; i++) {
//				TreeNode childNode = childList.get(i);
//				insertFlag = childNode.insertJuniorNode(treeNode);
//				if (insertFlag == true)
//					return true;
//			}
//			return false;
//		}
//	}

//public TreeNode findTreeNodeByIndex(int index)
//{
//   if (this.selfIndex == index)
//    return this;
//   if (childList.isEmpty() || childList == null) {
//    return null;
//   } else {
//    int childNumber = childList.size();
//    for (int i = 0; i < childNumber; i++) {
//     TreeNode child = childList.get(i);
//     TreeNode resultNode = child.findTreeNodeByIndex(index);
//     if (resultNode != null) {
//      return resultNode;
//     }
//    }
//    return null;
//   }
//}

//public void traverse()
//{
//   if (selfIndex < 0)
//    return;
//   print(this.selfIndex);
//   if (childList == null || childList.isEmpty())
//    return;
//   int childNumber = childList.size();
//   for (int i = 0; i < childNumber; i++)
//   {
//    TreeNode child = childList.get(i);
//    child.traverse();
//   }
//}
public void print(String content)
{
   System.out.println(content);
}
public void print(int content)
{
   System.out.println(String.valueOf(content));
}
public void setChildList(List<TreeNode> childList)
{
   this.childList = childList;
}

public void setLevel(int level)
{
   this.level= level;
}
public int getLevel()
{
   return this.level;
}
public TreeNode getParentNode()
{
   return parentNode;
}
public void setParentNode(TreeNode parentNode)
{
   this.parentNode = parentNode;
}
public String getNodeName()
{
   return nodeName;
}
public String getNodeId()
{
   return nodeId;
}
public void setNodeName(String nodeName)
{
   this.nodeName = nodeName;
}
public void setNodeId(String nodeId)
{
   this.nodeId = nodeId;
}
}