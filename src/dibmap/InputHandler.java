package dibmap;

import java.net.InetAddress;
import java.net.UnknownHostException;
//import java.util.Scanner;
//import org.apache.commons.cli.*;	// Provare ad usare questa libreria per input parametri

public class InputHandler {
	
//	private Scanner s;
//	private String target;
//	private char scanType;
	
	private InetAddress target = null;
	
	public InputHandler() {
		
	}
	
	boolean validateTarget(String target) {
//		String target = args[0];
		
//		String[] targetIP = target.split("\\.");
//		int length = targetIP.length;
		
//		if (length != 4)
//			return false;
//		
//		for (int i = 0; i < targetIP.length; i++) {
//			System.out.println(targetIP[i]);
//			try {
//				int temp = Integer.parseInt(targetIP[i]); 
//				if (temp < 0 || temp > 255)
//					return false;
//			} catch (NumberFormatException e) {
//				return false;
//			}
//		}
		
		InetAddress ip;
		
		try {
//			ip = InetAddress.getByName(target);
//			System.out.println(ip);
			this.target = InetAddress.getByName(target);
			System.out.println(this.target);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Wrong IP.");
			e.printStackTrace();
		} catch (SecurityException e) {
			System.out.println("Security issue.");
			e.printStackTrace();
		}
		System.out.println("Target validated.");
		return true;
	}
	
	InetAddress getTargetIP() {
		return target;
	}
	
	boolean validateParameters() {
		return true;
	}
	
	// Deprecated
//	void Scan() {
//		
//		//Options options = new Options();
//		
//		//Option input1 = new Option("i", "input", true, "input file path");
//	    //input1.setRequired(true);
//	    //options.addOption(input1);
//		
//		
//		s = new Scanner(System.in);
//		System.out.println("Thanks for using dibmap. Type 'h' for help in any moment.");
//		while (true) {
//			System.out.print("Target: ");
//			target = s.nextLine();
//			if (validateTarget(target))
//				break;
//			else
//				if (!target.equals("h"))
//					System.out.println("Wrong target format. Check it and try again.");
//				else
//					printGuide(1);
//		}
//		while (true) {
//			System.out.print("Scan type [c/s/f/u]: ");
//			String input = s.next();
//			scanType = input.charAt(0);
//			if (validateScanType(scanType))
//				break;
//			else
//				if (scanType != 'h')
//					System.out.println("Option not supported. Select a valid option.");
//				else
//					printGuide(2);
//		}
//	}
	
//	private boolean validateTarget(String target) {
//		String[] ipAddr;
//		
//		if (target.equals("localhost"))
//			return true;
//		
////		else if (target.equals("broadcast")) {
////			boh?
////		}
//		
////		else if (target.contains("\\/"))
////			return false;
//		
//		else if (target.contains(".")) {
//			ipAddr = target.split("\\.");
//			if (ipAddr.length != 4)
//				return false;
//			int value;
//			for (int i = 0; i < 4; i++) {
//				try {
//					value = Integer.parseInt(ipAddr[i]);
//					if (value < 0 || value > 255)
//						return false;
//				} catch (NumberFormatException e) {
//					return false;
//				}
//			}
//			return true;
//		}
//		return false;
//	}
//	
//	private boolean validateScanType(char c) {
//		switch (c) {
//		case 'c':
//		case 's':
//		case 'f':
//		case 'u':
//			return true;
//		default:
//			return false;
//		}
//	}
//	
//	private void printGuide(int inputHelp) {
//		switch (inputHelp) {
//		case 1:
//			System.out.println("To select a target, enter a valid IPv4 address in the form 'xxx.xxx.xxx.xxx'.\nTo scan your own host, you can also type 'localhost'");			
//			break;
//		case 2:
//			System.out.println("Select a scan type.\n\tc - performs a complete three-way handshake\n\ts - sends SYN packet only\n\tf - sends FIN packet only\n\tu - UDP scan");
//			break;
//		}
//	}
//	
//	String getTarget() {
//		return target;
//	}
//	
//	char getScanType() {
//		return scanType;
//	}
}
