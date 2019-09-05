public class Rectangle
{
 private int _length;
 private int _width;
 private Point _sw;
// constructor
 public Rectangle(int l, int w, Point sw)
 {
 _length = l;
 _width = w;
 _sw = new Point (sw);
 }

}