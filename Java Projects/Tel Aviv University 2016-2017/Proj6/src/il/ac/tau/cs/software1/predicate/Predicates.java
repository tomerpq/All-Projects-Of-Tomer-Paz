package il.ac.tau.cs.software1.predicate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Predicates implements PredicatesSet<Person> {
	private List<Person> list;
	public Predicates(List<Person> list){
		this.list = list;
	}
	
	@Override
	public void remove(Predictor pred) {
		if(this.list == null)
			return;
		for(Iterator<Person> iter = this.list.iterator(); iter.hasNext();){
			Person p = iter.next();
			if(pred.test(p))
				iter.remove();
		}
	}

	@Override
	public void retain(Predictor pred) {
		if(this.list == null)
			return;
		for(Iterator<Person> iter = this.list.iterator(); iter.hasNext();){
			Person p = iter.next();
			if(!pred.test(p))
				iter.remove();
		}
	}

	@Override
	public List<Person> collect(Predictor pred) {
		List<Person> list = new ArrayList<>();
		if(this.list == null)
			return list;
		for(Iterator<Person> iter = this.list.iterator(); iter.hasNext();){
			Person p = iter.next();
			if(pred.test(p))
				list.add(p);
		}
		return list;
	}

	@Override
	public int find(Predictor pred) {
		if(this.list == null)
			return -1;
		int index = 0;
		for(Iterator<Person> iter = this.list.iterator(); iter.hasNext();){
			Person p = iter.next();
			if(pred.test(p))
				return index;
			index++;
		}
		return -1;
	}

}
