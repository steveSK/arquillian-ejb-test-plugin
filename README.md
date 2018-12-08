# arquillian ejb test plugin
This small project allows you to write Arquillian tests for your EJB application and run them from one place without necessity to add arquillian to your project. It can serve to build integration tests. The project is designed to be extended, adding your bussiness logic

## Guidelines

1. First add your project as a dependency to arquillian-ejb-plugin
2. Add your EJB interfaces to ArquillianTest class
3. create a deployment.xml with all dependencies
4. configure arquillian in arquillian.xml
5. write tests by inheritting from ArquillianTest

