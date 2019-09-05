/**
 * This class represents a line segment on the plane parallel to the x axis.
 * @version 24.4.2015
 * @author Tomer Paz
 */
public class Segment{
//parameters:
private Point1 _poLeft;
private Point1 _poRight;
//constructors:
/**
 * Construct a segment. If the segment is not parallel to the x axis then change the y coordinate of the right point according to the y coordinate of the left point
 * @param left The left point of the segment
 * @param right The right point of the segment
 */
public Segment(Point1 left, Point1 right){
    _poLeft = new Point1(left);
    _poRight = new Point1(right);
    _poRight.setY(_poLeft.getY());
}
/**
 * Construct a segment. If the segment is not parallel to the x axis then change the y coordinate of the right point according to the y coordinate of the left point
 * @param leftX The x coordinate of the left point
 * @param leftY The y coordinate of the left point
 * @param rightX The x coordinate of the right point
 * @param rightY The y coordinate of the right point
 */
public Segment(int leftX, int leftY, int rightX, int rightY){
   _poLeft = new Point1(leftX, leftY);
   _poRight = new Point1(rightX, rightY);
   if (leftY == rightY){
    _poLeft.setY(leftY);
    _poRight.setY(rightY);
}
else{
_poLeft.setY(leftY);
_poRight.setY(leftY);
}  
}
/**
 * Copy constructor for Segments. Construct a segment with the same left and right points of other segment.
 * @param other The segment object from which to construct the new segment
 */
public Segment(Segment other){
    _poLeft = new Point1(other._poLeft);
    _poRight = new Point1(other._poRight);
}
//Methods:
/**
 * Return the left point of the segment.
 * @return The left point of the segment.
 */
public Point1 getPoLeft(){
    return _poLeft;
}
/**
 * Return the right point of the segment.
 * @return The right point of the segment.
 */
public Point1 getPoRight(){
    return _poRight;
}
/**
 * Return the segment length.
 * @return The segment length.
 */
public int getLength(){
    return _poRight.getX() - _poLeft.getX();
}
/**
 * Return a string representation of this segment.
 * @return String representation of this segment
 */
public String toString(){
    return "(" + _poLeft.getX() + "," + _poLeft.getY() + ")---(" + _poRight.getX() + "," + _poRight.getY() + ")";
}
/**
 * Check if this segment equals other segment.
 * @param other The segment to be compared with this segment
 * @return True if this segment equals other segment
 */
public boolean equals(Segment other){
    if (_poLeft == other._poLeft && _poRight == other._poRight)
    return true;
    return false;
}
/**
 * Check if this segment is above other segment.
 * @param other The segment to check if this segment is above
 * @return True if this segment is above other segment
 */
public boolean isAbove(Segment other){
    if (_poLeft.getY() > other._poLeft.getY())
    return true;
    return false;
}
/**
 * Check if this segment is under other segment.
 * @param other The segment to check if this segment is under
 * @return True if this segment is under other segment
 */
public boolean isUnder(Segment other){
    return other.isAbove(this);
}
/**
 * Check if this segment is to the left of other segment.
 * @param other The segment to check if this segment is left of
 * @return True if this segment is to the left of other segment
 */
public boolean isLeft(Segment other){
    if (_poRight.getX() < other._poLeft.getX())
    return true;
    return false;
}
/**
 * Check if this segment is to the right of other segment.
 * @param other The segment to check if this segment is right of
 * @return True if this segment is to the right of other segment
 */
public boolean isRight(Segment other){
    return other.isLeft(this);
}
/**
 * Move segment by delta in the x direction.
 * @param delta The amount to move in the x direction
 */
public void moveHorizontal(int delta){
    _poLeft.setX(_poLeft.getX() + delta);
    _poRight.setX(_poRight.getX() + delta);
}
/**
 * Move segment by delta in the y direction.
 * @param delta The amount to move in the y direction
 */
public void moveVertical(int delta){
    _poLeft.setY(_poLeft.getY() + delta);
    _poRight.setY(_poRight.getY() + delta);
}
/**
 * Change (increase or decrease) the segment length by delta.
 * @param delta The amount by which to increase or decrease the segment length
 */
public void changeSize(int delta){
    if ((_poRight.getX() + delta) >= (_poLeft.getX()))
    _poRight.setX(_poRight.getX() + delta);
}
/**
 * Check if point lies on this segment.
 * @param p The point to check if it lies on the segment
 * @return True if the point lies on this segment
 */
public boolean pointOnSegment(Point1 p){
    if ((p.getX() >= _poLeft.getX()) && (p.getX() <= _poRight.getX()))
    return true;
    return false;
}
/**
 * Check if this segment is bigger than other segment.
 * @param other The segment to check if this segment is bigger than
 * @return True this segment is bigger than other segment
 */
public boolean isBigger(Segment other){
    if ((_poRight.getX() - _poLeft.getX()) > (other._poRight.getX() - other._poLeft.getX()))
    return true;
    return false;
}
/**
 * Calculate the length of the overlap between this and other segments.
 * @param other The other segment to calculate the overlap with
 * @return The length of the overlap between this and other segments
 */
public int overlap(Segment other){
    if (_poLeft.getX() <= other._poLeft.getX()){
        if ((_poRight.getX() > other._poLeft.getX()) && (_poRight.getX() <= other._poRight.getX()))
        return _poRight.getX() - other._poLeft.getX();
        else if(_poRight.getX() > other._poRight.getX())
        return other._poRight.getX() - other._poLeft.getX();
        else
        return 0;
    }
    else if ((_poLeft.getX() > other._poLeft.getX()) && (_poLeft.getX() < other._poRight.getX())){
        if (_poRight.getX() <= other._poRight.getX())
        return _poRight.getX() - _poLeft.getX();
        else
        return other._poRight.getX() - _poLeft.getX();
    }
    else
    return 0;
}
/**
 * Calculate the perimeter of the trapeze formed by this and other segments.
 * @param other The other segment
 * @return The perimeter of the trapeze formed by this and other segments
 */
public double trapezePerimeter(Segment other){
    return getLength() + 
    other.getLength() + 
    Math.sqrt(Math.pow((_poLeft.getX() - other._poLeft.getX()), 2) + Math.pow((_poLeft.getY() - other._poLeft.getY()), 2)) +
    Math.sqrt(Math.pow((_poRight.getX() - other._poRight.getX()), 2) + Math.pow((_poRight.getY() - other._poRight.getY()), 2));
}
}//class