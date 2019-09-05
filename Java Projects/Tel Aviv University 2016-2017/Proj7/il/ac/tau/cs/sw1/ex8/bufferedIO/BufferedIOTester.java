package il.ac.tau.cs.sw1.ex8.bufferedIO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedIOTester {
	public static final String RESOURCES_FOLDER = "resources/hw8/out/";

	public static void main(String[] args) throws IOException{
		String outputFileName = RESOURCES_FOLDER + "rocky1_out.txt";
		String outString1 = "Now somewhere in the Black mining Hills of Dakota\nThere lived a young boy named Rocky Raccoon,\n"; 
		String outString2 = "And one day his woman";
		String outString3 = " ran off with another guy,\nHit young Rocky in the eye.";
		System.out.println(outString1.length() + " " + outString2.length() + " " + outString3.length() + " " + (95+21+54));
		FileWriter fWriter = new FileWriter(new File(outputFileName));
		IBufferedWriter bW = new MyBufferedWriter(fWriter,12);
		bW.write(outString1);
		bW.write(outString2);
		bW.write(outString3);
		bW.close();
		
		/***
		 * The output file this tester creates should be identical to rocky1_correct.txt
		 * 
		 */
	}
}
