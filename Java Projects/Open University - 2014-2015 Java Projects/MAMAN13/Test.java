public class Test
{ 
    public static boolean T(int[] arr){
        return T(arr,0);
    }
    private static boolean T(int[] a, int index){
        if (index < 0 || index >= a.length || a[index] == -1)
        return false;
        if (a[index] == 0)
        return true;
        int temp = a[index];
        a[index] = -1;
        boolean answer = T(a,index+temp) || T(a,index-temp);
        a[index] = temp;
        return answer;
    }
    public static boolean isSum(Range[] a, int x){
        if (a == null)
        return false;
        int length = 0;
        for (int i = 0; i < a.length; i++){
            length = length + a[i].getBig() - a[i].getSmall() + 1;
        }
        int[] arr = new int[length];
        int index = 0;
        int cell = 0;
        int k = a[cell].getSmall();
        while (cell < a.length){
            arr[index] = k;
            if (k + 1 <= a[cell].getBig())
            k++;
            else if (cell + 1 < a.length){
            cell++;
            k = a[cell].getSmall();
        }
        else
        break;
        index++;
        }
        int start = 0, end = arr.length -1;
        while (start < end){
            if (arr[start] + arr[end] == x)
            return true;
            else if (arr[start] + arr[end] < x)
            start++;
            else
            end--;
        }
        return false;
    }
    public static boolean isSum2(Range[] a, int x){
        int cellstart = 0;
        int cellend = a.length -1;
        int start = a[cellstart].getSmall();
        int end = a[cellend].getBig();
        while (start < end){
            if (start + end == x)
            return true;
            else if (start + end < x){
                if (start + 1 <= a[cellstart].getBig())
                start++;
                else if (cellstart + 1 < a.length){
                    cellstart++;
                    start = a[cellstart].getSmall();
                }
                else
                break;
            }
            else{
                if (end - 1 >= a[cellend].getSmall())
                end--;
                else if (cellend - 1 >= 0){
                    cellend--;
                    end = a[cellend].getBig();
                }
                else
                break;
            }
        }
        return false;
    }
    public static int fun1(String s, int f) 
    {
        int ans = f;
        for(int i =f+1; i<s.length(); i++)
            if (s.charAt(ans) > s.charAt(i))
                ans = i;
        return ans;
    }

    public static String fun2(String s, int i) 
    {
        char value = s.charAt(i);
        while (i > 0 && s.charAt(i-1) > value) {
            s=s.substring(0,i)+s.charAt(i-1)+s.substring(i+1);
            i = i-1;
        }
        s=s.substring(0,i)+value+s.substring(i+1);
        return s;
    }

    public static String something(String s) 
    {
        for(int i=0; i<s.length()-1; i++) {
            int m = fun1(s, i);
            s=fun2(s, m);
        }
        return s;
    }
    public static void printPath(int[][] mat){
        if (mat == null)
        return;
        String print = "";
        if (mat.length >= 1)
        printPath(mat,0,0,mat[0][0],print);
    }
    public static void printPath(int[][] mat, int row, int col, int value, String print){
        print = print + "(" + row + "," + col + ")";
        if (row + 1 < mat.length && mat[row+1][col] > value)
        printPath(mat,row+1,col,mat[row+1][col],print);
        else if (col + 1 < mat[0].length && mat[row][col+1] > value)
        printPath(mat,row,col+1,mat[row][col+1],print);
        else if (row - 1 >= 0 && mat[row-1][col] > value)
        printPath(mat,row-1,col,mat[row-1][col],print);
        else if (col - 1 >= 0 && mat[row][col-1] > value)
        printPath(mat,row,col-1,mat[row][col-1],print);
        else
        System.out.println(print);
    }
    public static int findMax(int[] arr){
        int start = 0, end = arr.length -1, mid;
        int max = arr[start];
        int indexMax = start;
        while (start < end){
            mid = (start + end) / 2;
            if (arr[end] >= arr[start]){
                max = arr[mid];
                start = mid + 1;
                indexMax = mid;
            }
            else
            end = mid - 1;
        }
        return indexMax;
    }
    public static void main (String [] args) 
    {
        int[][] m = {{3,8,7,1},{5,15,2,4},{2,1,3,2},{4,6,7,52}};
        printPath(m);
      
    }
}
