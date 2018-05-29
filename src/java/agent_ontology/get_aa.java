package agent_ontology;

import jason.architecture.AgArch;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.StringTermImpl;
import jason.asSyntax.Term;

public class get_aa extends DefaultInternalAction{
	
	private static InternalAction singleton = null;
	
	public static InternalAction create() {
		if(singleton == null) {
			singleton = new get_aa();
		}
		return singleton;
	}
	
	@Override public int getMinArgs() { return 1; }
	@Override public int getMaxArgs() { return 1; }
	
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception{
		
		checkArguments(args);
		
		AgArch teste = ts.getUserAgArch();
		
		return un.unifies(args[0], new StringTermImpl(teste.toString()));
	}

}
