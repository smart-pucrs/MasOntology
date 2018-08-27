package agent_ontology;

/* 
 * Class for measuring the perfomance of an application in terms of memory
 * 
 * It worths noting that this object is also accounted during the mesurement.
 * 
 *  @author Lucas Hilgert
 * 
 * */

public class Measurer {
	
	public static final long MEGABT = 1024L * 1024L;
	public static final long KYLOBT = 1024L;
	
	public static long getMemoryInMega() {
		
		callGarbageCollection();
		
		long cMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		
		return cMemory / MEGABT;
		
	}
	
	public static long getMemoryInKylo() {
		
		callGarbageCollection();
		
		long cMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		
		return cMemory / KYLOBT;
		
	}

	private static void callGarbageCollection() {

		Runtime.getRuntime().gc();
		
	}

}
