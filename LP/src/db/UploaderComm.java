package db;

import HTTPClient.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import ontology.people.ELearner;

public class UploaderComm {

    public File uploadFile;
    public String eid;
    static {
        /* Configures HTTPClient to accept all cookies
         * this should be done at least once per iMazing Uploader
         * invokation */
        CookieModule.setCookiePolicyHandler(new CookiePolicyHandler() {

            public boolean acceptCookie(Cookie cookie, RoRequest req, RoResponse resp) {
                return true;
            }

            public boolean sendCookie(Cookie cookie, RoRequest req) {
                return true;
            }
        });

        // http://cvs.sourceforge.net/viewcvs.py/jameleon/jameleon/src/java/net/sf/jameleon/util/JsseSettings.java?rev=1.4&view=markup
        // http://tp.its.yale.edu/pipermail/cas/2004-March/000348.html
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
    }

    public UploaderComm(ELearner el) {
        eid = el.getId();
        uploadFile = new File("files/owl/" + eid + ".owl");
    }

    public void uploadFiles(boolean async) {
        UploadTask uploadTask = new UploadTask();
        doTask(uploadTask, async);
    }

    abstract class ImazingTask implements Runnable {

        HTTPConnection mConnection;
        boolean interrupt = false;
        boolean terminated = false;
        Thread thread = null;

        public ImazingTask() {
        }

        public void run() {
            thread = Thread.currentThread();

            runTask();

            cleanUp();
        }

        public void interrupt() {
            thread.interrupt();
            interrupt = true;
        }

        public void cleanUp() {
            terminated = true;
        }

        abstract void runTask();
    }

    /**
     * This class does upload job.
     *
     * @author Wannasoft
     *
     */
    class UploadTask extends ImazingTask {

        UploadTask() {
            super();
        }

        void runTask() {
            uploadPicture(uploadFile);
        }

        boolean uploadPicture(File file) {
            try {


                // setup the protocol parameters
                NVPair[] opts = {
                    new NVPair("elearner_id", eid)
                };

                // setup the multipart form data
                
                NVPair[] afile = {new NVPair("file", uploadFile.getAbsolutePath())};
                NVPair[] hdrs = new NVPair[1];
                byte[] data = Codecs.mpFormDataEncode(opts, afile, hdrs);

                String responseString = requestResponse(hdrs, data, new URL(UploaderConstants.UPLOAD_URL_STRING), true, this);

                if(responseString.startsWith("success")) {
                    return true;
                }

            }  catch (NumberFormatException nfe) {
                ;
            } catch (SocketException swe) {
            } catch (IOException ioe) {
            } catch (ModuleException me) {
            }

            return false;
        }
    }

    /**
     * POSTSs a request to the iMazing server with the given form data.
     */
    String requestResponse(NVPair form_data[], URL iUrl, ImazingTask task) throws ModuleException, IOException {
        return requestResponse(form_data, null, iUrl, true, task);
    }

    String requestResponse(NVPair form_data[], byte[] data, URL iUrl, boolean checkResult, ImazingTask task) throws ModuleException, IOException {
        return requestResponse(form_data, data, iUrl, checkResult, task, false);
    }

    /**
     * POSTs a request to the iMazing server with the given form data.  If data is
     * not null, a multipart MIME post is performed.
     */
    String requestResponse(NVPair form_data[], byte[] data, URL iUrl, boolean checkResult, ImazingTask task, boolean alreadyRetried) throws ModuleException, IOException {
        // assemble the URL
        String urlPath = iUrl.getFile();

        // create a connection
        HTTPConnection mConnection = new HTTPConnection(iUrl);

        // set the user-agent for all requests
        // also try to disable pipelining, to help with uploading large numbers of files
        ArrayList<NVPair> nvPairs = new ArrayList<NVPair>();

        nvPairs.add(new NVPair("Connection", "close"));

        // If Imazing specified the user agent, then we'll use that.  If not, we'll let
        // HTTPClient choose a default agent.
        String userAgent = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)";
        if (userAgent != null) {
            nvPairs.add(new NVPair("User-Agent", userAgent));
        }

        mConnection.setDefaultHeaders((NVPair[]) nvPairs.toArray(new NVPair[nvPairs.size()]));

        HTTPResponse rsp;
        // post multipart if there is data
        if (data == null) {
            if (form_data == null) {
                rsp = mConnection.Get(urlPath);
            } else {
                rsp = mConnection.Post(urlPath, form_data);
            }
        } else {
            rsp = mConnection.Post(urlPath, data, form_data);
        }

        // handle 30x redirects
        if (rsp.getStatusCode() >= 300 && rsp.getStatusCode() < 400) {
            // retry, the library will have fixed the URL
            if (data == null) {
                if (form_data == null) {
                    rsp = mConnection.Get(urlPath);
                } else {
                    rsp = mConnection.Post(urlPath, form_data);
                }
            } else {
                rsp = mConnection.Post(urlPath, data, form_data);
            }
        }

        // handle response
        if (rsp.getStatusCode() >= 300) {
            throw new IOException("HTTP POST ERROR");
        } else {
            // load response
            String response;
            try {
                response = rsp.getText().trim();
            } catch (ParseException e) {
                response = new String(rsp.getData()).trim();
            }

            return response;
        }
    }

    void doTask(ImazingTask task, boolean async) {
        if (async) {
            Thread t = new Thread(task);
            t.start();
        } else {
            task.run();
        }
    }
}
