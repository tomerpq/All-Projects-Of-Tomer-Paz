package il.ac.tau.cs.software1.ip;

public class IPAddressShort implements IPAddress {
	private short[] address;
	
	IPAddressShort(short[] address) {
		this.address = address;
	}

	@Override
	public String toString() {
		if(this == null)
			return null;
		return(Short.toString(this.address[0]) + "." + Short.toString(this.address[1]) + "." + Short.toString(this.address[2]) + "." + Short.toString(this.address[3]));
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
		return (int)this.address[index];
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
