/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lp.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author t
 */
public class MySocket extends Socket{
    public MySocket(){
        super();
    }
    public  MySocket(String host, int port) throws UnknownHostException, IOException{
        super(host,port);
    }
     public MySocket(InetAddress address, int port) throws IOException {
	super(address,port);
    }

    public String userId;
}
