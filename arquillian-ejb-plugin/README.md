# Arquillian Test Framework for iDetect

## Introduction

Arquillian test framework for Idetect is built on top of Arquillian, Junit and few extensions. 

Arquillian is an powerfull integration testing framework developed by JBOSS comunity. 
It allows to write integration tests in same manner as basic unit tests (using junit) and easy to deploy it on provided container.

Our Testing framework consists of these main components:

* Arquillian - main framework for integration tests
* Arquillian Suite extension - to be able to deploy to container just once per Deployment
* Arquiilian Persistence extension - to be able to write more advanced persistence tests 
* Junit - responsible for execution of tests
* Container - Wildfly 10 or JBOSS EAP 7 to be able to run tests in the web container

## Framework Part

Framework is built around ArquillianTest class. 
This class contains:
* Deployment part, 
* EJB beans interfaces to be able to access EJB methods for testing
* Entity Manager for access to database

Deployment is implemented using Arquillian @Deployment annotation and ShrinkWrap. 
ShrinkWrap is resposible for packaging all necessary packages and resources to one single java archive which is then deployed on remote container.
To avoid hard-coded definition of archive. Definition which items should be deployed is defined by user in deployment.xml for more see User guide.
Each Junit test class need to inherit ArquillianTest to be able to run Arquillian Tests, Arquillian is invoked using this class.

@ArquillianSuiteDeployment annotation of  Suite extension ensures that Deployment is executed only once, not per each Junit class. 
To give user possibility to  divide tests between different Junit test classes. Each time all test are runned in project, archive is deployed just once

## User Guide

1 First step is to build arquillian framework and publish it to local maven repository: 
   ```
   mvn clean install
   ```

2 setup maven in a test project, these are required dependencies
   ```
   <dependency>
            <groupId>org.jboss.arquillian.core</groupId>
            <artifactId>arquillian-core-api</artifactId>
            <version>${arquillian.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.arquillian.protocol</groupId>
            <artifactId>arquillian-protocol-servlet</artifactId>
            <version>${arquillian.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.arquillian</groupId>
            <artifactId>arquillian-bom</artifactId>
            <version>${arquillian.version}</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.arquillian.junit</groupId>
            <artifactId>arquillian-junit-container</artifactId>
            <version>${arquillian.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.arquillian.extension</groupId>
            <artifactId>arquillian-transaction-bom</artifactId>
            <version>${arquillian.transaction.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.arquillian.extension</groupId>
            <artifactId>arquillian-transaction-jta</artifactId>
            <version>${arquillian.transaction.version}<</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.wildfly.arquillian</groupId>
            <artifactId>wildfly-arquillian-container-remote</artifactId>
            <version>${arquillian.container.version}/version>
            <scope>test</scope>
        </dependency>
 

        <!-- Only required to run tests in an IDE that bundles an older version -->
        <dependency>
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-launcher</artifactId>
            <version>${junit.platform.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- Only required to run tests in an IDE that bundles an older version -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.jupiter.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- Only required to run tests in an IDE that bundles an older version -->
        <dependency>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
            <version>${junit.vintage.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.36</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.arquillian.ape</groupId>
            <artifactId>arquillian-ape-sql-container-api</artifactId>
            <version>2.0.0-alpha.4</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.arquillian.ape</groupId>
            <artifactId>arquillian-ape-sql-container-dbunit</artifactId>
            <version>2.0.0-alpha.4</version>
            <scope>test</scope>
    </dependency>
    ```
   
   
    

3 To be able to run test with arquillian inside of the container, all your test classes need to inherit ArquillianTest class 
  IMPORTANT!!! : class need to be in same namespace that parent class ArquillianTest "org.namematching.test.arquillian", 
  without Arquillian is unable to load the class
  
  ```
  public class ArqFrameworkTest1 extends ArquillianTest 
  ```
   
   
4 You need install container on some machine and configure access in arquillian.xml 
 
    ```
    <container qualifier="wildfly-remote" default="true">
		<configuration>
			<property name="managementAddress">localhost</property>
			<property name="managementPort">9990</property>
			<property name="username">admin</property>
			<property name="password">1admin1</property>
		</configuration>
	</container>
	```
   
   
