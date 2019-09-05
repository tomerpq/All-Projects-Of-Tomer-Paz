
public class D implements Comparable<D>{
	private int i1;
	private int i2;
	public D(int i1,int i2){
		this.i1 = i1;
		this.i2 = i2;
	}
	public D(int i1,String s){
		this.i1 = i1;
		this.i2 = Integer.parseInt(s);
	}
	@Override
	public int compareTo(D d){
		return 0;
	}
}
