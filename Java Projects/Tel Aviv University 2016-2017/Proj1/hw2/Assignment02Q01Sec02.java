public class Assignment02Q01Sec02 {
	public static void main(String[] args){
		int valSum = 0;
		String s = "";
		for(int i = 0; i < args.length; i++){
			int temp = 0;
			for(int j = 0; j < args[i].length(); j++){
				temp += (int)args[i].charAt(j);
			}
			if(temp >= valSum){
				valSum = temp;
				s = args[i];
			}
		}
		System.out.println(valSum + " " + s);
	}
}