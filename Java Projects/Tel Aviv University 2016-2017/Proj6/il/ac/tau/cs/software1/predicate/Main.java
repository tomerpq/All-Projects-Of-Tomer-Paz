package il.ac.tau.cs.software1.predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {	
	
	private static Scanner scanner;
	
	public static List<Person> getPersonFromUser()
	{
		List<Person> list = new ArrayList<Person>();
		System.out.println("Please choose number of persons (or X for exit):");
		String s = "";
		boolean notDone = true;
		while(notDone){
			s = scanner.next();
			if(s.equals("X")){
				scanner.close();
				System.exit(0);
			}
			if(!onlyNumbersNotZero(s)){
				System.out.println("Unknown command. Please try again.");
				System.out.println("Please choose number of persons (or X for exit):");
			}
			else
				notDone = false;
		}
		int numOfPer = Integer.parseInt(s);
		String name = "";
		int age = 0;
		for(int i = numOfPer; i > 0; i--){
			System.out.print("Please enter age: ");
			age = scanner.nextInt();
			System.out.print("Please enter name: ");
			name = scanner.next();
			list.add(new Person(age,name));
		}
		System.out.println("Your list is: " + list.toString());
		return list;
	}
	private static boolean onlyNumbersNotZero(String s){
		for(int i = 0; i < s.length(); i++)
			if(!Character.isDigit(s.charAt(i)))
				return false;
		if(Integer.parseInt(s) <= 0)
			return false;
		return true;
	}
	
	public static Predictor getPredictorFromUsers()
	{
		System.out.println("Please choose Predictor:" +"\n" + "E – Even" + "\n" + "B – Big" + "\n" + "S – StartWith");
		String s = "";
		boolean notDone = true;
		while(notDone){
			s = scanner.next();
			if(!s.equals("E") && !s.equals("B") && !s.equals("S")){
				System.out.println("Unknown command. Please try again.");
				System.out.println("Please choose Predictor:" +"\n" + "E – Even" + "\n" + "B – Big" + "\n" + "S – StartWith");
			}
			else
				notDone = false;
		}
		if(s.equals("E"))
			return new Even();
		if(s.equals("B")){
			System.out.println("Please enter a number to compare with:");
			int num = scanner.nextInt();
			return new Big(num);
		}
		// "S":
		System.out.println("Please enter a char to compare:");
		char c = scanner.next().charAt(0);
		return new StartWith(c);
	}
	
	public static List<Person> apply(List<Person> lst, Predictor p)
	{
		List<Person> list = lst;
		Predicates obj = new Predicates(list);
		while(true){
			System.out.println("What would you like to do:" + "\n" + "R – Remove" + "\n" + "E – Retrain" + "\n" + "C – Collect" + "\n" + "F – Find" + "\n" +  "N – Insert a new list");
			String s = scanner.next();
			if(!s.equals("R") && !s.equals("E") && !s.equals("C") && !s.equals("F") && !s.equals("N"))
				System.out.println("Unknown command. Please try again.");
			else{
				if(s.equals("R")){
					obj.remove(p);
					System.out.println("The Result is: " + list.toString());
				}
				else if(s.equals("E")){
					obj.retain(p);
					System.out.println("The Result is: " + list.toString());
				}
				else if(s.equals("C")){
					list = obj.collect(p);
					obj = new Predicates(list);
					System.out.println("The Result is: " + list.toString());
				}
				else if(s.equals("F")){
					System.out.println("The Result is: " + obj.find(p) + ", for list: " + list.toString());
				}
				else
					return getPersonFromUser();
			}
		}
	}
	
	public static void run()
	{
		scanner = new Scanner(System.in);
		List<Person> list = getPersonFromUser();
		while(true){
			Predictor pred = getPredictorFromUsers();
			list = apply(list,pred);
		}
	}
	
	
}