5 Configure extensions in arquillian.xml
    
    ```
    <extension qualifier="transaction">
		<property name="manager">java:jboss/UserTransaction</property>
	</extension>

	<extension qualifier="persistence">
		<property name="defaultDataSource">java:/NameMatchingDS</property>
	</extension>
	<extension qualifier="persistence-dbunit">
		<property name="datatypeFactory">org.dbunit.ext.mysql.MySqlDataTypeFactory</property>
		<property name="excludePoi">true</property>
   </extension>
   ```
   
	
6 create deployment.xml in resources dir and define the deployment package, for instance:
 


 ```
 deployment>
    <packages>
        <package recursive="true">org.namematching</package>
        <package recursive="true">org.tempuri</package>
        <package recursive="true">us.gov.treasury</package>
        <package recursive="true">javax.jms</package>
        <package recursive="true">org.hibernate</package>
        <package recursive="true">net.sf.ehcache</package>
        <package recursive="true">org.apache.lucene</package>
        <package recursive="true">org.apache.commons</package>
        <package recursive="true">net.sf.jasperreports</package>
        <package recursive="true">org.kie.api</package>
        <package recursive="true">org.bouncycastle</package>
        <package recursive="true">io.swagger</package>
        <package recursive="true">com.pff</package>
        <package recursive="true">com.itextpdf</package>
        <package recursive="true">com.fasterxml.jackson</package>
        <package recursive="true">com.levigo</package>
        <package recursive="true">com.thomsonreuters.accelus</package>
        <package recursive="true">org.quartz</package>
    </packages>
    <resources>
        <resource resourceName="META-INF/persistence.xml" target="META-INF/persistence.xml"/>
        <resource resourceName="META-INF/jboss-deployment-structure.xml" target="META-INF/jboss-deployment-structure.xml"/>
        <resource resourceName="ehcache.xml" target="ehcache.xml"/>
        <resource resourceName="local/audit.properties" target="audit.properties"/>
        <resource resourceName="local/errors.properties" target="errors.properties"/>
        <resource resourceName="org/namematching/resources/stopwords.txt" target="org/namematching/resources/stopwords.txt"/>
        <resource resourceName="org/namematching/resources/stopwordsfile.txt" target="org/namematching/resources/stopwordsfile.txt"/>
        <resource resourceName="org/namematching/resources/synonyms.txt" target="org/namematching/resources/synonyms.txt"/>
        <resource resourceName="org/namematching/resources/synonyms_firstname.txt" target="org/namematching/resources/synonyms_firstname.txt"/>
        <resource resourceName="org/namematching/resources/mappingISO1Latin.txt" target="org/namematching/resources/mappingISO1Latin.txt"/>
    </resources>
</deployment>

```
 
7 Add All necessary resources (persistence.xml etc.) which was specified in deployment.xml  
 

## Example
 
 Here is an example with two tests, using persistence extension
 
 ```
 public class ArqFrameworkTest extends ArquillianTest {



    @Test
    @DataSource("java:/NameMatchingDS")
    @UsingDataSet("datasets/users.yml")
    @Cleanup(phase = TestExecutionPhase.AFTER,strategy = CleanupStrategy.USED_TABLES_ONLY)
    public void testCheckUserName(){
        String username = "test";
        Assert.assertTrue(this.admin.checkUserName(username) != 0);
    }


    @Test
    @DataSource("java:/NameMatchingDS")
    @UsingDataSet("datasets/users.yml")
    @ShouldMatchDataSet("datasets/expected-users.yml")
    @Cleanup(phase = TestExecutionPhase.AFTER,strategy = CleanupStrategy.USED_TABLES_ONLY)
    @Transactional(TransactionMode.COMMIT)
    public void persistenceTest(){


        UserData user = this.entityManager.find(UserData.class,1);
        UserData newUser = this.entityManager.find(UserData.class, 1);
        Assert.assertTrue(user!= null);
        newUser.setEmail("admin2@gmail.com");
        this.admin.mergeUser(newUser.toUser(),user.toUser(),false,"test");

    }
 }
 ```
  
        

    
    
    