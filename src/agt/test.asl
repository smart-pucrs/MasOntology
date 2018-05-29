// Agent test in project agent_ontology

/* Initial beliefs and rules */

/* Initial goals */

{ include("bb_10000.asl") }

!start.

/* Plans */

+!start : true <- .print("hello!").

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
