package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;

public class TransportShip extends AbsSpaceship{
	
	private int cargoCapacity;
	private int passengerCapacity;
	
	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, int cargoCapacity, int passengerCapacity){
		super(name,commissionYear,maximalSpeed,crewMembers);
		this.cargoCapacity = cargoCapacity;
		this.passengerCapacity = passengerCapacity;
	}
	public int getCargoCapacity(){
		return cargoCapacity;
	}
	public int getPassengerCapacity(){
		return passengerCapacity;
	}
	@Override
	public int getAnnualMaintenanceCost(){
		return 3000 + 5*cargoCapacity + 3*passengerCapacity;
	}
	public String toString(){
		return "TransportShip" + super.toString() + "\n\tCargoCapacity=" + getCargoCapacity() + "\n\tPassengerCapacity=" + getPassengerCapacity();
	}
}
