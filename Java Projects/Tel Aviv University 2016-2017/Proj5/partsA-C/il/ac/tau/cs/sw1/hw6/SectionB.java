package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class SectionB {
	
	/*
	* @post $ret == true iff exists i such that array[i] == value
	*/
	public static boolean contains(int[] array, int value) { 
		if(array == null)
			return false;
		for(int i = 0; i < array.length; i++)
			if(array[i] == value)
				return true;
		return false;
	}
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	*/
	public static int guess(int[] array) { 
		return 69;
	}
	/*
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre array.length >= 1
	* @post for all i array[i] >= $ret
	*/
	public static int min(int[] array) { 
		return array[0];
	}
	
	/*
	* @pre array.length >=1
	* @post for all i array[i] >= $ret
	* @post Arrays.equals(array, prev(array))
	*/
	public static int min2(int[] array) { 
		int min = 0;
		for(int i = 0; i < array.length; i++)
			if(array[i] < min)
				min = array[i];
		return min;
	}
	
	/*
	* @pre word.length() >=1
	* @post for all i : $ret.charAt(i) == word.charAt(a.length() - i - 1)

	*/
	public static String reverse(String word) 
	{
		String w = "";
		for(int i = 0; i < word.length(); i++)
			w += word.charAt(word.length() - i - 1);
		return w;
	}


}
