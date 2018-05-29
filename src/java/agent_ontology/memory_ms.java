package agent_ontology;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

public class memory_ms extends DefaultInternalAction{
	
	private static InternalAction singleton = null;
	
	public static InternalAction create() {		
		if(singleton == null) {
			singleton = new memory_ms();
		}		
		return singleton;		
	}
	
	@Override public int getMinArgs() { return 1; }
    @Override public int getMaxArgs() { return 1; }
    
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {    	
    	checkArguments(args);    	
    	long memoryConsuption = Measurer.getMemoryInKylo();    	
    	return un.unifies(args[0], new NumberTermImpl(memoryConsuption));    	
    }

}
