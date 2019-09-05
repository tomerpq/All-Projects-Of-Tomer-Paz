/**
 * This class represertices all the methods of MMn 14/
 * @version 12.6.2015
 * @author Tomer Paz
 */
public class Ex14{
    /* Question 1 */
    /**
     *Q1(A): Statements 3,5,6 are true.
    */
    //B:
    /**1:findValWhat:
     * the method starts at the right upper corner of the array
     * if the cell's int equals to val returns true, if not and it is
     * bigger then val it goes left in the row. if its smaller then val
     * it goes down in the column. if the loop reaches the borders of the array
     * without finding val it returns false.
     * @param m the 2d array that the method gets if what returns true on it
     * @param val the int's searched in the array
     * @return true if the method finds val in the array.
     */
    public static boolean findValWhat(int[][] m, int val){
        int i = 0;
        int j = m[0].length -1;//starts at the right upper corner of the array.
        while (i < m.length && j >= 0){
            if (m[i][j] == val){
                return true;
            }
            else if (m[i][j] > val){
                j--; //goes left from the point, time complexty-O(n) worst cast.
            }
            else{
                i++; //goes from that point down, time complexty-O(n) worst cast.
            }
        }
        return false; //time complexity = O(n) + O(n) = O(n).
    }
    /**2:findValTest:
         * the first loop runs all over the first column and checks
         * if val is bigger or the same then the first cell in the first row
         * and smaller or same(that will be for must if its equal to the first 
         * cell in the upper row because of "test-true"), if the loop
         * "catches" this two rows it will run a loop inside them both to
         * search for the value, and if its found it will return true.
         * if it will not find the value in the rows it cought it will never
         * find more rows so the inner loop will run maximum 2 times.(no O(n^2))!
         * also there is another loop that is not depended that checks
         * the last row that might be not checked.
         * @param m the 2d array that the method gets if test returns true on it.
         * @param val the int's searched in the array
         * @return true if the method finds val in the array.
        */
    public static boolean findValTest(int[][] m, int val){
        int n = m.length;
        for (int i=0; i < (n-1); i++){//loop for equaling first in two rows.O(n)
            if (m[i][0] <= val && m[i+1][0] >= val)
            for (int j=0; j < n; j++)//loop that checks 2 rows for val maximum. O(n)
                if (m[i][j] == val || m[i+1][j] == val)
                return true;
        }   //total = O(n) + O(n) = O(n)
        for (int i=0; i < n; i++){
            if (m[n-1][i] == val)
            return true;
        }   //total = O(n)
        return false;
        //total time complexity = O(n) + O(n) = O(n) place complexity = O(1)
    }
/* Questions 2 */
//A.the method what returns the length of the longest even sum
//possible from all the possible sums of all the numbers from the array.
//B.O(n^2). (depended loop inside a loop).
/**C:what:
 * the method counts the number of even and odd numbers in the array
 * the longest even sum is made of the sum of all the even numbers
 * and the maximum even number of odd numbers in the array. that means-if the number of odd numbers
 * is even we add it to the length of the sum. if it is odd-we remove 1 from it and add it to the sum.
 * @param a the 1d array that the method gets
 * @return the length of the longest even sum possible in the array.
 */
public static int what(int[] a){
    int temp = 0;//counts the longest even sum's length in the array
    int even = 0;//counts even numbers in the array
    int odd = 0;//counts odd numbers in the array
    for (int i=0; i < a.length; i++){
        if (a[i]%2 == 0)
        even++;
        else
        odd++;
    }
    if (odd%2 != 0)
    odd--;
    temp = even + odd;
    return temp;
    //D:time complexity: O(n) because there's only one loop that runs on the array.
    //(one dimesional array)
} 
/* Questions 3 */
//A:
/**1.weight:
 * the method checks what's the weight of the digit inserted in the num inserted
 * that means- if the digit is in the "1" place (first time) it returns 1, "2"-10, "3"-100...
 * @param num the positive int number inserted
 * @param digit the digit inserted to find what its number in the parameter num.
 * @return the weight of the digit in the first place in num it sees her.
 */
public static int weight(int num, int digit){
    int place = 1;//number in place 1-1, 2-10, 3-100...
    while (num != 0){
        if (digit == num%10)
        return place;
    num = num/10;
    place *= 10;
   }
   return 0;//number isn't exzisting in the num.
}
/**2.weightRec:
 * the recursive! method checks what's the weight of the digit inserted in the num inserted
 * that means- if the digit is in the "1" place (first time) it returns 1, "2"-10, "3"-100...
 * @param num the positive int number inserted
 * @param digit the digit inserted to find what its number in the parameter num.
 * @return the weight of the digit in the first place in num it sees her.
 */
public static int weightRec(int num, int digit){
    return weightRec(num,digit,1);
}
//overloading weightRec:
private static int weightRec(int num, int digit, int place){
    if (num != 0){
        if (digit == num%10)
        return place;
    }
    else
    return 0;
    return weightRec(num/10,digit,place*10);
}
//B:
/**3.reverse:
 * the method return an int paramter inserted in her in reverse.
 * for example "9456"- input, "6549"-output.
 * @param num the number inserted to be reversed.
 * @return the reveresed number of the parameter num.
 */
public static int reverse(int num){
    int countNumDigits = 0;//number of digits in num
    int pow = 1;//number that represents the size of the digit in num..
    int temp = num;//so num will not get lost..
    int newNum = 0;//opposite returned number.
    while (temp != 0){
        countNumDigits++;
        temp = temp/10;
    }
    while (countNumDigits > 1){//this instead of using Math.pow(x,y)
        countNumDigits--;
        pow *= 10;
    }
    while (num != 0){
        newNum += (num%10) * pow; 
        num = num/10;
        pow = pow/10;
    }
    return newNum;
}
/**4.reverseRec:
 * the recursive! method return an int paramter inserted in her in reverse.
 * for example "9456"- input, "6549"-output.
 * @param num the number inserted to be reversed.
 * @return the reveresed number of the parameter num.
 */
public static int reverseRec(int num){
    return reverseRec(num,reverseRec(num,1),-1);
}
//overloading reverseRec:
private static int reverseRec(int num, int pow){
    if (num > 0 && num < 10)
    return pow;
    return reverseRec(num/10,pow*10);
}
private static int reverseRec(int num, int pow, int reverse){
    if (num == 0)
    return 0;
    return reverseRec(num/10,pow/10,reverse) + (num%10)*pow;
    //reverse parameter isn't doing anything but it is necessary for my overloading.
}
/* Questions 4 */
/**countPaths: 
 * the recursive (backtracking) method returns the number of possible
 * paths from the left upper cell in the 2d array inserted in it to the
 * down right cell. each path countiues cell to cell by the number in it
 * for example the cell has "15"- we can move 1 in the rows and 5 in columns
 * or 1 in columns and 5 in the rows. the path cannot proceed the array borders.
 * @param mat the 2d array inserted.
 * @return the number of paths possible from the left upper cell to the right down cell.
 */
public static int countPaths(int[][] mat){
    return countPaths(mat,0,0);
}
//overloading countPaths:
private static int countPaths(int[][] mat, int x, int y){
    if (mat[x][y] == 0 && (0 != mat[mat.length -1][mat[0].length -1]))
    return 0;//if the cell's int is 0 it cannot proceed.
    if (mat[x][y] == mat[mat.length -1][mat[0].length -1])
    return 1;//checks if we reached the end-right down corner of the array.
    if ((mat[x][y]%10 + x < mat.length) && ((mat[x][y]/10)%10 + y < mat[0].length)
    && (mat[x][y]%10 + y < mat[0].length) && ((mat[x][y]/10)%10 + x < mat.length))
    return countPaths(mat,x + mat[x][y]%10,y + (mat[x][y]/10)%10) +
           countPaths(mat,x + (mat[x][y]/10)%10,y + mat[x][y]%10);//when both options can work.
    if ((mat[x][y]%10 + x < mat.length) && ((mat[x][y]/10)%10 + y < mat[0].length))
    return countPaths(mat,x + mat[x][y]%10,y + (mat[x][y]/10)%10);//if option I works.
    if ((mat[x][y]%10 + y < mat[0].length) && ((mat[x][y]/10)%10 + x < mat.length))
    return countPaths(mat,x + (mat[x][y]/10)%10,y + mat[x][y]%10);//if option II works.
    return 0;//no paths from this cell.
}
}//class