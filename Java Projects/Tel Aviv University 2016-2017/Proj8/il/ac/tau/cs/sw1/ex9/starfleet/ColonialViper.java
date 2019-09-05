package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class ColonialViper extends Fighter{
	
	private int numOfWoman;
	
	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers,List<Weapon> weapons) {
		super(commissionYear,name,maximalSpeed,crewMembers,weapons);
		this.numOfWoman = crewMembers.size();
	}
	@Override
	public int getAnnualMaintenanceCost(){
		return 4000 + getWeaponsMAINTENANCE() + 500*numOfWoman + (int)(500*getMaximalSpeed());
	}
	public String toString(){
		return "ColonialViper" + super.toString2();
	}
}
