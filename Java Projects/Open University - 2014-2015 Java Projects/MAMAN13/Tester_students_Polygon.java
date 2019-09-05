
public class Tester_students_Polygon {
	
	public static void main(String[] args){
		
		Polygon myPolygon = new Polygon();
		myPolygon.addVertex(1, 1);
		myPolygon.addVertex(1, 2);
		myPolygon.addVertex(2, 2);
		myPolygon.addVertex(2, 1);
		System.out.println(myPolygon);
		
		boolean flag = true;
		
		if (Math.abs(myPolygon.calcArea()-1) > 0.1){
			System.out.println("Check your calcArea function");
			flag = false;
		}
		
		if (Math.abs(myPolygon.calcPerimeter() - 4) > 0.1) {
			flag = false;
			System.out.println("Check your calcPerimeter function");
		}	
		
		if (myPolygon.findVertex(new Point1(1, 1)) != 0){
			flag = false;
			System.out.println("Check your findVertex function");
		}
		
		if (Math.abs(myPolygon.getBoundingBox().calcArea() - 1) > 0.1) {	
			flag = false;
			System.out.println("Check your getBoundingBox function");
		}
			
		if (!(myPolygon.getNextVertex(new Point1(1, 1)).equals(new Point1(1, 2)))){
			flag = false;
			System.out.println("Check your getNextVertex function" + 
					myPolygon.getNextVertex(new Point1(1, 1)) + new Point1(1, 2));
		}
			
		if (!(myPolygon.highestVertex().getY() == 2)){
			flag = false;
			System.out.println("Check the returned type of highestVertex");
		}
			
		System.out.println("Is it a good String representation of a polygon: " + myPolygon + "?");
					
		
		if (flag)
			System.out.println("Any way, a good start");
		
				
	}

}
