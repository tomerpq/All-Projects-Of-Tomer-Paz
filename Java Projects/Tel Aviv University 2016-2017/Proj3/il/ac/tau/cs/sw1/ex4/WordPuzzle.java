package il.ac.tau.cs.sw1.ex4;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	public static final int MAX_VOCABULARY_SIZE = 3000;
	
	
	public static String[] scanVocabulary(Scanner scanner){          // Q - 1
		String[] array1 = new String[MAX_VOCABULARY_SIZE];
		int i = 0;
		while(scanner.hasNextLine() && i < MAX_VOCABULARY_SIZE){
			Scanner scanner2 = new Scanner(scanner.nextLine());
			while(scanner2.hasNext() && i < MAX_VOCABULARY_SIZE){
				String w = scanner2.next();
				if(checkWord(w)){
					String wordToArray = w.toLowerCase();
					if(wordNotInArray(array1,wordToArray)){
						array1[i] = wordToArray;
						i++;
					}
				}
			}
			scanner2.close();
		}
		int words = MAX_VOCABULARY_SIZE;
		for(int j = 0; j < array1.length; j++){
			if(array1[j] == null){
				words = j;
				break;
			}
		}
		String[] arr = new String[words];
		for(int j = 0; j < words ; j++)
			arr[j] = array1[j];
		Arrays.sort(arr);
		return arr;
	}
	private static boolean checkWord(String word){
		char[] chars = word.toCharArray();
		if(chars.length == 0)
			return false;
		for(char c : chars)
			if(!Character.isLetter(c))
				return false;
		return true;
	}
	private static boolean wordNotInArray(String[] a, String w){
		for(int j = 0; j < a.length; j++)
			if(a[j] != null)
				if(a[j].equals(w))
					return false;
		return true;
	}
	
	
	
	public static boolean isInVocabulary(String[] vocabulary, String word){ // Q - 2
		for(int i = 0; i < vocabulary.length; i++){
			if(word.equals(vocabulary[i]))
				return true;
		}
		return false;
	}

	
	public static boolean isLegalPuzzleStructure(char[] puzzle){  // Q - 3
		int cnt = 0;
		for(int i = 0; i < puzzle.length; i++){
			if(!(Character.isLowerCase(puzzle[i]) || puzzle[i] == HIDDEN_CHAR))
				return false;
			if(puzzle[i] == HIDDEN_CHAR)
				cnt++;
		}
		return cnt >= 1;
	}
	
	
	public static int countHiddenInPuzzle(char[] puzzle){ // Q - 4
		int cnt = 0;
		for(int i = 0; i < puzzle.length; i++)
			if(puzzle[i] == HIDDEN_CHAR)
				cnt++;
		return cnt;
	}
	
	
	public static boolean checkSolution(char[] puzzle, String word){ // Q - 5
		if(word.length() != puzzle.length)
			return false;
		for(int i = 0; i < word.length(); i++)
			if(puzzle[i] != HIDDEN_CHAR)
				if(word.charAt(i) != puzzle[i])
					return false;
		int[] arr = new int[200];
		for(int i = 0; i< word.length(); i++)
			arr[(int)word.charAt(i)]++;
		for(int i = 0; i < arr.length; i++){
			if(arr[i] >= 2){
				char l = (char)i;
				int cnt = 0;
				for(int j = 0; j < puzzle.length; j++)
					if(puzzle[j] == l)
						cnt++;
				if(cnt != 0 && cnt != arr[i])
					return false;
			}
		}
		return true;
	}
	
	
	public static String checkSingleSolution(char[] puzzle, String[] vocabulary){ // Q - 6
		int cntsol = 0;
		String sol = "";
		for(String word : vocabulary){
			if(checkSolution(puzzle,word)){
				cntsol++;
				sol = word;
			}
			if(cntsol > 1)
				return null;
		}
		if(cntsol == 0)
			return null;
		else
			return sol;
		
	}
	
	
	public static int applyGuess(char guess, String solution, char[] puzzle){ // Q - 7
		int changed = 0;
		for(int i = 0; i < solution.length(); i++)
			if(solution.charAt(i) == guess)
				if(puzzle[i] == HIDDEN_CHAR){
					puzzle[i] = guess;
					changed++;
				}
		return changed;
	}


	public static void main(String[] args) throws Exception{ //Q - 8
		if(args.length == 0)
			System.out.println("Error, no filepath entered.");
		else{
			String[] vocabulary = scanVocabulary(new Scanner(new File(args[0])));
			printReadVocabulary(args[0],vocabulary.length);
			printSettingsMessage();
			printEnterPuzzleMessage();
			Scanner scanner = new Scanner(System.in);
			String stringPuzzle = scanner.next();
			char[] puzzle = new char[stringPuzzle.length()];
			for(int i = 0; i < puzzle.length; i++)
				puzzle[i] = stringPuzzle.charAt(i);
			boolean notPass = true;
			while(notPass){
				if(!isLegalPuzzleStructure(puzzle)){
					printIllegalPuzzleMessage();
					printEnterPuzzleMessage();
					stringPuzzle = scanner.next();
					puzzle = new char[stringPuzzle.length()];
					for(int i = 0; i < puzzle.length; i++)
						puzzle[i] = stringPuzzle.charAt(i);
				}
				else if(checkSingleSolution(puzzle,vocabulary) == null){
					printIllegalSolutionsNumberMessage();
					printEnterPuzzleMessage();
					stringPuzzle = scanner.next();
					puzzle = new char[stringPuzzle.length()];
					for(int i = 0; i < puzzle.length; i++)
						puzzle[i] = stringPuzzle.charAt(i);
				}
				else
					notPass = false;
			}
			printGameStageMessage();
			int trys = countHiddenInPuzzle(puzzle) +3;
			String solution = checkSingleSolution(puzzle,vocabulary);
			boolean notEnd = true;
			while(notEnd){
				if(trys == 0){
					notEnd = false;
					scanner.close();
					printGameOver();
				}
				else{
					printPuzzle(puzzle);
					printEnterYourGuessMessage();
					String stringGuess = scanner.next();
					char guess = stringGuess.charAt(0);
					if(applyGuess(guess,solution,puzzle) >= 1){
						if(countHiddenInPuzzle(puzzle) == 0){
							printWinMessage();
							notEnd = false;
							scanner.close();
						}
						else{
							trys -= 1;
							printCorrectGuess(trys);
						}
					}
					else{
						trys -= 1;
						printWrongGuess(trys);
					}
				}
			}
		}
	}


	/*************************************************************/
	/*********************  Don't change this ********************/
	/*******************************************************/
	
	public static void printReadVocabulary(String vocabularyFileName, int numOfWords){
		System.out.println("Read " + numOfWords + " words from " + vocabularyFileName);
	}

	public static void printSettingsMessage(){
		System.out.println("--- Settings stage ---");
	}
	
	public static void printEnterPuzzleMessage(){
		System.out.println("Enter your puzzle:");
	}
	
	public static void printIllegalPuzzleMessage(){
		System.out.println("Illegal puzzle, try again!");
	}
	
	public static void printIllegalSolutionsNumberMessage(){
		System.out.println("Puzzle doesn't have a single solution, try again!");
	}
	
	
	public static void printGameStageMessage(){
		System.out.println("--- Game stage ---");
	}
	
	public static void printPuzzle(char[] puzzle){
		System.out.println(puzzle);
	}
	
	public static void printEnterYourGuessMessage(){
		System.out.println("Enter your guess:");
	}
	
	public static void printCorrectGuess(int attemptsNum){
		System.out.println("Correct Guess, " + attemptsNum + " guesses left");
	}
	
	public static void printWrongGuess(int attemptsNum){
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left");
	}

	public static void printWinMessage(){
		System.out.println("Congratulations! You solved the puzzle");
	}
	
	public static void printGameOver(){
		System.out.println("Game over!");
	}

}
