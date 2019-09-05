package recognition;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;


/**
 * @version 4.6
 *
 */
public class NeuronNet implements Serializable {

	private static final long serialVersionUID = 328778L;
	private final int LAYERS;
	private final int [] NEURONS_IN_LAYERS;
	private double [][][] weights;
	public double [][][] deltaW;
	public double [][] idealNeurons; 	// non-rectangle matrix!
	
	public double [][] idealInputNeurones = {
			{1,1,1,1,0,1,1,0,1,1,0,1,1,1,1}, //0
			{0,1,0,0,1,0,0,1,0,0,1,0,0,1,0}, //1
			{1,1,1,0,0,1,1,1,1,1,0,0,1,1,1}, //2
			{1,1,1,0,0,1,1,1,1,0,0,1,1,1,1}, //3
			{1,0,1,1,0,1,1,1,1,0,0,1,0,0,1}, //4
			{1,1,1,1,0,0,1,1,1,0,0,1,1,1,1}, //5
			{1,1,1,1,0,0,1,1,1,1,0,1,1,1,1}, //6
			{1,1,1,0,0,1,0,0,1,0,0,1,0,0,1}, //7
			{1,1,1,1,0,1,1,1,1,1,0,1,1,1,1}, //8
			{1,1,1,1,0,1,1,1,1,0,0,1,1,1,1}  //9
			};
	int iInLength = idealInputNeurones.length;
	
	public NeuronNet(int... neuronsInLayers) {
		LAYERS = neuronsInLayers.length;
		NEURONS_IN_LAYERS = neuronsInLayers.clone();
		weights = new double [LAYERS-1][][];
		Random rd = new Random(328778L);
		idealNeurons = new double [LAYERS-1][];
		for (int l=0;l<(LAYERS-1);l++) {
			weights[l] = new double[NEURONS_IN_LAYERS[l+1]][];
			idealNeurons[l] = new double [NEURONS_IN_LAYERS[l+1]];
			for (int i=0;i<NEURONS_IN_LAYERS[l+1];i++) {
				weights[l][i] = new double[NEURONS_IN_LAYERS[l]+1];
				for (int j = 0; j<NEURONS_IN_LAYERS[l]+1;j++){
					weights[l][i][j]= rd.nextGaussian();
				}
			}
		}
	}

	/**
	 * @return	double [][][] new array, not reference
	 */
	public double[][][] getWeights() {
		double[][][] wts = new double [LAYERS-1][][];
		for(int i = 0, l;i<LAYERS-1;i++) {
			l = weights[i].length;
			wts[i] = new double [l][];
			for(int j = 0;j<l;j++) {
				wts[i][j] = weights[i][j].clone();
			}
		}
		return wts;
	}
	
	/**
	 *	Method don't change the array passed as an argument.
	 * @param weights double [][][] array isn't changing in method 
	 */
	public void setWeights(double[][][] weights) {
		for(int i = 0, l;i<LAYERS-1;i++) {
			l = weights[i].length;
			this.weights[i]= new double [l][];
			for(int j = 0;j<l;j++) {
				this.weights[i][j] = weights[i][j].clone();
			}
		}
	}
	
