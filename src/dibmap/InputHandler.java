package dibmap;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class InputHandler {
	
	private InetAddress target = null;
	private ArrayList<String> parameters = new ArrayList<String>();
	private String[] validParametersArray = {"c", "s", "f", "u", "p"};
	private ArrayList<String> validParameters = new ArrayList<String>();
	private int startingPort = 1;
	private int endPort = 1023;
	
	public InputHandler() {
		validParameters.addAll(Arrays.asList(validParametersArray));
//		Debug for loop
//		
//		for (int i = 0; i < validParameters.size(); i++)
//			System.out.println(validParameters.get(i));
	}
	
	boolean validateTarget(String target) {
		try {
			this.target = InetAddress.getByName(target);
//			System.out.println(this.target);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
//			System.out.println("Wrong IP.");
			e.printStackTrace();
		} catch (SecurityException e) {
//			System.out.println("Security issue.");
			e.printStackTrace();
		}
//		System.out.println("Target validated.");
		return true;
	}
	
	InetAddress getTargetIP() {
		return target;
	}
	
	boolean validateParameters(String[] args) {
		
		/*
		 * To be implemented: check for duplcated parameters
		 */
		
		String[] temporaryParameters = new String[args.length - 1];
		for (int i = 0; i < temporaryParameters.length; i++)
			temporaryParameters[i] = args[i + 1];
		
		for (int i = 0; i < temporaryParameters.length; i++) {
			if (validParameters.contains(temporaryParameters[i])) {
//				System.out.println(temporaryParameters[i] + " is a valid character");
				if (temporaryParameters[i].equals("p")) {
					try {
//						if (temporaryParameters[i+1].contains("\\0{2,}")) {
//							System.out.println("Too many ports");
//							return false;
//						}
//						else if (temporaryParameters[i+1].contains("\\-{1}")) {
						if (temporaryParameters[i+1].contains("-")) {
							String[] ports = temporaryParameters[i+1].split("-");
							if (ports.length == 2) {
								startingPort = Integer.parseInt(ports[0]);
								endPort = Integer.parseInt(ports[1]);
								i += 2;
							}
							else {
								System.out.println("Too many ports");
								return false;
							}
						}
						else {
							startingPort = Integer.parseInt(temporaryParameters[i+1]);
							endPort = startingPort;
							i += 1;
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
						return false;
					} catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
						return false;
					}
				}
				else
					parameters.add(temporaryParameters[i]);
			}
			else
				System.out.println(temporaryParameters[i] + " not yet implemented or not supported.");
		}
		return true;
	}
	
	String[] getParameters() {
		String[] param;
		if (parameters.size() != 0) {
			param = new String[parameters.size()];
			for (int i = 0; i < parameters.size(); i++)
				param[i] = parameters.get(i);
		}
		else {
			param = new String[1];
			param[0] = "c";
		}
		return param;
	}

	int getStartingPort() {
		return startingPort;
	}
	
	int getEndPort() {
		return endPort;
	}
}
