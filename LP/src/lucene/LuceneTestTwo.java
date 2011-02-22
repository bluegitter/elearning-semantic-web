/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import util.Constant;

/**
 *
 * @author student
 */
public class LuceneTestTwo {

    public static void main(String[] args) throws IOException, ParseException {
        Hits hits = null;
        String queryString = "树";
        Query query = null;
        IndexSearcher searcher = new IndexSearcher("D:\\semantic\\LP\\test\\owl");

        Analyzer analyzer = new StandardAnalyzer();
        try {
            QueryParser qp = new QueryParser("body", analyzer);
            query = qp.parse(queryString);
        } catch (ParseException e) {
        }
        if (searcher != null) {
            hits = searcher.search(query);
            if (hits.length() > 0) {
                System.out.println("找到:" + hits.length() + " 个结果!");
            }
        }
    }
}
