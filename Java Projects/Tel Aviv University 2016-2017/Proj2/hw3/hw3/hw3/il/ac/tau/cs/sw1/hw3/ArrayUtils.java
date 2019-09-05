package il.ac.tau.cs.sw1.hw3;

public class ArrayUtils {
	
	public static int[] reverseArray(int[] array)
	{
		for (int i = 0, j = array.length -1; i < array.length && j > i; i++, j--){
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			
		}
		return array;
		
	}
	
	public static int[] shiftArrayToTheRightCyclic(int[] array, int move)
	{
		if(move > 0 && array.length > 0){
		int[] a = new int[array.length];
		int moveMod = move % array.length;
		for(int i = 0; i < array.length; i++){
			int moving = moveMod + i;
			if(moving >= array.length){
				moving = moving - array.length;
			}
			a[moving] = array[i];
		}
		for(int i = 0; i < array.length; i++){
			array[i] = a[i];
		}
		}
		return array;
	}
	
	public static int alternateSum(int[] array)
	{
		if(array.length > 0){
			int start = 0, end = array.length -1;
			int maxsum = maxaSum(array,start,end);
			while(start < end){
				int sum1 = maxaSum(array,start,end);
				if(sum1 > maxsum){
					maxsum = sum1;
				}
				start ++;
			}
			return maxsum;
		}
		return 0;
		
	}
	private static int maxaSum(int[] arr, int st, int end){
		int sum = arr[st] - arr[st+1], maxsum = arr[st] - arr[st+1];
		for(int i = st +2; i <= end; i += 2){
			sum += arr[i];
			if(sum >= maxsum){
				maxsum = sum;
			}
			if(i+1 <= end){
			sum -= arr[i+1];
			}
			if(sum >= maxsum){
				maxsum = sum;
			}
		}
		return maxsum;
	}
	
	public static int findPath(int[][] m, int i, int j)
	{
		if(m.length == 0){
			return 0;
		}
		int[][] arr = new int[m.length][m.length];
		for(int i1 = 0; i1 < arr.length; i1++){
			for(int j1 = 0; j1 < arr.length; j1++){
				arr[i1][j1] = m[i1][j1];
			}
		}
		boolean [][] b = new boolean[arr.length][arr.length];
		int r = 0;
		findPath(arr,i,j,b);
		for(boolean[] b1 : b){
			for(boolean b2 : b1){
				if(b2){
					r = 1;
					break;
				}
			}
		}
		return r;
		
	}
	private static void findPath(int[][] m, int i, int j, boolean[][] b){
		for(int z = 0; z < m.length; z++){
			if(m[i][z] == 1){
				if(z == j){
					b[i][z] = true;
				}
				else{
				m[i][z] = 0;
				m[z][i] = 0;
				findPath(m,z,j,b);
				}
			}
		}
	}

}
