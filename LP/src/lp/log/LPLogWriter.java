/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.log;

import java.io.File;

/**
 *
 * @author student
 */
public class LPLogWriter {

    private File logFile;

    public LPLogWriter() {
    }

    public LPLogWriter(File file) {
        this.logFile = file;
    }
}
