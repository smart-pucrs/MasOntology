// CArtAgO artifact code for project agent_ontology

package agent_ontology;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import cartago.*;
import pucrs.seateams.ontology.providers.OntologyQueryLayer;
import pucrs.seateams.ontology.providers.OwlOntologyLayer;

public class OntologyArtifact extends Artifact {
	
	private OwlOntologyLayer onto;
	private OntologyQueryLayer queryEngine;
	
	void init(String ontoFilePath) {
		
		try {
			onto = new OwlOntologyLayer(ontoFilePath);
			onto.setReasoner(new Reasoner(onto.getOntology()));
			queryEngine = new OntologyQueryLayer(onto);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		
	}

	@OPERATION 
	void loadOntology(String filePath) throws OWLOntologyCreationException {
		queryEngine.loadOntology(filePath);		
	}
	
	@OPERATION
	void addInstance(String instanceName) {
		queryEngine.addInstance(instanceName);
	}
	
	@OPERATION
	void addInstance(String instanceName, String conceptName) {
		queryEngine.addInstance(instanceName, conceptName);
	}
	
	@OPERATION
	void isInstanceOf(String instanceName, String conceptName, OpFeedbackParam<Boolean> isInstance) {
		isInstance.set(queryEngine.isInstanceOf(instanceName, conceptName));
	}
	
	@OPERATION
	void getInstances(String conceptName, OpFeedbackParam<String> instances){
		List<String> individuals = new ArrayList<String>();
		for(OWLNamedIndividual individual : queryEngine.getInstances(conceptName)){
			individuals.add(individual.getIRI().getFragment());
		}
		instances.set(individuals.toString());
	}
	
	@OPERATION
	void addProperty(String domainName, String propertyName, String rangeName) {
		queryEngine.addProperty(domainName, propertyName, rangeName);
	}
	
	@OPERATION
	void isRelated(String domainName, String propertyName, String rangeName, OpFeedbackParam<Boolean> isRelated) {
		isRelated.set(queryEngine.isRelated(domainName, propertyName, rangeName));
	}
	
	@OPERATION
	void getInstances(String domain, String propertyName, OpFeedbackParam<String> instances) {
		List<String> individuals = new ArrayList<String>();
		for(OWLNamedIndividual individual : queryEngine.getInstances(domain, propertyName)) {
			individuals.add(individual.getIRI().getFragment().replaceAll("-","_"));
		}
		instances.set(individuals.toString());
	}
	
	@OPERATION
	void addConcept(String conceptName) {
		queryEngine.addConcept(conceptName);
	}
	
	@OPERATION
	void isSubConcept(String subConceptName, String superConceptName, OpFeedbackParam<Boolean> isSubConcept) {
		isSubConcept.set(queryEngine.isSubConceptOf(subConceptName, superConceptName));
	}
	
	@OPERATION
	void getConcepts() {
		List<OWLClass> concepts = new ArrayList<OWLClass>();
		for (OWLClass concept : queryEngine.getConcepts()) {
			concepts.add(concept);
		}
	}
	
	@OPERATION
	void saveOntotogy(String outputFile) {
		try {
			queryEngine.saveOntology(outputFile);
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	}
	
	@OPERATION
	void test(String concept, Object[] list) {
		
		Date date = new Date(System.currentTimeMillis());
		System.out.println("Initial time "+date);
		
		for (int i = 0; i < list.length; i++) {
			isInstanceOf(list[i].toString(), concept);
		}
		
		date = new Date(System.currentTimeMillis());
		System.out.println("Final time "+date);
		
	}
	
	@OPERATION
	void isInstanceOf(String instanceName, String conceptName) {
		queryEngine.isInstanceOf(instanceName, conceptName);
	}
	
}



















