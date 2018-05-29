# MasOntology

This project is a developing and infrastructure layer coded in CArTAgO to enable **ontology** reasoning and querying features in Jason.

## Getting ready

First, clone or download to a local directory. To start developing, open Eclipse, and select from the toolbar:

File > Import > Jacamo > Jacamo Project

## CArTAgO Artifact

The artifact provides the following operations:

| Operation | Function |
| ------------- | ------------- |
| **loadOntology** | load ontology from an <code>OWL</code> file |
| **addInstance** | add new instance to the ontology |
| **addInstance** | add new instance to the ontology, the new instance<br> is added as an instance of a predefined concept |
| **isInstanceOf** | verifies if the instance <code>instanceName</code> <br>is an instance of the concept <code>conceptName</code> |
| **getInstances** | returns all instances from the concept <code>conceptName</code> |
| **addProperty** | add new property to the ontology |
| **isRelated** | verifies if there is a instance of the property in which <br> the instance referred by <code>domainName</code> corresponds <br> to the domain of the property, and the instance referred<br> by <code>rangeName</code> corresponds to range of the property. |
| **getInstances** | return a set of instances that correspond to the range <br> of the property referred by <code>propertyName</code> when <br> the domain of the property is the instance referred <code>propertyName</code> |
| **addConcept** | add new concept to the ontology |
| **isSubConcept** | tests if the concept referred by <code>subConceptName</code> <br>is a sub-concept of the concept referred <br>by the name <code>superConceptName</code> |
| **getConcepts** | return all the concepts of the ontology |
| **saveConcepts** | saves the current ontology to a <code>XML</code> file,<br> using the <code>OWL</code> notation |
