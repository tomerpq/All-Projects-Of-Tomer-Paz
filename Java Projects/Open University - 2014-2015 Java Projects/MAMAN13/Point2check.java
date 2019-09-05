/*#shadi:
 * grade 29/30
 */

/**
 * This class represents a two-dimensional point on a grid in the first quadrant of the Cartezian coordinate system. The x and y coordinate values must be non-negative (positive or zero) integers.
 * @version 25.4.2015
 * @author Tomer Paz
 */
public class Point2 {
    //parameters:
    private double _radius;
    private double _alpha;
    //constructors:
    /**
     * Construct a point. If one of the parameters is negative then it should be initialized to zero.
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Point2(int x, int y){
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        _radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        _alpha = (180 * Math.atan((double)y/(double)x))/Math.PI;
    }

    /**
     * Copy constructor for Points. Construct a point with the same coordinates as other point.
     * @param other The point object from which to construct the new point
     */
    public Point2(Point2 other){
        _radius = other._radius;
        _alpha = other._alpha;
    }
    //Methods:
    /**
     * Return the x coordinate of the point.
     * @return The x coordinate of the point.
     */
    public int getX(){
        return (int)Math.round(_radius * Math.cos((_alpha * Math.PI) / 180));
    }

    /**
     * Return the y coordinate of the point.
     * @return The y coordinate of the point.
     */
    public int getY(){
        return (int)Math.round(_radius * Math.sin((_alpha * Math.PI) / 180));
    }

    /**
     * Set the x coordinate of the point. If a negative number is received then x coordinate does not change.
     * @param x The new x coordinate.
     */
    public void setX(int x){
        if (x >= 0){
            _alpha = (180 * Math.atan((double)getY()/(double)x))/Math.PI;
            _radius = Math.sqrt(Math.pow(x, 2) + Math.pow(getY(), 2));
        }
    }

    /**
     * Set the y coordinate of the point. If a negative number is received then y coordinate does not change.
     * @param y The new y coordinate
     */
    public void setY(int y){
        if (y >= 0){
            _alpha = (180 * Math.atan((double)y/(double)getX()))/Math.PI;
            _radius = Math.sqrt(Math.pow(getX(), 2) + Math.pow(y, 2));
        }
    }

    /**
     * Check if this point equals other point.
     * @param other The point to be compared with this point
     * @return True if this point equals other point
     */
    public boolean equals(Point2 other){
        if (getX() == other.getX() && getY() == other.getY())
            return true;
        return false;
    }

    /**
     * Check if this point is above other point.
     * @param other The point to check if this point is above
     * @return True if this point is above other point
     */
    public boolean isAbove(Point2 other){
        if (getY() > other.getY())
            return true;
        return false;
    }

    /**
     * Check if this point is under other point.
     * @param other The point to check if this point is under
     * @return True if this point is under other point
     */
    public boolean isUnder(Point2 other){
        return other.isAbove(this);
    }

    /**
     * Check if this point is to the left of other point.
     * @param other The point to check if this point is left of
     * @return True if this point is to the left of other point
     */
    public boolean isLeft(Point2 other){
        if (getX() < other.getX())
            return true;
        return false;
    }

    /**
     * Check if this point is to the right of other point.
     * @param other The point to check if this point is right of
     * @return True if this point is to the right of other point
     */
    public boolean isRight(Point2 other){
        return other.isLeft(this);
    }

    /**
     * Calculate the distance between this point and other point.
     * @param other The point to calculate distance from
     * @return The distance between this and other points
     */
    public double distance(Point2 other){
        return Math.sqrt(Math.pow((getY() - other.getY()), 2) + Math.pow((getX() - other.getX()), 2));
    }

    /**
     * Move the x coordinate by dX and the y coordinate by dY. If movement moves the point outside first quadrant then the point will remain at the same place and not move.
     * @param dX The amount to move in the x direction
     * @param dY The amount to move in the y direction
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
        Testing Point2 failed.
     */
    public void move(int dX, int dY){
        int a = getX() + dX;
        int b = getY() + dY;
        if (a >= 0)
            setX(a);
        if (b >= 0)
            setY(b);
    }

    /**
     * Return a string representation of this point.
     * @return String representation of this point
     */
    public String toString(){
        return "(" + getX() + "," + getY() + ")"; 
    }
}//class

/*#solution
 * 
public class LPoint2
{
    private double _radius;
    private double _alpha;

    private static double DEG_IN_1RAD = 360/(2*Math.PI);
    private static double RAD_IN_1DEG = (2*Math.PI)/360;

    public Point2(int x, int y) {
        if (x<0) x=0;
        if (y<0) y=0;

        _radius = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        // _radius = Math.round(_radius*10000)/(double)10000;
       // _alpha = Math.atan((double)y/x) * DEG_IN_1RAD;
        _alpha = Math.atan2(y,x) * DEG_IN_1RAD; 
        // _alpha = Math.round(_alpha*10000)/(double)10000;
    }

    public Point2(Point2 other) {
        if(other != null) {
            _radius = other._radius;
            _alpha = other._alpha;
        }
    }

    public int getX() {
        double alpha = _alpha * RAD_IN_1DEG;
        return (int)Math.round(Math.cos(alpha) * _radius);
    }

    public int getY() {
        double alpha = _alpha * RAD_IN_1DEG;
        return (int)Math.round(Math.sin(alpha) * _radius);
    }

    public void setX (int x) {
        if(x>=0) {
            Point2 p = new Point2(x,getY());
            _radius = p._radius;
            _alpha = p._alpha;
        }

    }

    public void setY (int y) {
        if(y>=0) {
            Point2 p = new Point2(getX(),y);
            _radius = p._radius;
            _alpha = p._alpha;
        }  
    }

    public boolean equals(Point2 other) {
        return _radius == other._radius && _alpha == other._alpha;
    }

    public boolean isAbove(Point2 other) {
        return other!=null && getY() > other.getY();
    }

    public boolean isUnder(Point2 other) {
        return other!=null && other.isAbove(this);
    }

    public boolean isLeft(Point2 other) {
        return other!=null && getX() < other.getX();
    }

    public boolean isRight(Point2 other) {
        return other!=null && other.isLeft(this);
    }

    public double distance(Point2 other) {
        double dist = Math.sqrt(Math.pow(getX() - other.getX(), 2) + Math.pow(getY() - other.getY(), 2));
        return dist;
    }

    public void move(int dx,int dy) {
        int x = getX()+dx;
        int y = getY()+dy;
        if(x>=0 && y>=0){
            Point2 p = new Point2(x,y);
            _radius = p._radius;
            _alpha = p._alpha;
        }
    }

    public String toString(){
        return "(" + getX() + "," + getY() + ")";
    }
}
 */