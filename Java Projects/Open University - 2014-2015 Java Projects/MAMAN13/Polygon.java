/*#shadi:
 * grade 80/80
 */

/**
 * This class represents an one dimesional array of vertices of polygon(max 10)
 * @version 9.5.2015
 * @author Tomer Paz
 */
public class Polygon {
    //Instance variables:
    final int MAX_VERTICES = 10;
    private Point1[] _vertices;
    private int _noOfVertices = 0;
    //Constructors:
    /**
     * construct an array in maximum size (10 vertices)
     */
    public Polygon(){
        _vertices = new Point1[MAX_VERTICES];
    }
    //Methods:
    /**
     * adds vertice to the polygon in the first possible place
     * @param x the x of the vertice
     * @param y the y of the vertice
     * @return true if the add was done and false if the array is full.
     */
    public boolean addVertex(int x, int y){
        for (int k = 0; k < _vertices.length; k++){
            if (_vertices[k] == null){
                _vertices[k] = new Point1(x,y);
                _noOfVertices++;
                return true;
            }
        }
        return false;
    }

    /**
     * returns the vertice with the highest y
     * @return Point1 represting the highest vertice with higehst y.
     */
    public Point1 highestVertex(){
        if (_noOfVertices == 0)
            return null;
        else{
            Point1 highest = new Point1(_vertices[0]);
            for (int k = 1; k < _noOfVertices; k++){
                if ((_vertices[k].isAbove(highest)) == true)
                    highest = new Point1(_vertices[k]);
            }
            return new Point1(highest);
        }
    }
    //private method that put all verticles in that formation :"(x1,y1),(x2,y2)..."
    //that method is put into toString public method.
    private String getAllVertices(){
        //s at the end is the missing string at toString method.
        String s = "";
        for (int k = 0; k < _noOfVertices; k++){
            s = s + "(" + _vertices[k].getX() + "," + _vertices[k].getY() + ")";
            if (k < _noOfVertices - 1)
                s = s + ",";
        }
        return s;
    }

    /**
     * return string represting the vertices in the array. represting the polygon.
     * @return string represting the polygon's vertices.
     */
    public String toString(){
        if (_noOfVertices == 0)
            return "The polygon has 0 vertices.";
        else 
            return "The polygon has " + _noOfVertices + " vertices:" + "\n" + "(" + getAllVertices() + ")";       
    }

    /**
     * return double that represents the perimeter of the poylgon.
     * @return double that represents the perimeter of the poylgon.
     */
    public double calcPerimeter(){
        if (_noOfVertices <= 1)
            return (int)0;
        else {
            //d at the end is the perimeter of the polygon.
            double d = 0.0;
            for (int k = 0; k < _noOfVertices -1; k++){
                d = d + _vertices[k].distance(_vertices[k+1]);
            }
            if (_noOfVertices > 2)
                d = d + _vertices[_noOfVertices -1].distance(_vertices[0]);
            return d;
        }
    }
    //private method that put into calcArea method and calculates the s of the
    //triangle by Heron's formula:
    //the methods get the perimeter as parameters.
    private double sHeron(double a, double b, double c){
        //s1-perimeter of triangle:
        double s1 = (a + b + c)/2.0;
        //st-area of the triangle by Heron formula(before sqrt):
        double st = (s1 * (s1 - a) * (s1 - b) * (s1 - c));
        //area-area of the triangle:
        double area = Math.sqrt(st);
        return area;
    }

    /**
     * return double that represents the area of the polygon by heron formula
     * @return double that represents the area of the polygon by heron formula
     */
    public double calcArea(){
        if (_noOfVertices < 3)
            return (int)0;
        else {
            //s in the end is the area of the polygon.
            //we get it by summing the ares of the triangles buildling it:
            double s = 0.0;
            for (int k = 1; k < _noOfVertices -1; k++){
                s = s + sHeron(
                    _vertices[0].distance(_vertices[k]),//first edge.
                    _vertices[k].distance(_vertices[k+1]),//second edge.
                    _vertices[k+1].distance(_vertices[0]));//third edge.
            }
            return s;
        }
    }

    /**
     * checks if the polygon is bigger in area then the parameter polygon.
     * @param other the other polygon
     * @return true if the current polygon is bigger in the area then the param polygon 
     */
    public boolean isBigger(Polygon other){
        if (calcArea() > other.calcArea())
            return true;
        return false;
    }

    /**
     * finds the place of the point in the array
     * @param p the point
     * @return the place in the array and -1 if the array doesnt have it
     */
    public int findVertex(Point1 p){
        if (p != null){
            for (int k = 0; k < _noOfVertices; k++){
                if (_vertices[k].equals(p))
                    return k;
            }
        }
        return -1;
    }

    /**
     * gets a point as parameter and return the next point in the array
     * @param p the point that the method gets
     * @return the next point and null if p isnt in the array.
     */
    public Point1 getNextVertex(Point1 p){
        if (p != null){
            for (int k = 0; k < _noOfVertices; k ++){
                if (_vertices[k].equals(p) && k != _noOfVertices -1)
                    return new Point1(_vertices[k+1]);
                else if (_vertices[_noOfVertices - 1].equals(p))
                    return new Point1(_vertices[0]);
            }
        }
        return null;
    }

