package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ontology.EConcept;

import db.DbOperation;

public class ConceptToTree {

	
	private static TreeNode rootNode = new TreeNode ("rootname","rootid",0);
	private static List<EConcept> conceptList = new ArrayList();
	private static List<TreeNode> treeList = new ArrayList();
	private static int TreeHight = 0;
	
	public ConceptToTree() throws Exception
	{
		this.init();
	}
	
	public void init() throws Exception
	{
		this.conceptList = null;
			//DbOperation.getAllEConcepts();
		treeList.add(rootNode);
		setTreeHeight();
		setParentSonRelation();
	}
 	
	public static TreeNode getRootNode()
	{
		return rootNode;
	}
	
	public static int getconceptListSize()
	{
		return conceptList.size();
	}

	//设置树的高度 TreeHight=0表示只有根节点
	public void setTreeHeight()
	{
		int height=0;
		int size = getconceptListSize();
		if (size <=0)
			this.TreeHight = 0;
		else if (size == 1)
			this.TreeHight = 1;
		else{
			height = 2;
			String temp[] = null;
			for(int i=0;i<size;i++)
			{
				EConcept conceptNode = conceptList.get(i);
				String conceptid = conceptNode.getCid();
				String conceptname = conceptNode.getName();
				temp = conceptid.split("\\.");
				treeList.add(new TreeNode(conceptname,conceptid,temp.length));
				
				if ((height-1) < temp.length)
					height = temp.length + 1;
			}
			this.TreeHight = height;
		}
	}
	//返回树的高度 TreeHight=0表示只有根节点
	public int getTreeHeight()
	{
		return this.TreeHight;
	}

	//已经设定了每个node的level，此函数关联父子节点
	public static void setParentSonRelation() throws Exception
	{
		List<TreeNode> nodes =  new ArrayList<TreeNode>();
		int size;
		//if(level >= 0 && level <=(TreeHight-1))
		//{
		nodes.add(rootNode);
		int level;
		for(level = 1; level<TreeHight;level++)
		{
			size = getconceptListSize();
			for(int i=0;i<size;i++)
			{
				EConcept conceptNode = conceptList.get(i);
				Pattern pattern = Pattern.compile("^([a-zA-Z0-9]+[\\.]{1}){"+(level-1)+"}[a-zA-Z0-9]+$");
				Matcher matcher = pattern.matcher(conceptNode.getCid());
				if (matcher.find())
				{
					System.out.println(matcher.group()+"===hahahahahaha");
					nodes.add(new TreeNode());
				}
			}
		}
	}
	
	public static void addAllNodes (TreeNode tn,int level) throws Exception
	{

		if(level == 1)
		{
	        //得到第一层的节点
		//	List<TreeNode> nodes = getNodes(1);
			List<TreeNode> nodes = null;
			for(int j=0;j<nodes.size();j++)
			{
				TreeNode tnode = nodes.get(j);
				tnode.setParentNode(tn);
				tn.addChildNode(tnode);
				addAllNodes(tnode,level+1);				
			}				
		}
		else
		{
			addChilds(tn,level);
		}

	}

	
	public static void addChilds(TreeNode tn,int level) throws Exception
	{
		String strId = tn.getNodeId();
		List<EConcept> list = null;
		//DbOperation.getEConceptsOfLevel(level);
		for(int i =0; i<list.size();i++)
		{
			String regId = list.get(i).getCid();
			String regName = list.get(i).getName();
			if(regId.startsWith(strId))
			{
				
				TreeNode child = new TreeNode(tn,regName,regId);
				tn.addChildNode(child);
				addChilds(child,level+1);
			}
		}
		
	}
	
	public static void printTree()
	{
		List<TreeNode> list = rootNode.getChildList();
		printList(list ,1);
			
	}
		
	
	public static void printList(List<TreeNode> list,int level)
	{
		System.out.println("=======level "+level+"=======");
		for(int j = 0;j<list.size();j++)
		{
			TreeNode tn = list.get(j);
			printList(tn.getChildList(),level+1);
			
		}
		
	}
	
}
