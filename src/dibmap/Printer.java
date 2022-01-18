package dibmap;

public final class Printer {
	
	synchronized static void print(String toBePrinted) {
		System.out.println(toBePrinted);
	}
}
