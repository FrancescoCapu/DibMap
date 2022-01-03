package dibmap;

public class Result {

	int port;
	private String scanType;
	private String protocol = "To be implemented";
	String status;
	
	public Result(int port, String scanType, String status) {
		this.port = port;
		this.scanType = scanType;
		this.status = status;
	}
	
	@Override
	public String toString(){
		return port + "/" + scanType + "\t" + status + "\t" + protocol;
	}
}