public class Assignment1 {
	public static void main(String[] args){
		int a = Integer.parseInt(args[0]);
		int b = Integer.parseInt(args[1]);
		int c = Integer.parseInt(args[2]);
		if(a<0 || b<0 || c<0){
			System.out.print("Invalid input!");
		}
		else if((a^2 + b^2) == (c^2)){
			System.out.print("The input (" + a + "," + b + "," + c + ") defines a valid triangle!");
		}
		else{
			System.out.print("The input (" + a + "," + b + "," + c + ") does not define a valid triangle!");
		}
	}
}