package il.ac.tau.cs.sw1.ex9.riddles.forth;

import java.util.Iterator;

public class B4 implements Iterator<String>{
	private String[] strings;
	private int rand;
	private final int length;
	private int cnt = 0;
	
	
	public B4(String[] strings,int rand){
		this.strings = strings;
		this.rand = rand;
		this.length = strings.length;
	}
	public boolean hasNext(){
		return (cnt <= length -1 +(rand-1)*length);
	}
	public String next(){
		int i = checki();
		cnt ++;
		return strings[i];
	}
	private int checki(){
		for(int i = 0; i < length; i++){
			int k = cnt - i;
			if(k % length == 0)
				return i;//always will return..
		}
		return 0;
	}
}
