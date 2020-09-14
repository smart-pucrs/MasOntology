package pucrs.smart.ontology;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class OwlOntoLayer {
    protected OWLOntology ontology = null;
    protected OWLReasoner reasoner = null;
    protected String ontologyFilePath = null;

    public OwlOntoLayer(String ontologyFilePath) throws OWLOntologyCreationException {
        IRI iri = IRI.create((File)new File(ontologyFilePath));
        this.ontologyFilePath = iri.toString();
        System.out.println("Java - OwlOntoLayer ontologyFilePath " + ontologyFilePath);
        this.ontology = OwlOntoLayer.loadOntology(iri);
    }
    public OwlOntoLayer(String ontologyFilePath, OWLReasoner reasoner) throws OWLOntologyCreationException {
        IRI iri = IRI.create((File)new File(ontologyFilePath));
        this.ontologyFilePath = iri.toString();
        this.ontology = OwlOntoLayer.loadOntology(iri);
        System.out.println("Java - OwlOntoLayer reasoner " + reasoner);
        this.reasoner = reasoner;
    }

    public OWLReasoner getReasoner() {
        return this.reasoner;
    }
    public void setReasoner(OWLReasoner reasoner) {
        this.reasoner = reasoner;
    }

    public OWLOntology getOntology() {
        return this.ontology;
    }
    public void setOntology(OWLOntology ontology) {
        this.ontology = ontology;
    }

    public IRI getOntologyIRI() {
        if (this.ontology != null) {
            return this.ontology.getOntologyID().getOntologyIRI().get();
        }
        return null;
    }

    public static OWLOntology loadOntology(IRI ontologyIRI) throws OWLOntologyCreationException {
        if (ontologyIRI == null) {
            throw new NullPointerException("Path to the ontoloy file can't be null.");
        }
        OWLOntologyManager ontologyManager = OwlOntoLayer.createOntologyManager();
        return ontologyManager.loadOntologyFromOntologyDocument(ontologyIRI);
    }

    public static OWLOntologyManager createOntologyManager() {
        return OWLManager.createOWLOntologyManager();
    }

    public static OWLClass getOWLThing() {
        return OwlOntoLayer.createOntologyManager().getOWLDataFactory().getOWLThing();
    }

    public String getFilePath() {
        return this.ontologyFilePath;
    }

    public List<OWLClass> getClasses() {
    	return new ArrayList<OWLClass>(this.ontology.classesInSignature().collect(Collectors.toList()));
    }
    
   public List<OWLDataProperty> getDataProperties() {
        return new ArrayList<OWLDataProperty>(this.ontology.dataPropertiesInSignature().collect(Collectors.toList()));
    }
    
    public List<OWLObjectProperty> getObjectProperties() {
        return new ArrayList<OWLObjectProperty>(this.ontology.objectPropertiesInSignature().collect(Collectors.toList()));
    }

    public List<OWLAnnotationProperty> getAnnotationProperties() {
        return this.ontology.annotationPropertiesInSignature().collect(Collectors.toList());
    }

    public List<OWLIndividual> getIndividuals() {
        return new ArrayList<OWLIndividual>(this.ontology.individualsInSignature().collect(Collectors.toList()));
    }

    public List<OWLClass> getSubClasses(OWLClass owlClass, boolean onlyDirectSub) {
        if (this.reasoner != null) {
            return new ArrayList<OWLClass>(this.reasoner.getSubClasses((OWLClassExpression)owlClass, onlyDirectSub).getFlattened());
        }
        throw new NullPointerException("No reasoner has been defined");
    }
       

    public Set<OWLNamedIndividual> getInstances(OWLClass concept, boolean direct) {
        return this.reasoner.getInstances((OWLClassExpression)concept, direct).getFlattened();
    }

    public boolean isInstanceOf(OWLNamedIndividual instance, OWLClass concept) {
        for (OWLClass clas : this.getInstanceTypes(instance, false)) {
            if (!concept.getIRI().getFragment().equals(clas.getIRI().getFragment())) continue;
            return true;
        }
        return false;
    }

    public boolean isSubConceptOf(OWLClass subConcept, OWLClass superConcept) {
        OWLClass subClass = subConcept;
        Iterator<OWLClass> i$ = this.getSubClasses(superConcept, false).iterator();
        while (i$.hasNext()) {
            OWLClass concept;
            OWLClass owlClass = concept = i$.next();
            if (!subClass.getIRI().equals((Object)owlClass.getIRI())) continue;
            return true;
        }
        return false;
    }

    public Set<OWLNamedIndividual> getObjectPropertyValues(OWLNamedIndividual domain, OWLObjectProperty property) {
        return this.reasoner.getObjectPropertyValues(domain, (OWLObjectPropertyExpression)property).getFlattened();
    }

    public Set<OWLClass> getInstanceTypes(OWLNamedIndividual instance, boolean onlyDirectTypes) {
        return this.reasoner.getTypes(instance, onlyDirectTypes).getFlattened();
    }

    public boolean isRelated(OWLNamedIndividual domain, OWLObjectProperty property, OWLNamedIndividual range) {    	
        Set<OWLNamedIndividual> values = this.getObjectPropertyValues(domain, property);
        for (OWLNamedIndividual individual : values) {
            if (!individual.getIRI().getFragment().equals(range.getIRI().getFragment())) continue;
            return true;
        }
        return false;
    }

    public void addNewConcept(String conceptName) {
        this.addNewEntity((OWLEntity)this.getNewConcept(conceptName));
    }

    private OWLClass getNewConcept(String conceptName) {
        OWLDataFactory dataFactory = this.ontology.getOWLOntologyManager().getOWLDataFactory();
        IRI baseIRI = this.ontology.getOntologyID().getDefaultDocumentIRI().get();
        return dataFactory.getOWLClass(IRI.create((String)((Object)baseIRI + "#" + conceptName)));
    }

    private void addNewEntity(OWLEntity entity) {
        OWLDataFactory dataFactory = this.ontology.getOWLOntologyManager().getOWLDataFactory();
        OWLDeclarationAxiom declareNewEntity = dataFactory.getOWLDeclarationAxiom(entity);
        this.ontology.getOWLOntologyManager().addAxiom(this.ontology, (OWLAxiom)declareNewEntity);
    }

    public void addNewInstance(String conceptName, String instanceName) {
        OWLDataFactory dataFactory = this.ontology.getOWLOntologyManager().getOWLDataFactory();
        IRI baseIRI = this.ontology.getOntologyID().getDefaultDocumentIRI().get();
        OWLClass concept = dataFactory.getOWLClass(IRI.create((String)((Object)baseIRI + "#" + conceptName)));
        OWLNamedIndividual instance = dataFactory.getOWLNamedIndividual(IRI.create((String)((Object)baseIRI + "#" + instanceName)));
        OWLClassAssertionAxiom classAssertionAxiom = dataFactory.getOWLClassAssertionAxiom((OWLClassExpression)concept, (OWLIndividual)instance);
        this.ontology.getOWLOntologyManager().addAxiom(this.ontology, (OWLAxiom)classAssertionAxiom);
    }

    public void addNewInstance(String instanceName) {
        this.addNewEntity((OWLEntity)this.getNewInstance(instanceName));
    }

    private OWLNamedIndividual getNewInstance(String instanceName) {
        OWLDataFactory dataFactory = this.ontology.getOWLOntologyManager().getOWLDataFactory();
        IRI baseIRI = this.ontology.getOntologyID().getDefaultDocumentIRI().get();
        return dataFactory.getOWLNamedIndividual(IRI.create((String)((Object)baseIRI + "#" + instanceName)));
    }

    public void addNewObjectProperty(String domainName, String objectPropertyName, String rangeName) {
        OWLDataFactory dataFactory = this.ontology.getOWLOntologyManager().getOWLDataFactory();
        IRI baseIRI = this.ontology.getOntologyID().getDefaultDocumentIRI().get();
        OWLNamedIndividual domain = dataFactory.getOWLNamedIndividual(IRI.create((String)((Object)baseIRI + "#" + domainName)));
        OWLNamedIndividual range = dataFactory.getOWLNamedIndividual(IRI.create((String)((Object)baseIRI + "#" + rangeName)));
        OWLObjectProperty objectProperty = dataFactory.getOWLObjectProperty(IRI.create((String)((Object)baseIRI + "#" + objectPropertyName)));
        OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom((OWLObjectPropertyExpression)objectProperty, (OWLIndividual)domain, (OWLIndividual)range);
        this.ontology.getOWLOntologyManager().addAxiom(this.ontology, (OWLAxiom)propertyAssertion);
    }

    public void saveOntology(String outputFilePath) throws OWLOntologyStorageException {
        File file = new File(outputFilePath);
        OWLOntologyManager owlManager = this.ontology.getOWLOntologyManager();
        OWLDocumentFormat format =  owlManager.getOntologyFormat(this.ontology);
        OWLXMLDocumentFormat owlFormat = new OWLXMLDocumentFormat();
        if (format.isPrefixOWLDocumentFormat()) {
            owlFormat.copyPrefixesFrom(format.asPrefixOWLDocumentFormat());
        }
        owlManager.saveOntology(this.ontology, (OWLDocumentFormat)owlFormat, IRI.create((URI)file.toURI()));
    }
}

