/*#shadi:
 * grade 19/20
 */

/**
 * this class represents a two dimensional array that has the int numbers in the
 * range 0-255 while 0 is white and 255 is black.
 * the Matrix represents an black-white picture.
 * @version 9.5.2015
 * @author Tomer Paz
 */
public class Matrix {
    //Instance variables:
    /*#shadi:
     * should be declared as private
     */
    private int[][] mat;
    //Constructors:
    /**
     * constructs a size1 by size2 Matrix of zeros.
     * @param size1 the number of rows of the array.
     * @param size2 the number of columns of the array.
     */
    public Matrix(int size1, int size2){
        mat = new int[size1][size2];
    }

    /**
     * contructs a Matrix from a two dimensional array; the dimesion as well as the
     * values of this Matrix will be the same as the dimesions and values
     * of the two-dimesional array.
     * @param array the Matrix's array will be the same as "array" given.
     */
    public Matrix(int[][] array){
        //variables for transfering the number of rows and columns from the parameter array to the
        //Matrix we build in this constructor.
        int rows = array.length;
        int cols = array[0].length;
        mat = new int[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                mat[i][j] = array[i][j];
            }
        }
    }
    //Methods:
    /**
     * return a string represting the 2d array
     * @return a string represting the 2d array
     */
    public String toString(){
        //s is the empty string that will build up to the formation
        //asked and will be returned.
        String s = "";
        for (int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[0].length; j++){
                s = s + mat[i][j];
                if (j < mat[0].length - 1)
                    s = s + "\t";
            }
            s = s + "\n";
        }
        return s;
    }

    /**
     * returns the negative image of the picture.(every white cell become black and the opposite.)
     * all the grey shades will be the negative two.
     * @return the negative Matrix array.
     */
    public Matrix makeNegative(){
        //obj m1 that gets the instances of mat negatively.
        //rows and columns from mat.
        int rowMat = mat.length;
        int colMat = mat[0].length;
        Matrix m1 = new Matrix(rowMat,colMat);
        for (int i = 0; i < rowMat; i++){
            for (int j = 0; j < colMat; j++){
                /*#shadi:-1
                 * 255 should be declared as final
                 */
                m1.mat[i][j] = 255 - mat[i][j];
            }
        }
        return m1;
    }

    /**
     * returns the picture that gets from "smoothing the picture"
     * every cell becomes the average of it with his neighbors.
     * @return the Matrix after the "smoothing".
     */
    public Matrix imageFilterAverage(){
        //obj m2 that gets the instances of mat filteraveraged.
        //rows and columns from mat.
        int s; // for the average calculation
        int row = mat.length;
        int col = mat[0].length;
        Matrix m2 = new Matrix(row,col);
        for (int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[0].length; j++){
                s = 0;
                //checking0neighbors
                if (row == 1 && col == 1)
                    s = mat[i][j];
                //check1&2
                if (row == 1 && col > 1){
                    if (j == 0)
                        s = (mat[i][j] + mat[i][j+1])/2;
                    if (j == col -1)
                        s = (mat[i][j] + mat[i][j-1])/2;
                    if (j > 0 && j < col -1)
                        s = (mat[i][j] + mat[i][j-1] + mat[i][j+1])/3;
                }
                if (row > 1 && col == 1){
                    if (i == 0)
                        s = (mat[i][j] + mat[i+1][j])/2;
                    if (i == row -1)
                        s = (mat[i][j] + mat[i-1][j])/2;
                    if (i > 0 && i < row -1)
                        s = (mat[i][j] + mat[i-1][j] + mat[i+1][j])/3;
                }
                //check3&5&8
                if (row > 1 && col > 1){
                    if (i == 0){
                        if (j == 0)
                            s = (mat[i][j] + mat[i][j+1] + mat[i+1][j] + mat[i+1][j+1])/4;
                        if (j == col-1)
                            s = (mat[i][j] + mat[i][j-1] + mat[i+1][j] + mat[i+1][j-1])/4;
                        if (j > 0 && j < col -1)
                            s = (mat[i][j] + mat[i][j+1] + mat[i][j-1] + mat[i+1][j] + mat[i+1][j-1] + mat[i+1][j+1])/6; 
                    }
                    if (i == row -1){
                        if (j == 0)
                            s = (mat[i][j] + mat[i][j+1] + mat[i-1][j] + mat[i-1][j+1])/4;
                        if (j == col-1)
                            s = (mat[i][j] + mat[i][j-1] + mat[i-1][j] + mat[i-1][j-1])/4;
                        if (j > 0 && j < col -1)
                            s = (mat[i][j] + mat[i][j+1] + mat[i][j-1] + mat[i-1][j] + mat[i-1][j-1] + mat[i-1][j+1])/6;
                    }
                    if (j == 0){
                        if (i > 0 && i < row -1)
                            s = (mat[i][j] + mat[i+1][j] + mat[i-1][j] + mat[i][j+1] + mat[i+1][j+1] + mat[i-1][j+1])/6;
                    }
                    if (j == col -1){
                        if (i > 0 && i < row -1)
                            s = (mat[i][j] + mat[i+1][j] + mat[i-1][j] + mat[i][j-1] + mat[i-1][j-1] + mat[i+1][j-1])/6;
                    }
                    if (row > 2 && col > 2){
                        if (i > 0 && i < row -1 && j > 0 && j < col -1)
                            s = (mat[i][j] + mat[i][j+1] + mat[i][j-1] + mat[i-1][j] + mat[i-1][j+1] + mat[i-1][j-1] + mat[i+1][j] + mat[i+1][j+1] + mat[i+1][j-1])/9;
                    }
                }
                m2.mat[i][j] = (int)s;
            }
        }
        return m2;
    }

}//class
/*#solution
 * public class LMatrix
{
    private int[][] _array;

    private static final int MAX_PIXEL = 255;
    
    public Matrix(int size1, int size2)
    {
        _array = new int[size1][size2];    
    }

   
    public Matrix(int[][] array)
    {
        _array = new int[array.length][array[0].length];
        for (int i=0; i<array.length; i++)
            for (int j=0; j < array[0].length; j++)
                _array[i][j] = array[i][j];
    }

  
    public String toString()
    {
        String res = "";
        for (int i=0; i<_array.length; i++)
        {
            for (int j=0; j < _array[0].length-1; j++)
                res += _array[i][j] + "\t";
            res += _array[i][_array[0].length-1] + "\n";
        }
        return res;
    }

    public Matrix makeNegative()
    {
        Matrix neg = new Matrix(_array);
        for (int i=0; i<_array.length; i++)
            for (int j=0; j<_array[0].length; j++)
                neg._array[i][j] = MAX_PIXEL - neg._array[i][j];
        return neg;
    }

    public Matrix imageFilterAverage() 
    {
        Matrix smooth = new Matrix(_array); 

        for (int i=0; i<_array.length; i++)
        {
            for (int j=0; j<_array[0].length; j++)
            {
                smooth._array[i][j] = getSmoothVal(i,j);
            }
        }
        return smooth; 
    }

    private int getSmoothVal(int i, int j)
    {
        int counter = 0;
        int sum = 0;
        for (int i1 = i-1; i1<=i+1; i1++)
        {
            for (int j1 = j-1; j1<=j+1; j1++)
            {
                if (i1>=0 && i1<_array.length && j1>=0 && j1< _array[0].length)
                {
                    counter++;
                    sum += _array[i1][j1];
                }
            }
        }
        return sum/counter;
    }
}

 */