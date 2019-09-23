package recognition;


import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	public static void main(String[] args) {
		LogController.configure();
		
		Logger LOGGER =Logger.getLogger(NeuronNet.class.getName());
		
		NeuronNet wts;
		int i;
		LOGGER.finest("Программа стартовала");
		Scanner sc = new Scanner(System.in);
		LOGGER.config("0. Prepare training samples for learning.\n" +
				"1. Learn the network\n" + 
				"2. Guess all numbers\n" + 
				//"3. Guess a number from  a text file\nYour choice: ");
				"5. Выход\nYour choice: ");

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
			String line = sc.nextLine();
			sc.close();
			String[] nums = line.split(" ");
			neurons = new int[nums.length];
			LOGGER.config("Learning...  ");
			for (i=0;i<nums.length; i++) {
				neurons[i] = Integer.valueOf(nums[i]);
			}
			
			wts = new NeuronNet(neurons);
			//wts.selfLearning(1000, 0, 1, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - 46,12%
			wts.selfLearning(1000, 0, 30, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - 94,3% 
			//wts.selfLearning(1000, 0, 100, 0.5, 10, 0.15, 0, 0); // 784 6 16 10 - 87,44%
			//wts.selfLearning(1000, 0, 100, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - nnw5c - 98,06%
			//wts.selfLearning(7000, 0, 100, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - 97,21%

			LOGGER.config("Done. Saved to the file.");
			break;
		case 2:
			LOGGER.fine("Typed: 2");
			sc.close();
			LOGGER.config("Guessing...");
			wts = NeuronNet.loadFromF();
			int count = 700; // count of each number [0-9]
			//wts.loadInputNumbers(7000, 0);
			wts.loadInputNumbers(count, 1200);
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
			LOGGER.finest("Программа закончилась\nСчастливо оставаться!");

		}
	}

}
