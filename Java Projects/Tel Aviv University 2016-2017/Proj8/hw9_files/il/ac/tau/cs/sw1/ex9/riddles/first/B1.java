package il.ac.tau.cs.sw1.ex9.riddles.first;

public class B1 extends A1 {
	protected boolean foo(){
		C1 c = new C1();
		if(c.foo())
			return false;
		else
			return true;
	}
}
