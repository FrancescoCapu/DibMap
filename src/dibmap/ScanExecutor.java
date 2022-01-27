package dibmap;

import java.io.IOException;

// Net libraries
import java.net.InetAddress;
import java.net.UnknownHostException;
// TCP sockets
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.DatagramPacket;
// UDP sockets
import java.net.DatagramSocket;

public class ScanExecutor implements Runnable {
	private InetAddress target;
	private String[] scanType;
	private int port = 0;
	private ScanCommander commander;
	private int timeout = 3000;
	private Socket s;
	private Result r;
	private DatagramSocket d;
	private DatagramPacket packet;

	public ScanExecutor(InetAddress target, String[] scanType, ScanCommander commander) {
		this.target = target;
		this.scanType = scanType;
		this.commander = commander;
	}

	@Override
	public void run() {
		while (port != -1) {
			port = commander.getPort();
			int scanTypeLength = scanType.length;
			for (int i = 0; i < scanTypeLength; i++) {
				if (port == -1) {
					Main.executorShutdown();
					break;
				}
				switch (scanType[i]) {
				// TCP full 3-way handshake scan
				case "t":
					if (port != -1) {
						try {
							InetSocketAddress addr = new InetSocketAddress(target, port);
							s = new Socket();
							s.connect(addr, timeout);
							s.close();
							r = new Result(port, "tcp", "open");
							commander.recordResult(r);
						} catch (UnknownHostException e) {
							e.printStackTrace();
							break;
						} catch (SocketTimeoutException e) {
							r = new Result(port, "tcp", "filtered - timed out");
						} catch (IOException e) {
							r = new Result(port, "tcp", "closed");
							commander.recordResult(r);
						} catch (SecurityException e) {
							r = new Result(port, "tcp", "filtered");
							commander.recordResult(r);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
							break;
						} catch (NullPointerException e) {
							e.printStackTrace();
							break;
						}
					}
					break;
					// UDP scan
				case "u":
					if (port != -1) {
						// for loop used to achieve a more precise result - ICMP type 3 reply may not always be received
						for (int j = 0; j < 4; j++) {
							try {
								d = new DatagramSocket();
								String msg = "\n";
								byte[] buffer = new byte[msg.length()];
								buffer = msg.getBytes();


								d.connect(target, port);
								packet = new DatagramPacket(buffer, buffer.length, target, port);

								d.send(packet);
								d.setSoTimeout(timeout);
								d.receive(packet);

								r = new Result(port, "udp", "open");
								break;
							} catch (PortUnreachableException e) {
								r = new Result(port, "udp", "closed");
								break;
							} 
							catch (SecurityException e) {
								r = new Result(port, "udp", "filtered");
								break;
							} catch (IOException e) {
								r = new Result(port, "udp", "open|filtered");
							}
						}
						commander.recordResult(r);						
					}
					break;
				}
			}
		}
	}
}
