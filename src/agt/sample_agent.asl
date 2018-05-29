// Agent sample_agent in project agent_ontology

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true 
<- 	//ontology_reader.get_tasks(100,10000,List);
	!get_jason_instances(List).
	
+!create_ontology_artifact : true
<- 	// ontology = ontology path 
	makeArtifact("ontology","agent_ontology.OntologyArtifact",["ontology"],Onto);
	print(Onto);
	focus(cobj_1).
	
+!get_jason_instances(List) : true
<- 	agent_ontology.memory_ms(M);
	//!create_ontology_artifact;
	for(.range(I,1,10)) {
		.create_agent(Bob,"test.asl");
	}
	.wait(500);
	agent_ontology.memory_ms(MA);
	.print(MA-M).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
//{ include("$moiseJar/asl/org-obedient.asl") }
