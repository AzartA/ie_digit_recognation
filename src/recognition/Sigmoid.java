package recognition;

import java.io.Serializable;

public class Sigmoid extends Activation implements Serializable {
	private static final long serialVersionUID = 328779L;
	@Override
	public double func(double x) {
		return 1/(1+ Math.pow(Math.E, -x));
	}

	@Override
	public double deriv(double activatedNeuron) {
		return activatedNeuron*(1-activatedNeuron);
	}

}
