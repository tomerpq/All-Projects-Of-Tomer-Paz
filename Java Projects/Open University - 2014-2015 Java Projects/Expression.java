import java.util.Scanner;
/**The class reads 3 integers in an specific order (x then y then z)
 * and then finds the way to get maximum number from one multification and one addition of the
 * numbers that the user put in a specific combination of the multification and addition place
 * int the expression. the integers could be negative or zero two, and that adds combinations of
 * maximum number expressions for various cases.
 */
public class Expression
{
    /**The class uses if statments for the wide range of combinations avaliable,
     * and prints the max number according to that combination
     */
    public static void main(String[] args)
    {
        //useful final:
        final String l = "The maximum value is obtained by the following expression: ";
        //ints for all 4 options and int for "3 integers":
        int x, y, z, a, b, c, d;
        Scanner scan = new Scanner(System.in);
        //reads the 3 integers from user
        System.out.println("Hello. Please enter three integers:");
        x = scan.nextInt();
        y = scan.nextInt();
        z = scan.nextInt();
        System.out.println();
        //options-shortcut
        a = x + (y * z);
        b = (x + y) * z;
        c = (x * y) + z;
        d = x * (y + z);
        //incase all 3 are positive
        if ((x >= 0) && (y >= 0) && (z >= 0)) 
        {
            if ((x >= y) && (x >= z))
                System.out.println(l + x + "*(" + y + "+" + z + ")" + "=" + d);
                else if ((y >= x) && (y >= z))
                {
                    if (x >= z)
                System.out.println(l + x + "*(" + y + "+" + z + ")" + "=" + d);
                else if (z >= x)
                System.out.println(l + "(" + x + "+" + y + ")" + "*" + z + "=" + b);
            }
            else if ((z >= x) && (z >= y))
            System.out.println(l + "(" + x + "+" + y + ")" + "*" + z + "=" + b);
        }
        //if only one is negative
        if ((x < 0) && (y > 0) && (z > 0))
        System.out.println(l + x + "+(" + y + "*" + z + ")" + "=" + a);
        else if ((x > 0) && (y > 0) && (z < 0))
        System.out.println(l + "(" + x + "*" + y + ")" + "+" + z + "=" + c);
        else if ((x > 0) && (y < 0) && (z > 0))
        {
            if (x >= z)
            System.out.println(l + "(" + x + "+" + y + ")" + "*" + z + "=" + b);
            else if (z >= x)
            System.out.println(l + x + "*(" + y + "+" + z + ")" + "=" + d);
        }
        //if two is negative
        if ((x > 0) && (y < 0) && (z < 0))
        System.out.println(l + x + "+(" + y + "*" + z + ")" + "=" + a);
        else if ((x < 0) && (y < 0) && (z > 0))
        System.out.println(l + "(" + x + "*" + y + ")" + "+" + z + "=" + c);
        else if ((x < 0) && (y > 0) && (z < 0))
        {
            if(z <= x)
            System.out.println(l + x + "*(" + y + "+" + z + ")" + "=" + d);
            else if(x <= z)
            System.out.println(l + "(" + x + "+" + y + ")" + "*" + z + "=" + b);
        }
        //if all are negative
        if ((x <= 0) && (y <= 0) && (z <= 0))
        {
            if ((x <= y) && (x <= z))
                System.out.println(l + x + "*(" + y + "+" + z + ")" + "=" + d);
                else if ((y <= x) && (y <= z))
                {
                    if (x <= z)
                System.out.println(l + x + "*(" + y + "+" + z + ")" + "=" + d);
                else if (z <= x)
                System.out.println(l + "(" + x + "+" + y + ")" + "*" + z + "=" + b);
            }
            else if ((z <= x) && (z <= y))
            System.out.println(l + "(" + x + "+" + y + ")" + "*" + z + "=" + b);
        }   
    }//main
}//class