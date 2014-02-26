package dd143x;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

public class Print {
	
		private String url = "scoreOutput.txt";
		private File outputFile;
		private FileWriter outStream;
		private BufferedWriter writer;
		
		public Print(){
			outputFile = new File(url);
			try {
				outputFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println("Output file found at:" + outputFile.getAbsolutePath());
			try {
				outStream = new FileWriter(outputFile);
				writer = new BufferedWriter(outStream);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void close(){
			try {
				outStream.close();
				writer.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void writeInt(int input){
			try {
				writer.write(input + "; ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}
