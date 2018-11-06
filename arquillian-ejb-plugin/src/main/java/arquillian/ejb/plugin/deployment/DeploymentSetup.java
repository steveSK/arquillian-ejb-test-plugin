package arquillian.ejb.plugin.deployment;

import org.apache.log4j.Logger;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by stefan on 10/17/17.
 */
public class DeploymentSetup {

    private static Logger log = Logger.getLogger(DeploymentSetup.class);
    private static String FILE_NAME = "deployment.xml";
    private static String ARQ_PACKAGE_NAME = "arquillian-test.jar";

    public JavaArchive createDeployment() throws ParserConfigurationException, IOException, SAXException {



        ClassLoader classLoader = getClass().getClassLoader();

        File fXmlFile = new File(classLoader.getResource(FILE_NAME).getFile());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, ARQ_PACKAGE_NAME);
        jar.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        NodeList packages = doc.getElementsByTagName("package");
        log.info("number of packages: " + packages.getLength());

        NodeList resources = doc.getElementsByTagName("resource");
        log.info("number of resources: " + resources.getLength());

        log.info("Deploying packages:");
        for(int i=0;i<packages.getLength();i++){
            Node package0 = packages.item(i);
            NamedNodeMap attributes = package0.getAttributes();
            if(attributes.getNamedItem("recursive").getNodeValue().equals("true")){
                jar.addPackages(true,package0.getTextContent());
            } else {
                jar.addPackages(false,package0.getTextContent());
            }
            log.info("deploying package: " + package0.getTextContent());
        }

        log.info("Deploying resources:");
        for(int j=0;j<resources.getLength();j++){
            Node resource = resources.item(j);
            NamedNodeMap attributes = resource.getAttributes();
            log.info("deploying resource: " + attributes.getNamedItem("resourceName"));
            jar.addAsResource(attributes.getNamedItem("resourceName").getNodeValue(),
                    attributes.getNamedItem("target").getNodeValue());
        }

        return jar;
    }
}
