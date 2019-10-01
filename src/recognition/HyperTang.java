package recognition;

import java.io.Serializable;

public class HyperTang extends Activation implements Serializable{
	private static final long serialVersionUID = 328780L;
	@Override
	public double func(double neuron) {
		return Math.tanh(neuron);
	}

	@Override
	public double deriv(double activatedNeuron) {
		return 1-activatedNeuron*activatedNeuron;
	}

}
