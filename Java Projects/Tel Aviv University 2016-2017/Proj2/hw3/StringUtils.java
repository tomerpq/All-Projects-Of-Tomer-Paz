package il.ac.tau.cs.sw1.hw3;

import java.util.Arrays;

public class StringUtils {
	
	public static String sortStringWords (String str)
	{
		String[] arr = str.split(" ");
		Arrays.sort(arr);
		String s1 = "";
		for(String s : arr){
			s1 = s + " " + s1;
		}
		return s1;
	}

	public static String mergeStrings(String a, String b)
	{
		String s = "";
		for(int i = 0; i < a.length(); i++){
			for(int j = 0; j < b.length(); j++){
				if(b.charAt(j) == a.charAt(i)){
					s += a.charAt(i);
					break;
				}
			}
		}
		return s;
		
	}
	
	public static boolean isAnagram(String a, String b)
	{
		for(int i = 0; i < a.length(); i++){
			String s = "" + a.charAt(i);
			if(!b.contains(s)){
				return false;
			}
		}
		for(int i = 0; i < b.length(); i++){
			String s = "" + b.charAt(i);
			if(!a.contains(s)){
				return false;
			}
		}
		return true;
		
	}
}
