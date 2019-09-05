package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;
import java.util.Map;

public class EntryComp implements Comparator<Map.Entry<OfficerRank,Integer>> {
	public int compare(Map.Entry<OfficerRank,Integer> entry1,Map.Entry<OfficerRank,Integer> entry2){
		int i1 = entry1.getValue(), i2 = entry2.getValue();
		if(i1 != i2)
			return -1 *(Integer.compare(i1,i2));
		else
			return -1 *(entry1.getKey().compareTo(entry2.getKey()));
	}
}
