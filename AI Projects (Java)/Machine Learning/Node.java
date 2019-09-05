

import java.util.ArrayList;
import java.util.List;

/**represents label in DecitionTrees(and its label sons)*/
public class Node {
	private List<Node> sons;//sons of a label by DT algo. empty if none
	private String labelName;//name of the label. "" if a leaf
	private String gotFrom;//type of Label we came from. "" if none(root)
	private String classType;//type of class(in the leaf) "" if not leaf
	/*ctor*/
	public Node(String labelName,String gotFrom,String classType){
		sons = new ArrayList<Node>();
		this.labelName = labelName;
		this.gotFrom = gotFrom;
		this.classType = classType;
	}
	/*getters:*/
	public List<Node> getSons(){return sons;}
	public String getLabelName(){return labelName;}
	public String getGotFrom(){return gotFrom;}
	public String getClassType(){return classType;}
	public String toString(){
		return "labelName = " + labelName + " gotFrom = " + gotFrom + " classType = " + classType;
	}
	
}
