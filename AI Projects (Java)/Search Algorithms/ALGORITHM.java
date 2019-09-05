package ex1;

import java.util.ArrayList;
import java.util.List;



/*the Algorithm class that represents the work of the algorithm (choosed from the 3 different algorithms)
 * on the starting table of the game until reaching the goal.
 * the requested information of the algorithm will be returned
 */
class ALGORITHM {
	
	/*start state:*/
	private Node start;
	/*the parameters to write to the file later:*/
	int algoFirstParam;
	int algoSecondParam;
	/*constructor that saves for start state empty moves strinrgs and initalize it*/
	ALGORITHM(int size,int[][] startTable){
		List<String> directionMoved = new ArrayList<String>();
		this.start = new Node(size,startTable,0,directionMoved);
	}
	/*BFS algorithm on the graph to find goal*/
	AlgoReturn BFS() {
		this.algoSecondParam = 0;//just 0 as told
		this.algoFirstParam = 0;//open list size
		List<Node> nodes = new ArrayList<Node>();//queue
		nodes.add(this.start);
		while(!nodes.isEmpty()) {
			this.algoFirstParam++;
			Node n = nodes.get(0);
			nodes.remove(0);
			if(n.getGoal() == 1) {
				return new AlgoReturn(n.getDirectionMoved(),this.algoFirstParam,this.algoSecondParam);
			}
			n.setNexts();
			nodes.addAll(n.getNexts());
		}
		return null;//not supposed to happen but added to finish the function
	}
	/*IDS algorithm on the graph to find goal*/
	AlgoReturn IDS() {
		this.algoFirstParam = 0;//number of nodes in the last iteration
		this.algoSecondParam = 0;//minimal depth to find
		Node n = null;
		while(n == null) {
			this.algoFirstParam = 0;//number of nodes in the last iteration
			n = DLS(this.start,this.algoSecondParam);
			if(n == null) {
				this.algoSecondParam ++;
			}
		}
		return new AlgoReturn(n.getDirectionMoved(),this.algoFirstParam,this.algoSecondParam);
	}
	/*helper recursive method for IDS using changed, increasd depth*/
	private Node DLS(Node n,int depth) {
		this.algoFirstParam ++;
		if(n.getGoal() == 1) {
			return n;
		}
		if(depth == 0) {
			return null;
		}
		n.setNexts();
		for(int i = 0; i < n.getNexts().size(); i++) {
			Node rtr = DLS(n.getNexts().get(i),depth -1);
			if(rtr != null) {
				return rtr;
			}
		}
		return null;
	}
	/* *A algorithm on the graph to find goal*/
	AlgoReturn CohavA() {
		this.algoSecondParam = 0;//f(start) (g(start) + h(start))
		this.algoFirstParam = 0;//open list size
		List<Node> nodes = new ArrayList<Node>();//queue
		nodes.add(this.start);
		while(!nodes.isEmpty()) {
			Node n = nodes.get(0);
			int index = 0;
			int minF = f(n.getTable(),n);
			for(int i = 1; i < nodes.size(); i++) {
				int newF = f(nodes.get(i).getTable(),nodes.get(i));
				if(newF < minF) {
					minF = newF;
					index = i;
					n = nodes.get(i);
				}
			}
			this.algoFirstParam++;
			nodes.remove(index);
			if(n.getGoal() == 1) {
				this.algoSecondParam = f(n.getTable(),n);
				return new AlgoReturn(n.getDirectionMoved(),this.algoFirstParam,this.algoSecondParam);
			}
			n.setNexts();
			nodes.addAll(n.getNexts());
		}
		return null;//not supposed to happen but added to finish the function
	}
	/*f(n):=
	 * g(n) + h(n)
	 */
	private int f(int[][] table,Node node) {
		int time = node.getTime();//g(n)-role time = distance from start because each role == 1
		return time + h(table);
	}
	
	/*h(table):=
	 * heuristic function of sum of manhatan distances(for current table):
	 */
	private int h(int[][] table) {
		int cnt = 0;
		int currentNum;
		int[] indexes;
		int indexX, indexY;
		int size = table.length;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				currentNum = table[i][j];
				indexes = getIndexOfNumber(size,currentNum);
				indexX = indexes[0];
				indexY = indexes[1];
				cnt += Math.abs(indexX - i);
				cnt += Math.abs(indexY - j);
			}
		}
		return cnt;
	}
	/*return the indexes of number in table of goal state */
	private int[] getIndexOfNumber(int size,int number) {
		int[][] goodTable = new int[size][size];
		int[] rtr = new int[2];
		int num = 0;
		int i,j;
		for(i = 0; i < size; i++) {
			for(j = 0; j < size; j++) {
				num++;
				if(i == size -1 && j == size -1) {
					goodTable[i][j] = 0;
				}
				else {
					goodTable[i][j] = num;
				}
			}
		}
		int breakParam = 0;
		for(i = 0; i < size; i++) {
			for(j = 0; j < size; j++) {
				if(goodTable[i][j] == number) {
					rtr[0] = i;
					rtr[1] = j;
					breakParam = 1;
					break;
				}
			}
			if(breakParam == 1)
				break;
		}
		return rtr;
	}
}
