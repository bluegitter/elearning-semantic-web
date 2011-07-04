package jena;import java.io.File;import java.io.FileOutputStream;import java.io.IOException;import java.util.logging.Level;import java.util.logging.Logger;import com.hp.hpl.jena.rdf.model.Model;import db.UploaderCommTwo;import java.io.BufferedReader;import java.io.FileInputStream;import java.io.FileWriter;import java.io.InputStreamReader;import java.io.OutputStreamWriter;import java.net.URL;import java.net.URLConnection;import java.net.URLEncoder;import org.semanticweb.owlapi.model.OWLOntologyCreationException;import org.semanticweb.owlapi.model.OWLOntologyStorageException;import util.Constant;public class OwlOperation {    /******************************************************************************************     * Write a serialization of this model as an XML document.     *     * The language in which to write the model is specified by the lang argument.     * Predefined values are "RDF/XML", "RDF/XML-ABBREV", "N-TRIPLE" and "N3".     * The default value is represented by null is "RDF/XML".     *     * Parameters:     * @param model     * @param file THE FILE for the output stream to which the XML will be written     * @param lang     * @throws IOException     */    public static void writeRdfFile(Model model, File file, String lang) throws IOException {        FileOutputStream out = new FileOutputStream(file);        model.write(out, lang);        //model.write(out, "N3");        out.flush();        out.close();        System.out.println("RDF File Update Success");    }    /***************************************************************************     * Operation On OWL API 3.2.3     */    public static void writeOwlFile(org.semanticweb.owlapi.model.OWLOntology ontology, File file) throws IOException {        try {            org.semanticweb.owlapi.model.OWLOntologyManager manager = org.semanticweb.owlapi.apibinding.OWLManager.createOWLOntologyManager();            manager.setOntologyDocumentIRI(ontology, org.semanticweb.owlapi.model.IRI.create(file));            manager.saveOntology(ontology);            System.out.println("Owl File Update Success");        } catch (OWLOntologyStorageException ex) {            Logger.getLogger(OwlOperation.class.getName()).log(Level.SEVERE, null, ex);        }    }    public static void writeOwlFileFromRdfFile(File rdfFile, File owlFile) {        try {            org.semanticweb.owlapi.model.OWLOntologyManager manager = org.semanticweb.owlapi.apibinding.OWLManager.createOWLOntologyManager();            org.semanticweb.owlapi.model.OWLOntology ontology = manager.loadOntologyFromOntologyDocument(rdfFile);            manager.saveOntology(ontology);            System.out.println("Owl File Update Success");        } catch (OWLOntologyCreationException ex) {            Logger.getLogger(OwlOperation.class.getName()).log(Level.SEVERE, null, ex);        } catch (OWLOntologyStorageException ex) {            Logger.getLogger(OwlOperation.class.getName()).log(Level.SEVERE, null, ex);        }    }    public static int getVersion(String uid) {        FileInputStream in = null;        try {            File verFile = new File(Constant.USERVERSION + uid);            if (!verFile.exists()) {                verFile.createNewFile();                setVersion(uid, 0);                return 0;            }            in = new FileInputStream(verFile);            int ch1 = in.read();            int ch2 = in.read();            int ch3 = in.read();            int ch4 = in.read();            return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));        } catch (IOException ex) {            Logger.getLogger(OwlOperation.class.getName()).log(Level.SEVERE, null, ex);        } finally {            try {                in.close();            } catch (IOException ex) {                Logger.getLogger(OwlOperation.class.getName()).log(Level.SEVERE, null, ex);            }        }        return -1;    }    public static void setVersion(String uid, int v) {        try {            File verFile = new File(Constant.USERVERSION + uid);            if (!verFile.exists()) {                verFile.createNewFile();            }            FileOutputStream out = new FileOutputStream(verFile);            out.write((v >>> 24) & 0xFF);            out.write((v >>> 16) & 0xFF);            out.write((v >>> 8) & 0xFF);            out.write((v >>> 0) & 0xFF);            out.flush();            out.close();        } catch (IOException ex) {            Logger.getLogger(OwlOperation.class.getName()).log(Level.SEVERE, null, ex);        }    }    public static boolean downloadUserFile(String eid) {        try {            // Construct data            File userFile = new File("files/owl/" + eid + ".owl");            int version = OwlOperation.getVersion(eid);            if (!userFile.exists()) {                version = 0;                userFile.createNewFile();            }            String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(eid, "UTF-8");            data += "&" + URLEncoder.encode("ver", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(version), "UTF-8");            // Send data            URL url = new URL(Constant.DOWNLOAD_URL_STRING_PHP);            URLConnection conn = url.openConnection();            conn.setDoOutput(true);            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());            wr.write(data);            wr.flush();            // Get the response            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));            String head = br.readLine();            if (head.trim().equals("latest")) {                System.out.println("latest");                return false;            } else {                String[] vs = head.split(" ");                int newVersion = Integer.parseInt(vs[1].trim());                // LPApp.VERSION = version;                OwlOperation.setVersion(eid, newVersion);                if (newVersion > 0) {                    FileWriter fw = new FileWriter(userFile);                    char[] buf = new char[1024];                    int bl;                    while ((bl = br.read(buf)) != -1) {                        fw.write(buf, 0, bl);                    }                    fw.flush();                    fw.close();                } else {                    return false;                }            }            wr.close();            br.close();            return true;        } catch (Exception e) {            return false;        }    }    public static void uploadUserFile(String eid, String owlString) {        UploaderCommTwo up = new UploaderCommTwo(eid, owlString);        up.uploadFile();    }    public static void main(String[] args) {        //  setVersion("el001",6);        int i = OwlOperation.getVersion("masheng");        System.out.println("i:" + i);        //     downloadUserFile("el001");        // System.out.println("s:" + s);    }}