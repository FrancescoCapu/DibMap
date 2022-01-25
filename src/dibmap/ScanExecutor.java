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
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

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
		// TODO Auto-generated method stub
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
					break;
				// UDP scan
				case "u":
					// datagramchannel
					// Port unreachable with datagram channel
					if (port != -1) {
						// for loop used to achieve a more precise result - ICMP type 3 reply may not always be received
						for (int j = 0; j < 4; j++) {
							try {
								d = new DatagramSocket();
								String msg = "\n";
								//							byte[] buffer = new byte[0];
								byte[] buffer = new byte[msg.length()];
								buffer = msg.getBytes();


								d.connect(target, port);
								packet = new DatagramPacket(buffer, buffer.length, target, port);

								//							System.out.println("Sending packet to port " + port);
								d.send(packet);
								//							d.send(null);
								//							packet = new DatagramPacket(buffer, buffer.length, target, port);
								//							System.out.println("Waiting for an answer from " + target + ":" + port);
								d.setSoTimeout(timeout);
								d.receive(packet);

								String received = new String(packet.getData(), 0, packet.getLength());
								System.out.println("Answer: " + received);
								r = new Result(port, "udp", "open");
								break;
							} catch (PortUnreachableException e) {
								System.out.println("Port " + port + " unreachable!");
								r = new Result(port, "udp", "closed");
								break;
								//							e.printStackTrace();
							} /*catch (SocketException e) {
							// TODO Auto-generated catch block
							System.out.println("Socket timed out.");
							r = new Result(port, "udp", "maybe open");
							e.printStackTrace();
						}*/ catch (SecurityException e) {
							r = new Result(port, "udp", "filtered");
							e.printStackTrace();
							} catch (IOException e) {
							// TODO Auto-generated catch block
								r = new Result(port, "udp", "open|filtered");
							//							e.printStackTrace();
							}
						}
						commander.recordResult(r);

						//						DatagramChannel channel;
						//						try {
						//							channel = DatagramChannel.open();
						////							channel.configureBlocking(false);
						//							channel.socket().bind(null);
						//							channel.connect(new InetSocketAddress(target, port));
						//							ByteBuffer bytes = ByteBuffer.allocate(10);
						//							channel.send(bytes, new InetSocketAddress(target, port));
						//							r = new Result(port, "udp", "open");
						//						} catch (PortUnreachableException e) {
						//							r = new Result(port, "udp", "closed");
						//							e.printStackTrace();
						//						} catch (IOException e) {
						//							// TODO Auto-generated catch block
						//							r = new Result(port, "udp", "open|filtered");
						//							e.printStackTrace();
						//						}
						//						commander.recordResult(r);

					}
					break;
				}
			}
		}
	}
}
