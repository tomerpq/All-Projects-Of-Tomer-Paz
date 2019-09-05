package il.ac.tau.cs.sw1.ex4;

import java.util.Scanner;

public class Scanning {
	public static void main(String[] args){
		Scanner s = new Scanner("");
		String text = "";
		while(s.hasNextLine()){
			Scanner sc = new Scanner(s.nextLine());
			while(sc.hasNext()){
				text += sc.next()+ " ";
				System.out.println(text);
			}
			sc.close();
		}
		s.close();
		char[] arr = "".toCharArray();
		int a = (int)('z');
		char h = (char)45;
		System.out.println(h);
	
	}
}
