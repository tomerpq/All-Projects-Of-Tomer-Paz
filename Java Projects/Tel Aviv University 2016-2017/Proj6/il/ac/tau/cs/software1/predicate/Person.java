package il.ac.tau.cs.software1.predicate;

public class Person {
	
	private int age;
	private String name;
	
	public Person(int a, String n)
	{
		age = a;
		name = n;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return "(" + name + "," + age + ")";
	}
	
	

}
