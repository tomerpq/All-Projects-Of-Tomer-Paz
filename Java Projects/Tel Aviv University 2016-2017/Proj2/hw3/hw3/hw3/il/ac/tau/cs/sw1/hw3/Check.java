package il.ac.tau.cs.sw1.hw3;

public class Check {
	public static void main(String[] args){
		int [] a = {1,2,3,4,5};
		int[] b = ArrayUtils.reverseArray(a);
		for(int i = 0; i < b.length; i++){
			System.out.print(b[i]);
		}
		int [] aa = {1,2,-3,4,5};
		System.out.println(StringUtils.sortStringWords("to be or not to be"));
		System.out.println(StringUtils.mergeStrings("abcdefg","bcgfhi"));
		System.out.println(StringUtils.isAnagram("software","weratsof"));
		System.out.println(ArrayUtils.alternateSum(aa));
		int[][] z = {{1,0,0,1},{0,1,0,1},{0,0,1,0},{1,1,0,1}};
		int[][] w = {{1,0,0},{0,1,1},{0,1,1}};
		System.out.println(ArrayUtils.findPath(z,0,2));
	}
}
