package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;

public abstract class AbsSpaceship implements Spaceship {
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private final int baseFirePower = 10;
	private Set<? extends CrewMember> crewMembers;
	public AbsSpaceship(String name,int commissionYear,float maximalSpeed,Set<? extends CrewMember> crewMembers){
		this.name = name;
		this.commissionYear = commissionYear;
		this.maximalSpeed = maximalSpeed;
		this.crewMembers = crewMembers;
	}
	public String getName(){
		return name;
	}
	public int getCommissionYear(){
		return commissionYear; 
	}
	public float getMaximalSpeed(){
		return maximalSpeed;
	}
	public int getFirePower(){
		return baseFirePower;
	}
	public Set<? extends CrewMember> getCrewMembers(){
		return crewMembers;
	}
	public abstract int getAnnualMaintenanceCost();
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbsSpaceship other = (AbsSpaceship) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public String toString(){
		return "\n\tName=" + getName() + "\n\tCommissionYear=" + getCommissionYear() + "\n\tMaximalSpeed=" + getMaximalSpeed() + "\n\tFirePower=" + getFirePower() + "\n\tCrewMembers=" + getCrewMembers().size() + "\n\tAnnualMaintenanceCost=" + getAnnualMaintenanceCost();
	}
}
