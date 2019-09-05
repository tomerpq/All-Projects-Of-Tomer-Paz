package il.ac.tau.cs.sw1.ex9.starfleet;

public abstract class AbsCrewMember implements CrewMember {
	private String name;
	private int age;
	private int yis;
	
	public AbsCrewMember(String name,int age,int yearsInService){
		this.name = name;
		this.age = age;
		this.yis = yearsInService;
	}
	public String getName(){
		return name;
	}
	public int getAge(){
		return age;
	}
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
		AbsCrewMember other = (AbsCrewMember) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public int getYearsInService(){
		return yis;
	}
	public String toString(){
		return "\n\tName=" + getName() + "\n\tAge=" +getAge() + "\n\tYearsInService=" + getYearsInService();
	}
}
