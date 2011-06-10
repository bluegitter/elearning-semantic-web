/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.server.socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontology.people.ELearner;

/**
 *
 * @author t
 */
public class FileClient {

    public FileClient() {
    }
    public ELearner el;

    public void downloadFile() {
        InputStream netin = null;
        try {
            File file = new File("files/owl/"+el.getId()+".owl");
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            Socket server = new Socket(InetAddress.getLocalHost(), ServerUtil.PORT);
            
            netin = server.getInputStream();
            InputStream in = new DataInputStream(new BufferedInputStream(netin));

            byte[] buf = new byte[ServerUtil.BUFFER];
            int num = in.read(buf);
            while (num != (-1)) {
                raf.write(buf, 0, num);
                raf.skipBytes(num);
                num = in.read(buf);
            }
            in.close();
            raf.close();
        } catch (IOException ex) {
            Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                netin.close();
            } catch (IOException ex) {
                Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        FileClient fc = new FileClient();
        fc.el = new ELearner("el001");
        fc.downloadFile();
    }
}
