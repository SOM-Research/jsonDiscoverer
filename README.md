JSON-Discoverer
===============

Visit the [webpage of the project](http://atlanmod.github.io/json-discoverer) to play with the service.

What is this project about?
---------------------------

The JSON discoverer is a tool that allows you to get the implicit schema of your JSON documents. The tool is inspired in the research paper [Discovering Implicit Schemas in JSON Data](http://hal.inria.fr/docs/00/81/89/45/PDF/icwe2013-CanovasCabot.pdf) published in the [ICWE'13 conference](http://icwe2013.webengineering.org/).

The tool also supports the discovery of composition links among the elements of the schemas of several APIs.

What can you find in this repository?
-------------------------------------

So far, these are the projects:

* fr.inria.atlanmod.json.discoverer. This project includes the needed discoverers to get Ecore models from JSON files.
* fr.inria.atlanmod.json.discoverer.coverage. EMF project defining an Ecore model to represent coverage models
* fr.inria.atlanmod.json.discoverer.ui. This project contributes to Eclipse platform, in particular, the MoDisco project to provide the needed UI for using the discoverers.
* fr.inria.atlanmod.json.discoverer.tests. This project includes some classes to test the discoverers. This may not work in your computer.
* fr.inria.atlanmod.json.discoverer.zoo. This project includes some JSON examples coming form different APIs.
* fr.inria.atlanmod.json.web. Web project including some servlets to provide web access to the discoverers. 

The webpage of the service can be found in the branch gh-pages.

This project also uses the following external libraries:

* com.google.gson. GSON library built as an Eclipse plugin project.

Installation and Usage
----------------------
Requirements:

* JRE 1.6 or above
* Eclipse 3.6 or above
* EMF 2.8 or above
* MoDisco 0.10 or above
* Xtext 2.3 or above

The project fr.inria.atlanmod.json.web is a stand-alone project, meaning that it includes the needed libraries to execute the tool out of Eclipse.

If you have any problem, just contact us.

What is coming next?
--------------------

We have released the web-based version of the tool and now we are testing the service to improve the discoverer.

Who is behind this project?
---------------------------
* [Javier Canovas](http://github.com/jlcanovas/ "Javier Canovas")
* [Jordi Cabot](http://github.com/jcabot/ "Jordi Cabot")

Javier and Jordi work in [Atlanmod](http://www.emn.fr/z-info/atlanmod), a research team of Inria.
