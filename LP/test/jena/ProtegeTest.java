/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import edu.stanford.smi.protege.model.Model;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.writer.rdfxml.rdfwriter.OWLModelWriter;
import util.Constant;

/**
 *
 * @author student
 */
public class ProtegeTest {

    public static void main(String[] args) throws Exception {
        //D:\semantic\LP\test\bak\owl\elearning.owl
        String filePath = "D:\\semantic\\LP\\test\\bak\\owl\\elearning.owl";
        FileInputStream file = new FileInputStream(filePath);
        Reader in = new InputStreamReader(file, "UTF-8");
        OWLModel owlModel = null;

        try {

            owlModel = ProtegeOWL.createJenaOWLModelFromReader(in);
               System.out.printf("AddOK!");
        } catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Model dodel=(Model)owlModel.getJenaModel();
        in.close();
        //写入：
        //
        String writeFile = "\\test\\jean\\writeFile.owl";
        FileOutputStream outFile = new FileOutputStream(writeFile);
        Writer out = new OutputStreamWriter(outFile, "UTF-8");
        OWLModelWriter omw = new OWLModelWriter(owlModel, owlModel.getTripleStoreModel().getActiveTripleStore(), out);
        omw.write();
        out.close();


    }
}
