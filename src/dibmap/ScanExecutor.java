package dibmap;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ScanExecutor implements Runnable {

	private String target;
	private char scanType;
	private int port;
	private ScanCommander commander;
	
	public ScanExecutor(String target, char scanType, ScanCommander commander) {
		this.target = target;
		this.scanType = scanType;
		this.commander = commander;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		switch (scanType) {
		case 'c':
			Socket s;
			Result r;
			while (true) {
				port = commander.getPort();
				if (port != -1) {
					try {
						s = new Socket(target, port);
						r = new Result(port, "tcp", "open");
						commander.recordResult(r);
					} catch (UnknownHostException e) {
						e.printStackTrace();
						break;
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
					}
				}
				else
					break;
			}
			break;
		case 's':
			break;
		case 'f':
			break;
		case 'u':
			break;
		}
	}
}
