public class SegmentTester {
    public static void main(String[] args)
    {        
        System.out.println("--- Testing Segment API ---");
        System.out.println("Test Passed means that method exists in Segment with correct signature.");
        System.out.println("Test Passed does not mean that the results are correct!!!");
        
        // segment 1
        int seg1LeftX = 1;
        int seg1LeftY = 1;
        int seg1RightX = 6;
        int seg1RightY = 1;        
        Point1 seg1PoLeft = new Point1(seg1LeftX, seg1LeftY);
        Point1 seg1PoRight = new Point1(seg1RightX, seg1RightY);
        
        // segment 2
        int seg2LeftX = 4;
        int seg2LeftY = 5;
        int seg2RightX = 10;
        int seg2RightY = 5;
        Point1 seg2PoLeft = new Point1(seg2LeftX, seg2LeftY);
        Point1 seg2PoRight = new Point1(seg2RightX, seg2RightY);
        
        int deltaX = 2;
        int deltaY = -3;        
        int delta  = 1;
        
        System.out.println("\nSegment1:");
        System.out.println("seg1LeftX = " + seg1LeftX + ";");
        System.out.println("seg1LeftY = " + seg1LeftY + ";");
        System.out.println("seg1RightX = " + seg1RightX + ";");
        System.out.println("seg1RightY = " + seg1RightY + ";");

        System.out.println("\nSegment2:");
        System.out.println("seg2LeftX = " + seg2LeftX + ";");
        System.out.println("seg2LeftY = " + seg2LeftY + ";");
        System.out.println("seg2RightX = " + seg2RightX + ";");
        System.out.println("seg2RightY = " + seg2RightY + ";"); 
        
        System.out.println("\ndeltaX = " + deltaX + ";"); 
        System.out.println("deltaY = " + deltaY + ";"); 
        System.out.println("delta  = " + delta  + ";"); 
        
        System.out.println("\nTesting constructor 4 parameters");       
        System.out.println("\tSegment seg1 = new Segment(seg1LeftX, seg1LeftY, seg1RightX, seg1RightY);");
        Segment seg1 = new Segment(seg1LeftX, seg1LeftY, seg1RightX, seg1RightY);
        System.out.println("Passed.");
        
        System.out.println("\nTesting constructor 2 Point1 parameters");
        System.out.println("\tSegment seg2 = new Segment(seg2PoLeft, seg2PoRight);");
        Segment seg2 = new Segment(seg2PoLeft, seg2PoRight);
        System.out.println("Passed.");
        
        System.out.println("\nTesting copy constructor");
        System.out.println("\tSegment seg3 = new Segment(seg2);");
        Segment seg3 = new Segment(seg2);
        System.out.println("Passed.");
        
        System.out.println("\nTesting toString");
        System.out.println("\tSegment seg1 = " + seg1);
        System.out.println("\tSegment seg2 = " + seg2);
        System.out.println("\tSegment seg3 = " + seg3);
        System.out.println("Passed.");        
        
        System.out.println("\nTesting getPoLeft and getPoRight");       
        System.out.println("\tseg1.getPoLeft() = " + seg1.getPoLeft() + ", seg1.getPoRight() = " + seg1.getPoRight());
        System.out.println("\tseg2.getPoLeft() = " + seg2.getPoLeft() + ", seg2.getPoRight() = " + seg2.getPoRight());
        System.out.println("Passed.");      
        
        System.out.println("\nTesting getLength");       
        System.out.println("\tseg1.getLength() = " + seg1.getLength() + ", seg2.getLength() = " + seg2.getLength());
        System.out.println("Passed.");      

        System.out.println("\nTesting equals");
        System.out.println("\tseg1.equals(seg2) = " + seg1.equals(seg2));
        System.out.println("Passed.");      
        
        System.out.println("\nTesting isAbove and isUnder");
        System.out.println("\tseg1.isAbove(seg2) = " + seg1.isAbove(seg2) + ", seg2.isAbove(seg1) = " + seg2.isAbove(seg1));
        System.out.println("\tseg1.isUnder(seg2) = " + seg1.isUnder(seg2) + ", seg2.isUnder(seg1) = " + seg2.isUnder(seg1));
        System.out.println("Passed.");      
        
        System.out.println("\nTesting isLeft and isRight");
        System.out.println("\tseg1.isLeft(seg2) = " + seg1.isLeft(seg2) + ", seg2.isLeft(seg1) = " + seg2.isLeft(seg1));
        System.out.println("\tseg1.isRight(seg2) = " + seg1.isRight(seg2) + ", seg2.isRight(seg1) = " + seg2.isRight(seg1));
        System.out.println("Passed.");    
        
        System.out.println("\nTesting moveHorizontal and moveVertical");
        seg3.moveHorizontal(deltaX);
        System.out.println("\tseg3.moveHorizontal(" + deltaX +") = " + seg3);
        seg3.moveVertical(deltaY);        
        System.out.println("\tseg3.moveVertical(" + deltaY +") = " + seg3);
        System.out.println("Passed.");
        
        System.out.println("\nTesting changeSize");
        seg3.changeSize(delta);
        System.out.println("\tseg3.changeSize(" + delta +") = " + seg3);
        System.out.println("Passed.");   
        
        System.out.println("\nTesting pointOnSegment");
        System.out.println("\tSegment seg2 = " + seg2);
        System.out.println("\tseg2.pointOnSegment(" + seg1PoLeft +") = " + seg2.pointOnSegment(seg1PoLeft));
        System.out.println("\tseg2.pointOnSegment(" + seg2PoRight +") = " + seg2.pointOnSegment(seg2PoRight));
        System.out.println("Passed.");   
        
        System.out.println("\nTesting isBigger");
        System.out.println("\tSegment seg1 = " + seg1);
        System.out.println("\tSegment seg2 = " + seg2);
        System.out.println("\tSegment seg3 = " + seg3);
        System.out.println("\tseg1.isBigger(seg2) = " + seg1.isBigger(seg2));
        System.out.println("\tseg3.isBigger(seg1) = " + seg3.isBigger(seg1));
        System.out.println("Passed."); 
        
        System.out.println("\nTesting overlap");
        System.out.println("\tseg1.overlap(seg2) = " + seg1.overlap(seg2));
        System.out.println("Passed."); 
        
        System.out.println("\nTesting trapezePerimeter");
        System.out.println("\tseg1.trapezePerimeter(seg2) = " + seg1.trapezePerimeter(seg2));
        System.out.println("Passed.");         
    }
}
