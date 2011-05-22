/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import jena.impl.ELearnerModelImpl;
import ontology.resources.ISCB_Resource;
import util.Constant;

/**
 *
 * @author student
 */
public class LuceneTest {

    public static File file = new File("D:\\semantic\\LP\\src\\lucene\\testlucene.txt");

    private static Directory directory = new RAMDirectory();
    public static void preIndex() throws Exception{
        String fileName1 = Constant.LuceneTestFile;
//        String fileName2 = "./data/searchShow2.txt";
//        String fileName3 = "./data/test.txt";
        IndexWriter writer = new IndexWriter(directory,new StandardAnalyzer(),true);
        Document doc1 = getDocument(fileName1);
//        Document doc2 = getDocument(fileName2);
//        Document doc3 = getDocument(fileName3);
        writer.addDocument(doc1);
//        writer.addDocument(doc2);
//        writer.addDocument(doc3);
        writer.close();

    }
    //先建立索引才能执行

    //termQuery   rangeQuery   booleanQuery的查询在Searcher类中
    public static void rangeQuery() throws Exception{
        Term startTerm = new Term("lastmodified","20070620");
        Term endTerm = new Term("lastmodified","20070622");
        RangeQuery query = new RangeQuery(startTerm,endTerm,true);
        IndexSearcher searcher = new IndexSearcher(directory);
        Hits hits = searcher.search(query);
        prtHits(hits);
    }
    public static void prefixQuery() throws Exception{
        Term term = new Term("fileName","searchShow.txt");
        Term prefixterm = new Term("fileName","searchShow");
        IndexSearcher searcher = new IndexSearcher(directory);
        Query query = new TermQuery(term);
        Query prefixQuery = new PrefixQuery(prefixterm);
        Hits hits = searcher.search(query);
        Hits prefixHits = searcher.search(prefixQuery);
        prtHits(hits);
        System.out.println("----------");
        prtHits(prefixHits);
    }
    public static void phraseQuery() throws Exception{
        IndexSearcher searcher = new IndexSearcher(directory);
        PhraseQuery query = new PhraseQuery();
        query.setSlop(2);
        query.add(new Term("contents","quick"));
        query.add(new Term("contents","fox"));
        Hits hits = searcher.search(query);
        prtHits(hits);
    }
    public static void wildcardQuery() throws Exception{
        IndexSearcher searcher = new IndexSearcher(directory);
        Query query = new WildcardQuery(new Term("contents","?ild*"));
        Hits hits = searcher.search(query);
        prtHits(hits);
    }
    public static void fuzzyQuery() throws Exception{
        IndexSearcher searcher = new IndexSearcher(directory);
        Term term = new Term("rid000531","-");
        FuzzyQuery query = new FuzzyQuery(term);
        Hits hits = searcher.search(query);
        prtHits(hits);
    }
    public static Document getDocument(String fileName) throws Exception{
        File file = new File(fileName);
        Document doc = new Document();
        doc.add(Field.Keyword("fileName",file.getName() ));
        Date modified = new Date(file.lastModified());
        String lastmodified = new SimpleDateFormat("yyyyMMdd").format(modified);
        doc.add(Field.Keyword("lastmodified", lastmodified));
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file)));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        doc.add(Field.Text("contents",sb.toString() ));
        return doc;
    }
    public static void prtHits(Hits hits) throws Exception{
        for(int i=0;i<hits.length();i++){
            Document doc = hits.doc(i);
            System.out.println(doc.get("fileName"));
            System.out.println(doc.get("lastmodified"));
        }
    }
    public static void main(String[] args) throws Exception{
        preIndex();
//        rangeQuery();
//        prefixQuery();
//        phraseQuery();
//        wildcardQuery();
        fuzzyQuery();
    }

    public static void getModel() throws IOException {
        ELearnerModelImpl emi = new ELearnerModelImpl();
        HashSet<ISCB_Resource> res = emi.getAllEResources();

        FileWriter fw = new FileWriter(file);
        for (ISCB_Resource r : res) {
            fw.write(r.getRid());
            fw.write(" - ");
            fw.write(r.getName());
            fw.write("\n");
        }
        fw.close();
    }
}
