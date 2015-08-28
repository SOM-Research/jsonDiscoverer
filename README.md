jsonDiscoverer
===============

Visit the [webpage of the project](http://som-research.uoc.edu/tools/jsonDiscoverer) to play with the service.

What is this project about?
---------------------------

The JSON discoverer is a tool that allows you to get the implicit schema of your JSON documents. The tool also supports the discovery of composition links among the elements of the schemas of several APIs.

The tool is inspired in the research papers:

 - [Discovering Implicit Schemas in JSON Data](https://www.researchgate.net/publication/262317804_Discovering_implicit_schemas_in_JSON_data?ev=prf_pub) published at [ICWE'13 conference](http://icwe2013.webengineering.org/)
 - [Composing JSON-based Web APIs](https://www.researchgate.net/publication/272824956_Composing_JSON-based_Web_APIs?ev=prf_pub)  published at [ICWE'14 conference](http://icwe2014.webengineering.org/)

What can you find in this repository?
-------------------------------------

So far, these are the projects (all of them developed as Eclipse plugins):

* **jsonDiscoverer**. Includes the core implementation of the discoverers to get schemas (and data models) out of JSON documents. Schemas are represented as Ecore files while data models are XMI files conforming to the Ecore ones. This means that the tool depends on [Eclipse Modeling Framework](https://eclipse.org/modeling/emf/).
* **jsonDiscoverer.coverage**. EMF-generated Java Code from an Ecore model to represent coverage models. Coverage models are used to specify which parts in a general Ecore model are covered by smaller Ecore models.
* **jsonDiscoverer.web**. Web project including (1) an angularJS-based client-side application and (2) a servlet-based server-side application to provide access to the discoverers. Servlets are basically the facade to the discoverers implemented in the jsonDiscoverer project.
* **jsonDiscoverer.tests**. Includes some JUnit-based classes to test the discoverers. 
* **jsonDiscoverer.examples**. Includes some code examples to launch the discoverers from Java. The project also includes some generation examples for the discoverers (see folder `exampleData` in the project). 
* **jsonDiscoverer.zoo**. Some JSON documents collected from several Web APIs to test the discoverers.

The project uses the [GSON library](https://github.com/google/gson) to parse JSON documents.

Using JSONDiscoverer 
---
You can use the tool in three ways:
* **Accesing the tool website**. The easiest to use our tool is to access to the [webpage of the project](http://som-research.uoc.edu/tools/jsonDiscoverer)
* **In Java**. You can directly use the Java implementation of the discoverers provided in the jsonDiscoverer project. Refer to project jsonDiscoverer.examples to see some Java example code to use the discoverers
* **As Web application**. You can create the WAR container file out of the jsonDiscoverer.web project and deploy it in your Tomcat (currently tested in Tomcat 7). The WAR is not provided because some tuning has to be made, in particular, see the file `config.properties` located in the `WebContent/WEB-INF`folder

Can I collaborate?
---
Absolutely!. This project is licensed under the EPL license so feel free to contribute. You can find our (simple) governance rules in this [file](https://github.com/SOM-Research/jsonDiscoverer/blob/master/governance.md)

Who is behind this project?
---------------------------
* [Javier Canovas](http://github.com/jlcanovas/ "Javier Canovas")
* [Jordi Cabot](http://github.com/jcabot/ "Jordi Cabot")
