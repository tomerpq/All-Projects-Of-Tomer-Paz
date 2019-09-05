package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;

public class CompShip implements Comparator<Spaceship>{
	public int compare(Spaceship s1, Spaceship s2){
		int i1 = Integer.compare(s1.getFirePower(),s2.getFirePower());
		if(i1 != 0)
			return -i1;
		int  i2 = Integer.compare(s1.getCommissionYear(),s2.getCommissionYear());
		if(i2 != 0)
			return -i2;
		return s1.getName().compareTo(s2.getName());
	}
}
