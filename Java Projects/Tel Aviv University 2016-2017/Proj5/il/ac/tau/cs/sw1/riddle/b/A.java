package il.ac.tau.cs.sw1.riddle.b;

/**
 * Complete the code of A's methods without changing B and C.
 */
public class A {

	private B b;

	public A(B b) {
		this.b = b;
	}

	public static void printA(B b) {
		B b2 = new B(b,"kaki");
		System.out.println();
	}

	public void printA2() {
		System.out.println();
		B.foo(this.b);
		System.out.println();
	}

	public static void printA3(A a) {
		System.out.println();
		a.b.methodB(null);
	}
	
}
