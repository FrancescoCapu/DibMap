package dibmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Result {

	int port;
	private String scanType;
	private String protocol;
	String status;
	
	public Result(int port, String scanType, String status) {
		this.port = port;
		this.scanType = scanType;
		this.status = status;
		protocol = getProtocol();
	}
	
	@Override
	public String toString(){
		String toBeReturned = port + "/" + scanType + "\t";
		
		// /tcp or /udp are 4 characters long, + max 3 for port numbers under 1000
		if (port < 1000)
			toBeReturned += "\t";
		toBeReturned += status + "\t";
		if (status.length() < 8)
			toBeReturned += "\t";
		
		toBeReturned += protocol;
		return toBeReturned;
	}
	
	private String getProtocol() {
		String defaultReturn = "unknown";
		String ret = "";
		
		if (port >= 49152)
			return "ephemeral port";
		File file = new File("./../files/port_list.txt");
		try {
			Scanner scanner = new Scanner(file);
			int result;
			String[] values;
			scanner.nextLine();	// To scan header line
			do {
				values = scanner.nextLine().split("\\t+");
				
//				Debug
//				
//				for (int i = 0; i < values.length; i++)
//					System.out.println(values[i]);
				
				try {
					result = Integer.parseInt(values[0]);
				} catch (NumberFormatException e) {
					scanner.close();
					return defaultReturn;
				}
				
//				if ((scanType.equalsIgnoreCase("TCP") && !values[1].equalsIgnoreCase("TCP")) || (scanType.equalsIgnoreCase("UDP") && !values[2].equalsIgnoreCase("UDP"))) {
//					scanner.close();
//					return defaultReturn;
//				}
						
				if (result == port) {
					int valuesLength = values.length;
					for (int i = 1; i < valuesLength - 1; i++) {
						if (!values[i].equalsIgnoreCase("TCP") && !values[i].equalsIgnoreCase("UDP"))
							ret += values[i];;
					}
					scanner.close();
					return ret;
				}
			} while(port > result);
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defaultReturn;
	}
}