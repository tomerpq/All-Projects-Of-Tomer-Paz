public class TestValue
{
public static int binarySearch(int[] inputArr, int key) {
         
        int start = 0;
        int end = inputArr.length - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (key == inputArr[mid]) {
                return mid;
            }
            if (key < inputArr[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }
    public static void printPathWeights(int[][] m){
        printPathWeights(m,0,0,m.length -1,m[0].length -1,m[m.length-1][m[0].length-1]);
    }
    private static void printPathWeights(int[][] m, int strow, int stcol, int endrow, int endcol, int cost){
        if (strow == endrow && stcol == endcol){
        System.out.print(cost + "\n");
        return;
    }
    int temp = m[strow][stcol];
    m[strow][stcol] = -1;
        if (strow + 1 < m.length && m[strow+1][stcol] != -1)
        printPathWeights(m,strow+1,stcol,endrow,endcol,cost+temp);
        if (stcol + 1 < m[0].length && m[strow][stcol+1] != -1)
        printPathWeights(m,strow,stcol+1,endrow,endcol,cost+temp);
        if (strow - 1 >= 0 && m[strow-1][stcol] != -1)
        printPathWeights(m,strow-1,stcol,endrow,endcol,cost+temp);
        if (stcol - 1 >= 0 && m[strow][stcol-1] != -1)
        printPathWeights(m,strow,stcol-1,endrow,endcol,cost+temp);
        m[strow][stcol] = temp;
    }
    public static void printPathWeights2(int [][] m)
{
 printPathWeights2 (m, 0, 0, 0);
}

private static void printPathWeights2(int [][]m, int i, int j,
 int sum)
{
 if (i<0 || i>=m.length || j<0 || j>=m[0].length)
 return;
 if (m[i][j] == -1)
 return;
 if (i==m.length-1 && j==m[0].length-1)
 System.out.println (sum + m[m.length-1][m[0].length-1]);
 int temp = m[i][j];
 m[i][j] = -1;
 printPathWeights2 (m, i+1, j, sum+temp);
 printPathWeights2 (m, i, j+1, sum+temp);
 printPathWeights2 (m, i-1, j, sum+temp);
 printPathWeights2 (m, i, j-1, sum+temp);
 m[i][j] = temp;
} 
public static void fillHadamard(int mat[][]){
    fillHadmard(mat,mat.length,1,0,0);
}
private static void fillHadmard(int m[][], int size, int sign, int row, int col){
    if (size == 1)
    m[row][col] = sign;
    else{
        fillHadmard(m,size/2,sign,row,col);
        fillHadmard(m,size/2,sign,row,size/2 + col);
        fillHadmard(m,size/2,sign,row + size/2,col);
        fillHadmard(m,size/2,sign*-1,row + size/2,col + size/2);
    }
}
   public static void main (String [] args) {
 int[][] arr1 = {{8,4,2,4,3},{6,3,8,4,5},{1,4,9,9,7},{2,1,7,6,5}};
} 

}