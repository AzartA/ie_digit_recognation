package recognition;

public class Sigmoid extends Activation {

	@Override
	public double func(double x) {
		return 1/(1+ Math.pow(Math.E, -x));
	}

	@Override
	public double deriv(double activatedNeuron) {
		return activatedNeuron*(1-activatedNeuron);
	}

}
