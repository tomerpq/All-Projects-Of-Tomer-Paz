
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**DT algorithm*/
public class DecisionTree extends LearningAlgorithm{
	/*constructor */
	public DecisionTree(String[][] train){
		super(train);
	}
	@Override
	/*the DT algorithm: */
	public String[] getOutputFromTest(String[][] test){
		String[] rtr = new String[test.length];
		Node tree = makeTree();//make DT as nodes
		rtr[test.length-1] = getTreeString(tree,1);//the stringAsTree to be printed
		for(int i = 1; i < test.length; i++){
			String currClass = test[i][test[i].length-1];
			Node tmp = tree;
			while(!(tmp.getSons().isEmpty())){//we go to the desired leaf ot the tree
				for(int j = 0; j < test[0].length -1; j++){//search for label in our example that equals to the current node of the tree
					if(tmp.getLabelName().equals(test[0][j])){
						String type = new String(test[i][j]);
						for(Node n : tmp.getSons()){//we go down the tree by the type of the label in our example
							if(n.getGotFrom().equals(type)){
								tmp = n;
								break;
							}
						}
						break;
					}
				}
			}
			
			//we got to the leaf. now we get the desired class:
			rtr[i-1] = new String(tmp.getClassType());
			//for accuracy update:
			if(rtr[i-1].equals(currClass)){
				accuracy[1]++;
			}
			accuracy[0]++;
		}
		return rtr;
	}
	/*make DT tree by ID3 algorithm */
	private Node makeTree(){
		String[][] wentTrough = new String[2][train[0].length-1];//types went trough in order
		for(int j = 0; j < wentTrough[0].length; j++){
			wentTrough[0][j] = new String(train[0][j]);//labels
			wentTrough[1][j] = "";//type of label we wentTrough
		}
		return helperMakeTree(wentTrough,true,"","");
	}
	/*helper method for makeTree - loop will be false if we are at leaf*/
	private Node helperMakeTree(String[][] wentTrough,boolean loop,String cameFrom,String leaf){
		Node root;
		boolean needToLoop = loop;
		for(int j = 0; j < wentTrough[0].length; j++){//check if need to loop.
			if(wentTrough[1][j].equals("")){
				break;
			}
			if(j == wentTrough[0].length -1){
				needToLoop = false;
			}
		}
		if(!needToLoop){
			root = new Node("",cameFrom,leaf);
			return root;
		}
		int J = -1;
		double maxGain = -1.0;
		String maxGainLabel = "";
		Map<String,String> types = new HashMap<>();
		List<String> allTypes = new ArrayList<>();
		//go over all possible labels to check gain:
		//go over wentTrough and find the label to continue:
		List<String> ourClasses = new ArrayList<>();
		List<String> numberOfOurClasses = new ArrayList<>();
		for(int j = 0; j < wentTrough[0].length; j++){
			List<String> tmpAllTypes = new ArrayList<>();
			for(int a = 1; a <  train.length; a++){
				String newType = new String(train[a][j]);
					if(!(tmpAllTypes.contains(newType))){
						tmpAllTypes.add(newType);
					}
			}
			Map<String,String> tmpTypes = new HashMap<>();
			if(wentTrough[1][j].equals("")){//we check the label corresponding because we didnt come from him yet
				double gain = 0.0;
				int addedClasses = 0;
				ourClasses = new ArrayList<>();
				numberOfOurClasses = new ArrayList<>();
				for(int a = 1; a < train.length; a++){//for checking entropy without the new label to check
					boolean checkItsClass = true;
					for(int z = 0; z < train[0].length -1; z++){
						if(!(wentTrough[1][z].equals(""))){
							if(!(train[a][z].equals(wentTrough[1][z]))){
								checkItsClass = false;
							}
						}
					}
					if(checkItsClass){
						String newType = new String(train[a][j]);
						if(!(tmpTypes.containsKey(newType))){
							tmpTypes.put(newType,"1");
						}
						else{
							tmpTypes.put(train[a][j],stringPlusPlus(tmpTypes.get(train[a][j])));
						}
						String c = new String(train[a][train[a].length-1]);
						if(addedClasses == 0){
							ourClasses.add(c);
							addedClasses++;
							numberOfOurClasses.add("1");
						}
						else if(addedClasses == 1){
							if(c.equals(ourClasses.get(0))){
								numberOfOurClasses.set(0,stringPlusPlus(numberOfOurClasses.get(0)));
							}
							else{
								ourClasses.add(c);
								addedClasses++;
								numberOfOurClasses.add("1");
							}
						}
						else{
							if(c.equals(ourClasses.get(0))){
								numberOfOurClasses.set(0,stringPlusPlus(numberOfOurClasses.get(0)));
							}
							else{
								numberOfOurClasses.set(1,stringPlusPlus(numberOfOurClasses.get(1)));
							}
						}
					}
				}
				if(addedClasses == 2){
					double all = Double.parseDouble(numberOfOurClasses.get(0)) + Double.parseDouble(numberOfOurClasses.get(1));
					double p = Double.parseDouble(numberOfOurClasses.get(0))/all;
					gain += entropy(p);
				}
				double sumTypes = 0.0;
				for(String type : tmpTypes.keySet()){
					sumTypes += Double.parseDouble(tmpTypes.get(type));
				}
				for(String type : tmpTypes.keySet()){//check entropy with the new types of the label
					addedClasses = 0;
					ourClasses = new ArrayList<>();
					numberOfOurClasses = new ArrayList<>();
					for(int a = 1; a < train.length; a++){//for checking entropy without the new label to check
						boolean checkItsClass = true;
						for(int z = 0; z < train[0].length -1; z++){
							if(!(wentTrough[1][z].equals(""))){
								if(!(train[a][z].equals(wentTrough[1][z]))){
									checkItsClass = false;
								}
							}
							if(z == j){//extra check for the specified type
								if(!(train[a][j].equals(type))){
									checkItsClass = false;
								}
							}
						}
						if(checkItsClass){
							String c = new String(train[a][train[a].length-1]);
							if(addedClasses == 0){
								ourClasses.add(c);
								addedClasses++;
								numberOfOurClasses.add("1");
							}
							else if(addedClasses == 1){
								if(c.equals(ourClasses.get(0))){
									numberOfOurClasses.set(0,stringPlusPlus(numberOfOurClasses.get(0)));
								}
								else{
									ourClasses.add(c);
									addedClasses++;
									numberOfOurClasses.add("1");
								}
							}
							else{
								if(c.equals(ourClasses.get(0))){
									numberOfOurClasses.set(0,stringPlusPlus(numberOfOurClasses.get(0)));
								}
								else{
									numberOfOurClasses.set(1,stringPlusPlus(numberOfOurClasses.get(1)));
								}
							}
						}
					}
					if(addedClasses == 2){
						double all = Double.parseDouble(numberOfOurClasses.get(0)) + Double.parseDouble(numberOfOurClasses.get(1));
						double p = Double.parseDouble(numberOfOurClasses.get(0))/all;
						double pType = (Double.parseDouble(tmpTypes.get(type)))/sumTypes;
						gain -= (pType*entropy(p));
					}
				}//for states from label of outer for
				if(gain > maxGain){
					maxGain = gain;
					maxGainLabel = new String(wentTrough[0][j]);
					types = tmpTypes;
					allTypes = tmpAllTypes;
					J = j;
				}
			}//general if
		}//general for
		//now as we found max gain label in our condition in the tree, we will build the tree:
		root = new Node(maxGainLabel,cameFrom,"");//i guese there will be always a root that is not a class.(label at first)
		//now we take care of sons:
		for(int y = 0; y < allTypes.size(); y++){
			String type = allTypes.get(y);
			int addedClasses = 0;
			List<String> oldOurClasses = new ArrayList<>();
			List<String> oldNumberOfOurClasses = new ArrayList<>();
			for(int t = 0; t < ourClasses.size(); t++){
				oldOurClasses.add(ourClasses.get(t));
			}
			for(int t = 0; t < numberOfOurClasses.size(); t++){
				oldNumberOfOurClasses.add(numberOfOurClasses.get(t));
			}
			ourClasses = new ArrayList<>();
			numberOfOurClasses = new ArrayList<>();
			for(int a = 1; a < train.length; a++){
				boolean checkItsClass = true;
				for(int z = 0; z < train[0].length -1; z++){
					if(!(wentTrough[1][z].equals(""))){
						if(!(train[a][z].equals(wentTrough[1][z]))){
							checkItsClass = false;
						}
					}
					if(z == J){//extra check for the specified type
						if(!(train[a][J].equals(type))){
							checkItsClass = false;
						}
					}
				}
				if(checkItsClass){
					String c = new String(train[a][train[a].length-1]);
					if(addedClasses == 0){
						ourClasses.add(c);
						addedClasses++;
						numberOfOurClasses.add("1");
					}
					else if(addedClasses == 1){
						if(c.equals(ourClasses.get(0))){
							numberOfOurClasses.set(0,stringPlusPlus(numberOfOurClasses.get(0)));
						}
						else{
							ourClasses.add(c);
							addedClasses++;
							numberOfOurClasses.add("1");
						}
					}
					else{
						if(c.equals(ourClasses.get(0))){
							numberOfOurClasses.set(0,stringPlusPlus(numberOfOurClasses.get(0)));
						}
						else{
							numberOfOurClasses.set(1,stringPlusPlus(numberOfOurClasses.get(1)));
						}
					}
				}
			}
			String[][] wentTroughRec = new String[wentTrough.length][wentTrough[0].length];
			for(int q = 0; q < wentTrough.length; q++)
				for(int w = 0; w < wentTrough[0].length; w++)
					wentTroughRec[q][w] = new String(wentTrough[q][w]);
			wentTroughRec[1][J] = new String(type);
			if(addedClasses == 2){
				String s;
				int num1 = Integer.parseInt(numberOfOurClasses.get(0));
				int num2 = Integer.parseInt(numberOfOurClasses.get(1));
				if(num1 > num2)
					s = new String(ourClasses.get(0));
				else if(num1 < num2)
					s = new String(ourClasses.get(1));
				else{
					if(ourClasses.get(0).equals("yes") || ourClasses.get(0).equals("true") || ourClasses.get(0).equals("1") || ourClasses.get(0).equals("T")){
						s = new String(ourClasses.get(0));
					}
					else
						s = new String(ourClasses.get(1));
				}
 				root.getSons().add(helperMakeTree(wentTroughRec,true,type,s));
			}
			else if(addedClasses == 1){
				root.getSons().add(helperMakeTree(wentTroughRec,false,type,new String(ourClasses.get(0))));
			}
			else{
				root.getSons().add(helperMakeTree(wentTroughRec,false,type,leaf));
			}
		}//for states from label of outer for
		return root;
	}
	/*calculates entropy*/
	private double entropy(double p){
		return (-(p*log2(p)) -((1-p)*log2(1-p)));
	}
	/*log2:*/
	private double log2(double d){
		return (Math.log(d)/Math.log(2));
	}
	/*make string that represents the tree from the ID3 tree. to be printed later. */
	private String getTreeString(Node n,int tabNum){
		String rtr = new String("");
		Node tmp = n;
		if(!(tmp.getSons().isEmpty())){//label node
			String label = new String(tmp.getLabelName());
			label = label.concat("=");
			Map<String,Node> map = new HashMap<>();//label typbe to node
			for(Node son : tmp.getSons()){
				map.put(son.getGotFrom(),son);
			}
			String[] sortedLex = new String[map.size()];
			int i = 0;
			for(Node son : tmp.getSons()){
				sortedLex[i] = new String(son.getGotFrom());
				i++;
			}
			Arrays.sort(sortedLex);//sort lexicography
			for(i = 0; i < sortedLex.length; i++){//will go recursively now:
				String fullLabel = new String(label);
				fullLabel = fullLabel.concat(sortedLex[i]);
				if(map.get(sortedLex[i]).getClassType().equals("")){
					String goDown = "\n";
					String tab = "\t";
					for(int z = 0; z < tabNum; z++){
						goDown = goDown.concat(tab);
					}
					String p = "|";
					goDown = goDown.concat(p);
					fullLabel = fullLabel.concat(goDown);
				}
				rtr = rtr.concat(fullLabel);
				rtr = rtr.concat(getTreeString(map.get(sortedLex[i]),tabNum+1));
				if(i+1 < sortedLex.length && tabNum > 1){//if another iteration
					for(int u = 0; u < tabNum-1; u++){
						rtr = rtr.concat("\t");
					}
					rtr = rtr.concat("|");
				}
			
			}
		}
		else{//classification node
			String t = ":";
			t = t.concat(tmp.getClassType());
			rtr = rtr.concat(t);
			rtr = rtr.concat("\n");
		}
		return rtr;
	}
}
