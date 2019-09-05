
public class Tester_students_Matrix {

	public static void main(String[] args)
	{
		boolean flag = true;
		Matrix myMatrix = new Matrix(2,2);
	
		System.out.println("Is it a good String representation of a Matrix:\n" + myMatrix + "?");
		
		if (!(myMatrix.imageFilterAverage().toString().equals(myMatrix.toString())))
		{
			flag = false;
			System.out.println("Check your imageFilterAverage function");
		}
		
		if (!myMatrix.makeNegative().makeNegative().toString().equals(myMatrix.toString()))
		{
			flag = false;
			System.out.println("Check your makeNegative function");
		}
		 
		if (flag)
			System.out.println("Any way, a good start");
	}
	
}
