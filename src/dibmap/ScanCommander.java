package dibmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScanCommander {

	private int activeThreads;
	private int index = 0;
	private ArrayList<Integer> portInterval = new ArrayList<Integer>();
	private ArrayList<Result> results = new ArrayList<Result>();
	
	public ScanCommander(int startingPort, int endingPort, int activeThreads) {
		this.activeThreads = activeThreads;
		for (int i = startingPort; i <= endingPort; i++) {
			portInterval.add(i);
		}
		
//		Debug
//		
//		for (int i = 0; i < portInterval.size(); i++)
//			System.out.println(portInterval.get(i));
	}
	
	/*
	 * To be reviewed since dibmap should support different scan types at once
	 */
	synchronized int getPort() {
		if (index < portInterval.size())
			return portInterval.get(index++);
		else {
			activeThreads--;
			if (activeThreads == 0)
				Main.PrintResults();
			return -1;
		}
	}
	
	synchronized void recordResult(Result r) {
		results.add(r);
	}
	
	ArrayList<Result> getResults() {
		Collections.sort(results, new Comparator<Result>() {
			@Override
			public int compare(Result result1, Result result2) {
				// TODO Auto-generated method stub
				return result1.port < result2.port ? 0 : 1;
			}
		});
		return results;
	}
}
