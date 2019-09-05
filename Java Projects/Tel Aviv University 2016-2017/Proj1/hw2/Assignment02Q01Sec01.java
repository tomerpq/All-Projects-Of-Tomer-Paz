public class Assignment02Q01Sec01{
	public static void main(String[] args){
		for(int i = 0; i < args.length; i++){
			if(((int)args[i].charAt(0)) % 2 == 0){
				System.out.println(args[i]);
			}
		}
	}
}