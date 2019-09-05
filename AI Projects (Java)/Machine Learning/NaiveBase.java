
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/** NaiveBase Algorithm*/
public class NaiveBase extends LearningAlgorithm{
	private Map<String,String> numDiffAtrr;//count for each atrribute the number of different types of it.
	/*constructor */
	public NaiveBase(String[][] train){
		super(train);
		//count for each atrribute the number of different types of it.
		this.numDiffAtrr = new HashMap<>();
		for(int j = 0; j < train[0].length -1; j++){
			String atrr = new String(train[0][j]);
			int cnt = 0;
			Set<String> types = new HashSet<>();
			for(int a = 1; a < train.length; a ++){
				String type = new String(train[a][j]);
				if(!(types.contains(type))){
					types.add(type);
					cnt ++;
				}
			}
			this.numDiffAtrr.put(atrr,String.valueOf(cnt));
		}
	}
	/*this is the implementation of the naiveBase Algorithm: */
	@Override
	public String[] getOutputFromTest(String[][] test){
		String[] rtr = new String[test.length-1];
		/* the number of the classes occuring in the train file, and probabilities for calculations*/
		double sumClass1 = Double.parseDouble(classesNum.get(class1));
		double sumClass2 = Double.parseDouble(classesNum.get(class2));
		double sumClass12 = sumClass1 + sumClass2;
		double pClass1 = sumClass1/sumClass12;
		double pClass2 = sumClass2/sumClass12;
		/* going over all examples to check*/
		for(int i = 1; i < test.length; i++){
			/* naiveBase Algorithm :*/
			String currClass = test[i][test[i].length-1];
			//incase there is only one class in train
			if(sumClass2 == 0.0){
				rtr[i-1] = new String(class1);
				if(currClass.equals(rtr[i-1])){
					accuracy[1]++;
				}
				accuracy[0] ++;
				continue;
			}
			double[] class1Mul = new double[test[i].length-1];
			double[] class2Mul = new double[test[i].length-1];
			for(int j = 0; j < test[i].length-1; j++){//go over all attributes
				String attr = test[i][j];
				double numOfAttrClass1 = 0;
				double numOfAttrClass2 = 0;
				for(int a = 1; a < train.length; a++){
					String currentAttr = train[a][j];
					String currentClass = train[a][train[a].length-1];
					if(currentAttr.equals(attr) && currentClass.equals(class1)){
						numOfAttrClass1 += 1.0;
					}
					else if(currentAttr.equals(attr) && currentClass.equals(class2)){
						numOfAttrClass2 += 1.0;
					}
				}
				class1Mul[j] = (numOfAttrClass1 + 1.0) / (sumClass1 + Double.parseDouble(numDiffAtrr.get(train[0][j])));
				class2Mul[j] = (numOfAttrClass2 + 1.0) / (sumClass2 + Double.parseDouble(numDiffAtrr.get(train[0][j])));
			}
			/*calculating: */
			double finalPClass1 = 1.0;
			double finalPClass2 = 1.0;
			for(int c = 0; c < test[i].length-1; c++){
				finalPClass1 *= class1Mul[c];
				finalPClass2 *= class2Mul[c];
			}
			finalPClass1 *= pClass1;
			finalPClass2 *= pClass2;
			if(finalPClass1 > finalPClass2){
				rtr[i-1] = new String(class1);
			}
			else if(finalPClass1 < finalPClass2){
				rtr[i-1] = new String(class2);
			}
			else{//equal prob
				if(sumClass1 > sumClass2){
					rtr[i-1] = new String(class1);
				}
				else if(sumClass1 < sumClass2){
					rtr[i-1] = new String(class2);
				}
				else{//equal general classification
					if(class1.equals("true") || class1.equals("yes") || class1.equals("1") || class1.equals("T") || class1.equals("t")){
						rtr[i-1] = new String(class1);
					}
					else{
						rtr[i-1] = new String(class2);
					}
				}
			}
			if(currClass.equals(rtr[i-1])){
				accuracy[1]++;
			}
			accuracy[0] ++;
		}
		return rtr;
	}
}
