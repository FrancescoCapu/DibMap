package dibmap;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ScanExecutor implements Runnable {

	//	private String target;
	private InetAddress target;
	private InetSocketAddress addr;
	private String[] scanType;
	private int port;
	private ScanCommander commander;
	private int timeout = 2000;

	public ScanExecutor(InetAddress target, String[] scanType, ScanCommander commander) {
		this.target = target;
		this.scanType = scanType;
		this.commander = commander;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < scanType.length; i++) {
			switch (scanType[i]) {
			case "c":
				Socket s;
				Result r;
				while (true) {
					port = commander.getPort();
					if (port != -1) {
						try {
							//						System.out.println("Scanning port " + port);
							addr = new InetSocketAddress(target, port);
							//						s = new Socket(target, port);
							s = new Socket();
							//						s.setSoTimeout(2000);
							s.connect(addr, timeout);
							s.close();
							r = new Result(port, "tcp", "open");
							commander.recordResult(r);
						} catch (UnknownHostException e) {
							e.printStackTrace();
							break;
						} catch (SocketTimeoutException e) {
							//						e.printStackTrace();
							System.out.println("Connection on port " + port + " timed out.");
							r = new Result(port, "tcp", "? - timed out");
						} catch (IOException e) {
							// e.printStackTrace();
							r = new Result(port, "tcp", "closed");
							commander.recordResult(r);
						} catch (SecurityException e) {
							// e.printStackTrace();
							r = new Result(port, "tcp", "cannot determine");
							commander.recordResult(r);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
							break;
						} catch (NullPointerException e) {
							e.printStackTrace();
							break;
						}
					}
					else
						break;
				}
				break;
			case "s":
				break;
			case "f":
				break;
			case "u":
				break;
			}
		}
	}
}
