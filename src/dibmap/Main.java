package dibmap;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	private static String target;
	private static char scanType;
	private static final int threads = 16;
	private static ScanCommander commander;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		InputHandler in = new InputHandler();	
		in.Scan();
		
		target = in.getTarget();
		scanType = in.getScanType();
		
		HostUpChecker checker = new HostUpChecker(target);
		if (checker.checkReachability()) {
			
			// aggiungere opzione per cambiare intervallo in base a input
			commander = new ScanCommander(1, 1023, threads);
			
			ExecutorService pool = Executors.newFixedThreadPool(threads);
			for (int i = 0; i < threads; i++) {
				ScanExecutor se = new ScanExecutor(target, scanType, commander);
				pool.execute(se);
			}
			pool.shutdown();
		}
		else {
			System.out.println("Error during reachability test. Reason: " + checker.getErrorReason());
		}
			
	}
	
	static void PrintResults() {
		ArrayList<Result> results = commander.getResults();
		System.out.println("-----SCAN RESULTS-----");
		System.out.println("Port\tStatus\tProtocol");
		
		int count = 0;
		int size = results.size();
		for (int i = 0; i < size; i++)
			if (!results.get(i).status.equals("closed")) {
				System.out.println(results.get(i).toString());
				count++;
			}
		System.out.println("-----END-----");
		System.out.println("Open ports: " + count);
	}
}