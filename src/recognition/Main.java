package recognition;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		LogController logs = new LogController();
		logs.on();
		int[] neurons = new int[100];
		NeuronNet wts;
		int i;
		Scanner sc = new Scanner(System.in);
		System.out.println("0. Prepare training samples for learning.\n" +
				"1. Learn the network\n" + 
				"2. Guess all numbers\n" + 
				"3. Guess a number from  a text file\nYour choice: ");
		switch (sc.nextInt()) {
		case 0:
			Assets as1  = new Assets();
			as1.fillTrainingSamples();
			break;
		case 1:
			System.out.print("Enter the sizes of the layers: ");
			i = 0;
			while(sc.hasNextInt()) {
				neurons[i++]= sc.nextInt();
			}
			sc.close();
			System.out.println("Learning...  ");
			neurons = Arrays.copyOf(neurons, i);
			wts = new NeuronNet(neurons);
			//wts.selfLearning(1000, 0, 1, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - 46,12%
			wts.selfLearning(1000, 0, 30, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - 94,3% 
			//wts.selfLearning(1000, 0, 100, 0.5, 10, 0.15, 0, 0); // 784 6 16 10 - 87,44%
			//wts.selfLearning(1000, 0, 100, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - nnw5c - 98,06%
			wts.selfLearning(7000, 0, 100, 0.5, 10, 0.15, 0, 0); // 784 16 16 10 - 97,21%

			System.out.println("Done. Saved to the file.");
			break;
		case 2:
			sc.close();
			System.out.println("Guessing...");
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
			System.out.printf("The network prediction accuracy: " + i + "/" + (count*10) + ", %1$.2f%1$1%", (double)i*100/(count*10));
			
			break;
		case 3:	
			System.out.println("Enter filename:");
			String file = sc.next();
			sc.close();
			wts = NeuronNet.loadFromF();
			Assets as = new Assets();
			System.out.println("The number is " + wts.getDigit(as.getinputSample(file)));
			break;
		default:
			System.out.println("Unknown comand.");
		}
	}

}
