package il.ac.tau.cs.software1.predicate;

public class Even implements Predictor {

	@Override
	/*
	 * (non-Javadoc)
	 * Return true iff (Person)o.age %2 == 0
	 */
	public boolean test(Object o) {
		Person p = (Person)o;
		if(p == null)
			return false;
		if(p.getAge() % 2 == 0)
			return true;
		return false;
	}

}
