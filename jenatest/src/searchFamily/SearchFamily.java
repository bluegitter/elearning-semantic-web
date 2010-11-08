package searchFamily;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;

public class SearchFamily {
private final static String FATHER="father";
private final static String MOTHER="mother";
private final static String DAUGHTER="daughter";
private final static String SON="son";
private final static String SISTER="sister";
private final static String BROTHER="brother";
private final static String ANCESTOR="ancestor";
private final static String OFFSPRING="offSpring";
private final static String GRANDPA="grandPa";
private final static String rulePath="D:\\EclipseWorkspace\\jenatest\\src\\family\\family.rules";

private final static String pre="http://zhumzhu.com/family#";
public final static String fileSchema="D:\\EclipseWorkspace\\jenatest\\src\\searchFamily\\familySchema.xml";
public final static String fileData="D:\\EclipseWorkspace\\jenatest\\src\\searchFamily\\familyData.xml";
/*

下面的方法，接受一个人的名字，然后构造出sparql语句

*/
public static String getSearchString(String keyWords){
     StringBuffer sb=new StringBuffer("PREFIX fa: <http://zhumzhu.com/family#>" +
             " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
               " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
   );
     sb.append(" SELECT ?father ?mother ?son ?daughter ?sister ?brother ?ancestor ?offSpring ?grandPa ");
     sb.append("WHERE { "); 
     
     sb.append(" OPTIONAL { ");
     sb.append(keyWords+" fa:hasFather ?father ");
     sb.append(" }. ");
     sb.append(" OPTIONAL { ");
     sb.append(keyWords+" fa:hasMother ?mother ");
     sb.append(" }. ");
     sb.append(" OPTIONAL { ");
    sb.append(keyWords+" fa:hasSon ?son ");
    sb.append(" }. ");
     sb.append(" OPTIONAL { ");
    sb.append(keyWords +" fa:hasDaughter ?daughter ");
    sb.append(" }. ");
     sb.append(" OPTIONAL { ");
     sb.append(keyWords+" fa:hasSister ?sister ");
     sb.append(" }. ");
     sb.append(" OPTIONAL { ");
     sb.append(keyWords +" fa:hasBrother ?brother ");
     sb.append(" }. ");
     sb.append(" OPTIONAL { ");
     sb.append(keyWords+" fa:hasAncestor ?ancestor ");
     sb.append(" }. ");
     sb.append(" OPTIONAL { ");
    sb.append(keyWords +" fa:hasOffSpring ?offSpring ");
    sb.append(" }. ");
    sb.append(" OPTIONAL { ");
    sb.append(keyWords +" fa:hasGrandPa ?grandPa ");
    sb.append(" } ");

     sb.append(" }");
     System.out.println(sb);
   return sb.toString();
}


public static Map getResultsList(String keyWords,String fileSchema,String fileData,String rule){
  
  
   //Model m=getModel(fileSchema, fileData);//没有使用家族规则
  
  
   if(rule==null) rule=rulePath;
     Model rm=getModelByRules(fileSchema, fileData, rule); //使用了家族规则  
  
   String searchString=getSearchString("fa:"+keyWords);
  
    
   Map map=getResultsList(searchString, rm);
  
  
   return map;
  
}
public static Model getModel(String fileSchema,String fileData){
         FileManager fm=FileManager.get();
      Model schema = fm.loadModel(fileSchema);
      Model data = fm.loadModel(fileData);
    
     Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
     reasoner = reasoner.bindSchema(schema);
     
     InfModel m = ModelFactory.createInfModel(reasoner, data);
     
     OntModel om=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, m);
     return om;
}
/**

根据rule，构建model对象

*/
public static Model getModelByRules(String fileSchema,String fileData,String rulePath){
   FileManager fm=FileManager.get();
      Model schema = fm.loadModel(fileSchema);
      Model data = fm.loadModel(fileData);

       //设置新的领域规则
      
     List rules = Rule.rulesFromURL(rulePath);
     
     GenericRuleReasoner rea = new GenericRuleReasoner(rules);
     rea.setOWLTranslation(true);               // not needed in RDFS case
     rea.setTransitiveClosureCaching(true);
     rea =(GenericRuleReasoner) rea.bindSchema(schema);
     
     InfModel m = ModelFactory.createInfModel(rea, data);
     
     OntModel om=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, m);
     return om;
}
private static Map getResultsList(String searchString,Model model){
    Query q = QueryFactory.create(searchString);

     QueryExecution qe=QueryExecutionFactory.create(q, model);
     ResultSet rs=qe.execSelect(); //执行查询
     System.out.println("rs.getReaultVars--"+rs.getResultVars());
     QuerySolution qs;
     RDFNode father,mother,son,daughter,sister,ancestor,offSpring,brother,grandPa;
     Map<String, Set<RDFNode>> map=new HashMap<String, Set<RDFNode>>();
     
     Set<RDFNode> father_=new HashSet<RDFNode>();
     Set<RDFNode> mother_=new HashSet<RDFNode>();
     Set<RDFNode> son_=new HashSet<RDFNode>();
     Set<RDFNode> sister_=new HashSet<RDFNode>();
     Set<RDFNode> ancestor_=new HashSet<RDFNode>();
     Set<RDFNode> offSpring_=new HashSet<RDFNode>();
     Set<RDFNode> brother_=new HashSet<RDFNode>();
     Set<RDFNode> daughter_=new HashSet<RDFNode>();
     Set<RDFNode> grandPa_=new HashSet<RDFNode>();
     
     
     while(rs.hasNext()){
     qs= rs.nextSolution();
      father=qs.get(FATHER); 
      mother=qs.get(MOTHER);
      son=qs.get(SON);
      daughter=qs.get(DAUGHTER);
      sister=qs.get(SISTER);
      ancestor=qs.get(ANCESTOR);
      offSpring=qs.get(OFFSPRING);
      brother=qs.get(BROTHER);
      grandPa=qs.get(GRANDPA);
      
      if(father!=null)
      father_.add(father);
      if(mother!=null)
      mother_.add(mother);
      if(son!=null)
      son_.add(son);
      if(daughter!=null)
      daughter_.add(daughter);
      if(sister!=null)
      sister_.add(sister);
      if(ancestor!=null)
      ancestor_.add(ancestor);
      if(offSpring!=null)
      offSpring_.add(offSpring);
      if(brother!=null)
      brother_.add(brother);
      if(grandPa!=null)
      grandPa_.add(grandPa);
      
    
      
   
    }
     map.put(FATHER, father_);
     map.put(MOTHER, mother_);
     map.put(SON, son_);
     map.put(SISTER, sister_);
     map.put(BROTHER, brother_);
     map.put(ANCESTOR, ancestor_);
     map.put(DAUGHTER, daughter_);
     map.put(OFFSPRING, offSpring_);
     map.put(GRANDPA, grandPa_);
     
    // ResultSetFormatter.out(System.out, rs,q);
     qe.close();
     return map;
           
    }
public static void print( Map<String, Set<RDFNode>> map){
Set<String> keys=map.keySet();
Iterator<String> keyi=keys.iterator();
String key;
Set<RDFNode> l;
Iterator<RDFNode> i;
RDFNode r;
while(keyi.hasNext()){
   key=keyi.next();
   System.out.println(key+"----:");
   l=map.get(key);
   i=l.iterator();
  
   while(i.hasNext()){
    r=i.next();
   
    if(r.isResource()){
     System.out.print( ((Resource)r).getLocalName()+" -- ");
    }
    if(r.isLiteral()){
     System.out.print( ((Literal)r).getString()+" -- ");
    }
   
   }
   System.out.println();
  
}


}
public static void main(String[] args) {
String keyWords="zhangxiaoli";

Map<String, Set<RDFNode>> map=getResultsList(keyWords, fileSchema, fileData,null);
print(map);
}
}


