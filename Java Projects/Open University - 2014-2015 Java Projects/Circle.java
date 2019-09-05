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
        
        