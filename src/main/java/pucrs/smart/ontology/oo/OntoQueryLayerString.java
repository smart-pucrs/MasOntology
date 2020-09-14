package pucrs.smart.ontology.oo;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import pucrs.smart.ontology.OntoQueryLayer;
import pucrs.smart.ontology.OwlOntoLayer;

public class OntoQueryLayerString{
	private OntoQueryLayer ontoQuery;

    public OntoQueryLayerString(OwlOntoLayer ontology) {
        this.ontoQuery = new OntoQueryLayer(ontology); 
    }
    
    public OntoQueryLayer getQuery() {
    	return this.ontoQuery;
    }

    public List<Object> getObjectPropertieNames() {
        ArrayList<Object> objectProperties = new ArrayList<Object>();
        for (OWLObjectProperty objectProperty : this.ontoQuery.getOntology().getObjectProperties()) {
            if (objectProperty.isOWLBottomObjectProperty()) continue;
            objectProperties.add(objectProperty.asOWLObjectProperty().getIRI().getFragment());
        }
        return objectProperties;
    }
    
    public List<Object> getDataPropertieNames() {
        ArrayList<Object> objectProperties = new ArrayList<Object>();
        for (OWLDataProperty dataProperty : this.ontoQuery.getOntology().getDataProperties()) {
            if (dataProperty.isOWLBottomObjectProperty()) continue;
            objectProperties.add(dataProperty.asOWLDataProperty().getIRI().getFragment());
        }
        return objectProperties;
    }

    public List<Object> getAnnotationPropertieNames() {
        ArrayList<Object> annotationProperties = new ArrayList<Object>();
        for (OWLAnnotationProperty annotationProperty : this.ontoQuery.getOntology().getAnnotationProperties()) {
            if (annotationProperty.isBottomEntity()) continue;
            annotationProperties.add(annotationProperty.asOWLAnnotationProperty().getIRI().getFragment());
        }
        return annotationProperties;
    }

}

