package dibmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScanCommander {
	private int index = 0;
	private ArrayList<Integer> portInterval = new ArrayList<Integer>();
	private ArrayList<Result> results = new ArrayList<Result>();
	
	public ScanCommander(int startingPort, int endingPort, int activeThreads) {
		for (int i = startingPort; i <= endingPort; i++) {
			portInterval.add(i);
		}
	}
	
	synchronized int getPort() {
		if (index < portInterval.size())
			return portInterval.get(index++);
		else {
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
				return result1.port - result2.port;
			}
		});
		return results;
	}
}
