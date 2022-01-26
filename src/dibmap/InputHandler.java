package dibmap;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class InputHandler {
	private InetAddress target = null;
	private ArrayList<String> parameters = new ArrayList<String>();
	private String[] validParametersArray = {"t", "u", "p"};
	private ArrayList<String> validParameters = new ArrayList<String>();
	
	// Default port range: 1-1023 (well-known ports)
	private int startingPort = 1;
	private int endPort = 1023;
	private final int MIN_PORT = 1;
	private final int MAX_PORT = 65535;
	
	public InputHandler() {
		validParameters.addAll(Arrays.asList(validParametersArray));
	}
	
	boolean validateTarget(String target) {
		try {
			this.target = InetAddress.getByName(target);
		} catch (UnknownHostException e) {
			System.out.println("Wrong IP.");
			return false;
		} catch (SecurityException e) {
			System.out.println("Security issue. Cannot validate target.");
			return false;
		}
		return true;
	}
	
	InetAddress getTargetIP() {
		return target;
	}
	
	boolean validateParameters(String[] args) {
		int len = args.length;
		for (int i = 1; i < len; i++) {
			args[i] = args[i].replace("-", "");
			if (args[i].equals("p"))
				++i;
		}
		
		String[] temporaryParameters = new String[args.length - 1];
		int tmeporaryParametersLength = temporaryParameters.length;
		for (int i = 0; i < tmeporaryParametersLength; i++)
			temporaryParameters[i] = args[i + 1];
		
		for (int i = 0; i < tmeporaryParametersLength; i++) {
			if (validParameters.contains(temporaryParameters[i])) {
				if (temporaryParameters[i].equals("p")) {
					try {
						if (temporaryParameters[i+1].contains("-")) {
							String[] ports = temporaryParameters[i+1].split("-");
							if (ports.length == 2) {
								try {
									startingPort = Integer.parseInt(ports[0]);
									endPort = Integer.parseInt(ports[1]);
									i += 2;
									if (startingPort < MIN_PORT || startingPort > MAX_PORT || endPort < MIN_PORT || endPort > MAX_PORT) {
										System.out.println("Invalid port number");
										return false;
									}
									if (endPort < startingPort) {
										System.out.println("Invalid port range - ascending order required.");
										return false;
									}
								} catch (NumberFormatException e) {
									System.out.println("Invalid port number.");
									return false;
								}
							}
							else {
								System.out.println("Too many ports.");
								return false;
							}
						}
						else {
							startingPort = Integer.parseInt(temporaryParameters[i+1]);
							endPort = startingPort;
							if (startingPort < MIN_PORT || startingPort > MAX_PORT) {
								System.out.println("Invalid port number.");
								return false;
							}
							i += 1;
						}
					} catch (NumberFormatException e) {
						System.out.println("Invalid port number.");
						return false;
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Too few arguments - port number(s) missing.");
						return false;
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Too few arguments - port number(s) missing.");
						return false;
					}
				}
				else
					parameters.add(temporaryParameters[i]);
			}
			else {
				System.out.println("unknown \"-" + temporaryParameters[i] + "\" option.");
				return false;
			}
		}
		for (int i = 0; i < tmeporaryParametersLength; i++ ) {
			for (int j = i + 1; j < tmeporaryParametersLength; j++)
				if (temporaryParameters[i].equals(temporaryParameters[j])) {
					System.out.println("Duplicated parameters");
					return false;
				}
		}
		return true;
	}
	
	String[] getParameters() {
		String[] param;
		if (parameters.size() != 0) {
			param = new String[parameters.size()];
			int parametersSize = parameters.size();
			for (int i = 0; i < parametersSize; i++)
				param[i] = parameters.get(i);
		}
		else {
			param = new String[1];
			param[0] = "t";
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
