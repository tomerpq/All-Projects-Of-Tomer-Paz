import java.util.Scanner;
/**TravelAgency program calculates the price of a flight for some distance that
 * the user inputs, tell him the price and asks him for his credit card number
 * that will work if it has precisely 6 digits (the first one isn't 0) and the remain of
 * the division of the first 5 numbers of the card by 7 is the sixth digit.
 * if the credit card is valid it purchases him a ticket and tells him. if not-the opposite.
 */
public class TravelAgency
{
    public static void main(String[] args)
    {
        //(x,y) that the user enters from there to (0,0)
        int x, y;
        //creditcard numbers:
        int c;
        //100km or less flight price (dollar)
        final int p = 50;
        //(x,y) distance to (0,0)
        double distance;
        //100kms in distance:
        double km;
        Scanner scan = new Scanner(System.in);
        //reads waypoint from user
        System.out.println("Hello. Please enter your destination in two integers:");
        x = scan.nextInt();
        y = scan.nextInt();
        //calculates the distance from (0,0)
        distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        //calculates price and prints it and calculates km
        km = (Math.ceil(distance / 100)) * (50);
        if(km == 0)
        {
        System.out.println("The price of the flight is:");
        System.out.println(0);
    }
        else if(km <= 100)
        {
        System.out.println("The price of the flight is:");
        System.out.println(p);
    }
        else 
        {
        System.out.println("The price of the flight is:");
        System.out.println((int)km);
    }
    //credit card ask from user
    System.out.println("Please enter your credit card number:");
    c = scan.nextInt();
    //credit card validity check-not more then 7 digits and "0" cannot be the first num.
    //check if first 5 digit number / 7 remains 4
    int cFirst5Numbers = c / 10;
    int cLast6Number = c % 10;
    if((c >= 100000) && (c <= 999999) && ((cFirst5Numbers % 7) == cLast6Number))
    System.out.println("Your credit card is valid. Bon Voyage!");
    else
    System.out.println("Your credit card is not valid. You cannot buy the ticket.");
    }//main
}//class