package recognition;

/**
 * There are necessary matrix mathematics calculations for the project.  
 */

public final class MatrixMath {
	
	public static double [] activateNeuron (double [] vec, double [][] matrix) {
		if(vec.length!=matrix[0].length) {
			throw new IllegalArgumentException("Illegal length of vector");
		}
		double [] resVec = new double [matrix.length];
		for (int i=0;i<matrix.length;i++) {
			for (int j = 0; j<matrix[0].length;j++){
				resVec[i]+= vec[j]*matrix[i][j];
			}
			resVec[i] = sigmoid(resVec[i]);
		}
		return resVec;
		
	}
	
	// function of activation (sigmoid)
	public static double sigmoid (double x) {
	
		return 1/(1+ Math.pow(Math.E, -x));
	}
	
	
	
	public static double derivativeOfSigmoid (double x) {
		
		return sigmoid(x)*(1-sigmoid(x));
	}
	
	
	
	
}
