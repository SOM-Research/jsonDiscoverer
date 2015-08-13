jsonDiscoverer
===============

Visit the [webpage of the project](http://som-research.uoc.edu/tools/jsonDiscoverer) to play with the service.

What is this project about?
---------------------------

The JSON discoverer is a tool that allows you to get the implicit schema of your JSON documents. The tool also supports the discovery of composition links among the elements of the schemas of several APIs.

The tool is inspired in the research papers:

 - [Discovering Implicit Schemas in JSON Data](http://hal.inria.fr/docs/00/81/89/45/PDF/icwe2013-CanovasCabot.pdf) published at [ICWE'13 conference](http://icwe2013.webengineering.org/)
 - [Composing JSON-based Web APIs](http://modeling-languages.com/composing-json-based-web-apis/)  published at [ICWE'14 conference](http://icwe2014.webengineering.org/)

What can you find in this repository?
-------------------------------------

So far, these are the projects:

* **jsonDiscoverer**. Includes the core implementation of the discoverers to get Ecore models from JSON documents.
* **jsonDiscoverer.coverage**. EMF-generated Java Code defining an Ecore model to represent coverage models.
* **jsonDiscoverer.web**. Web project including (1) an angularJS-based client-side application and (2) a servlet-based server-side application to provide access to the discoverers. 
* **jsonDiscoverer.tests**. Includes some JUnit-based classes to test the discoverers. 
* **jsonDiscoverer.examples**. Includes some code examples to launch the discoverers.
* **jsonDiscoverer.zoo**. Some JSON documents collected from several Web APIs to test the discoverers.

The project uses the [GSON library](https://github.com/google/gson) to parse JSON documents.

Who is behind this project?
---------------------------
* [Javier Canovas](http://github.com/jlcanovas/ "Javier Canovas")
* [Jordi Cabot](http://github.com/jcabot/ "Jordi Cabot")