    /**
     * returns the rectangle as polygon that is parralel to x and y
     * and blocks the current polygon
     * @return the rectangle that blocks the polygon as polygon. if the polygon has less then 3 vertices it returns null.
     */
    public Polygon getBoundingBox(){
        //rect is a temp Polygon for rectangle that blocks the existing Polygon
        //rect is parralel to x and y
        Polygon rect = new Polygon();
        if (_noOfVertices < 3)
            return null;
        //4 ints that represents the min, max-x,y of the current Polygon:
        //we will put them in the rectangle after they get the correct value.
        else {
            int minX = _vertices[0].getX();
            int minY = _vertices[0].getY();
            int maxX = _vertices[0].getX();
            int maxY = _vertices[0].getX();
            for (int k = 1; k < _noOfVertices; k++){
                if (minX > _vertices[k].getX())
                    minX = _vertices[k].getX();
                if (maxX < _vertices[k].getX())
                    maxX = _vertices[k].getX();
                if (minY > _vertices[k].getY())
                    minY = _vertices[k].getY();
                if (maxY < _vertices[k].getY())
                    maxY = _vertices[k].getY();
            }
            rect.addVertex(minX,minY);
            rect.addVertex(maxX,minY);
            rect.addVertex(maxX,maxY);
            rect.addVertex(minX,maxY);
        }
        return rect;
    }
}//class

/*#solution
 * 
public class LPolygon
{
    // instance variables 
    private Point1[] _vertices;
    private int _noOfVertices;

    public static final int MAX_NUM_OF_VERTICES = 10;

    public Polygon()
    {
        _vertices = new Point1[MAX_NUM_OF_VERTICES];
        _noOfVertices = 0;
    }

    public boolean addVertex(int x, int y)
    {
        if (_noOfVertices >= MAX_NUM_OF_VERTICES)
            return false;

        _vertices[_noOfVertices++] = new Point1(x,y); 
        return true;
    }

    public Point1 highestVertex()
    {
        if (_noOfVertices<=0) return null;

        Point1 highest = _vertices[0];
        for (int i=1; i<_noOfVertices; i++)
        {
            if (_vertices[i].isAbove(highest))
                highest = _vertices[i];
        }
        return new Point1(highest);
    }

    public String toString()
    {
        if (_noOfVertices==0)
            return "The polygon has 0 vertices.";

        String res = "(" + _vertices[0];
        for (int i=1; i<_noOfVertices; i++)
            res += "," + _vertices[i];
        res += ")";
        return res;
    }

    public double calcPerimeter()
    {
        if (_noOfVertices<=1)
            return 0;

        double perimeter = 0;
        for (int i=0; i < _noOfVertices-1; i++)
        {
            perimeter += _vertices[i].distance(_vertices[i+1]);   
        }
        if (_noOfVertices>2)
            perimeter += _vertices[0].distance(_vertices[_noOfVertices-1]);
        return perimeter;
    }

    public double calcArea()
    {
        if (_noOfVertices<3)
            return 0;

        double area = 0;
        for (int i=1; i < _noOfVertices-1; i++)
        {
            area += triangleArea(_vertices[0],_vertices[i],_vertices[i+1]);   
        }
        return area;
    }

    private double triangleArea(Point1 a, Point1 b, Point1 c) {
        double A = a.distance(b), B = a.distance(c), C = b.distance(c);
        double perimeter = (A + B + C) / 2;
        return Math.sqrt(perimeter * (perimeter - A) * (perimeter - B) * (perimeter - C));
    }

    public int findVertex(Point1 p)
    {
        if (p==null)
            return -1;

        for (int i=0; i < _noOfVertices; i++)
        {
            if (p.equals(_vertices[i]))
                return i;
        }
        return -1;
    }

    public Point1 getNextVertex(Point1 p)
    {
        int pos = findVertex(p);
        if (pos < 0)
            return null;
        if (++pos == _noOfVertices)
            pos = 0;
        return new Point1(_vertices[pos]);
    }

    public Polygon getBoundingBox() {

        if (_noOfVertices <3) {
            return null;
        }

        int L = _vertices[0].getX();
        int R = _vertices[0].getX();
        int U = _vertices[0].getY();
        int D = _vertices[0].getY();

        for (int i=1; i < _noOfVertices; i++) {
            if (_vertices[i].getX() < L) {
                L = _vertices[i].getX();
            } else if (_vertices[i].getX() > R) {
                R = _vertices[i].getX();
            }
            if (_vertices[i].getY() < D) {
                D = _vertices[i].getY();
            } else if (_vertices[i].getY() > U) {
                U = _vertices[i].getY();
            }
        }

        Polygon boundingBox = new Polygon();
        boundingBox.addVertex(L, D);
        boundingBox.addVertex(R, D);
        boundingBox.addVertex(R, U);
        boundingBox.addVertex(L, U);
        return boundingBox;
    }
}

 */