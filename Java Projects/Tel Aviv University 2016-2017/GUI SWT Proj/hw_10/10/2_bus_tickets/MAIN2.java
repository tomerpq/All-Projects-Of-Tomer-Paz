

public class MAIN2 {
		
		private final static float BASE_PRICE_PER_DAY = 2.0f;

		public enum BusTicket {
			DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY;
			
			
			
			
			private String duration(){
				switch(this){
				case DAILY:
					return "1";
				case WEEKLY:
					return "7";
				case MONTHLY:
					return "30";
				case QUARTERLY:
					return "90";
				case YEARLY:
					return "365";
				default:
					return "";
				}
			}
			private String price(){
				
			}
			private String description(){
				
			}
		}
		
		public static void main(String [] args) {
			System.out.println("Avialable tickets:");
			for (BusTicket ticket: BusTicket.values()) {
				System.out.println(ticket + " " + ticket.duration() + " " + ticket.price() + " " + ticket.description());
			}
		}
	}

