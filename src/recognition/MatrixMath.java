package recognition;

/**
 * There are necessary matrix mathematics calculations for the project.  
 */

public final class MatrixMath {
		
	public static double [] activateNeuron (double [] vec, double [][] matrix, Activation activ) {
		
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
	

	
	
	
	
}
