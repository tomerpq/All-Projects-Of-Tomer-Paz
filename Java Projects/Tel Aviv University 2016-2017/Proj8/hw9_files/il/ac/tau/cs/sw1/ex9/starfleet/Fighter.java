package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Fighter extends AbsSpaceship {
	
	private List<Weapon> weapons;
	private int weaponsFP = 0;
	private int weaponsMAINTENANCE = 0;
	
	public Fighter(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons){
		super(name,commissionYear,maximalSpeed,crewMembers);
		this.weapons = weapons;
		FpAndMaintenance();
	}
	public Fighter( int commissionYear,String name, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons){
		super(name,commissionYear,maximalSpeed,crewMembers);
		this.weapons = weapons;
		FpAndMaintenance();
	}
	public int getWeaponsMAINTENANCE(){
		return weaponsMAINTENANCE;
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
		return 2500 + weaponsMAINTENANCE + (int)(1000*getMaximalSpeed());
	}
	public String toString(){
		return "Fighter" + super.toString() + "\n\tWeaponArray=" + getWeapon();
	}
	public String toString2(){
		return super.toString() + "\n\tWeaponArray=" + getWeapon();
	}
}
