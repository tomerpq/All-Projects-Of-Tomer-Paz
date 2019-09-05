package il.ac.tau.cs.software1.predicate;

public class Big implements Predictor {

	private int value;
	
	public Big(int value)
	{
		this.value = value;
	}
	@Override
	/**
	 * Return true iff (Person)o.age > value
	 */
	public boolean test(Object o) {
		Person p = (Person)o;
		if(p == null)
			return false;
		if(p.getAge() > this.value)
			return true;
		return false;
	}

}
