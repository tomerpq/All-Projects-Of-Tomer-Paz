import java.util.List;
import java.util.*;

public class Point2 {
	Integer x;
	Integer y;
	Point2(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	static int compare(Point2 p1, Point2 p2)
	{
		return p1.x-p2.x;
		
	}
	public String toString(){
		return "(" + x + "," + y + ")"; 
	}
	public static void main(String[] args) {
		
		List<Point2> points = new ArrayList<Point2>();
		points.add(new Point2(10,2));
		points.add(new Point2(5,3));
		points.add(new Point2(7,6));
		
		Collections.sort(points,new Comparator<Point2>(){public int compare(Point2 p1,Point2 p2){return Point2.compare(p1,p2);}});	
		System.out.println(points);
    }
}
