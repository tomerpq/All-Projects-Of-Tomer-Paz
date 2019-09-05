package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T>{
	
	private class Targil8Comparator implements Comparator<T>{
		public int compare(T item1,T item2){
			return item1.compareTo(item2);
		}
	}
	public class IntegerComparator implements Comparator<Integer>{
		public int compare(Integer i1,Integer i2){
			return -1 *(i1 - i2); // from big to small
		}
	}
	
	private List<T> objList;
	private List<Integer> valList;
	private List<Integer> sortedValList;
	private int size;
	
	
	public HashMapHistogramIterator(Map<T,Integer> m){
		this.objList = new ArrayList<T>();
		this.valList = new ArrayList<Integer>();
		this.sortedValList = new ArrayList<Integer>();
		for(Map.Entry<T,Integer> entry: m.entrySet()){
			this.objList.add(entry.getKey());
			this.valList.add(entry.getValue());//corresponding
			this.sortedValList.add(entry.getValue());
		}
		Collections.sort(this.sortedValList, new IntegerComparator());
		this.size = m.size();
	}
	
	@Override
	public boolean hasNext() {
		return size > 0;
	}

	@Override
	public T next() {
		if(hasNext()){
			size--;
			int val = sortedValList.get(0);
			sortedValList.remove(0);
			if(sortedValList.contains(val)){
				List<T> sublistsort = new ArrayList<>();
				int stindex = valList.indexOf(val);
				int endindex = valList.lastIndexOf(val);
				for(int i = stindex; i <= endindex; i++){
					if(valList.get(i) == val){
						sublistsort.add(objList.get(i));
					}
				}
				Collections.sort(sublistsort, new Targil8Comparator());
				T obj = sublistsort.get(0);
				int indexVal = objList.indexOf(obj);
				objList.remove(indexVal);
				valList.remove(indexVal);
				return obj;
			}
			else{
				int indexVal = valList.indexOf(val);
				valList.remove(indexVal);
				T obj = objList.get(indexVal);
				objList.remove(indexVal);
				return obj;
			}
		}
		else{
			System.out.println("No next");
			return null;
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