	public void saveToF() {
		 
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("nnw4.bin"))) {
			out.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	   // System.out.println("Saved successfully.");
	}
	
	public void loadFromF() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("nnw4.bin"))) {
			this.setWeights(((NeuronNet)in.readObject()).getWeights());
						
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Loaded successfully.");
	}
	
	public void learnNeuronNet() {
		final double eta = 0.5;
		double [][] neurons = new double[2][];
		
		CountIdealNeurons();
		for(int n =0;n<iInLength;n++) {								// input number
			neurons[1] = idealInputNeurones[n].clone();
			for (int l=0;l<LAYERS-1;l++) { 						//layer
				neurons[0] = Arrays.copyOf(neurons[1], neurons[1].length+1);
				neurons[0][neurons[0].length-1] = 1.0;
				neurons[1] = MatrixMath.activateNeuron(neurons[0], weights[l]);
				for(int i = 0;i<weights[l].length;i++) {
					if (l ==LAYERS-2) idealNeurons[l][i] = (i==n?1:0);
					for(int j = 0; j<weights[l][i].length; j++) {
						deltaW[l][i][j] += eta*neurons[0][j]*(idealNeurons[l][i]-neurons[1][i]);
					}
				}
			}
		}
		recountWeights();	
		
		
	}
	
	private void recountWeights() {
		for (int l=0;l<(weights.length);l++) {
			for (int m=0;m<(weights[l].length);m++) {
				for (int n=0;n<(weights[l][m].length);n++) {
					weights[l][m][n] += deltaW[l][m][n]/iInLength;
				}
			}
		}
	}
	
	private void CountIdealNeurons() {
		for (int l = LAYERS-2; l >=0 ; l--) {			// for each layer back
			for(int n = 0; n<iInLength; n++) {					// for each input number
				if(l == LAYERS-2) {							// for the last layer
					for (int i = 0; i < NEURONS_IN_LAYERS[LAYERS-1]; i++) { 
						idealNeurons[LAYERS-2][i] = i==n?1:0;
					}
				}else {
					for (int i = 0; i < idealNeurons[l].length; i++) {
						for (int j = 0; j < idealNeurons[l+1].length; j++) {
							idealNeurons[l][i] +=idealNeurons[l+1][j]/weights[l+1][j][i]/iInLength;
						}
					}
				}	
			}
			/*for(int m = 0; m<idealNeurons[l].length; m++) {
				idealNeurons[l][m] = MatrixMath.sigmoid(idealNeurons[l][m]);
			}*/
		}
	}
	
	public int takeDigit(double [] inNeurons) {
		int digit = -1;
		double bestRes = -1000.0;
		double [][] neurons = new double[2][]; 	
		neurons[1] = inNeurons.clone();
		for (int l=0;l<LAYERS-1;l++) { 								//layer
			neurons[0] = Arrays.copyOf(neurons[1], neurons[1].length+1);
			neurons[0][neurons[0].length-1] = 1.0;
			neurons[1] = MatrixMath.activateNeuron(neurons[0], weights[l]);
		}
		for (int i=0;i<NEURONS_IN_LAYERS[LAYERS-1];i++) {
			if(neurons[1][i]>bestRes) {
				bestRes = neurons[1][i];
				digit = i;
			}	
		}
			return digit;
	}
	
	public void selfLearning (int iterations) {
		System.out.println("Learning...");
		
		// deltaWeight initialization
		deltaW = new double [LAYERS-1][][];				
		for (int l=0;l<(LAYERS-1);l++) {
			deltaW[l] = new double[NEURONS_IN_LAYERS[l+1]][];
			for (int i=0;i<NEURONS_IN_LAYERS[l+1];i++) {
				deltaW[l][i] = new double[NEURONS_IN_LAYERS[l]+1];
			}
		}
		//---
		double max = -1;
		for(int i = 0;i<iterations;i++) {
			double[][][] oldWts = getWeights();
			learnNeuronNet();
			double dif;
			
			for (int l = 0;l<LAYERS-1;l++)	{
				for (int v = 0;v<NEURONS_IN_LAYERS[l+1];v++)	{
					for (int k = 0; k<NEURONS_IN_LAYERS[l];k++){
						 dif= Math.abs(weights[l][v][k] - oldWts[l][v][k]);
						 if(dif>max) max = dif;
					}
				}
			}
			/*if(max<=0.02) {
				System.out.println("Done "+ i +" iteration.");
				break;		
			}*/
		}
		saveToF();
		System.out.println("Saved to a file.");
		System.out.println(max);
	}

	public void selfLearning () {
		selfLearning (100);
	}
	
	public static void printArray(double [][] array) {
		String a = Arrays.deepToString(array);
		a = a.replace("],", "]\n");
		System.out.println(a);
		System.out.println();
	}
	public static void printArray(double [][][] array) {
		String a = Arrays.deepToString(array);
		a = a.replace("],", "]\n");
		a = a.replace("]]", "]]\n");
		System.out.println(a);
		System.out.println();
	}
	
	public static void main(String[] args) {
		NeuronNet nn = new NeuronNet(15, 12, 12, 10);
		//nn.loadFromF();
		nn.selfLearning(1000);
		System.out.println(nn.takeDigit(new double[]{1.0,1.0,1.0,1.0,0.0,1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,1.0,1.0}));
		
		
	}
}


