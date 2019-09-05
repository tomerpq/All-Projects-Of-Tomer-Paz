
package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

public class FileIndexTester {
	
	public static final String INPUT_FOLDER = "resources/hw8/input";

	public static void main(String[] args){
		testRankedWordComparator();
		testFileIndex();
		System.out.println("done!");
	}

	public static void testRankedWordComparator(){
		Map<String, Integer> ranks1 = new HashMap<>();
		ranks1.put("file1", 1);
		ranks1.put("file2", 120);
		ranks1.put("file3", 8);
		RankedWord rWord1 = new RankedWord("Julia", ranks1);
		
		Map<String, Integer> ranks2 = new HashMap<>();
		ranks2.put("file1", 70);
		ranks2.put("file2", 50);
		ranks2.put("file3", 3);
		RankedWord rWord2 = new RankedWord("Eleanor", ranks2);
		RankedWordComparator minComp = new RankedWordComparator(rankType.min);
		RankedWordComparator maxComp = new RankedWordComparator(rankType.max);
		RankedWordComparator averageComp = new RankedWordComparator(rankType.average);

		if (minComp.compare(rWord1, rWord2) >= 0){
			printErrorNum(1);
		}
		if (maxComp.compare(rWord1, rWord2) <= 0){
			printErrorNum(2);
		}
		if (averageComp.compare(rWord1, rWord2) <= 0){
			printErrorNum(3);
		}
		System.out.println("finished RankedWord Test!");
	}
	
	public static void testFileIndex(){
		FileIndex fIndex = new FileIndex();
		fIndex.indexDirectory(INPUT_FOLDER);
		try {
			if (fIndex.getCountInFile("rocky1.txt", "Rocky") != 5) {
				printErrorNum(4);
			}
			if (fIndex.getRankForWordInFile("rocky3.txt", "and") != 1){
				printErrorNum(5);
			}

		} catch (FileIndexException e) {
			printErrorNum(6);
		}
		try{
			fIndex.getRankForWordInFile("help.txt", "rocky"); //non existing file
			printErrorNum(7);
		}
		catch (FileIndexException e) {
		}
		
		//in rocky1.txt: rank = 1, in rocky2.txt: rank = 4, in rocky3.txt: rank = 2
		if (fIndex.getAverageRankForWord("rocky") != Math.round((1+4+2)/3.)){
			printErrorNum(8);
		}

		List<String> topByMin = fIndex.getWordsBelowMinRank(1);
		//"rocky" is ranked 
		if (!topByMin.contains("rocky") || !topByMin.contains("and") || !topByMin.contains("his")){
			printErrorNum(9);
		}
		
		//highest rank for rocky is 4
		List<String> topByMax = fIndex.getWordsAboveMaxRank(5);
		if (!topByMax.get(0).equals("rocky")){
			printErrorNum(10);
		}
//		
		System.out.println("finished fileIndex Test!");

	}
	
	private static void printErrorNum(int num){
		System.out.println("ERROR " + num);
	}
}
