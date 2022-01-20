package dibmap;

import java.io.IOException;

// Net libraries
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
// TCP sockets
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.DatagramPacket;
// UDP sockets
import java.net.DatagramSocket;

public class ScanExecutor implements Runnable {

	private InetAddress target;
//	private InetSocketAddress addr;
	private String[] scanType;
	private int port = 0;
	private ScanCommander commander;
	private int timeout = 5000;
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
//		for (int i = 0; i < scanType.length; i++) {
//			Result r;
//			switch (scanType[i]) {
//			case "c":
//				Socket s;
//				while (true) {
//					port = commander.getPort();
//					if (port != -1) {
//						try {
//							//						System.out.println("Scanning port " + port);
//							InetSocketAddress addr = new InetSocketAddress(target, port);
//							//						s = new Socket(target, port);
//							s = new Socket();
//							//						s.setSoTimeout(2000);
//							s.connect(addr, timeout);
//							s.close();
//							r = new Result(port, "tcp", "open");
//							commander.recordResult(r);
//						} catch (UnknownHostException e) {
//							e.printStackTrace();
//							break;
//						} catch (SocketTimeoutException e) {
//							//						e.printStackTrace();
//							System.out.println("Connection on port " + port + " timed out.");
//							r = new Result(port, "tcp", "? - timed out");
//						} catch (IOException e) {
//							// e.printStackTrace();
//							r = new Result(port, "tcp", "closed");
//							commander.recordResult(r);
//						} catch (SecurityException e) {
//							// e.printStackTrace();
//							r = new Result(port, "tcp", "cannot determine");
//							commander.recordResult(r);
//						} catch (IllegalArgumentException e) {
//							e.printStackTrace();
//							break;
//						} catch (NullPointerException e) {
//							e.printStackTrace();
//							break;
//						}
//					}
//					else
//						break;
//				}
//				break;
//			case "s":
//				break;
//			case "f":
//				break;
//			case "u":
//				DatagramSocket d;
//				while (true) {
//					port = commander.getPort();
//					if (port != -1) {
//						try {
//							d = new DatagramSocket();
//							byte[] buffer = new byte[256];
//							DatagramPacket packet = new DatagramPacket(buffer, buffer.length, target, port);
//							System.out.println("Sending packet to port " + port);
//							d.send(packet);
//							packet = new DatagramPacket(buffer, buffer.length, target, port);
//							System.out.println("Waiting for an answer from " + target + ":" + port);
//							d.setSoTimeout(timeout);
//							d.receive(packet);
//							String received = new String(packet.getData(), 0, packet.getLength());
//							System.out.println(received);
//							r = new Result(port, "udp", "open");
//						} catch (PortUnreachableException e) {
//							System.out.println("Port " + port + " unreachable!");
//							r = new Result(port, "udp", "closed");
//							e.printStackTrace();
//						} /*catch (SocketException e) {
//							// TODO Auto-generated catch block
//							System.out.println("Socket timed out.");
//							r = new Result(port, "udp", "maybe open");
//							e.printStackTrace();
//						}*/ catch (SecurityException e) {
//							r = new Result(port, "udp", "filtered");
//							e.printStackTrace();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							r = new Result(port, "udp", "open|filtered");
//							e.printStackTrace();
//						}
//						commander.recordResult(r);
//					}
//					else
//						break;
//				}
//				break;
//			}
//		}
		while (port != -1) {
			port = commander.getPort();
			for (int i = 0; i < scanType.length; i++) {
				switch (scanType[i]) {
				case "c":
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
					else
						break;
					break;
				case "s":
					break;
				case "f":
					break;
				case "u":
					// datagramchannel
					// Port unreachable with datagram channel
					if (port != -1) {
						try {
							d = new DatagramSocket();
							String msg = "\n";
//							byte[] buffer = new byte[0];
							byte[] buffer = new byte[msg.length()];
							buffer = msg.getBytes();

//							d.connect(target, port);
							packet = new DatagramPacket(buffer, buffer.length, target, port);

							System.out.println("Sending packet to port " + port);
							d.send(packet);
							
//							packet = new DatagramPacket(buffer, buffer.length, target, port);
							System.out.println("Waiting for an answer from " + target + ":" + port);
							d.setSoTimeout(timeout);
							d.receive(packet);
							
							String received = new String(packet.getData(), 0, packet.getLength());
							System.out.println("Answer: " + received);
							r = new Result(port, "udp", "open");
						} catch (PortUnreachableException e) {
							System.out.println("Port " + port + " unreachable!");
							r = new Result(port, "udp", "closed");
							e.printStackTrace();
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
							e.printStackTrace();
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
					else
						break;
					break;
				}
			}
		}
	}
}
