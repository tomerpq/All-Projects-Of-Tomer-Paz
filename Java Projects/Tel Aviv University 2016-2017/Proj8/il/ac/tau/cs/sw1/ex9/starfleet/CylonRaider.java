package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class CylonRaider extends Fighter {

	private int numOfCylon;
	
	public CylonRaider(String name, int commissionYear, float maximalSpeed, Set<Cylon> crewMembers,	List<Weapon> weapons) {
		super(commissionYear,name,maximalSpeed,crewMembers,weapons);
		this.numOfCylon = crewMembers.size();
	}

	@Override
	public int getAnnualMaintenanceCost(){
		return 3500 + getWeaponsMAINTENANCE() + 500*numOfCylon + (int)(1200*getMaximalSpeed());
	}
	public String toString(){
		return "CylonRaider" + super.toString2();
	}
}
