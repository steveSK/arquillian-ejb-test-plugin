package arquillian.ejb.test;

import arquillian.ejb.plugin.ArquillianTest;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

@RunWith(Arquillian.class)
public class PersonEJBTest extends ArquillianTest {



    @Test
    public void testGetAllPersons(){
        Assert.assertEquals(6,this.personEjb.getAllPersons().size());
    }

    @Test
    public void testGetMarriedPersons(){
        Assert.assertEquals(2,this.personEjb.getMarriedPersons().size());
    }

    @Test
    public void testGetAllPersonOlderThan(){
        Assert.assertEquals(4,this.personEjb.getAllPersonOlderThan(LocalDate.of(1989,1,1)).size());
    }

    @Test
    public void testGetPersonByName(){
        Assert.assertTrue(this.personEjb.getPersonByName("Jakob Adan")!= null);
    }

}
