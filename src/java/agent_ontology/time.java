package agent_ontology;

import java.util.Calendar;
import java.util.GregorianCalendar;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

public class time extends DefaultInternalAction{
	
	private static InternalAction singleton = null;
	
	public static InternalAction create() {
		if(singleton == null) {
			singleton = new time();
		}
		return singleton;
	}
	
	@Override public int getMinArgs() { return 4; }
    @Override public int getMaxArgs() { return 4; }
    
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception{
    	
    	checkArguments(args);
    	
    	Calendar now = new GregorianCalendar();
    	return 	un.unifies(args[0], new NumberTermImpl(now.get(Calendar.HOUR_OF_DAY))) &&
    			un.unifies(args[1], new NumberTermImpl(now.get(Calendar.MINUTE))) &&
    			un.unifies(args[2], new NumberTermImpl(now.get(Calendar.SECOND))) &&
    			un.unifies(args[3], new NumberTermImpl(now.get(Calendar.MILLISECOND)));
    }

}
