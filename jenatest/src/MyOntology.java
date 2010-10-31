import java.util.Iterator;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;

public class MyOntology {
	public static void main(String[] args) {
		
		// ��������ģ��
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModel.read("file:./Creature.owl"); // ��ȡ�ļ�������ģ��

		// ����һ������Ϊģ����Animal��ĵȵȼ��࣬�����ע��
		OntClass cls = ontModel.createClass(":DongwuClass");
		cls.addComment("the EquivalentClass of Animal...", "EN");
  
		OntClass oc = ontModel.getOntClass("http://www.owl-ontologies.com/marine.owl#Animal");
		oc.addEquivalentClass(cls); // ����ǰ����������ΪAnimal�ĵȼ���

		// ������ʾģ���е���
		for (Iterator i = ontModel.listClasses(); i.hasNext();) {
			OntClass c = (OntClass) i.next();
   
			if (!c.isAnon()) { 
				// �������������
				System.out.print("Class");
    
				// ��ȡ���URI������������ʱ��URI���˼򻯣��������ռ�ǰ׺ʡ�ԣ�
				System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()));
    
				// ����Animal��
				if (c.getLocalName().equals("Animal")) { // �����ǰ����Animal
					System.out.println("  URI@" + c.getURI()); // �����������URI    
					// ȡ���ض���ĵȼ���
					System.out.print("  Animal's EquivalentClass is "+ c.getEquivalentClass());
					// ����ȼ����ע��
					System.out.println(" [comments:" + c.getEquivalentClass().getComment("EN")+"]");
				}    
 
				// ������ʾ��ǰ��ĸ���
				for (Iterator it = c.listSuperClasses(); it.hasNext();){
					OntClass sp = (OntClass) it.next();
					String str = c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()) // ��ȡURI
        + "'s superClass is " ;
					String strSP = sp.getURI();
					try{ 
						// ��һ�ּ򻯴���URI�ķ���
						str = str + ":" + strSP.substring(strSP.indexOf('#')+1);
						System.out.println("  Class" +str);
					}
					catch( Exception e ){
					}
				}

				// ������ʾ��ǰ�������
				for (Iterator it = c.listSubClasses(); it.hasNext();){
					System.out.print("  Class");
					OntClass sb = (OntClass) it.next();
					System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()) + "'s suberClass is " + sb.getModel().getGraph().getPrefixMapping().shortForm(sb.getURI()));
				}
    
				// ������ʾ�뵱ǰ����ص���������
				for(Iterator ipp = c.listDeclaredProperties(); ipp.hasNext();){
					OntProperty p = (OntProperty)ipp.next();
					System.out.println("  associated property: " + p.getLocalName());
				}
			} 
		}
	}
}

