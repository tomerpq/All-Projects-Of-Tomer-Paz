package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in ascending order by commission year
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear (Collection<Spaceship> fleet) {
		List<Spaceship> fleet2 = new ArrayList<>();
		for(Iterator<Spaceship> it = fleet.iterator(); it.hasNext();){
			Spaceship s = it.next();
			fleet2.add(s);
		}
		List<String> list = new ArrayList<String>();
		Collections.sort(fleet2,new CompShip());
		for(Iterator<Spaceship> it = fleet2.iterator(); it.hasNext();){
			Spaceship s = it.next();
			list.add(s.toString());
		}
		return list;
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		Map<String,Integer> map = new HashMap<>();
		for(Iterator<Spaceship> it = fleet.iterator(); it.hasNext();){
			Spaceship s = it.next();
			String name = s.getClass().getSimpleName();
			if(map.containsKey(name))
				map.put(name,map.get(name)+1);
			else
				map.put(name,1);
		}
		return map;
	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (Collection<Spaceship> fleet) {
		int m = 0;
		for(Iterator<Spaceship> it = fleet.iterator(); it.hasNext();){
			Spaceship s = it.next();
			m += s.getAnnualMaintenanceCost();
		}
		return m;
	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> set = new HashSet<>();
		for(Iterator<Spaceship> it = fleet.iterator(); it.hasNext();){
			Spaceship s = it.next();
			if(s instanceof Fighter || s instanceof Bomber){
				if(s instanceof Fighter){
					Fighter f = (Fighter)s;
					for(Iterator<Weapon> it2 = f.getWeapon().iterator(); it2.hasNext();){
						Weapon wep = it2.next();
						set.add(wep.getName());
					}
				}
				else{
					Bomber f = (Bomber)s;
					for(Iterator<Weapon> it2 = f.getWeapon().iterator(); it2.hasNext();){
						Weapon wep = it2.next();
						set.add(wep.getName());
					}
				}
			}
		}
		return set;
	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		int m = 0;
		for(Iterator<Spaceship> it = fleet.iterator(); it.hasNext();){
			Spaceship s = it.next();
			m += s.getCrewMembers().size();
		}
		return m;
	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships. 
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		float sum = 0;
		float num = 0;
		int m = 0;
		for(Iterator<Spaceship> it = fleet.iterator(); it.hasNext();){
			Spaceship s = it.next();
			for(Iterator<? extends CrewMember> it2 = s.getCrewMembers().iterator(); it2.hasNext();){
				CrewMember c = it2.next();
				if(c instanceof Officer){
					sum += (float)c.getAge();
					num += 1.0;
				}
			}
		}
		return sum/num;
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {
		Map<Officer,Spaceship> map = new HashMap<>();
		for(Iterator<Spaceship> it = fleet.iterator(); it.hasNext();){
			Spaceship s = it.next();
			List<Officer> officers = new ArrayList<>();
			for(Iterator<? extends CrewMember> it2 = s.getCrewMembers().iterator(); it2.hasNext();){
				CrewMember c = it2.next();
				if(c instanceof Officer){
					Officer o = (Officer)c;
					officers.add(o);
				}
			}
			if(!officers.isEmpty()){
				Collections.sort(officers,new CompMember());
				map.put(officers.get(0),s);
			}
		}
		return map;
	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted descendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {
		Map<OfficerRank,Integer> map = new HashMap<>();
		for(Iterator<Spaceship> it = fleet.iterator(); it.hasNext();){
			Spaceship s = it.next();
			for(Iterator<? extends CrewMember> it2 = s.getCrewMembers().iterator(); it2.hasNext();){
				CrewMember c = it2.next();
				if(c instanceof Officer){
					Officer o = (Officer)c;
					OfficerRank rank = o.getRank();
					if(map.containsKey(rank))
						map.put(rank,map.get(rank)+1);
					else
						map.put(rank,1);
				}
			}
		}
		List<Map.Entry<OfficerRank,Integer>> list = new ArrayList<>();
		for(Map.Entry<OfficerRank,Integer> entry : map.entrySet())
			list.add(entry);
		Collections.sort(list,new EntryComp());
		return list;
	}

}
