package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StealthCruiser extends Fighter {
	
	private static int num = 0;
	
	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
		num++;
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		this(name,commissionYear,maximalSpeed,crewMembers,Arrays.asList(new Weapon[]{new Weapon("Laser Cannons",10,100)}));
	}
	@Override
	public int getAnnualMaintenanceCost(){
		return super.getAnnualMaintenanceCost() + num*50;
	}
	public String toString(){
		return "StealthCruiser" + super.toString2();
	}
}
