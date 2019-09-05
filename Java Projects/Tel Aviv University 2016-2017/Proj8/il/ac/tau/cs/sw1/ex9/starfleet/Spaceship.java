package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;

public interface Spaceship {
    String getName();//public by default
	int getCommissionYear();
	float getMaximalSpeed();
	int getFirePower();
	Set<? extends CrewMember> getCrewMembers();
	int getAnnualMaintenanceCost();
}
