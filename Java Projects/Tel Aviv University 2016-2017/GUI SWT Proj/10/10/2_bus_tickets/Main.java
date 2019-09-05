public class Main {
	
	private final static float BASE_PRICE_PER_DAY = 2.0f;

	public enum BusTicket {

	}
	
	public static void main(String [] args) {
		System.out.println("Avialable tickets:");
		for (BusTicket ticket: BusTicket.values()) {
			System.out.println(
					String.format("Name: %10s \t Validity: %3d days "
							+ "\t Price: %3.2f NIS \t Description: %s", 
							ticket, ticket.duration(), ticket.price(), ticket.description()));
		}
	}
}