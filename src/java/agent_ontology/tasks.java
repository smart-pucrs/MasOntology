package agent_ontology;

import java.util.ArrayList;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Term;

public class tasks extends DefaultInternalAction{
	
	@Override public int getMinArgs() { return 3; }
	@Override public int getMaxArgs() { return 3; }

	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception{		
		Integer numberSel = Integer.parseInt(args[0].toString());
		Integer numberAll = Integer.parseInt(args[1].toString());
		
		int interval = numberAll / numberSel;
		int intervalSum = 0;
		
		ArrayList<String> task = new ArrayList<String>();
		
		for (int i = 0; i < numberSel; i++) {
			task.add("task_"+intervalSum);
			intervalSum = intervalSum + interval;
		}
		
		return un.unifies(args[2], ASSyntax.parseTerm(task.toString()));		
	}
	
}
