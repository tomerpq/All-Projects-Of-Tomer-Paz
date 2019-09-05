public class Assignment02Q03 {
	public static void main(String[] args){
		int sum = 2;
		int prev = 1;
		int prevPrev = 1;
		int current = 2;
		String l1 = "The first " + args[0] + " Fibonacci numbers are:";
		String l2 = "";
		for(int i = 2; i < Integer.parseInt(args[0]); i++){
			l2 += current + " ";
			sum += current;
			prevPrev = prev;
			prev = current;
			current = prevPrev + prev;
		}
		double avg = sum/((double)Integer.parseInt(args[0]));
		String l3 = "The average is: " + avg;
		System.out.print(l1 + "\n" + l2 + "\n" + l3);
	}
}