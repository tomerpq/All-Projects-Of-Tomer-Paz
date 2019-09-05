package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	private Map<T,Integer> m;
	
	public HashMapHistogram(){
		this.m = new HashMap<T,Integer>();
	}

	@Override
	public void addItem(T item) {
		if(m.containsKey(item))
			m.put(item,m.get(item)+1);
		else
			m.put(item,1);
	}

	@Override
	public void addItemKTimes(T item, int k) throws IllegalKValue {
		if(k < 0)
			throw new IllegalKValue(k);
		else if(k == 0)
			return;
		else{
			if(m.containsKey(item))
				m.put(item,m.get(item)+k);
			else
				m.put(item,k);
		}
	}

	@Override
	public int getCountForItem(T item) {
		if(m.containsKey(item))
			return m.get(item);
		else
			return 0;
	}

	@Override
	public void addAll(Collection<T> items) {
		for(Iterator<T> iter = items.iterator(); iter.hasNext();){
			T item = iter.next();
			addItem(item);
		}
	}

	@Override
	public void clear() {
		m.clear();
	}

	@Override
	public Set<T> getItemsSet() {
		return m.keySet();
	}

	@Override
	public Iterator<T> iterator() {
		return new HashMapHistogramIterator(m);
	}
	
}
