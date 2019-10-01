package recognition;

import java.io.Serializable;
import java.util.logging.Logger;

public abstract class Activation implements Serializable {
	protected static final Logger LOGGER = Logger.getLogger(NeuronNet.class.getName());
	protected NeuronNet net;
	
	abstract public double func(double neuron);
	abstract public double deriv(double neuron);

}
