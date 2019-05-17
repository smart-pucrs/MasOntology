// Agent sample_agent in project onto

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */
+!start 
	: true 
<- 
	.print("Let's use an ontology");
	getInstances("Beagle", Instances);
	.print("Instances: ",Instances);
	!print(Instances);
	.
	
+!print([]).
+!print([instance(_,H)|T])
<-
	.print("I have a dog ",H);
	!print(T)
	.

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
