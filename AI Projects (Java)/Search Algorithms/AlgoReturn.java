package ex1;

import java.util.List;

/*the information returned from the algorithm working on the game to be write on the file*/
class AlgoReturn {
	
	/*fields with the information returned from the algorithm on the game to be saved:*/
	private List<String> path;
	private int algoFirstParam;
	private int algoSecondParam;
	
	/*constructor(trivial)*/
	AlgoReturn(List<String> path,int algoFirstParam,int algoSecondParam){
		this.path = path;
		this.algoFirstParam = algoFirstParam;
		this.algoSecondParam = algoSecondParam;
	}
	/*getters(trivial)*/
	List<String> getPath(){
		return this.path;
	}
	int getAlgoFirstParam() {
		return this.algoFirstParam;
	}
	int getAlgoSecondParam() {
		return this.algoSecondParam;
	}
}
