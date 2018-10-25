# About this application
A demo application (with an in-memory-database) with:
- users with different roles
- a product page where you can add/update/delete depending on your role
- restricted pages

# How to run the application
- run maven-command "package" (either from your IDE or from the commandline)
- go to the target directory and run "java -jar springboot-demo-1.0.0-SNAPSHOT.jar"
- that's it!

# Prerequisites
JDK 8

# summary of used technologies

- springboot v2.0.5 
  - internationalization (messages are locale-specific)
- an in-memory-DB (H2) which can be used during development/testing
- springboot security with 3 example-users
- thymeleaf with 
  - the thymeleaf-security extension with some examples
  - the thymleaf-layout-dialect in order to reuse components
- webjars in order to manage javascript and css (including webjars-locate)
  - bootstrap 4 (pages include: responsive navbar, fixed footer)
  - font awesome 5 (with some examples within the pages)
  - jquery
 
