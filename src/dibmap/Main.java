package dibmap;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	private static InetAddress target;
	private static String[] scanType;
	private static final int threads = 64;
	private static ScanCommander commander;
	private static boolean endProgram = false;
	private static int startingPort;
	private static int endPort;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InputHandler in = new InputHandler();
		if (in.validateTarget(args[0]))
			target = in.getTargetIP();
		else 
			endProgram = true;

		if (in.validateParameters(args) && !endProgram) {
			scanType = in.getParameters();
			startingPort = in.getStartingPort();
			endPort = in.getEndPort();
		}
		else 
			endProgram = true;

		if (!endProgram) {
			HostUpChecker checker = new HostUpChecker();
			if (checker.checkReachability(target)) {
				// aggiungere opzione per cambiare intervallo in base a input
				commander = new ScanCommander(startingPort, endPort, threads);

				ExecutorService pool = Executors.newFixedThreadPool(threads);
				for (int i = 0; i < threads; i++) {
					ScanExecutor se = new ScanExecutor(target, scanType, commander);
					pool.execute(se);
				}
				pool.shutdown();
			}
			else {
				endProgram = true;
				Printer.print("Error during reachability test. Host seems down. Reason: " + checker.getErrorReason());
//				System.out.println("Error during reachability test. Reason: " + checker.getErrorReason());
			}	
		}
	}

	static void PrintResults() {
		ArrayList<Result> results = commander.getResults();
		System.out.println("-----SCAN RESULTS-----");
		System.out.println("Port\t\tStatus\t\tProtocol");

		int count = 0;
		int size = results.size();
		for (int i = 0; i < size; i++) {
//			if (!results.get(i).status.equals("closed")) {
				System.out.println(results.get(i).toString());
				if (!results.get(i).status.equals("closed"))
					count++;
		}
//			}
		System.out.println("-----END-----");
		int totalPorts = (endPort - startingPort + 1) * scanType.length;
		System.out.println("Open ports: " + count + " out of total " + totalPorts + " ports");
	}
}