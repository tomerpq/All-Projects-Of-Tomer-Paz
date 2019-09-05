package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class HashMapHistogramTester {
	public static void main(String[] args){
		List<Integer> intLst = Arrays.asList(1, 2, 1, 2, 3, 4, 3, 1);
		IHistogram<Integer> intHist = new HashMapHistogram<>();
		for (int i : intLst){
			intHist.addItem(i);
		}
		if (intHist.getCountForItem(1) != 3){
			System.out.println("ERROR 1");
		}
		if (intHist.getCountForItem(5) != 0){
			System.out.println("ERROR 2");
		}
		Iterator<Integer> intHistIt = intHist.iterator();
		List<Integer> tmpList = new ArrayList<Integer>();
		while (intHistIt.hasNext()){
			tmpList.add(intHistIt.next());
		}
		if (tmpList.get(0) != 1){
			System.out.println("ERROR 3");
		}
		if (tmpList.size() != 4){
			System.out.println("ERROR 4");
		}
		
		
		IHistogram<String> stringHist = new HashMapHistogram<>();
		try{
			stringHist.addItemKTimes("bb", 5);
			stringHist.addItemKTimes("aa", 5);
		}
		catch (IllegalKValue exp){
			System.out.println("ERROR 5");
		}
		stringHist.addItem("abc");
		stringHist.addItem("de");
		stringHist.addItem("abc");
		stringHist.addItem("de");
		stringHist.addItem("abc");
		stringHist.addItem("de");
		stringHist.addItem("de");
		if (stringHist.getCountForItem("abc") != 3){
			System.out.println("ERROR 6");
		}
		Iterator<String> it = stringHist.iterator();
		/* the order of the returned items should be: "aa", "bb", "de", "abc"
		 * aa" and "bb" both appear 5 times, so in this case we sort by the natural order
		 * of the elements "aa" and "bb". This is why "aa" should appear before "bb"
		 */
		if (!it.next().equals("aa")){
			System.out.println("ERROR 7");
		}
		if (!it.next().equals("bb")){
			System.out.println("ERROR 8");
		}
		if (!it.next().equals("de")){
			System.out.println("ERROR 8");
		}
		System.out.println("done!");
	}
}
