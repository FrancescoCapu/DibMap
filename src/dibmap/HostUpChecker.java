package dibmap;

import java.io.IOException;
import java.net.InetAddress;

public class HostUpChecker {
	private String errorReason;
	
	public HostUpChecker() {

	}
	
	boolean checkReachability(InetAddress target) {
		int timeout = 5000;
		try {
			if (target.isReachable(timeout))
				return true;
			errorReason = "Ping timed out - took more than " + timeout + " ms.";
			return false;
		} catch (IOException e) {
			errorReason = "IO Exception during ping process.";
			return false;
		} catch (IllegalArgumentException e) {
			errorReason = "Illegal argument exception.";
			return false;
		}
	}
	
	String getErrorReason() {
		return errorReason;
	}
}