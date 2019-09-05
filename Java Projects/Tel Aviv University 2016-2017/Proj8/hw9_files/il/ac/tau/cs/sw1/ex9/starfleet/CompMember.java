package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;

public class CompMember implements Comparator<Officer> {
	public int compare(Officer o1,Officer o2){
		return -1 *(o1.getRank().compareTo(o2.getRank()));
	}
}
