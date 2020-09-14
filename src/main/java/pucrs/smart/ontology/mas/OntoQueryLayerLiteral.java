package pucrs.smart.ontology.mas;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import pucrs.smart.ontology.OntoQueryLayer;
import pucrs.smart.ontology.OwlOntoLayer;

public class OntoQueryLayerLiteral {
	private final String FunctorInstance 	       = "instance"; 	      // instance(Concept,Instance,InstanceNameForJason)
	private final String FunctorConcept 	       = "concept"; 	      // concept(Concept,ConceptNameForJason)
	private final String FunctorObjectProperty     = "objectProperty";    // objectProperty(ObjectPropertyName,objectPropertyNameForJason)
	private final String FunctorAnnotationProperty = "annotationProperty";// annotationProperty(AnnotationProperty,annotationPropertyNameForJason)
	private final String FunctorDataProperty       = "dataProperty";      // dataProperty(DataProperty,dataPropertyNameForJason)
	
	private OntoQueryLayer ontoQuery;
	
    public OntoQueryLayerLiteral(OwlOntoLayer ontology) {
		this.ontoQuery = new OntoQueryLayer(ontology);
    }
    
    public OntoQueryLayer getQuery() {
    	return this.ontoQuery;
    }
    
    public static String removeAccents(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        str = str.replaceAll("-", "_");
        return str;
    }
    
    public static String getNameForJason(String str) {
        str = removeAccents(str.substring(0,1).toLowerCase() + str.substring(1));
        return str;
    }
        
    public List<Object> getClassNames() {
		List<Object> classNames = new ArrayList<Object>();
		try {
			for (OWLClass ontoClass : this.ontoQuery.getOntology().getClasses()) {
	            Literal l = ASSyntax.createLiteral(this.FunctorConcept, ASSyntax.createString(ontoClass.getIRI().getFragment()));
				l.addTerm(ASSyntax.createAtom(getNameForJason(ontoClass.getIRI().getFragment())));
				classNames.add(l);
	        }
		}
		catch(Exception e) {
			System.out.println("failed to parse: "+e.getMessage());
		}
        return classNames;
	}
    
    public List<Object> getObjectPropertyNames() {
		List<Object> objectProperties = new ArrayList<Object>();
		try {
			for (OWLObjectProperty objectProperty : this.ontoQuery.getOntology().getObjectProperties()) {
				if (objectProperty.isOWLBottomObjectProperty()) continue;
				Literal l = ASSyntax.createLiteral(this.FunctorObjectProperty, ASSyntax.createString(objectProperty.asOWLObjectProperty().getIRI().getFragment()));
				l.addTerm(ASSyntax.createAtom(getNameForJason(objectProperty.asOWLObjectProperty().getIRI().getFragment())));
				objectProperties.add(l);
	        }
		}
		catch(Exception e) {
			System.out.println("failed to parse: "+e.getMessage());
		}
        return objectProperties;
	}

	public List<Object> getDataPropertyNames() {
		List<Object> dataProperties = new ArrayList<Object>();
		try {
	        for (OWLDataProperty dataProperty : this.ontoQuery.getOntology().getDataProperties()) {
	            if (dataProperty.isOWLBottomObjectProperty()) continue;
	            Literal l = ASSyntax.createLiteral(this.FunctorDataProperty, ASSyntax.createString(dataProperty.getIRI().getFragment()));
				l.addTerm(ASSyntax.createAtom(getNameForJason(dataProperty.getIRI().getFragment())));
				dataProperties.add(l);
	        }
		}
		catch(Exception e) {
			System.out.println("failed to parse: "+e.getMessage());
		}
        return dataProperties;
	}

	public List<Object> getAnnotationPropertyNames() {
		List<Object> annotationProperties = new ArrayList<Object>();
		try {
			for (OWLAnnotationProperty annotationProperty : this.ontoQuery.getOntology().getAnnotationProperties()) {
				if (annotationProperty.isBottomEntity()) continue;
				Literal l = ASSyntax.createLiteral(this.FunctorAnnotationProperty, ASSyntax.createString(annotationProperty.getIRI().getFragment()));
				l.addTerm(ASSyntax.createAtom(getNameForJason(annotationProperty.getIRI().getFragment())));
				annotationProperties.add(l);
	        }
		}
		catch(Exception e) {
			System.out.println("failed to parse: "+e.getMessage());
		}
        return annotationProperties;
	}

	public List<Object> getIndividualNames(String conceptName) {
		List<Object> individuals = new ArrayList<Object>();
		try {
			Term concept = ASSyntax.createString(conceptName);
			
			for(OWLNamedIndividual individual : ontoQuery.getInstances(conceptName)){
				Literal l = ASSyntax.createLiteral(this.FunctorInstance, concept);
				l.addTerm(ASSyntax.createString(individual.getIRI().getFragment()));
				l.addTerm(ASSyntax.createAtom(getNameForJason(individual.getIRI().getFragment())));
				individuals.add(l);
			}
		}
		catch(Exception e) {
			System.out.println("failed to parse: "+e.getMessage());
		}
		return individuals;
	}
}

