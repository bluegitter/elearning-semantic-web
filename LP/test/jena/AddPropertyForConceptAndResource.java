/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import exception.jena.IndividualNotExistException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jena.impl.ELearnerModelImpl;
import ontology.EConcept;
import ontology.resources.ISCB_Resource;
import util.Constant;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class AddPropertyForConceptAndResource {

    public static void main(String[] args) throws IOException {
        addProperty();
    }

    public static void addProperty() {
        File owlFile = new File(Constant.OWLFile);
        ELearnerModelImpl emi = new ELearnerModelImpl(owlFile);
        BufferedReader in = null;
        try {
            File f = new File("files/data/res_con.txt");
            in = new BufferedReader(new FileReader(f));
            String temp = "";
            int i = 0;
            while ((temp = in.readLine()) != null) {
                String[] s = temp.split(",");
                System.out.println("temp:" + temp);
                ISCB_Resource res = emi.getEResource(s[0]);
                EConcept con = emi.getEConcept(s[1]);
                emi.addPropertyIsResourceOfC(res, con);
                i++;
            }
            in.close();
            jena.OwlOperation.writeRdfFile(emi.getOntModel(), owlFile, null);
            jena.OwlOperation.writeOwlFileFromRdfFile(owlFile, owlFile);
            System.out.println("整理了："+i+"个资源");
        } catch (IndividualNotExistException ex) {
            Logger.getLogger(AddPropertyForConceptAndResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddPropertyForConceptAndResource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(AddPropertyForConceptAndResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void sortFile() throws IOException {
        File f = new File("files/data/res_con.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        File srcFile = new File("files/data/res.txt");
        FileWriter fw = new FileWriter(f);
        BufferedReader in = new BufferedReader(new FileReader(srcFile));
        String temp = "";
        while ((temp = in.readLine()) != null) {
            String[] s = temp.split(",");
            if (!s[0].trim().equals("null")) {
                fw.write((s[0].trim() + "," + s[1].trim() + "\n"));
            }
            System.out.println("temp:" + temp);
        }
        in.close();
        fw.close();

    }
}
