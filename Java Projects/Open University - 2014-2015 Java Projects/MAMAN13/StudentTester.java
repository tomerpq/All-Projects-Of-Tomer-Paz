

public class StudentTester {

	public static void main(String[] args) {

		runTestQ1A();
		runTestQ1B();

		runTestQ2();

		runTestQ3A_loop();
		runTestQ3A_rec();

		runTestQ3B_loop();
		runTestQ3B_rec();

		runTestQ4();
	}

	private static void runTestQ1A() {
		System.out.println("------------------------------");
		System.out.println("***   Q1 A - findValWhat   ***");
		System.out.println("------------------------------");
		int[][] a = { { 5, 6, 7, 9 }, { 11, 12, 13, 16 },
				{ 21, 22, 23, 24}, { 32, 36, 37, 39 } };


		boolean stdRes = Ex14.findValWhat(a, 5);
		boolean expRes = true;
		if (stdRes != expRes) {
			System.out.println("Ex14.findValWhat(a, 5) FAILED ");
			System.out.println("expected=" + expRes + " : student="
					+ stdRes + "\n a - array:");
			print(a);
		}


	}

	private static void runTestQ1B() {
		int[][] lines = { { 5, 1, 3, 2 }, { 15, 11, 13, 12 },
				{ 25, 21, 23, 22 }, { 37, 31, 35, 32 } };
		System.out.println("------------------------------");
		System.out.println("***   Q1 B - findValTest   ***");
		System.out.println("------------------------------");

		boolean stdRes = Ex14.findValTest(lines, 5);
		boolean expRes = true;
		if (stdRes != expRes) {
			System.out.println("Ex14.findValTest(lines, 5) FAILED ");
			System.out.println("expected=" + expRes + " : student="
					+ stdRes + "\n lines - array:");
			print(lines);
		}

		stdRes = Ex14.findValTest(lines, 32);
		expRes = true;
		if (stdRes != expRes) {
			System.out.println("Ex14.findValTest(lines, 32) FAILED ");
			System.out.println("expected=" + expRes + " : student="
					+ stdRes + "\n lines - array:");
			print(lines);
		}
	}

	private static void runTestQ2() {
		int[] a1 = { 8, 2, 1, 1, 4 };
		System.out.println("---------------------");
		System.out.println("***   Q2 - what   ***");
		System.out.println("---------------------");
		int stdRes;
		int expRes;
			stdRes = Ex14.what(a1);
			expRes = 5;
			if (stdRes != expRes) {
				System.out.println("Ex14.what(a1) FAILED ");
				System.out.println("expected=" + expRes + " : student="
						+ stdRes + "\n a1 - array:");
				print(a1);
			}
	}

	private static void runTestQ3A_loop() {
		int stdRes;
		int expRes;
		System.out.println("-------------------------------");
		System.out.println("***   Q3 A - weight loops   ***");
		System.out.println("-------------------------------");
		stdRes = Ex14.weight(54321, 3);
		expRes = 100;
		if (stdRes != expRes) {
			System.out.println("Ex14.weight(54321, 3) FAILED ");
			System.out.println("expected=" + expRes + " : student="
					+ stdRes + "\n");
		}
	}

	private static void runTestQ3A_rec() {
		int stdRes;
		int expRes;
		System.out.println("-----------------------------------");
		System.out.println("***   Q3 A - weight recursive   ***");
		System.out.println("-----------------------------------");
		stdRes = Ex14.weightRec(54321, 3);
		expRes = 100;
		if (stdRes != expRes) {
			System.out.println("Ex14.weightRec(54321, 3) FAILED ");
			System.out.println("expected=" + expRes + " : student="
					+ stdRes + "\n");
		}
	}

	private static void runTestQ3B_loop() {
		int stdRes;
		int expRes;
		System.out.println("---------------------------------");
		System.out.println("***   Q3 B - reverse  loops   ***");
		System.out.println("---------------------------------");
		stdRes = Ex14.reverse(12345);
		expRes = 54321;
		if (stdRes != expRes) {
			System.out.println("Ex14.reverse(12345) FAILED ");
			System.out.println("expected=" + expRes + " : student="
					+ stdRes + "\n");
		}
	}

	private static void runTestQ3B_rec() {
		int stdRes;
		int expRes;
		System.out.println("-------------------------------------");
		System.out.println("***   Q3 B - reverse  recursive   ***");
		System.out.println("-------------------------------------");
		stdRes = Ex14.reverseRec(12345);
		expRes = 54321;
		if (stdRes != expRes) {
			System.out.println("Ex14.reverseRec(12345) FAILED ");
			System.out.println("expected=" + expRes + " : student="
					+ stdRes + "\n");
		}
	}


	private static void runTestQ4() {
		System.out.println("---------------------------");
		System.out.println("***   Q4 - countPaths   ***");
		System.out.println("---------------------------");

		int [][] a1 = { { 12, 22, 23, 54 }, { 43, 35, 21, 20 },
				{ 34, 21, 43, 21 }, { 25, 30, 0, 20 }, { 0, 22, 10, 10 },
				{ 20, 13, 3, 45 } };
		int stdRes;
		int expRes;
		stdRes = Ex14.countPaths(a1);
		expRes = 3;
		if (stdRes != expRes) {
			System.out.println("Ex14.countPaths(a1) FAILED ");
			System.out.println("expected=" + expRes + " : student="
					+ stdRes + "\n a1 - array:");
			print(a1);
		}
	}

	private static void print(int[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	private static void print(int[] a) {
		System.out.print("{ " + a[0]);
		for (int i = 1; i < a.length; i++) {
			System.out.print(", " + a[i]);
		}
		System.out.println(" } \n");
	}


}
