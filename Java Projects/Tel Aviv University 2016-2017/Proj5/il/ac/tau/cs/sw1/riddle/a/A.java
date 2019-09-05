package il.ac.tau.cs.sw1.riddle.a;

public class A {
	
	private B b;
	
	public A(B b) {
		this.b = b;
	}

	public void printA() {
		System.out.println();
		System.out.println("A1" + "\n");
		System.out.println(this.b.getI() + "\n");
		System.out.println("A2");
	}
}
