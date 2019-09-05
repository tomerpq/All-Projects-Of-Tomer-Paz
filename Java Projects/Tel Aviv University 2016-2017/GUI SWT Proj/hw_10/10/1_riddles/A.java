
public class A {
	private int i1;
	private int i2;
	public A(int i1,int i2){
		this.i1 = i1;
		this.i2 = i2;
	}
	public A(int i1,String s){
		this.i1 = i1;
		this.i2 = Integer.parseInt(s);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i1;
		result = prime * result + i2;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		A other = (A) obj;
		if (i1 != other.i1)
			return false;
		if (i2 != other.i2)
			return false;
		return true;
	}
}
