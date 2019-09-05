/*#shadi:
 * grade 30/30
 */
import java.util.Scanner;
/**The class reads four integers that represents (x,y) cordinates.
 * the first two numbers represent -(x1,y1), the second-(x2,y2).
 * the first cordinate is the the left upper corner of a rectengale
 * that is parralel to x and y, and the second cordinate is the
 * lower right corner of the same rectangle.
 */
public class Circle 
{
    /**the class calculates the radius, area, perimeter of the incircle
     * and the excircle of the rectenagle by the cordinates discussed
     * above put by the user.
     */
    public static void main(String[] args) 
    {
        //incircle radius
        double radius1;
        //excircle radius
        double radius2;
        //area, perimeter for incircle and excircle
        double area1, area2, perimeter1, perimeter2;
        Scanner scan = new Scanner(System.in);
        System.out.println("This program calculates the ares " + 
            "and the perimeters of the excircle and the incircle " +
            "of a given rectangle ");
        //reads the left up coordinate from the user
        System.out.println("Please enter the two coordinates of the " +
            "left-upper point of the rectangle");
        int leftUpX = scan.nextInt();
        int leftUpY = scan.nextInt();
        //reads the lower right coordinate from the user 
        System.out.println("Please enter the two coordinates of the " +
            "lower-right point of the rectangle");
        int lowerRightX = scan.nextInt();
        int lowerRightY = scan.nextInt();
        //radius, area, perimeter calculation for incircle, excircle
        radius1 = (leftUpY - lowerRightY)/(2.0);
        radius2 = (Math.sqrt(Math.pow((lowerRightX - leftUpX), 2) +
                Math.pow((leftUpY - lowerRightY), 2)))/(2.0);
        area1 = (Math.PI) * (Math.pow(radius1, 2));
        area2 = (Math.PI) * (Math.pow(radius2, 2));
        perimeter1 = 2 * (Math.PI) * (radius1);
        perimeter2 = 2 * (Math.PI) * (radius2);
        //outlet
        System.out.println("Incircle: radius = " + radius1 +
            ", area = " + area1 + ", perimeter = " + perimeter1);
        System.out.println("Excircle: radius = " + radius2 +
            ", area = " + area2 + ", perimeter = " + perimeter2);
    }//main
}//class

/*#solution
 * 
import java.util.Scanner;
public class LCircle
{
    public static void main (String [] args)
    {
        Scanner scan = new Scanner (System.in);
        System.out.println ("This program calculates the areas " +
            "and the perimeters of the excircle and the incircle " +
            "of a given rectangle "); 
        System.out.println ("Please enter the two coordinates of the " +
            "left-upper point of the rectangle");
        int leftUpX = scan.nextInt();
        int leftUpY = scan.nextInt();
        System.out.println ("Please enter the two coordinates of the " +
            "right-lower point of the rectangle");
        int rightDownX = scan.nextInt();
        int rightDownY = scan.nextInt();       

        int diameterSmall =  leftUpY -  rightDownY;
        double radiusSmall = diameterSmall/2.0;
        double areaSmall = Math.PI * Math.pow(radiusSmall,2);
        double perimeterSmall = 2* Math.PI * radiusSmall;

        double diameterBig =  Math.sqrt(Math.pow((leftUpX -  rightDownX),2) + Math.pow((leftUpY -  rightDownY),2));
        double radiusBig = diameterBig/2.0;
        double areaBig = Math.PI * Math.pow(radiusBig,2);
        double perimeterBig = 2* Math.PI * radiusBig;

        System.out.println("Incircle: radius = " + radiusSmall + ", area = " + areaSmall +", perimeter = "+perimeterSmall +
            "\nExcircle: radius = "+radiusBig+", area = "+areaBig+", perimeter = "+perimeterBig);

    } // end of method main
} //end of class Circle

 */