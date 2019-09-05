import java.util.List;
import java.util.*;

public class Point {
	Integer x;
	Integer y;
	Point(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	public String toString(){
		return "(" + x + "," + y + ")"; 
	}
	
	public static void main(String[] args) {
		
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(10,2));
		points.add(new Point(5,3));
		points.add(new Point(7,6));
		
		Collections.sort(points,new Comparator<Point>(){public int compare(Point p1,Point p2){return Integer.compare(p1.x,p2.x);}});		
		System.out.println(points);
    }
}
