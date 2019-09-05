public class C implements Comparable<C>{
	private int i1;
	private int i2;
	public C(int i1,int i2){
		this.i1 = i1;
		this.i2 = i2;
	}
	public C(int i1,String s){
		this.i1 = i1;
		this.i2 = Integer.parseInt(s);
	}
	public int geti1(){
		return i1;
	}
	public int geti2(){
		return i2;
	}
	public int compareTo(C c){
		return Integer.compare(this.geti2(),c.geti2());
	}
}
