
public class E {
	private int i1;
	private int i2;
	private String s;
	public E(int i1,int i2){
		this.i1 = i1;
		this.i2 = i2;
	}
	public E(int i1,String s){
		this.i1 = i1;
		this.s = s;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i1;
		result = prime * result + i2;
		result = prime * result + ((s == null) ? 0 : s.hashCode());
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
		E other = (E) obj;
		if (i1 != other.i1)
			return false;
		if (i2 != other.i2)
			return false;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}
}
