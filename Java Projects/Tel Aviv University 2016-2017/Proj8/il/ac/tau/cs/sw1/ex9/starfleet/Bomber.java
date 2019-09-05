package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Bomber extends AbsSpaceship{
	
	private int numberOfTechnicians;
	private List<Weapon> weapons;
	private int weaponsFP = 0;
	private int weaponsMAINTENANCE = 0;

	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians){
		super(name,commissionYear,maximalSpeed,crewMembers);
		this.weapons = weapons;
		FpAndMaintenance();
		this.numberOfTechnicians = numberOfTechnicians;
	}
	public List<Weapon> getWeapon(){
		return weapons;
	}
	private void FpAndMaintenance(){
		for(Iterator<Weapon> it = weapons.iterator(); it.hasNext();){
			Weapon wep = it.next();
			if(wep != null){
				weaponsFP += wep.getFirePower();
				weaponsMAINTENANCE += wep.getAnnualMaintenanceCost();
			}
		}
	}
	@Override
	public int getFirePower(){
		return super.getFirePower() + weaponsFP; 
	}
	@Override
	public int getAnnualMaintenanceCost(){
		int x = weaponsMAINTENANCE;
		int price = (int)((x * (100-10*numberOfTechnicians))/100);
		return 5000 + price;
	}
	public int getNumberOfTechnicians(){
		return numberOfTechnicians;
	}
	public String toString(){
		return "Bomber" + super.toString() + "\n\tWeaponArray=" + getWeapon() + "\n\tNumberOfTechnicians=" + getNumberOfTechnicians();
	}

}
