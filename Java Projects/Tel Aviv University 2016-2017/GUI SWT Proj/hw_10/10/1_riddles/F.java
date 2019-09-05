
public class F implements Comparable<F>{
	private int i1;
	private int i2;
	private String s;
	public F(int i1,int i2){
		this.i1 = i1;
		this.i2 = i2;
	}
	public F(int i1,String s){
		this.i1 = i1;
		this.s = s;
	}
	public int compareTo(F f){
		return -1;
	}
}
