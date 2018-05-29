// CArtAgO artifact code for project agent_ontology

package agent_ontology;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import pucrs.seateams.ontology.providers.OntologyQueryLayer;
import pucrs.seateams.ontology.providers.OwlOntologyLayer;
import uk.ac.manchester.cs.jfact.JFactFactory;
import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

/**
 * This artifact implements an interface layer between the agents
 * and the ontologies. 
 * <p>
 * 
 * 
 * @author Lucas Hilgert
 * @author Artur Freitas
 * @author Alison Panisson
 *
 */

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
	
	
	/**
	 * @param filePath Path to the file which defines the ontology.	 * @throws OWLOntologyCreationException
	 */
	@OPERATION 
	void loadOntology(String filePath) throws OWLOntologyCreationException {
		queryEngine.loadOntology(filePath);		
	}
	
	/**
	 * @param instanceName Name of the new instance.
	 */
	@OPERATION
	void addInstance(String instanceName) {
		queryEngine.addInstance(instanceName);
	}
	
	/**
	 * @param instanceName Name of the new instance.
	 * @param conceptName Name of the concept which the new instance instances.
	 */
	@OPERATION
	void addInstance(String instanceName, String conceptName) {
		queryEngine.addInstance(instanceName, conceptName);
	}
	
	/**
	 * @param instanceName Name of the instance.
	 * @param conceptName Name of the concept.
	 * @return true if the <code>instanceName</code> instances <code>conceptName</code>.
	 */
	@OPERATION
	void isInstanceOf(String instanceName, String conceptName, OpFeedbackParam<Boolean> isInstance) {
		isInstance.set(queryEngine.isInstanceOf(instanceName, conceptName));
	}
	
	/**
	 * @param conceptName Name of the concept.
	 * @return A list of instances of <code>conceptName</code>.
	 */
	@OPERATION
	void getInstances(String conceptName, OpFeedbackParam<String> instances){
		List<String> individuals = new ArrayList<String>();
		for(OWLNamedIndividual individual : queryEngine.getInstances(conceptName)){
			individuals.add(individual.getIRI().getFragment());
		}
		instances.set(individuals.toString());
	}
	
	/**
	 * @param domainName Name of the instance ({@link OWLNamedIndividual}} which represent the property <i>domain</i>.
	 * @param propertyName Name of the new property.
	 * @param rangeName Name of the instance ({@link OWLNamedIndividual}} which represent the property <i>range</i>.
	 */
	@OPERATION
	void addProperty(String domainName, String propertyName, String rangeName) {
		queryEngine.addProperty(domainName, propertyName, rangeName);
	}
	
	/**
	 * @param domainName Name of the instance which represents the domain of the property.
	 * @param propertyName Name of the property.
	 * @param rangeName Name of the instance which represents the range of the property.
	 * @return true if a instance of the property was found, false otherwise.
	 */
	@OPERATION
	void isRelated(String domainName, String propertyName, String rangeName, OpFeedbackParam<Boolean> isRelated) {
		isRelated.set(queryEngine.isRelated(domainName, propertyName, rangeName));
	}
	
	/**
	 * @param domain The name of the instance which corresponds to the domain of the property.
	 * @param propertyName Name of the property
	 * @return A list of ({@link OWLNamedIndividual}).
	 */
	@OPERATION
	void getInstances(String domain, String propertyName, OpFeedbackParam<String> instances) {
		List<String> individuals = new ArrayList<String>();
		for(OWLNamedIndividual individual : queryEngine.getInstances(domain, propertyName)) {
			individuals.add(individual.getIRI().getFragment().replaceAll("-","_"));
		}
		instances.set(individuals.toString());
	}
	
	/**
	 * @param conceptName Name of the new concept.
	 */
	@OPERATION
	void addConcept(String conceptName) {
		queryEngine.addConcept(conceptName);
	}
	
	/**
	 * @param subConceptName Name of the supposed sub-concept.
	 * @param superConceptName Name of the concept to be tested as the super-concept.
	 * @return true if <code>subConceptName</code> is a sub-concept of <code>sueperConceptName</code>, false
	 * otherwise.
	 */
	@OPERATION
	void isSubConcept(String subConceptName, String superConceptName, OpFeedbackParam<Boolean> isSubConcept) {
		isSubConcept.set(queryEngine.isSubConceptOf(subConceptName, superConceptName));
	}
	
	/**
	 * @return A list of concepts.
	 */
	@OPERATION
	void getConcepts() {
		List<OWLClass> concepts = new ArrayList<OWLClass>();
		for (OWLClass concept : queryEngine.getConcepts()) {
			concepts.add(concept);
		}
	}
	
	/**
	 * @param outputFile Path to the new file in the structure of directories.
	 * @throws OWLOntologyStorageException
	 */
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



















