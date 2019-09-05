package il.ac.tau.cs.sw1.ex9.starfleet;

public class CrewWoman extends AbsCrewMember {
	
	
	public CrewWoman(String name, int age, int yearsInService){
		super(name,age,yearsInService);
	}
	public String toString(){
		return "CrewWoman" + super.toString();
	}
}

