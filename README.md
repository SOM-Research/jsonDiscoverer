jsonDiscoverer
===============

Visit the [webpage of the project](http://som-research.uoc.edu/tools/jsonDiscoverer/#/) to play with the service.

What is this project about?
---------------------------

The JSON discoverer is a tool that allows you to get the implicit schema of your JSON documents. The tool also supports the discovery of composition links among the elements of the schemas of several APIs.

These are currently the main functionalities provided:

* **Simple discovery**, which discovers the schema of a given set of JSON documents returned by a single service.
* **Advanced discovery**, which discovers the schema from a set of JSON-based services. First, the schema of each JSON-based service is discovered (by using the simple discoverer), then the resulting schemas are composed to obtain a general one.
* **API Composer**, which takes a set of API schemas, looks for composition links (i.e., common concepts or attributes) and generates a composition graph. The result is used to help developers to compose APIs. The tool currently incoporates a sequence diagram generator to visualize API compositions.

Our tool draws schema information as UML class diagrams, including concepts (i.e., classes) and their properties (i.e., attributes and associations linking the different concepts). Potential API compositions are represented by means of UML sequence diagrams showing the possible sequence of API calls.

This video summarizes the main features of the tool:

[![JSON Discoverer v2.1.1](http://img.youtube.com/vi/FifSRxWHjIU/0.jpg)](http://www.youtube.com/watch?v=FifSRxWHjIU)

The tool is inspired in the research papers:

 - [Discovering Implicit Schemas in JSON Data](https://www.researchgate.net/publication/262317804_Discovering_implicit_schemas_in_JSON_data?ev=prf_pub) published at [ICWE'13 conference](http://icwe2013.webengineering.org/)
 - [Composing JSON-based Web APIs](https://www.researchgate.net/publication/272824956_Composing_JSON-based_Web_APIs?ev=prf_pub)  published at [ICWE'14 conference](http://icwe2014.webengineering.org/)

What can you find in this repository?
-------------------------------------

So far, these are the projects (all of them developed as Eclipse plugins):

* **jsonDiscoverer**. Includes the core implementation of the discoverers to get schemas (and data models) out of JSON documents. Schemas are represented as Ecore files while data models are XMI files conforming to the Ecore ones. This therefore means that the tool depends on [Eclipse Modeling Framework](https://eclipse.org/modeling/emf/).
* **jsonDiscoverer.coverage**. EMF-generated Java Code from an Ecore model to represent coverage models. Coverage models are used to specify which parts in a global Ecore model are covered by smaller Ecore models.
* **jsonDiscoverer.docs**. The javadoc documentation generated out of the classes of the other projects. Also available [here](http://som-research.uoc.edu/tools/jsonDiscoverer/#/javadoc).
* **jsonDiscoverer.web**. Web project including (1) an angularJS-based client-side application and (2) a servlet-based server-side application to provide access to the discoverers. Servlets are basically the facade to the discoverers implemented in the jsonDiscoverer project.
* **jsonDiscoverer.tests**. Includes some JUnit-based classes to test the discoverers. 
* **jsonDiscoverer.examples**. Includes some code examples to launch the discoverers from Java. The project also includes some generation examples for the discoverers (see folder `exampleData` in the project). 
* **jsonDiscoverer.zoo**. Some JSON documents collected from several Web APIs to test the discoverers. The project depends on [EMF2GV](https://marketplace.eclipse.org/content/emf-graphviz-emf2gv).

Additionally, the projects use the [GSON library](https://github.com/google/gson) to parse JSON documents.

Documentation
---
You can find further details about how the tool works in the [Inner Workings](http://som-research.uoc.edu/tools/jsonDiscoverer/#/doc) section of the webpage.

The generated javadoc is also available [here](http://som-research.uoc.edu/tools/jsonDiscoverer/javadoc/)

Using JSONDiscoverer 
---
You can use the tool in three different ways:

**Accesing the tool website**. The easiest way to use our tool is to access to the [webpage of the project](http://som-research.uoc.edu/tools/jsonDiscoverer). Once in the landing page you can use the menus on the top bar to access to the different functionalities.

**In Java**. We recommend you to download all the Eclipse projects included in this GitHub project and import them in your Eclipse instalation. The project dependencies require your Eclipse to include: [EMF](https://eclipse.org/modeling/emf/) and [EMF2GV](https://marketplace.eclipse.org/content/emf-graphviz-emf2gv)

You can directly use the Java implementation of the discoverers provided in the `jsonDiscoverer` and `jsonDiscoverer.coverage` projects. You will also find the corresponding jar files in the root of these projects (you need both jars if you want to integrate our tool in your projects). You can build the jars by using the Ant build file located at the root of the projects. The `build.xml` file defines a default target that will build the JAR file automatically.

To understand better how each class works you can have a look at the [inner workings](http://som-research.uoc.edu/tools/jsonDiscoverer/#/doc) section of the webpage. and the [javadoc documentation](http://som-research.uoc.edu/tools/jsonDiscoverer/javadoc/).

We also recomment you to have a look at the project `jsonDiscoverer.examples`, where you will find some Java examples to use the discoverers and the toolset.

**As Web application**. The web application can be deployed as a WAR container file in your Tomcat (currently tested in Tomcat 7). We provide two ways in this option: (1) using a pre-compiled WAR or (2) create your own WAR file. 

The pre-compiled version of the WAR has to be configured as follows. 

 1. Download the **jsonDiscoverer.web** project
 2. Locate the file `jsonDiscoverer.war` on the root of the project
 2. Open the file with any container manager (e.g., WinRAR, WinZIP, etc...)
 2. Go to `WebContent/WEB-INF`folder and edit the file `config.properties`. Each line in this file has been commented to help you configure the tool. Please, pay special attention to those variables regarding configuration folders (e.g., `workingDir`) and the path to the [DOT](http://www.graphviz.org/) executable (e.g., `dotExePath`), which is required to generate the pictures.
 3. Go to `app/services` folder and edit the variable `this.prefix` in the file `discoverer.js`. This variable configures the endpoint in the Web client.
 4. Close the WAR file in your container manager
 5. Deploy the file in your Tomcat.

If this option does not work, follow the indications provided below to build your own WAR. 

 1. Download the **jsonDiscoverer.web** project
 2. Go to `WebContent/WEB-INF`folder 
 3. Open and configure the `config.properties.server` file. Each line in this file has been commented to help you configure the tool. Please, pay special attention to those variables regarding configuration folders (e.g., `workingDir`) and the path to the [DOT](http://www.graphviz.org/) executable (e.g., `dotExePath`), which is required to generate the pictures.
 4. Open and edit the variable `this.prefix` in the file `discoverer.js` located at `jsonDiscoverer.web/WebContent/app/services`. This variable configures the endpoint in the Web client. You will see that there are two definitions: one is used in development time (included the comment `%DEVELOPMENT%`) and the other is used for production (including the comment `%PRODUCTION%`). You have to edit the second one.  
 4. Once you have edited these files, you can build the WAR by using the Ant build file located at the root of the project. The `build.xml` file defines a default target that will build the WAR file automatically
 5. Take the generated WAR and copy to the `webapps` folder of your Tomcat instalation.

Can I collaborate?
---
Absolutely!. You can follow the typical GitHub contribution flow to ask/contribute:

 - For bugs or feature requests you can directly create an [issue](https://github.com/SOM-Research/jsonDiscoverer/issues). 
 - For implementing new features or directly address any issue, please fork the project, perform the patch and send us a [pull request](https://github.com/SOM-Research/jsonDiscoverer/pulls).

Whatever your contribution is, you will have an answer from us in less than 7 days.

You can find our (simple) governance rules in this [file](https://github.com/SOM-Research/jsonDiscoverer/blob/master/governance.md).

Who is behind this project?
---------------------------
* [Javier Canovas](http://github.com/jlcanovas/ "Javier Canovas")
* [Jordi Cabot](http://github.com/jcabot/ "Jordi Cabot")

License
---
This project is licensed under the [EPL](http://www.eclipse.org/legal/epl-v10.html) license. Documentation is licensed under the under [CC BY 3.0](http://creativecommons.org/licenses/by/3.0/).