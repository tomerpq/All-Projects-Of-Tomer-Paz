package ex1;

import java.util.List;
import java.util.ArrayList;
/*class representing Graph that builds while the algorithm working on it is on*/
public class Node {
	/* fields: each node is representing the current state of the game*/
	private int[][] board;//the current board of the game
	private int goal = 1;//representing if its goal state (end of the game)
	private int[] emptyPlace;//x,y indexes of the 2d array where the empty spot in the current board
	private List<Node> nexts;//the next moves from that spot
	private int size;//size of the game where the board dimension is (sizeXsize)
	private List<String> directionMoved;//the move we did to get to this position
	private int time;//the number of the move(every move adds 1)
	
	/*node constructor*/
	Node(int size,int[][] board,int time,List<String> directionMoved){
		this.nexts = null;
		this.time = time;
		this.directionMoved = directionMoved;
		this.board = board;
		this.size = size;
		this.emptyPlace = new int[2];
		int i,j;
		int breakParam = 0;
		//checking where is the empty spot:
		for(i = 0; i < size; i++) {
			for(j = 0; j < size; j++) {
				if(board[i][j] == 0) {
					this.emptyPlace[0] = i;
					this.emptyPlace[1] = j;
					breakParam = 1;
					break;
				}
			}
			if(breakParam == 1)
				break;
		}
		int num = 1;
		breakParam = 0;
		//checking if the state is goal state:
		for(i = 0; i < size; i++) {
			for(j = 0; j < size; j++) {
				if(i == size - 1 && j == size - 1) {
					breakParam = 1;
					break;
				}
				if(num != board[i][j]) {
					this.goal = 0;
					breakParam = 1;
					break;
				}
				num++;
			}
			if(breakParam == 1)
				break;
		}
	}
	/*fill the list with the next options to play in the order U->D->L->R play
	 * options are not always avaiable:only if we can move number with direction to the empty place*/
	void setNexts() {
		this.nexts = new ArrayList<Node>();
		//getting the empty place indexes:
		int a = this.emptyPlace[0], b = this.emptyPlace[1];
		int i,j;
		//up option check:
		if(a+1 < this.size) {
			int[][] tab = new int[this.size][this.size];
			for(i = 0; i < this.size; i++)
				for(j = 0; j < this.size; j++)
					tab[i][j] = this.board[i][j];
			tab[a][b] = tab[a+1][b];
			tab[a+1][b] = 0;
			List<String> DM = new ArrayList<String>(this.directionMoved);
			DM.add("U");
			this.nexts.add(new Node(this.size,tab,this.time+1,DM));
		}
		//down option check:
		if(a-1 >= 0) {
			int[][] tab = new int[this.size][this.size];
			for(i = 0; i < this.size; i++)
				for(j = 0; j < this.size; j++)
					tab[i][j] = this.board[i][j];
			tab[a][b] = tab[a-1][b];
			tab[a-1][b] = 0;
			List<String> DM = new ArrayList<String>(this.directionMoved);
			DM.add("D");
			this.nexts.add(new Node(this.size,tab,this.time+1,DM));
			
		}
		//left option check:
		if(b+1 < this.size) {
			int[][] tab = new int[this.size][this.size];
			for(i = 0; i < this.size; i++)
				for(j = 0; j < this.size; j++)
					tab[i][j] = this.board[i][j];
			tab[a][b] = tab[a][b+1];
			tab[a][b+1] = 0;
			List<String> DM = new ArrayList<String>(this.directionMoved);
			DM.add("L");
			this.nexts.add(new Node(this.size,tab,this.time+1,DM));
		}
		//right option check:
		if(b-1 >= 0) {
			int[][] tab = new int[this.size][this.size];
			for(i = 0; i < this.size; i++)
				for(j = 0; j < this.size; j++)
					tab[i][j] = this.board[i][j];
			tab[a][b] = tab[a][b-1];
			tab[a][b-1] = 0;
			List<String> DM = new ArrayList<String>(this.directionMoved);
			DM.add("R");
			this.nexts.add(new Node(this.size,tab,this.time+1,DM));
		}
	}
	/*gets the current table*/
	int[][] getTable(){
		return this.board;
	}
	/*get the next moves avaiable*/
	List<Node> getNexts(){
		return this.nexts;
	}
	/*gets 1 iff this is the goal*/
	int getGoal() {
		return this.goal;
	}
	/*gets the time of the move*/
	int getTime() {
		return this.time;
	}
	/*gets the route of the play moves to this state*/
	List<String> getDirectionMoved() {
		return this.directionMoved;
	}
}
