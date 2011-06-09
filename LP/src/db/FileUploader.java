/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import db.httpClient.ModuleException;
import java.io.IOException;
import ontology.people.ELearner;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class FileUploader {

    public static void main(String[] args) throws IOException, ModuleException {

        ELearner el = new ELearner("el001");
        UploaderComm uc = new UploaderComm(el);
        uc.uploadFiles();
        System.out.println("-----------------");
        System.out.println("结束演示");
    }
}
