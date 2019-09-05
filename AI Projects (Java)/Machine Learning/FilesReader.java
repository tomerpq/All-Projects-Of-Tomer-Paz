

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/** read the info from the file to string[][] arrays that i will use with the algorithms*/
public class FilesReader {
	/* arrays*/
	private String[][] train;
	private String[][] test;
	/* reads the file into the array fields*/
	public FilesReader() throws IOException {
		//train.txt read and saving to string array:
		int iIndex = 0, jIndex = 0,jIndexTmp = 0;
		File trainFile = new File("train.txt");
		BufferedReader br;
		br = new BufferedReader(new FileReader(trainFile));
		String line; 
		while ((line = br.readLine()) != null){
			boolean cont = true;//if its blank line we don't do anything
			Scanner scanner = new Scanner(line);
			String word;
			while(scanner.hasNext()){
				word = scanner.next();
				jIndexTmp++;
				cont = false;
			}
			if(cont){
				continue;
			}
			if(jIndex == 0)
				jIndex = jIndexTmp;
			jIndexTmp = 0;
			scanner.close();
			iIndex++;
		}
		br.close();
		train = new String[iIndex][jIndex];//init
		jIndexTmp = 0;
		iIndex = 0;
		br = new BufferedReader(new FileReader(trainFile));
		while ((line = br.readLine()) != null){
			boolean cont = true;//if its blank line we don't do anything
			Scanner scanner = new Scanner(line);
			String word;
			while(scanner.hasNext()){
				word = scanner.next();
				train[iIndex][jIndexTmp] = new String(word);
				jIndexTmp++;
				cont = false;
			}
			if(cont){
				continue;
			}
			jIndexTmp = 0;
			scanner.close();
			iIndex++;
		}
		br.close();
		//making and reading into test array:
		jIndexTmp = 0;
		iIndex = 0;
		jIndex = 0;
		File testFile = new File("test.txt");
		br = new BufferedReader(new FileReader(testFile));
		while ((line = br.readLine()) != null){
			boolean cont = true;//if its blank line we don't do anything
			Scanner scanner = new Scanner(line);
			String word;
			while(scanner.hasNext()){
				word = scanner.next();
				jIndexTmp++;
				cont = false;
			}
			if(cont){
				continue;
			}
			if(jIndex == 0)//so we dont initalize jIndex for nothing and waste time.
				jIndex = jIndexTmp;
			jIndexTmp = 0;
			scanner.close();
			iIndex++;
		}
		br.close();
		test = new String[iIndex][jIndex];//
		jIndexTmp = 0;
		iIndex = 0;
		br = new BufferedReader(new FileReader(testFile));
		while ((line = br.readLine()) != null){
			boolean cont = true;//if its blank line we don't do anything
			Scanner scanner = new Scanner(line);
			String word;
			while(scanner.hasNext()){
				word = scanner.next();
				test[iIndex][jIndexTmp] = new String(word);
				jIndexTmp++;
				cont = false;
			}
			if(cont){
				continue;
			}
			jIndexTmp = 0;
			scanner.close();
			iIndex++;
		}
		br.close();
	}
	/*getters:*/
	public String[][] getTrain() {
		return this.train;
	}
	public String[][] getTest() {
		return this.test;
	}
}
