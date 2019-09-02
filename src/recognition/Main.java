package recognition;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		final int[][] nWeights = new int[][] {				//weights for each number: first index == number; last neuron of line == bias
			{1,1,1,1,-1,1,1,-1,1,1,-1,1,1,1,1,-1},			//0
			{-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,6},	//1
			{1,1,1,-1,-1,1,1,1,1,1,-1,-1,1,1,1,0},			//2
			{1,1,1,-1,-1,1,1,1,1,-1,-1,1,1,1,1,0},			//3
			{1,-1,1,1,-1,1,1,1,1,-1,-1,1,-1,-1,1,0},		//4	Why 0 pass the test? Mast be 2 (don't pass the test) by logic. 
			{1,1,1,1,-1,-1,1,1,1,-1,-1,1,1,1,1,0},			//5
			{1,1,1,1,-1,-1,1,1,1,1,-1,1,1,1,1,-1},			//6
			{1,1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,4},		//7
			{1,1,1,1,-1,1,1,1,1,1,-1,1,1,1,1,-2},			//8
			{1,1,1,1,-1,1,1,1,1,-1,-1,1,1,1,1,-1}			//9
		 };
		 int[] inNeurons = new int [16];		// input neurons
		 int[] outNeurons = new int[10]; 		// output neurons
		 inNeurons[15] = 1; 					// bias multiplier
		 int bestRes = -100;					
		 int digit = 0;
		 System.out.println("Input grid:");
		 Scanner sc = new Scanner(System.in);
		 for(int l = 0; l<5; l++) {
			  String line = sc.nextLine();
			  for(int i = 0; i<3;i++) {
				  inNeurons[l*3+i] = line.charAt(i) == 'X' ? 1 : -1; 
			  }
		 }
		 sc.close();
		 
		 for (int i=0;i<10;i++) {
				for (int j = 0; j<16;j++){
					outNeurons[i]+= inNeurons[j]*nWeights[i][j];
				}
				
				if(outNeurons[i]>bestRes) {
					bestRes = outNeurons[i];
					digit = i;
				}
		 }
		 
		 System.out.println("This number is " + digit );

	}

}
