package dibmap;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUpChecker {
	
	private String target;
	private String errorReason;
	
	public HostUpChecker(String target) {
		this.target = target;
	}
	
	boolean checkReachability() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(target);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorReason = "Impossible to determine IP address of specified target.";
			return false;
		} catch (SecurityException e) {
			e.printStackTrace();
			errorReason = "A security violation has been verified.";
			return false;
		}
		
		int timeout = 5000;
		try {
			if (addr.isReachable(timeout))
				return true;
			errorReason = "Ping timed out - took more than " + timeout + " ms.";
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorReason = "IO Exception during ping process.";
			return false;
		}
	}
	
	String getErrorReason() {
		return errorReason;
	}
}
