package ex1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
/*class to combines the inputs from the file reading*/
class AlgoNTable{
	//the fields that keep the data from the files
	private int algo;
	private int size;
	private int[][] startTable;
	/*constructor(trivial)*/
	AlgoNTable(int size,int algo,int[][] startTable){
		this.algo = algo;
		this.size = size;
		this.startTable = startTable;
	}
	/*getters(trivial):*/
	int getAlgo(){
		return this.algo;
	}
	int[][] getStartTable(){
		return this.startTable;
	}
	int getSize() {
		return this.size;
	}
}
/*helper class for main*/
class MainAux {
	/*read from the file the important data as requeseted:*/
	 AlgoNTable ReadFromInput() throws FileNotFoundException {
		 /*getting the data from the file inputs as requested:*/
		 File file = new File("input.txt");
		 Scanner sc = new Scanner(file);
		 /*algorithm to use,size of the board,start board:*/
		 int algo = sc.nextInt();
		 int boardSize = sc.nextInt();
		 int[][] board = new int[boardSize][boardSize];
		 sc.nextLine();
		 String s = sc.nextLine();
		 /** the problem was here. stupid reading from string*/
		 for(int i = 0; i < boardSize; i++) {
			 for(int j = 0; j < boardSize; j++) {
				 if(s.indexOf("-") != -1) {
					 board[i][j] = Integer.parseInt(s.substring(0,s.indexOf("-")));
					 s = s.substring(s.indexOf("-")+1);
				 }
				 else {
					 board[i][j] = Integer.parseInt(s);
				 }
			 }
		 }
		 AlgoNTable algoNtable = new AlgoNTable(boardSize,algo,board);
		 sc.close();
		 return algoNtable;//object with the important info
	 }
	 /*write to file the outputs from the algorithms solving the game as requested:*/
	 void writeToFile(List<String> path,int algoFirstParam,int algoSecondParam) throws IOException {
		 File file = new File("output.txt");
		 FileWriter fw = new FileWriter(file);
		 BufferedWriter bw = new BufferedWriter(fw);
		 for(int i = 0; i < path.size(); i++) {
			 bw.write(path.get(i));
		 }
		 bw.write(' ');
		 bw.write(Integer.toString(algoFirstParam));
		 bw.write(' ');
		 bw.write(Integer.toString(algoSecondParam));
		 bw.close();
	 }
	 
}
