package il.ac.tau.cs.software1.predicate;

public class StartWith implements Predictor {
	
	private char c;
	
	public StartWith(char c)
	{
		this.c = c;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * Return true iff (Person)O.name.charAt(0) == c
	 */
	public boolean test(Object o) {
		Person p = (Person)o;
		if((p.getName() != null) && (p.getName().length() > 0) && (p.getName().charAt(0) == this.c))
			return true;
		return false;
	}

}
