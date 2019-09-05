package il.ac.tau.cs.software1.ip;

import java.nio.ByteBuffer;

public class IPAddressInt implements IPAddress {

	private ByteBuffer batim;
	
	IPAddressInt(int address) {
		this.batim = ByteBuffer.allocate(4);
		String s = Integer.toBinaryString(address);
		int dif = 32 - s.length();
		for(int i = 0; i < dif; i++)
			s = "0" + s;
		byte b1 = (byte)Integer.parseInt(s.substring(0,8),2);
		byte b2 = (byte)Integer.parseInt(s.substring(8,16),2);
		byte b3 = (byte)Integer.parseInt(s.substring(16,24),2);
		byte b4 = (byte)Integer.parseInt(s.substring(24,32),2);
		this.batim.put(0,b1);
		this.batim.put(1,b2);
		this.batim.put(2,b3);
		this.batim.put(3,b4);
	}

	@Override
	public String toString() {
		if(this == null)
			return null;
		return (Integer.toString((int)(this.batim.get(0) & 0xFF)) + "." + Integer.toString((int)(this.batim.get(1) & 0xFF)) + "." + Integer.toString((int)(this.batim.get(2) & 0xFF)) + "." + Integer.toString((int)(this.batim.get(3) & 0xFF)));
	}

	@Override
	public boolean equals(IPAddress other) {
		if(this == null || other == null){
			if(this == null && other == null)
				return true;
			else
				return false;
		}
		return this.toString().equals(other.toString());
	}

	@Override
	public int getOctet(int index) {
		if(this == null)
			return -1;
		return (int)(this.batim.get(index) & 0xFF);
	}

	@Override
	public boolean isPrivateNetwork(){
		return (private1() || private2() || private3() || private4());
	}
	private boolean private1(){
		int a = this.getOctet(0);
		int b = this.getOctet(1);
		int c = this.getOctet(2);
		int d = this.getOctet(3);
		return (a == 10 && b >= 0 && b <= 255 &&  c >= 0 && c <= 255 && d >= 0 && d <= 255);
	}
	private boolean private2(){
		int a = this.getOctet(0);
		int b = this.getOctet(1);
		int c = this.getOctet(2);
		int d = this.getOctet(3);
		return (a == 172 && b >= 16 && b <= 31 &&  c >= 0 && c <= 255 && d >= 0 && d <= 255);

	}
	private boolean private3(){
		int a = this.getOctet(0);
		int b = this.getOctet(1);
		int c = this.getOctet(2);
		int d = this.getOctet(3);
		return (a == 192 && b == 168 &&  c >= 0 && c <= 255 && d >= 0 && d <= 255);
	}
	private boolean private4(){
		int a = this.getOctet(0);
		int b = this.getOctet(1);
		int c = this.getOctet(2);
		int d = this.getOctet(3);
		return (a == 169 && b == 254 &&  c >= 0 && c <= 255 && d >= 0 && d <= 255);
	}
	
}
