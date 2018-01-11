package comswpu.DubboMonitor.traceClass;

public class Trace {
	public String traceId;
	public String appId;
	public String span;
	public int remainder;
	public boolean RPCStatus;

	public Trace(String traceId,String span, int remainder) {
		this.traceId = traceId;
		this.span = span;
		this.remainder = remainder;
		RPCStatus = false;
	}

	public Trace(String traceId) {
		this.traceId = traceId;
		this.span = "1";
		this.remainder = 0;
		RPCStatus = false;
	}

}
