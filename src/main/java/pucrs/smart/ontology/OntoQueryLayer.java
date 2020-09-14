package pucrs.smart.ontology;

import java.io.File;
import java.util.Set;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class OntoQueryLayer {
    protected OwlOntoLayer ontology;

    public OntoQueryLayer(OwlOntoLayer ontology) {
        this.ontology = ontology;
    }

    public OwlOntoLayer getOntology() {
        return this.ontology;
    }

    public void setOntology(OwlOntoLayer ontology) {
        this.ontology = ontology;
    }

    public void loadOntology(String filePath) throws OWLOntologyCreationException {
    	
        IRI ontologyIRI = IRI.create(new File(filePath));
        this.ontology.setOntology(OwlOntoLayer.loadOntology(IRI.create(new File(filePath))));
    }

    private OWLClass getOWLClass(String className) {
        OWLDataFactory factory = this.ontology.getOntology().getOWLOntologyManager().getOWLDataFactory();
        String iri = this.ontology.getOntologyIRI() + "#" + className;
        return factory.getOWLClass(iri);
    }

    private OWLNamedIndividual getOWLIndividual(String individualName) {
        OWLDataFactory factory = this.ontology.getOntology().getOWLOntologyManager().getOWLDataFactory();
        return factory.getOWLNamedIndividual(this.ontology.getOntologyIRI() + "#" + individualName);
    }

    private OWLObjectProperty getOWLObjectProperty(String propertyName) {
        OWLDataFactory factory = this.ontology.getOntology().getOWLOntologyManager().getOWLDataFactory();
        return factory.getOWLObjectProperty(this.ontology.getOntologyIRI() + "#" + propertyName);
    }

    public void addInstance(String instanceName) {
        this.ontology.addNewInstance(instanceName);
    }

    public void addInstance(String instanceName, String conceptName) {
        this.ontology.addNewInstance(conceptName, instanceName);
    }

    public boolean isInstanceOf(String instanceName, String conceptName) {
        return this.ontology.isInstanceOf(this.getOWLIndividual(instanceName), this.getOWLClass(conceptName));
    }

    public Set<OWLNamedIndividual> getInstances(String conceptName) {
        return this.ontology.getInstances(this.getOWLClass(conceptName), false);
    }

    public void addProperty(String domainName, String propertyName, String rangeName) {
        this.ontology.addNewObjectProperty(domainName, propertyName, rangeName);
    }

    public boolean isRelated(String domainName, String propertyName, String rangeName) {
        return this.ontology.isRelated(this.getOWLIndividual(domainName), this.getOWLObjectProperty(propertyName), this.getOWLIndividual(rangeName));
    }

    public Set<OWLNamedIndividual> getObjectPropertyValues(String domain, String propertyName) {
    	return this.ontology.getObjectPropertyValues(this.getOWLIndividual(domain), this.getOWLObjectProperty(propertyName));

    }

    public void addConcept(String conceptName) {
        this.ontology.addNewConcept(conceptName);
    }

    public boolean isSubConceptOf(String subConceptName, String superConceptName) {
        return this.ontology.isSubConceptOf(this.getOWLClass(subConceptName), this.getOWLClass(superConceptName));
    }

    public void saveOntology(String outputFile) throws OWLOntologyStorageException {
        this.ontology.saveOntology(outputFile);
    }
}

