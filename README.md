# MasOntology

This project is a developing and infrastructure layer coded in CArTAgO to enable **ontology** reasoning and querying features in Jason.

## Getting ready

Getting ready
To start developing and contributing to this repository, open Eclipse, and select from the toolbar:

File > Import > Git > Projects from Git > Clone URI

Copy https://github.com/smart-pucrs/MasOntology.git and paste it on the URI field.

The rest should autocomplete. Add your github credentials in the Authentication fields.
Next > Tick master > Next > Select destination folder, do not tick clonesubmodules, remote name: origin > Next > Receiving objects (after that select import existing Eclipse projects) > Next > Tick masontology and search for nested projects, do not tick add project to working sets > Finish

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
