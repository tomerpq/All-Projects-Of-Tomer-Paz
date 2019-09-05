package il.ac.tau.cs.sw1.ex9.riddles.second;

public class B2 extends A2 {
	private boolean b;
	public B2(){}
	
	public B2(boolean b){
		this.b = b;
	}
	public A2 getA(boolean b){
		return new B2(b);
	}
	public String foo(String s){
		if(b)
			return s.toUpperCase();
		else
			return s.toLowerCase();
	}
}