package il.ac.tau.cs.sw1.ex9.starfleet;

public class Officer extends CrewWoman {
	
	private OfficerRank rank;
	public Officer(String name, int age, int yearsInService, OfficerRank rank) {
		super(name,age,yearsInService);
		this.rank = rank;
	}
	public OfficerRank getRank(){
		return rank;
	}
	public String toString(){
		return "Officer of type " + super.toString() + "\n\tRank=" + getRank();
	}
	
}
