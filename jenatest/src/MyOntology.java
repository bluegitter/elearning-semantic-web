import java.util.Iterator;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;

public class MyOntology {
	public static void main(String[] args) {
		
		// 创建本体模型
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModel.read("file:./Creature.owl"); // 读取文件，加载模型

		// 定义一个类作为模型中Animal类的等等价类，并添加注释
		OntClass cls = ontModel.createClass(":DongwuClass");
		cls.addComment("the EquivalentClass of Animal...", "EN");
  
		OntClass oc = ontModel.getOntClass("http://www.owl-ontologies.com/marine.owl#Animal");
		oc.addEquivalentClass(cls); // 将先前定义的类添加为Animal的等价类

		// 迭代显示模型中的类
		for (Iterator i = ontModel.listClasses(); i.hasNext();) {
			OntClass c = (OntClass) i.next();
   
			if (!c.isAnon()) { 
				// 如果不是匿名类
				System.out.print("Class");
    
				// 获取类的URI并输出，在输出时对URI做了简化（将命名空间前缀省略）
				System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()));
    
				// 处理Animal类
				if (c.getLocalName().equals("Animal")) { // 如果当前类是Animal
					System.out.println("  URI@" + c.getURI()); // 输出它的完整URI    
					// 取得特定类的等价类
					System.out.print("  Animal's EquivalentClass is "+ c.getEquivalentClass());
					// 输出等价类的注释
					System.out.println(" [comments:" + c.getEquivalentClass().getComment("EN")+"]");
				}    
 
				// 迭代显示当前类的父类
				for (Iterator it = c.listSuperClasses(); it.hasNext();){
					OntClass sp = (OntClass) it.next();
					String str = c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()) // 获取URI
        + "'s superClass is " ;
					String strSP = sp.getURI();
					try{ 
						// 另一种简化处理URI的方法
						str = str + ":" + strSP.substring(strSP.indexOf('#')+1);
						System.out.println("  Class" +str);
					}
					catch( Exception e ){
					}
				}

				// 迭代显示当前类的子类
				for (Iterator it = c.listSubClasses(); it.hasNext();){
					System.out.print("  Class");
					OntClass sb = (OntClass) it.next();
					System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()) + "'s suberClass is " + sb.getModel().getGraph().getPrefixMapping().shortForm(sb.getURI()));
				}
    
				// 迭代显示与当前类相关的所有属性
				for(Iterator ipp = c.listDeclaredProperties(); ipp.hasNext();){
					OntProperty p = (OntProperty)ipp.next();
					System.out.println("  associated property: " + p.getLocalName());
				}
			} 
		}
	}
}

