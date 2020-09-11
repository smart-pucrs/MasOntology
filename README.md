# MasOntology
This repository contains code to enable ontology reasoning and querying features in Jason. This work has been inspired by the research published in: 
- [Integrating Ontologies with Multi-Agent Systems through CArtAgO Artifacts](https://ieeexplore.ieee.org/abstract/document/7397351)
- [Applying ontologies to the development and execution of Multi-Agent Systems](https://content.iospress.com/articles/web-intelligence/web366)

![Jason](https://img.shields.io/badge/Jason-2.5-brightgreen.svg)
![OwlApi](https://img.shields.io/badge/OWLApi-5.1.10-brightgreen.svg)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

## Useful Tools

1. Git - https://git-scm.com/downloads
2. Java - https://www.java.com/pt_BR/download/
3. Eclipse - https://www.eclipse.org/downloads/
4. Jacamo - http://jacamo.sourceforge.net/doc/install.html
5. Gradle - https://gradle.org/install/

## Using this Artifact in your Project

Check out our JaCaMo demo project: demo/simple_example.

To get it running using gradle you need to: 
1. Add this to the gradle repositories block: 

``` maven { url 'https://jitpack.io' } ```

2. Add this to the gradle dependencies block: 

``` implementation 'com.github.smart-pucrs:MasOntology:<desired tag>'     ```

## Contributing to this Project

1. Open the terminal and clone this repository to your computer:

``` git clone https://github.com/smart-pucrs/MasOntology.git ```

2. Still in the terminal, in the project directory, enter the following command to configure the project to be opened in Eclipse:

```gradle eclipse```

3. Open the eclipse and import the project to view it.

4. Run the classe main.java


