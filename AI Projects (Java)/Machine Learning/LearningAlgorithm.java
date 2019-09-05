

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/** this is the father of 3 algorithms, it will learn most of the data for the algorithms*/
public abstract class LearningAlgorithm {
	protected String[][] train;//
	protected double[] accuracy;//
	protected Map<String,String> classesNum;//yes/no to numberOccuring
	protected String class1 = "";
	protected String class2 = "";
	/*constructor for loading the data of the algorithms(the training) */
	public LearningAlgorithm(String[][] train){
		this.train = train;
		accuracy = new double[2];
		accuracy[0] = 0.0;//will represent all predictions
		accuracy[1] = 0.0;//will represent number of good predictions
		/*how much from yes and no: */
		this.classesNum = new HashMap<>();
		this.classesNum.put("","0");//incase there is only one type of class
		int classNum = 0;
		for(int a = 1; a < train.length; a++){
			String classf = train[a][train[a].length-1];
			if(!(this.classesNum.containsKey(classf))){
				this.classesNum.put(classf,"0");
				if(classNum == 0){
					class1 = new String(classf);
					classNum++;
				}
				else if(classNum == 1){
					class2 = new String(classf);
					classNum++;
				}
				else//binary classes are only 2 so quit.
					break;
			}
		}
		for(int i = 1; i < train.length; i++){
			String classType = train[i][train[i].length-1];
			String classTypeNum = this.classesNum.get(classType);
			String classTypeNumPlus = stringPlusPlus(classTypeNum);
			this.classesNum.put(classType,classTypeNumPlus);
		}
	}
	/* increment the number as string*/
	public String stringPlusPlus(String addTo){
		int tmpInt = Integer.parseInt(addTo);
		tmpInt++;
		return String.valueOf(tmpInt);
	}
	/*abstract for the algorithms to implement */
	abstract public String[] getOutputFromTest(String[][] test);
	/*get the accuracy rate(how much of the classes in the examples are as they really need to be) */
	public double getAccuracyRate(){
		return accuracy[1]/accuracy[0];
	}
}
