

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/** write the data into the output file and the tree from DT to another file.*/
public class FilesWriter {
	/*we will save the strings returning from the algorithms as fields*/
	private String[] outputDT;
	private String[] outputKNN;
	private String[] outputNB;
	private double[] accuracyRates;
	private String DTTree;
	/*constructor:*/
	public FilesWriter(String[] outputDT, String[] outputKNN, String[] outputNB, double[] accuracyRates,String DTTree){
		this.outputDT = outputDT;
		this.outputKNN = outputKNN;
		this.outputNB = outputNB;
		this.accuracyRates = accuracyRates;
		this.DTTree = DTTree;
	}
	/* write all the data to the files*/
	public void writeFiles() throws IOException{
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		writer.println("Num\tDT\tKNN\tnaiveBase");
		for(int i = 0; i < outputKNN.length; i++){//DT length is taken in random, the 3 string arrays are in the same length!
			writer.println(i+1 + "\t" + outputDT[i] + "\t" + outputKNN[i] + "\t" + outputNB[i]);
		}
		String[] accuracyString = new String[3];
		for(int i = 0; i < 3; i++){
			if(accuracyRates[i] == 0.0){
				accuracyString[i] = "0";
				continue;
			}
			accuracyString[i] = String.valueOf(accuracyRates[i]);
			if(accuracyString[i].length() <= 4)
				continue;
			accuracyString[i] = String.valueOf(Double.valueOf(new DecimalFormat("#.##").format(accuracyRates[i])));
		}
		writer.print("\t" + accuracyString[0] +"\t" + accuracyString[1] + "\t" + accuracyString[2]);
		writer.close();
		writeDTTree();
	}
	/* helper for writing the tree from dt to file*/
	private void writeDTTree() throws IOException{
		PrintWriter writer = new PrintWriter("output_tree.txt", "UTF-8");
		writer.println(outputDT[outputDT.length-1]);
		writer.close();
		List<String> list = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader("output_tree.txt"));
        String strLine;
        while((strLine=br.readLine())!=null) {
            if (strLine.length()>0){
            	list.add(strLine);
            }
        }
        br.close();
        writer = new PrintWriter("output_tree.txt", "UTF-8");
        for(int i = 0; i < list.size(); i++){
        	writer.println(list.get(i));
        }
		writer.close();
	}
}
