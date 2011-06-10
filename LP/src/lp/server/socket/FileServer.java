/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.server.socket;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author t
 */
public class FileServer {

    public static void main(String[] args) {
        FileInputStream fos = null;
        try {
            File file = new File("files/owl/write.owl");
            fos = new FileInputStream(file);
            while (true) {

                ServerSocket ss = new ServerSocket(ServerUtil.PORT);
                System.out.println("Server starting....");
                Socket client = ss.accept();
                OutputStream netout = client.getOutputStream();
                OutputStream doc = new DataOutputStream(new BufferedOutputStream(netout));

                byte[] buf = new byte[ServerUtil.BUFFER];
                int num = fos.read(buf);

                while (num != (-1)) {
                    doc.write(buf, 0, num);
                    doc.flush();
                    num = fos.read(buf);
                }
                fos.close();
                doc.close();
                System.out.println("Deal with one socket");
                
            }
           // System.out.println("Server closed!");
        } catch (IOException ex) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
