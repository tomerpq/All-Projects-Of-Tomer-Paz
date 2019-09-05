package ex1;

import java.io.IOException;


/*user class, main:*/
public class java_ex1 {
	/*main method that combines all classes,and by that solves the input game with requested algorithm and outputs the
	 * requested data:*/
	public static void main(String[] args) throws IOException {
		MainAux m = new MainAux();
		AlgoNTable algoNtable = m.ReadFromInput();//read the data
		int algo = algoNtable.getAlgo();
		int size = algoNtable.getSize();
		int[][] startTable = algoNtable.getStartTable();
		AlgoReturn algoreturn = null;
		ALGORITHM algorithm = new ALGORITHM(size,startTable);
		if(algo == 1) {//IDS will work
			algoreturn = algorithm.IDS();
		}
		else if(algo == 2) {//BFS will work
			algoreturn = algorithm.BFS();
		}
		else {//*A will work
			algoreturn = algorithm.CohavA();
		}
		m.writeToFile(algoreturn.getPath(),algoreturn.getAlgoFirstParam(),algoreturn.getAlgoSecondParam());//save the data
	}
}
