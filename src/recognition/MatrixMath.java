package recognition;

/**
 * There are necessary matrix mathematics calculations for the project.  
 */

public final class MatrixMath {
	private static Activation activ;
	private static Cost error;
	private static Regularization reg;
	private static Initialization init;
	
	public Activation set(Activation activ) {
		activ = activ;
	return activ;
	}
	
	public static double [] activateNeuron (double [] vec, double [][] matrix) {
		
		if(vec.length!=matrix[0].length) {
			throw new IllegalArgumentException("Illegal length of vector");
		}
		double [] resVec = new double [matrix.length];
		for (int i=0;i<matrix.length;i++) {
			for (int j = 0; j<matrix[0].length;j++){
				resVec[i]+= vec[j]*matrix[i][j];
			}
			resVec[i] = activ.func(resVec[i]);
		}
		return resVec;
	}
	
	
	public static double getDerivative(double x) {
		return algorithm.derivative(x);
	}
	
	
	
}
