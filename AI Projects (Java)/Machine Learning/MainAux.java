

import java.io.IOException;

/**MainAux that combines all. */
public class MainAux {
	/*this methods will run the program by combining all the algorithms */
	public void makeOutputAndTreeOutput() throws IOException {
		//reading the info:
		FilesReader fr = new FilesReader();
		String[][] train = fr.getTrain();
		String[][] test = fr.getTest();
		double[] accuracyRates = new double[3];
		//KNN algorithm execute:
		LearningAlgorithm knn = new KNN(train);
		String[] outputKNN = knn.getOutputFromTest(test);
		accuracyRates[1] = knn.getAccuracyRate();
		//NaiveBase algorithm execute:
		LearningAlgorithm nb = new NaiveBase(train);
		String[] outputNB = nb.getOutputFromTest(test);
		accuracyRates[2] = nb.getAccuracyRate();
		//DecisionTree algorithm execute:
		LearningAlgorithm dt = new DecisionTree(train);
		String[] outputDT = dt.getOutputFromTest(test);
		accuracyRates[0] = dt.getAccuracyRate();
		//writing the data:
		FilesWriter fw = new FilesWriter(outputDT,outputKNN,outputNB,accuracyRates,outputDT[train[0].length-1]);
		fw.writeFiles();
	}
}
