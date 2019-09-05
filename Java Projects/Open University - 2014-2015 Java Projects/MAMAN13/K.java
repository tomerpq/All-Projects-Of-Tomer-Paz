public class K
{
    
    public K(){
    }
    public static void quickSort(int[] a){
        quickSort(a,0,a.length -1);
    }
    private static void quickSort(int[] a, int p, int r)
    {
        if(p<r)
        {
            int q=partition(a,p,r);
            quickSort(a,p,q);
            quickSort(a,q+1,r);
        }
    }

    private static int partition(int[] a, int p, int r) {

        int x = a[p];
        int i = p-1 ;
        int j = r+1 ;

        while (true) {
            i++;
            while ( i< r && a[i] < x)
                i++;
            j--;
            while (j>p && a[j] > x)
                j--;

            if (i < j)
                swap(a, i, j);
            else
                return j;
        }
    }

    private static void swap(int[] a, int i, int j) {
        // TODO Auto-generated method stub
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public boolean single(int[] values){
        quickSort(values);
        if (values.length == 1)
        return true;
        if (values.length == 2)
        if (values[0] != values[1])
        return true;
        for (int i=0; i < values.length -2; i++){
            if (values[0] != values[1])
            return true;
            if (values[values.length-2] != values[values.length-1])
            return true;
            if (values[i] != values[i+1] && values[i+1] != values[i+2])
            return true;
        }
        return false;
    }
    public boolean single(int[] values,int k){
        quickSort(values);
        int c = 1;
        int h = 0;
        for (int i=0; i < values.length -1; i++){
            if (values[i] == values[i+1])
            c++;
            else
            c = 1;
            if (c > h)
            h = c;
        }
        if (values.length == 1)
        if (k <= 1)
        return true;
        if (h >= k)
        return true;
        return false;
    }
    public static boolean what1T(int [] a, int [] b)
{
 if (a.length != b.length)
 return false;
 for (int i= 0; i<a.length; i++)
 for (int j=0; j<b.length; j++)
 if (b[j] < a[i])
 return false;
 return true;
} 

    public static boolean what1a(int[] a, int[] b){
        if (a.length != b.length)
        return false;
        //place-O(log(n)), time-O(nlog(n))
        quickSort(a);
        for (int i=0; i < b.length; i++){
            if (b[i] < a[a.length -1])
            return false;
        }
        return true;
    }
    public static boolean what1b(int[] a, int[] b){
        if (a.length != b.length)
        return false;
        //place-O(1), time-O(n)
        int aBiggest = a[0];
        int bSmallest = b[0];
        final int length = a.length;
        for (int i=0; i < length; i++){
            if (a[i] > aBiggest)
            aBiggest = a[i];
            if (b[i] < bSmallest)
            bSmallest = b[i];
        }
        if (bSmallest < aBiggest)
        return false;
        return true;
    }
    public static int what2T(int[] vec)
    {
 int m = 0;
 for (int i=0; i<vec.length; i++)
 {
 int c = 0;
 int n =i;
 do
 {
 n = find (vec, vec[n]+1);
 c ++;
 } while (n!= -1);
 if (c > m)
 m = c;
 }
 return m;
}

public static int find(int[] vec, int value)
{
 for (int i=0; i< vec.length; i++)
 if (vec[i] == value)
 return i;
 return -1;
}
public static int what2A(int[] vec){
    quickSort(vec);
    int m = 1;
    int k = 1;
    for (int i=0; i < vec.length -1; i++){
        if (vec[i+1] == vec[i] + 1){
        m++;
        if (m > k)
        k = m;
    }
        if (vec[i+1] > vec[i] + 1)
        m = 1;
    }
    return k;
}
private int sumRowV(int[][]m ,int row){
    return sumRow(m,row,0);
}private int sumRow(int[][] m,int row, int col){
    if (col >= m[row].length)
    return 0;
    return m[row][col] + sumRow(m,row,col + 1);
}
private int maxRowCheck(int[][] m,int row, int maxSum, int maxplace){
    if (row >= m.length)
    return maxplace;
    int s = sumRowV(m,row);
    if (s > maxSum){
    maxSum = s;
    maxplace = row;
}
    return maxRowCheck(m,row + 1,maxSum,maxplace);
}
public int maxRow(int[][]m){
    return maxRowCheck(m,0,0,0); 
}
public int whatq4(int[] _arr)
{
 int c = 1;
 for (int i=1; i<_arr.length; i++)
 {
 boolean u = true;
 for (int j=0; (j<i) && u; j++)
 {
 if (_arr[i] == _arr[j])
 u = false;
 }
 if (u)
 c++;
 }
 return c;
}
public int whataq4(int[] _arr){
quickSort(_arr);
    int c = 1;
for (int i =_arr.length -1; i>0; i--){
    if (_arr[i] != _arr[i-1])
    c++;
}
return c;
}
public int equalSum(int[] arr){
    return equalSum(arr,0);
}
private int equalSum(int[] arr, int startcheck){
    if (startcheck +1 == arr.length)
    return -1;
    if (sumrow(arr,0,startcheck) == sumrow(arr,startcheck +1,arr.length -1))
    return startcheck;
    return equalSum(arr,startcheck +1);
}
private int sumrow(int[] arr, int start, int end){
    if (start > end)
    return 0;
    return sumrow(arr,start + 1,end) + arr[start];
}
public void whatq6(int[] arr)
{
 int x = arr[0];
 int y = arr[0];
 for (int i=1; i<arr.length; i++)
 {
 if (arr[i] < x)
 x = arr[i];
 else if (arr[i] > y)
 y = arr[i];
 }
 System.out.println (x + " " + y);
} 
public static boolean isSubstring(String s1, String s2){
    return isSubstring(s1,s2,0,1);
}
private static boolean isSubstring(String s1, String s2, int s2st, int cut){
    if (s2.length() == 0)
    return true;
    if (s1.length() == 0 || s1 == null || s2.length() > s1.length())
    return false;
    if (s1.length() -1 >= s2st)
    if (s2.charAt(s2st) == s1.charAt(s2st)){
        if (s2.charAt(s2st) == s2.charAt(s2.length() -1))
        return true;
        return isSubstring(s1,s2,s2st +1,cut);
    }
    s2st = 0;
    return isSubstring(s1.substring(cut),s2,s2st,cut +1);
}
public static int[] merge(int[] ar1, int[] ar2){
    int[] ar = new int[ar1.length + ar2.length];
    return merge(ar1,ar2,0,0,0,ar);
}
private static int[] merge(int[] ar1, int[] ar2, int st1, int st2, int st, int[] ar){
    if (st1 <= ar1.length -1 && st2 <= ar2.length -1){
    if (ar1[st1] <= ar2[st2]){
    ar[st] = ar1[st1];
    return merge(ar1,ar2,st1 +1,st2,st +1,ar);
}
else{
    ar[st] = ar2[st2];
    return merge(ar1,ar2,st1,st2 +1,st +1,ar);
}
}
if (st1 <= ar1.length -1){
ar[st] = ar1[st1];
return merge(ar1,ar2,st1 +1,st2,st +1,ar);
}
if (st2 <= ar2.length -1){
    ar[st] = ar2[st2];
    return merge(ar1,ar2,st1,st2 +1,st +1,ar);
}
return ar;
}
public static void sortByFour(int[] arr){
    for (int i=0; i < arr.length -1; i++){
        if ((arr[i]%4 == 1 && arr[i+1]%4 == 0) ||
        ((arr[i]%4 == 2) && (arr[i+1]%4 == 1 || arr[i+1]%4 == 0)) ||
        ((arr[i]%4 == 3) && (arr[i+1]%4 == 2 || arr[i+1]%4 == 1 || arr[i+1]%4 == 0))){
        int temp = arr[i];
        arr[i] = arr[i+1];
        arr[i+1] = temp;
    }
    }
}
public static Rectangle smallestRect1(Point[] p){
    double highest = p[0].getY();
    double lowest = p[0].getY();
    double westest = p[0].getX();
    double eastest = p[0].getX();
    for (int i = 1; i < p.length; i++){
        if (p[i].getY() > highest)
        highest = p[i].getY();
        if (p[i].getY() < lowest)
        lowest = p[i].getY();
        if (p[i].getX() < westest)
        westest = p[i].getX();
        if (p[i].getX() > eastest)
        eastest = p[i].getX();
    }
    Point sw = new Point((int)westest,(int)lowest);
    int w = (int)(eastest - westest);
    int l = (int)(highest - lowest);
    return new Rectangle(l,w,sw);
}
public static Rectangle smallestRect2(Point[] p){
    double lowest = p[0].getY();
    double highest = p[p.length -1].getY();
    double westest, eastest;
    int start = 0, end = p.length -1;
    while (start < end){
        int mid = (start + end) / 2;
        if (p[mid].getY() == p[start].getY())
        start = mid +1;
        else
        end = mid;
    }
    westest = p[0].getX() < p[start].getX() ? p[0].getX() : p[start].getX();
    eastest = p[p.length -1].getX() > p[start -1].getX() ? p[p.length -1].getX() : p[start -1].getX();
    Point sw = new Point((int)westest,(int)lowest);
    int l = (int)(highest - lowest);
    int w = (int)(eastest - westest);
    return new Rectangle(l,w,sw);
}
}//class