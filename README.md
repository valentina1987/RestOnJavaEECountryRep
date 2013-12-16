# Rest Servlets and Ejb on JavaEE container

This clickstart integrates rest functionalities on JavaEE container (Jboss), using:

		EJB 3.2
		Jersey 1.8
		Jboss 7
		Java 6

Press the button to build, test and deploy this instantly:



## Running the project locally
```
	git clone git@github.com:valentina1987/RestOnJavaEEScheletonProject.git
	mvn jboss7:run
```

You can access this web-app at: http://localhost:8080/jboss-rest-ejb-clickstart2/RestDispatcherServlet



## Working in Eclipse/STS

### prerequisites
The following items should be installed in your system:
* Maven 3 (http://www.sonatype.com/books/mvnref-book/reference/installation.html)
* git command line tool (https://help.github.com/articles/set-up-git)
* Eclipse with the m2e plugin (m2e is installed by default when using the STS (http://www.springsource.org/sts) distribution of Eclipse)

Note: when m2e is available, there is an m2 icon in Help -> About dialog.
If m2e is not there, just follow the install process here: http://eclipse.org/m2e/download/


### Steps:

1) In the command line
```
git clone git@github.com:valentina1987/RestOnJavaEEScheletonProject.git
```
2) Inside Eclipse
```
File -> Import -> Maven -> Existing Maven project