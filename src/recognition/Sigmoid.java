package recognition;

public class Sigmoid implements Algorithm {

	@Override
	public double activate(double x) {
		return 1/(1+ Math.pow(Math.E, -x));
	}

	@Override
	public double derivative(double activatedNeuron) {
		return activatedNeuron*(1-activatedNeuron);
	}

}
