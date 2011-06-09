package db;

import db.httpClient.Codecs;
import db.httpClient.Cookie;
import db.httpClient.CookieModule;
import db.httpClient.CookiePolicyHandler;
import db.httpClient.HTTPConnection;
import db.httpClient.HTTPResponse;
import db.httpClient.ModuleException;
import db.httpClient.NVPair;
import db.httpClient.ParseException;
import db.httpClient.RoRequest;
import db.httpClient.RoResponse;
import db.httpClient.UploaderException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import ontology.people.ELearner;

public class UploaderComm {

    public File upFile;
    public ELearner el;

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
        this.el = el;
        upFile = new File("files/owl/" + el.getId() + ".owl");
    }


    private static boolean tryComm(HTTPConnection mConnection, String urlPath) {
        try {
            HTTPResponse rsp;
            rsp = mConnection.Head(urlPath);

            int rspCode = rsp.getStatusCode();   // try actual communication
            if (rspCode >= 300 && rspCode < 400) {
                // retry, the library will have fixed the URL
                rsp = mConnection.Head(urlPath);
                rspCode = rsp.getStatusCode();
            }

            return rspCode == 200;
        } catch (UnknownHostException uhe) {
        } catch (IOException ioe) {
            try {
                if (ioe instanceof javax.net.ssl.SSLPeerUnverifiedException) {
                    //JOptionPane.showMessageDialog(, JOptionPane.ERROR_MESSAGE);
                } else {
                }
            } catch (NoClassDefFoundError ncdfe) {
            }
        } catch (ModuleException me) {
        } catch (Exception e) {
        }
        return false;
    }

    public boolean uploadFiles() throws IOException, ModuleException {
        NVPair[] opts = {
            new NVPair("elearner_id", el.getId()),};
        // setup the multipart form data
        NVPair[] afile = {new NVPair("file", upFile.getAbsolutePath())};
        NVPair[] hdrs = new NVPair[1];
        byte[] data = Codecs.mpFormDataEncode(opts, afile, hdrs);

        while (true) {
            // load and validate the response
            String responseString = requestResponse(hdrs, data, new URL(UploaderConstants.UPLOAD_URL_STRING), true);

            BufferedReader in = new BufferedReader(new StringReader(responseString));
            String line = in.readLine();
            int scode = 0;
            while (line != null) {
                line = line.trim();
                if (line.length() > 0) {
                    int codeEnd = line.indexOf(' ');
                    if (codeEnd != -1) {
                        scode = Integer.parseInt(line.substring(0, codeEnd));
                        break;
                    } else {
                        try {
                            throw new UploaderException("Status Code Not Found");
                        } catch (UploaderException ex) {
                            Logger.getLogger(UploaderComm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                line = in.readLine();
            }
            if (scode == 200) {
                return true;
            } else if (scode == 401) {
                continue;
            }
            break;
        }
        return false;
    }

//    /**
//     * POSTSs a request to the iMazing server with the given form data.
//     */
//    String requestResponse(NVPair form_data[], ImazingTask task) throws ModuleException, IOException {
//        return requestResponse(form_data, null, new URL(Imazing.LOGIN_URL_STRING), true, task);
//    }
//
//    String requestResponse(NVPair form_data[], URL iUrl, ImazingTask task) throws ModuleException, IOException {
//        return requestResponse(form_data, null, iUrl, true, task);
//    }
//
    String requestResponse(NVPair form_data[], byte[] data, URL iUrl, boolean checkResult) throws ModuleException, IOException {
        return requestResponse(form_data, data, iUrl, checkResult, false);
    }
    /**
     * POSTs a request to the iMazing server with the given form data.  If data is
     * not null, a multipart MIME post is performed.
     */
    String requestResponse(NVPair form_data[], byte[] data, URL iUrl, boolean checkResult, boolean alreadyRetried) throws ModuleException, IOException {
        // assemble the URL
        String urlPath = iUrl.getFile();

        System.out.println("iUrl:"+iUrl);
        // create a connection
        HTTPConnection mConnection = new HTTPConnection(iUrl);

        // set the user-agent for all requests
        // also try to disable pipelining, to help with uploading large numbers of files
        ArrayList<NVPair> nvPairs = new ArrayList<NVPair>();

        nvPairs.add(new NVPair("Connection", "close"));
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
        } // handle 30x redirects
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
            if (checkResult) {
                // validate response
                int i = response.indexOf(UploaderConstants.PROTOCOL_MAGIC);
                if (i == -1) {
                    if (alreadyRetried) {
                        // failed one time too many
                        throw new IOException("iMazing Not Found");
                    } else {
                        // try again
                        return requestResponse(form_data, data, iUrl, checkResult, true);
                    }
                } else {
                    response = response.substring(i + UploaderConstants.PROTOCOL_MAGIC.length());
                }
                return response;
            } else {
                return null;


            }
        }
    }
}
