package agent_ontology;

import java.util.Calendar;
import java.util.GregorianCalendar;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

public class time_ms extends DefaultInternalAction{
	
	private static InternalAction singleton = null;
	
	public static InternalAction create() {
		if(singleton == null) {
			singleton = new time_ms();
		}
		return singleton;
	}
	
	@Override public int getMinArgs() { return 1; }
    @Override public int getMaxArgs() { return 1; }
    
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        checkArguments(args);

        Calendar now = new GregorianCalendar();
        return un.unifies(args[0], new NumberTermImpl(now.getTimeInMillis()));
    }

}
