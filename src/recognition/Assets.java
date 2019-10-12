package recognition;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;


public class Assets implements Serializable {
	private static final long serialVersionUID = 328779L;
	
	private transient final Path ZIP_PATH = Paths.get("assets","data.zip"); 
	public int[] inputSample = new int[785];
	public int[][][] trainingSamples = new int [10][][];
	public transient int[][] inputSamples = new int [70000][785];
	private transient int[] countOfInputSamples = new int[10];
	private transient int n=-1;
	protected static final Log LOGGER =Log.getLogger("Assets");
	
	public Assets() {
		//LOGGER.setLevel(Level.INFO);
	}
	
	
	public void fillTrainingSamples () {
		
		LOGGER.t("fillTrainingSamples","Reading...");
		try(FileSystem zipFileSys = FileSystems.newFileSystem(ZIP_PATH, null)) {
			
			for(Path path:zipFileSys.getRootDirectories()) {
				Files.walkFileTree(path, new SimpleFileVisitor <Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						n++;
						Scanner sc = new Scanner (file);
						for(int i = 0; i<785; i++) {
							inputSamples[n][i] = sc.nextInt();
						}
						sc.close();
						countOfInputSamples[inputSamples[n][784]]++;
						return FileVisitResult.CONTINUE;
					}
				});
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		for (int i = 0; i<10; i++) {
			trainingSamples[i] = new int[countOfInputSamples[i]][784];
		}
		int m[] = new int[10];
		for (int n = 0; n<70000;n++) {
			trainingSamples[inputSamples[n][784]][m[inputSamples[n][784]]++] = inputSamples[n].clone();
		}
		saveToF();
	}
	
	public void getinputSample (int fileNumber){
		String fileNum = String.valueOf(fileNumber);
		while(fileNum.length()<5) {
			fileNum="0"+fileNum;
		}
		
		try(FileSystem zipFileSys = FileSystems.newFileSystem(ZIP_PATH, null)) {
			Path path = zipFileSys.getPath("/data/" + fileNum + ".txt");
			Scanner sc = new Scanner (path);
			for(int i = 0; i<785; i++) {
				inputSample[i] = sc.nextInt();
			}
			sc.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
/*	public double[] getinputSample (String fileName){
		double[] inputSample = new double[785];
		try(FileSystem zipFileSys = FileSystems.newFileSystem(ZIP_PATH, null)) {
			Path path = zipFileSys.getPath("/data/" + fileName);
			Scanner sc = new Scanner (path);
			for(int i = 0; i<785; i++) {
				inputSample[i] = sc.nextDouble();
			}
			sc.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		LOGGER.log(Level.FINE, "The imput sample has downloaded.");
		return inputSample;
	}
*/
	
	public double[] getinputSample (String fileName){
		double[] inputSample = new double[785];
        try(FileInputStream fs = new java.io.FileInputStream(fileName)) {
        	Scanner sc = new Scanner (fs);
			for(int i = 0; i<784; i++) {
				inputSample[i] = sc.nextDouble();
			}
			sc.close();
			inputSample[784] = 0.0;
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		LOGGER.d("fillTrainingSamples", "The input sample has downloaded.");
		return inputSample;
	}
	
	
	public void saveToF() {
		 
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("base6.bin"))) {
			out.writeObject(this);
			LOGGER.d("fillTrainingSamples","Saved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	public Assets loadFromF() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("base6.bin"))) {
			Assets as = (Assets)in.readObject();
			LOGGER.d("fillTrainingSamples", "Loaded successfully.");	
			return as;
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public double [][] createTraningSet(int countOfNumberSamples, int startIndex){
		double [][] trainingSet = new double [countOfNumberSamples*10][785];
		for (int d = 0, t=0; d<10;d++) {
			int depthNumber = countOfNumberSamples+startIndex;
			for(int n = startIndex; n<depthNumber;n++,t++) {
				if((depthNumber>this.trainingSamples[d].length) && (n >= this.trainingSamples[d].length-1)) {
					depthNumber = depthNumber-n;
					n=0;
				}
				for(int i = 0; i<784; i++) {
					trainingSet[t][i] = ((double)this.trainingSamples[d][n][i])/255;		// input neuron must be between 0 and 1
				}
				trainingSet[t][784] = (double)this.trainingSamples[d][n][784];
			}
		}
		return trainingSet;
	}
}
