public class Assignment02Q02 {
	public static void main(String[] args){
		double sum = 0.0;
		double mehane = 1.0;
		Boolean signPlus = true;
		for(int i = 0; i < Integer.parseInt(args[0]); i++){
			if(signPlus){
				sum += 1/mehane;
				signPlus = false;
			}
			else{
				sum -= 1/mehane;
				signPlus = true;
			}
			mehane += 2;
		}
		sum = 4*sum;
		System.out.println(sum + " " + Math.PI);
	}
}