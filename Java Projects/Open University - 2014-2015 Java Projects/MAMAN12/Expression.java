/*#shadi:
 * grade 17/20
 */

/*#shadi:-3
 * wrong output:
         * 
        Input:
        num1= 5  num2 = -1   num3 = 0
        
        Expected Output: 
        Hello. Please enter three integers:
        The maximum value is obtained by the following expression: 5 + -1 * 0 = 5
        
        Student Output: 
        Hello. Please enter three integers:
        
        FAILED!! - but notice result! - if it is the same it is OK

        
        Input:
        num1= 0  num2 = 5   num3 = -1
        
        Expected Output: 
        Hello. Please enter three integers:
        The maximum value is obtained by the following expression: 0 * (5 + -1) = 0
        
        Student Output: 
        Hello. Please enter three integers:
 */

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

/*#solution
 * import java.util.Scanner;
public class LExpression
{
    public static void main(String[] args)
    {       
        Scanner scan = new Scanner (System.in);
        int num1,num2,num3;
        System.out.println("Hello. Please enter three integers:");
        num1= scan.nextInt();
        num2= scan.nextInt();
        num3= scan.nextInt();

        int max = num1 + num2 * num3;
        if(((num1+num2)*num3)>max)
            max = (num1+num2)*num3;
        if((num1*num2+num3) > max)
            max = num1*num2+num3;
        if((num1*(num2+num3)) > max)
            max = num1*(num2+num3);
        //print the maximum value
        if(max==(num1+num2*num3))
            System.out.println("The maximum value is obtained by the following expression: " + num1 + " + " + num2 + " * " + num3 + " = " + max);
        else if(max==((num1+num2)*num3))
            System.out.println("The maximum value is obtained by the following expression: (" + num1 + " + " + num2 + ") * " + num3 + " = " + max);
        else if(max==(num1*num2+num3))
            System.out.println("The maximum value is obtained by the following expression: " + num1 + " * " + num2 + " + " + num3 + " = " + max);
        else if(max==(num1*(num2+num3)))
            System.out.println("The maximum value is obtained by the following expression: " + num1 + " * (" + num2 + " + " + num3 + ")" + " = " + max);
    }
}

 */