package recognition;


import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	public static void main(String[] args) {
		LogController.configure();
		Logger LOGGER =Logger.getLogger(NeuronNet.class.getName());
		LogController.on();
		NeuronNet wts;
		int i;
		Scanner sc = new Scanner(System.in);
		LOGGER.config("0. Prepare training samples for learning.\n" +
				"1. Learn the network\n" + 
				"2. Guess all numbers\n" + 
				"3. Guess a number from  a text file\nYour choice: ");

		int res = sc.nextInt();
		
		switch (res) {
		case 0:
			LOGGER.fine("Typed: 0");
			Assets as1  = new Assets();
			as1.fillTrainingSamples();
			break;
		case 1:
			LOGGER.fine("Typed: 1");
			int[] neurons;
			LOGGER.config("Enter the sizes of the layers: ");
			String line= "";
			sc.nextLine(); 								// omit the \n which nextInt() leaves.
			line = sc.nextLine();
						
			String[] nums = line.split(" ");
			neurons = new int[nums.length];
			
			for (i=0;i<nums.length; i++) {
				neurons[i] = Integer.valueOf(nums[i]);
			}
			line = line.replace(" ", ", ");
			LOGGER.log(Level.FINE, "New Neuron Net with {0} neurons in the layers.", line);
			int numberOfTraningSets = 10000;
			
			wts = new NeuronNet(neurons);
			LOGGER.config("Choose an activation function:\n" + 
					"1. Sigmoid\n" +
					"2. Hyperbolic tangent\n" + 
					"3. RReLu\nYour choice:");
				switch (sc.nextInt()) {
				case 1:
					LOGGER.fine("Typed: 1");
					wts.setActivation(new Sigmoid());
					break;
				
				case 2:
					LOGGER.fine("Typed: 2");
					wts.setActivation(new HyperTang());
					break;
				
				case 3:
					LOGGER.fine("Typed: 3");
					wts.setActivation(new RReLU(3,8,numberOfTraningSets));
					break;
				}	
				sc.close();
			
			LOGGER.config("Learning...  ");
			//wts.selfLearning(1000, 0, 1, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - 46,12%
			//wts.selfLearning(1000, 0, 30, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - 94,3% 
			//wts.selfLearning(1000, 0, 100, 0.5, 10, 0.15, 0, 0); // 784 6 16 10 - 87,44%
			//wts.selfLearning(1000, 0, 100, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - nnw5c - 98,06%
			//wts.selfLearning(7000, 0, 100, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - 97,21%
			wts.selfLearning(numberOfTraningSets/10, 0, 100, 0.5, 10, 0.15, 0, 0); // 

			LOGGER.config("Done. Saved to the file.");
			break;
		case 2:
			LOGGER.fine("Typed: 2");
			sc.close();
			LOGGER.config("Guessing...");
			wts = NeuronNet.loadFromF();
			LOGGER.log(Level.FINEST, "The Net with {0} neurons, {1} activation function.", new Object[] {wts.getNeurons(), wts.getActivationClassName()});
			int count = 7000; // count of each number [0-9]
			wts.loadInputNumbers(count, 0);
			i=0;
			for(int u = 0; u<count*10;u++) {
				if((int)wts.inputNumbers[u][wts.inputNumbers[0].length-1]==wts.getDigit(wts.inputNumbers[u])) {
					i++;
				}
				
			}
			
			LOGGER.config(String.format("The network prediction accuracy: %1$d/%2$d, %3$.2f%3$%", i, count*10, (double)i*100/(count*10)));
			
			break;
		case 3:	
			LOGGER.fine("Typed: 3");
			LOGGER.config("Enter filename:");
			String file = sc.next();
			sc.close();
			wts = NeuronNet.loadFromF();
			Assets as = new Assets();
			LOGGER.config("The number is " + wts.getDigit(as.getinputSample(file)));
			break;
		default:
			LOGGER.log(Level.FINE, "Typed: {0}", res);
			sc.close();
			LOGGER.config("Unknown comand.");
			break;
		}
	}

}
