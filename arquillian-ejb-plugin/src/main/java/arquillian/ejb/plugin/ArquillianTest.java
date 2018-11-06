package arquillian.ejb.plugin;


import arquillian.ejb.example.beans.PersonEjb;
import arquillian.ejb.plugin.deployment.DeploymentSetup;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;
import org.xml.sax.SAXException;

import javax.ejb.EJB;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by stefan on 10/14/17.
 */
@ArquillianSuiteDeployment
@RunWith(Arquillian.class)
public class ArquillianTest {

    @EJB
    protected PersonEjb personEjb;

    @Deployment
    public static JavaArchive createDeployment() throws IOException, SAXException, ParserConfigurationException {
        return new DeploymentSetup().createDeployment();
    }


}
