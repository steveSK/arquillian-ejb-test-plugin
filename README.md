# arquillian ejb test plugin
This small plugin allows you to write Arquillian tests for your EJB application and run them from one place without necessity to add arquillian to your project. It can serve to build integration tests.

## Guidelines

1. First add your project as a dependency to arquillian-ejb-plugin
2. Add your EJB interfaces to ArquillianTest class
3. create a deployment.xml with all dependencies
4. write tests by inheritting from ArquillianTest

