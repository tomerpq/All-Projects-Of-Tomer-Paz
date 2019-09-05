public class Assignment02Q01Sec03 {
	public static void main(String[] args){
		int cnt = 0;
		for(int i = 0; i < args.length; i++){
			if((Integer.parseInt(args[i]) % 6) % 2 == 0){
				cnt++;
			}
		}
		System.out.println(cnt);
	}
}