**Weather Comparison Test Automation Framework**

**This project delivers to you a complete lean test architecture for your web using Page Object Design Pattern with best frameworks and practices. It also provides functionality to handle API calls.**

## Languages and Frameworks

This project using the following languages and frameworks:

* **Java 8** as the programming language
* **TestNG** as the UnitTest framework to support the test creation
* **Selenium WebDriver** as the web browser automation framework using the Java binding
* **Extent Report** as the testing report strategy
* **Slf4j** as the logging manage strategy
* **WebDriverManager** as the Selenium binaries management
* **Lombok** to minimize the boilerplate in the Java code
* **RestAssured** as the library to maintain tests for Restful APIs

### Local execution
This execution type uses **WebDriverManager** class to instantiate the web browsers.

The class **LocalDriverManager** create a browser instance from the value placed on the `browser` property on the `config.properties` file.
It matches the browser name with an internal Enum type on WebDriverManager.

### Parallel execution

The parallel test execution is based on the parallel tests feature on TestNG. This is in use on the `multi_browser.xml` test suite file by the `parallel="tests"` attribute and value, 
which means each `test` item inside the test suite will execute in parallel.
The browser in use for each `test` should be defined by a parameter, like:
```xml
<parameter name="browser" value="chrome"/>
```
The class ```DriverManager``` create a `ThreadLocal` for the WebDriver instance, to make sure there's no conflict when we run it in parallel.

### Configuration files
There are 2 properties (configuration) files :
* `config.properties`: Located on `src/main/resources/` contains general configuration as the base url, timeout, test data, api url, key and end points etc.
* `extent.properties`: Located on `src/test/resources/` contains general configuration of report generation path and properties.

### Profiles executors on pom.xml

There is a profile called _web_execution_ created to execute the test suite _multi_browser.xml_ inside _src/test/resources_ folder.
To execute this suite, via command line you can call the parameter `-P` and the profile id.

If you have more than one suite on _src/test/resources_ folder you can parameterize the xml file name.
To do this you need:

* Create a property on pom.xml called _suite_

```xml
    <properties>
        <suite>multi_browser</suite>
    </properties>
```

* Change the profile id

```xml
<profile>
   <id>web_execution</id>
</profile>   
```

* Replace the xml file name to `${suite}` on the profile

```xml
<configuration>
   <suiteXmlFiles>
      <suiteXmlFile>src/test/resources/suites/${suite}.xml</suiteXmlFile>
   </suiteXmlFiles>
</configuration>
```

* Use `-Dsuite=suite_name` to call the suite

````bash
mvn test -Pweb_execution -Dsuite=multi_browser
````

## Deploy the code on local
```git
git clone https://github.com/rajatgoel1994/testvagrant-assignment.git
```

## Run the code
**1.** Go to the directory ```testvagrant-assignment\testvagrantassignment```

**2.** Run below Maven command to run tests:

```cmd
mvn test -Pweb_execution -Dsuite=multi_browser
```

In case the user doesn't want to specify all or any of the above, the user may run it as below:

```cmd
mvn test
```

## Extent Report
Use ```testvagrant-assignment\testvagrantassignment\src\test\resources\extent-config.xml``` to update report format, name and details.

Once test suite execution is completed, go to ```testvagrant-assignment\testvagrantassignment\target\reports\extent\HtmlReport``` path to see ```TestReport.html``` report.

## Slf4j Logs
All the log is done by the Slf4j using the `@Slf4j` annotation.

The `logback.xml` has two strategies: console and file.
A file with all the log information will be automatically created in the folder with `automation_log.log` filename. 
If you want to change it, update the `appender.file.fileName` property value.

The `log.error` is used to log all the exceptions this architecture might throw. Use `log.info` or `log.debug` to log 
important information.

To see and debug logs, go to ```testvagrant-assignment\testvagrantassignment\logs``` path to see ```automation_log.log``` file.


