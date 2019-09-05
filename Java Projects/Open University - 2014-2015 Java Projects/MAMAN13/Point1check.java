/*#shadi:
 * grade 29/30
 */

/**
 * This class represents a two-dimensional point on a grid in the first quadrant of the Cartezian coordinate system. The x and y coordinate values must be non-negative (positive or zero) integers.
 * @version 25.4.2015
 * @author Tomer Paz
 */
public class Point1 {
    //parameters:
    private int _x; 
    private int _y;
    //constructors:
    /**
     * Construct a point. If one of the parameters is negative then it should be initialized to zero.
     * @param _x The x coordinate
     * @param _y The y coordinate
     */ 

    public Point1(int x, int y){
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        _x = x;
        _y = y;
    }

    /**
     * Copy constructor for Points. Construct a point with the same coordinates as other point.
     * @param other  The point object from which to construct the new point
     */
    public Point1(Point1 other){
        _x = other._x;
        _y = other._y;
    }
    //Methods:
    /** 
     * Return the x coordinate of the point.
     * @return The x coordinate of the point.
     */
    public int getX(){
        return _x;
    }

    /** 
     * Return the y coordinate of the point.
     * @return The y coordinate of the point.
     */
    public int getY(){
        return _y;
    }

    /** 
     * Set the x coordinate of the point. If a negative number is received then x coordinate does not change.
     * @param x The new x coordinate
     */
    public void setX(int num){
        if (num >= 0)
            _x = num;
    }

    /** 
     * Set the y coordinate of the point. If a negative number is received then y coordinate does not change.
     * @param y The new y coordinate
     */
    public void setY(int num){
        if (num >= 0)
            _y = num;
    }

    /** 
     * Return a string representation of this point.
     * @return String representation of this point 
     */
    public String toString(){
        return "(" + _x + "," + _y + ")";
    }

    /** 
     * Check if this point equals other point.
     * @param other The point to be compared with this point
     * @return True if this point equals other point
     */
    public boolean equals(Point1 other){
        if (_x == other._x && _y == other._y)
            return true;
        return false;
    }

    /**
     * Check if this point is above other point.
     * @param other The point to check if this point is above
     * @return True if this point is above other point
     */ 
    public boolean isAbove(Point1 other){
        if (_y > other._y)
            return true;
        return false;
    }

    /**
     * Check if this point is under other point.
     * @param other The point to check if this point is under
     * @return True if this point is under other point
     */
    public boolean isUnder(Point1 other){
        return other.isAbove(this);
    }

    /**
     * Check if this point is to the left of other point.
     * @param other The point to check if this point is left of
     * @return True if this point is to the left of other point
     */
    public boolean isLeft(Point1 other){
        if (_x < other._x)
            return true;
        return false;
    }

    /**
     * Check if this point is to the right of other point.
     * @param other The point to check if this point is right of
     * @return True if this point is to the right of other point
     */
    public boolean isRight(Point1 other){
        return other.isLeft(this);
    }

    /**
     * Calculate the distance between this point and other point.
     * @param other The point to calculate distance from
     * @return The distance between this and other points
     */
    public double distance(Point1 p){
        return Math.sqrt(Math.pow((_y - p._y), 2) + Math.pow((_x - p._x), 2));
    }

    /**
     * Move the x coordinate by dX and the y coordinate by dY. If movement moves the point outside first quadrant then the point will remain at the same place and not move.
     *@param dX The amount to move in the x direction
     *@param dY The amount to move in the y direction
     */
    /*#shadi:-1
     * Failed on move(int dx , int dy)
        Stage40
        Input:x=0 ,y=0
        Input:dx=1 ,dy=-1
        expected result:(0,0)
        Student result:(1,0)
        
        Failed on move(int dx , int dy)
        Stage41
        Input:x=0 ,y=0
        Input:dx=-1 ,dy=1
        expected result:(0,0)
        Student result:(0,1)
        Testing Point1 failed.
     */
    public void move(int dX, int dY){
        if ((_x + dX) >= 0)
            _x = _x + dX;
        if ((_y + dY) >= 0)
            _y = _y + dY;
    }
}//class

/*#solution
 * 
public class LPoint1
{
    private int _x;
    private int _y;

    public Point1(int x, int y){
        if (x<0)
            x=0;
        if (y<0)
            y=0;
        _x=x;
        _y=y;
    }

    public Point1 (Point1 other) {
        _x=other._x;
        _y=other._y;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public void setX (int num) {
        if(num>=0)
            _x=num;
    }

    public void setY (int num) {
        if(num>=0)
            _y=num;
    }

    public String toString(){
        return "(" + _x + "," + _y + ")";
    }

    public boolean equals (Point1 other) {
        return other!=null && _x == other._x && _y==other._y;
    }

    public boolean isAbove (Point1 other)  {
        return other!=null && _y > other._y;
    }

    public boolean isUnder (Point1 other) {
        return other!=null && other.isAbove(this);
    }

    public boolean isLeft(Point1 other) {
        return _x < other._x;
    }

    public boolean isRight(Point1 other) {
        return other.isLeft(this);
    }

    public double distance(Point1 other) {
        double dist = Math.sqrt(Math.pow(_x - other._x, 2) + Math.pow(_y - other._y, 2));
        return dist;
        // return Math.round(dist*1000000)/(double)1000000;
    }

    public void move (int dx, int dy) {
        int x = _x + dx;
        int y = _y + dy;
        if(x>=0 && y >=0) {
            _x=x;
            _y=y;
        }
    }
}


 */