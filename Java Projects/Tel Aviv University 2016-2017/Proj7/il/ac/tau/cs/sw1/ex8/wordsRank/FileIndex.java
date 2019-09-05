package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	public static final int UNRANKED_CONST = 20;
	private String folderpath = ""; //must be intialzied cuz using inded one time exzactly
	private Map<String,IHistogram<String>> mapfiles; //String is filename
	private RankedWord rankword;
	
	public FileIndex(){
		this.mapfiles = new HashMap<String,IHistogram<String>>();
	}
	

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 */
  	public void indexDirectory(String folderPath) {
		//This code iterates over all the files in the folder. add your code wherever is needed
		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				IHistogram<String> his = new HashMapHistogram<String>();
				try {
					his.addAll(FileUtils.readAllTokens(file));
				} catch (IOException e) {//not happening cuz of pre
					e.printStackTrace();
				}
				mapfiles.put(file.getName(),his);
			}
		}
  		this.folderpath = folderPath;
	}
	
  	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getCountInFile(String filename, String word) throws FileIndexException{
		 String w = word.toLowerCase();
		 boolean in = false;
		 File folder = new File(folderpath);
		 File[] listFiles = folder.listFiles();
		 for(File file : listFiles)
			 if(file.isFile() && file.getName().equals(filename)){
				 in = true;
				 break;
			 }
		 if(in == false)
			 throw new FileIndexException("folder doesn't contain this file");
		 return mapfiles.get(filename).getCountForItem(w);
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getRankForWordInFile(String filename, String word) throws FileIndexException{
		String w = word.toLowerCase();
		boolean in = false;
		 File folder = new File(folderpath);
		 File[] listFiles = folder.listFiles();
		 for(File file : listFiles)
			 if(file.isFile() && file.getName().equals(filename)){
				 in = true;
				 break;
			 }
		 if(in == false)
			 throw new FileIndexException("folder doesn't contain this file");
		 int rank = 0;
		 boolean notfound = true;
		 Iterator<String> it = mapfiles.get(filename).iterator();
		 while(notfound && it.hasNext()){
			 String t = it.next();
			 if(t.equals(w))
				 notfound = false;
			 rank ++;
		 }
		 if(!notfound)
			 return rank;
		 else{
			 return UNRANKED_CONST + rank; 
		 }
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre word is not null
	 */
	public int getAverageRankForWord(String word){
		String w = word.toLowerCase();
		Map<String,Integer> map = new HashMap<>();
		for(Map.Entry<String,IHistogram<String>> entry : mapfiles.entrySet()){
			try {
				map.put(entry.getKey(),getRankForWordInFile(entry.getKey(),w));
			} catch (FileIndexException e) {//not happening
				e.printStackTrace();
			}
		}
		RankedWord rw = new RankedWord(w,map);
		rankword = rw;
		return rw.getRankByType(rankType.average);
	}
	
	
	public List<String> getWordsWithAverageRankLowerThenK(int k){
		List<RankedWord> sortedrankword = new ArrayList<RankedWord>();
		List<String> sortedlst = new ArrayList<String>();
		Set<String> dic = new HashSet<String>();
		for(Map.Entry<String,IHistogram<String>> entry : mapfiles.entrySet()){
			IHistogram<String> his = entry.getValue();
			Iterator<String> it = his.iterator();
			while(it.hasNext()){
				String w = it.next();
				if(!dic.contains(w)){
					dic.add(w);
					getAverageRankForWord(w);
					int avgrankw = rankword.getRankByType(rankType.average);
					if(avgrankw <= k){
						sortedrankword.add(rankword);//because getaveg intialized above
					}
				}
			}
		}
		Collections.sort(sortedrankword, new RankedWordComparator(rankType.average));
		Iterator<RankedWord> iterator = sortedrankword.iterator();
		while(iterator.hasNext()){
			RankedWord rw = iterator.next();
			sortedlst.add(rw.getWord());
		}
		return sortedlst;
	}
	
	public List<String> getWordsBelowMinRank(int k){
		List<RankedWord> sortedrankword = new ArrayList<RankedWord>();
		List<String> sortedlst = new ArrayList<String>();
		Set<String> dic = new HashSet<String>();
		for(Map.Entry<String,IHistogram<String>> entry : mapfiles.entrySet()){
			IHistogram<String> his = entry.getValue();
			Iterator<String> it = his.iterator();
			while(it.hasNext()){
				String w = it.next();
				if(!dic.contains(w)){
					dic.add(w);
					getAverageRankForWord(w);
					int minrankw = rankword.getRankByType(rankType.min);
					if(minrankw <= k){
						sortedrankword.add(rankword);//because getaveg intialized above
					}
				}
			}
		}
		Collections.sort(sortedrankword, new RankedWordComparator(rankType.min));
		Iterator<RankedWord> iterator = sortedrankword.iterator();
		while(iterator.hasNext()){
			RankedWord rw = iterator.next();
			sortedlst.add(rw.getWord());
		}
		return sortedlst;
	}
	
	
	public List<String> getWordsAboveMaxRank(int k){
		List<RankedWord> sortedrankword = new ArrayList<RankedWord>();
		List<String> sortedlst = new ArrayList<String>();
		Set<String> dic = new HashSet<String>();
		for(Map.Entry<String,IHistogram<String>> entry : mapfiles.entrySet()){
			IHistogram<String> his = entry.getValue();
			Iterator<String> it = his.iterator();
			while(it.hasNext()){
				String w = it.next();
				if(!dic.contains(w)){
					dic.add(w);
					getAverageRankForWord(w);
					int maxrankw = rankword.getRankByType(rankType.max);
					if(maxrankw <= k){
						sortedrankword.add(rankword);//because getaveg intialized above
					}
				}
			}
		}
		Collections.sort(sortedrankword, new RankedWordComparator(rankType.max));
		Iterator<RankedWord> iterator = sortedrankword.iterator();
		while(iterator.hasNext()){
			RankedWord rw = iterator.next();
			sortedlst.add(rw.getWord());
		}
		return sortedlst;
	}

}
