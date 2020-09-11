package pucrs.smart.ontology.mas;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import pucrs.smart.ontology.OntoQueryLayer;
import pucrs.smart.ontology.OwlOntoLayer;

public class OntoQueryLayerLiteral {
	private final String FunctorInstance 	= "instance"; 	// instance(Concept,Instance)
	private final String FunctorConcept 	= "concept"; 	// concept(Parent,Concept,[Siblings],[Children])
	
	private OntoQueryLayer ontoQuery;
	
    public OntoQueryLayerLiteral(OwlOntoLayer ontology) {
		this.ontoQuery = new OntoQueryLayer(ontology);
    }
    
    public OntoQueryLayer getQuery() {
    	return this.ontoQuery;
    }

	public List<Object> getClassNames() {
		ArrayList<Object> classNames = new ArrayList<Object>();
        for (OWLClass ontoClass : this.ontoQuery.getOntology().getClasses()) {
        	StringTerm st = ASSyntax.createString(ontoClass.getIRI().getFragment());
            classNames.add(st);
        }
        return classNames;
	}

	public List<Object> getObjectPropertieNames() {
		ArrayList<Object> objectProperties = new ArrayList<Object>();
        for (OWLObjectProperty objectProperty : this.ontoQuery.getOntology().getObjectProperties()) {
            if (objectProperty.isOWLBottomObjectProperty()) continue;
            StringTerm st = ASSyntax.createString(objectProperty.asOWLObjectProperty().getIRI().getFragment());
            objectProperties.add(st);
        }
        return objectProperties;
	}

	public List<Object> getDataPropertieNames() {
		ArrayList<Object> objectProperties = new ArrayList<Object>();
        for (OWLDataProperty dataProperty : this.ontoQuery.getOntology().getDataProperties()) {
            if (dataProperty.isOWLBottomObjectProperty()) continue;
            StringTerm st = ASSyntax.createString(dataProperty.asOWLDataProperty().getIRI().getFragment());
            objectProperties.add(st);
        }
        return objectProperties;
	}

	public List<Object> getAnnotationPropertieNames() {
		ArrayList<Object> annotationProperties = new ArrayList<Object>();
        for (OWLAnnotationProperty annotationProperty : this.ontoQuery.getOntology().getAnnotationProperties()) {
            if (annotationProperty.isBottomEntity()) continue;
            StringTerm st = ASSyntax.createString(annotationProperty.asOWLAnnotationProperty().getIRI().getFragment());
            annotationProperties.add(st);
        }
        return annotationProperties;
	}

	public List<Object> getIndividualNames(String conceptName) {
		List<Object> individuals = new ArrayList<Object>();
		try {
			Term concept = ASSyntax.createString(conceptName);
			
			for(OWLNamedIndividual individual : ontoQuery.getInstances(conceptName)){
				Literal l = ASSyntax.createLiteral(this.FunctorInstance, concept);
				l.addTerm(ASSyntax.parseTerm(individual.getIRI().getFragment()));
				individuals.add(l);
			}
		}
		catch(Exception e) {
			System.out.println("failed to parse: "+e.getMessage());
		}
		return individuals;
	}
}

