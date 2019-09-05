public class Point1Tester {
    public static void main(String[] args)
    {        
        System.out.println("--- Testing Point1 API ---");
        System.out.println("Test Passed means that method exists in Point1 with correct signature.");
        System.out.println("Test Passed does not mean that the results are correct!!!");        
        
        System.out.println("\nTesting constructor\n\tPoint1 p1 = new Point1(3, 4);");
        Point1 p1 = new Point1(3, 4);
        System.out.println("Passed.");
        
        System.out.println("\nTesting getX() and getY()");
        System.out.println("\tp1.getX() = " + p1.getX() + ", p1.getY() = " + p1.getY());
        System.out.println("Passed.");
        
        System.out.println("\nTesting toString()");
        System.out.println("\tp1.toString() = " + p1.toString());
        System.out.println("Passed.");
        
        System.out.println("\nTesting copy constructor\n\tPoint1 p2 = new Point1(p1);");
        Point1 p2 = new Point1(p1);
        System.out.println("\tp2.toString() = " + p2.toString());
        System.out.println("Passed.");        
        
        System.out.println("\nTesting equals()");
        System.out.println("\tp1.equals(p2) = " + p1.equals(p2));
        System.out.println("Passed.");        
        
        System.out.println("\nTesting setX() and setY()");
        p2.setX(5);
        p2.setY(5);
        System.out.println("\tp2.getX() = " + p2.getX() + ", p2.getY() = " + p2.getY());
        System.out.println("\tp1.equals(p2) = " + p1.equals(p2));
        System.out.println("Passed.");
		
		System.out.println("\nTesting isAbove() and isUnder()");
		System.out.println("\tp1.isAbove(p2) = " + p1.isAbove(p2) + ", p2.isAbove(p1) = " + p2.isAbove(p1));
		System.out.println("\tp1.isUnder(p2) = " + p1.isUnder(p2) + ", p2.isUnder(p1) = " + p2.isUnder(p1));
        System.out.println("Passed.");
        
		System.out.println("\nTesting isLeft() and isRight()");
		System.out.println("\tp1.isLeft(p2) = " + p1.isLeft(p2) + ", p2.isLeft(p1) = " + p2.isLeft(p1));
		System.out.println("\tp1.isRight(p2) = " + p1.isRight(p2) + ", p2.isRight(p1) = " + p2.isRight(p1));		
        System.out.println("Passed.");
        
        System.out.println("\nTesting distance()");
        System.out.println("\tp1.distance(p2) = " + p1.distance(p2) + ", p2.distance(p1) = " + p2.distance(p1));
        System.out.println("Passed.");
        
        System.out.println("\nTesting move()\n\tPoint1 p1.move(-2, 7);");
        p1.move(-2, 7);
        System.out.println("\tp1 = " + p1.toString());
        System.out.println("Passed.");        
    }
}
