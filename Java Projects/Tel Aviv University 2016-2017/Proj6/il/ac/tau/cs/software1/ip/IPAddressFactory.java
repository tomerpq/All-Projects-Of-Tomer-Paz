package il.ac.tau.cs.software1.ip;

public class IPAddressFactory {
	
	public static IPAddress createAddress(String ip) {
		IPAddress ip1 = new IPAddressString(ip);
		return ip1;
	} 
	
	public static IPAddress createAddress(short[] ip) {
		IPAddress ip2 = new IPAddressShort(ip);
		return ip2;
	} 
	
	public static IPAddress createAddress(int ip) {
		IPAddress ip3 = new IPAddressInt(ip);
		return ip3;
	}
}