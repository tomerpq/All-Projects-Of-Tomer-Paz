

import java.util.HashMap;
import java.util.Map;

/**this class will have the 5-NN algorithm */
public class KNN extends LearningAlgorithm{
	/* constructor*/
	public KNN(String[][] train){
		super(train);
	}
	/*the 5-nn algorithm. */
	@Override
	public String[] getOutputFromTest(String[][] test){
		String[] rtr = new String[test.length -1];//
		for(int i = 1; i < test.length; i++){
			String currClass = test[i][test[i].length-1];
			int numClass1 = 0;
			int numClass2 = 0;
			int kChecked = 0;//if we get to 5 we break the current (smallest) hammingDistance check and no more checks.
			int hammWanted = 0;
			while(kChecked < 5){//until we reach 5 close ones
				for(int a = 1; a < train.length; a++){
					int hammLength = 0;
					for(int j = 0; j < train[0].length-1; j++){
						if(!(test[i][j].equals(train[a][j])))
							hammLength++;
					}
					if(hammLength == hammWanted){
						if(train[a][train[a].length-1].equals(class1)){
							numClass1 ++;
						}
						else{
							numClass2 ++;
						}
						kChecked ++;
						if(kChecked == 5){
							break;
						}
					}
				}
				hammWanted++;
			}
			if(numClass1 > numClass2){
				rtr[i-1] = new String(class1);
			}
			else{
				rtr[i-1] = new String(class2);
			}
			//for accuracy update:
			if(rtr[i-1].equals(currClass)){
				accuracy[1]++;
			}
			accuracy[0]++;
		}
		return rtr;
	}
}
