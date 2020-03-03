package recognition;

import java.io.Serializable;

public class RReLU extends Activation implements Serializable {
	private static final long serialVersionUID = 328781L;
	private double maxOfDist = 8;
	private double minOfDist = 3;
	private double mean = 5.5;
	public int numberOfTrainingSets = 1000;
	
	
	
	RReLU(double min, double max, int numberOfTraningSets){
		maxOfDist = max;
		minOfDist = min;
		mean = (max+min)/2; 
		this.numberOfTrainingSets = numberOfTraningSets;
		
	}
	
	@Override
	public double func(double neuron) {
		return this.func(neuron, net.rand);
		//return this.func(neuron, 1/mean);
	}

	private double func(double neuron, double random) {
		if(neuron>=0) {
			return neuron;
		}else {
			return neuron * random;
		}
	}
	
	@Override
	public double deriv(double neuron) {
		return this.deriv(neuron, net.rand);
	}

	
	private double deriv(double neuron, double random) {
		return neuron>=0? 1 : random;
	}

	public double getMaxOfDist() {
		return maxOfDist;
	}

	public double getMinOfDist() {
		return minOfDist;
	}

	public double getMean() {
		return mean;
	}
	

}
