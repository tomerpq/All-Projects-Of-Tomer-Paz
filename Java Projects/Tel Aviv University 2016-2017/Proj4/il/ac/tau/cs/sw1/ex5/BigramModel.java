package il.ac.tau.cs.sw1.ex5;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14000;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		String[] array1 = new String[MAX_VOCABULARY_SIZE];
		int i = 0;
		boolean someNumYet = true;
		if(!wordNotInArray(array1,SOME_NUM))
			someNumYet = false;
		BufferedReader bufferedRead = new BufferedReader(new FileReader(new File(fileName)));
		String line = bufferedRead.readLine();
		while(line != null && i < MAX_VOCABULARY_SIZE){
			Scanner scanner = new Scanner(line);
			while(scanner.hasNext() && i < MAX_VOCABULARY_SIZE){
				String word = scanner.next().toLowerCase();
				if(someNumYet && checkWordIsInteger(word)){
					someNumYet = false;
					array1[i] = SOME_NUM;
					i++;
				}
				else if(checkWordHasEL(word) && wordNotInArray(array1,word)){
					array1[i] = word;
					i++;
				}
			}
			scanner.close();
			line = bufferedRead.readLine();
		}
		bufferedRead.close();
		int words = MAX_VOCABULARY_SIZE;
		for(int j = 0; j < array1.length; j++)
			if(array1[j] == null){
				words = j;
				break;
			}
		String[] arr = new String[words];
		for(int j = 0; j < arr.length; j++)
			arr[j] = array1[j];
		return arr;
	}
	private boolean wordNotInArray(String[] array,String word){
		for(int i = 0; i < array.length; i++)
			if(array[i] != null)
				if(array[i].equals(word))
					return false;
		return true;
	}
	private boolean checkWordHasEL(String word){
		if(word == null)
			return false;
		for(int i = 0; i < word.length(); i++)
			if(isEnglishLetter(word.charAt(i)))
				return true;
		return false;
	}
	private boolean isEnglishLetter(char c){
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}
	private boolean checkWordIsInteger(String word){
		if(word == null)
			return false;
		for(int i = 0; i < word.length(); i++)
			if(isNotInteger(word.charAt(i)))
				return false;
		return true;
	}
	private boolean isNotInteger(char c){
		return !Character.isDigit(c);
	}
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		int l = vocabulary.length;
		int[][] countsarr = new int[l][l];
		BufferedReader buff = new BufferedReader(new FileReader(new File(fileName)));
		String line = buff.readLine();
		while(line != null){
			Scanner scan = new Scanner(line);
			String wordA = "";
			if(scan.hasNext()){
				wordA = scan.next().toLowerCase();
			}
			while(scan.hasNext()){
				String wordB = scan.next().toLowerCase();
				if(!wordNotInArray(vocabulary,wordA) && !wordNotInArray(vocabulary,wordB)){
					int a = 0, b = 0;
					for(int i = 0; i < l; i++){
						if(vocabulary[i].equals(wordA))
							a = i;
						if(vocabulary[i].equals(wordB))
							b = i;
					}
					countsarr[a][b]++;
				}
				wordA = new String(wordB);
			}
			scan.close();
			line = buff.readLine();
		}
		buff.close();
		return countsarr;
	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		String fileNameVoc = fileName + VOC_FILE_SUFFIX;
		String fileNameCounts = fileName + COUNTS_FILE_SUFFIX;
		File a = new File(fileNameVoc);
		File b = new File(fileNameCounts);
		a.createNewFile();
		b.createNewFile();
		PrintWriter writer1 = new PrintWriter(a);
		int l = mVocabulary.length;
		writer1.println(l + " words");
		for(int i = 0; i < l; i++)
			writer1.println(i + "," + mVocabulary[i]);
		writer1.close();
		PrintWriter writer2 = new PrintWriter(b);
		for(int i = 0; i < l; i++)
			for(int j = 0; j < l; j++)
				if(mBigramCounts[i][j] != 0)
					writer2.println(i + "," + j + ":" + mBigramCounts[i][j]);
		writer2.close();
	}
	
	
	
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		String fileNameVoc = fileName + VOC_FILE_SUFFIX;
		String fileNameCounts = fileName + COUNTS_FILE_SUFFIX;
		BufferedReader buff1 = new BufferedReader(new FileReader(new File(fileNameVoc)));
		BufferedReader buff2 = new BufferedReader(new FileReader(new File(fileNameCounts)));
		String numWordString = buff1.readLine();
		Scanner scan1 = new Scanner(numWordString);
		int l = scan1.nextInt();
		scan1.close();
		mVocabulary = new String[l];
		String line1 = buff1.readLine();
		while(line1 != null){
			Scanner scanner1 = new Scanner(line1).useDelimiter(",");
			mVocabulary[scanner1.nextInt()] = scanner1.next();
			scanner1.close();
			line1 = buff1.readLine();
		}
		buff1.close();
		mBigramCounts = new int[l][l];
		String line2 = buff2.readLine();
		while(line2 != null){
			int a = Integer.parseInt(line2.substring(0,1));
			int b = Integer.parseInt(line2.substring(2,3));
			int c = Integer.parseInt(line2.substring(4,5));
			mBigramCounts[a][b] = c;
			line2 = buff2.readLine();
		}
		buff2.close();
	}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: word is in lowercase
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		for(int i = 0; i < mVocabulary.length; i++)
			if(mVocabulary[i].equals(word))
				return i;
		return -1;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int a = getWordIndex(word1),b = getWordIndex(word2);
		if(a == -1 || b == -1)
			return 0;
		else
			return mBigramCounts[a][b];
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int index = getWordIndex(word);
		int maxAfterWordCnt = 0;
		int maxAfterWordIndex = 0;
		for(int j = 0; j < mVocabulary.length; j++)
			if(mBigramCounts[index][j] > maxAfterWordCnt){
				maxAfterWordCnt = mBigramCounts[index][j];
				maxAfterWordIndex = j;
			}
		if(maxAfterWordCnt == 0)
			return null;
		else
			return mVocabulary[maxAfterWordIndex];
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		Scanner scanner = new Scanner(sentence);
		String wordA = scanner.next();
		String wordB = scanner.next();
		if(getBigramCount(wordA,wordB) == 0)
			return false;
		while(scanner.hasNext()){
			wordA = new String(wordB);
			wordB = scanner.next();
			if(getBigramCount(wordA,wordB) == 0)
				return false;
		}
		scanner.close();
		return true;
	}
	
	
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = 0, otherwise
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		if(isZeroArray(arr1) || isZeroArray(arr2))
			return 0.0;
		double mone = 0.0;
		double a = 0.0, b = 0.0;
		int l = arr1.length;
		for(int i = 0; i < l; i++){
			mone += arr1[i] * arr2[i];
			a += Math.pow(arr1[i],2);
			b += Math.pow(arr2[i],2);
 		}
		double mehane = Math.pow(a,0.5) * Math.pow(b,0.5);
		return (mone/mehane);
	}
	private static boolean isZeroArray(int[] array){
		for(int i = 0; i < array.length; i++)
			if(array[i] != 0)
				return false;
		return true;
	}

	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		double MAXcCs = 0.0;
		String w = word;
		int indexWord = getWordIndex(word);
		int[] wordVector = makeVector(word);
		for(int i = 0; i < mVocabulary.length; i++){
			String tempW = mVocabulary[i];
			if(!tempW.equals(word)){
				int[] tempWVector = makeVector(tempW);
				double cCs = calcCosineSim(wordVector,tempWVector);
				if(cCs > MAXcCs){
					MAXcCs = cCs;
					w = new String(tempW);
				}
			}
		}
		return w;
	}
	private int[] makeVector(String word){
		int index = getWordIndex(word);
		int l = mVocabulary.length;
		int[] vector = new int[l];
		for(int j = 0; j < l; j++)
			vector[j] = mBigramCounts[index][j];
		return vector;
	}
}
