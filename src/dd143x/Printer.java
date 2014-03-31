package dd143x;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("unused")
public class Printer {
	
		private String url = "scoreOutput-";
		private File outputFile;
		private FileWriter outStream;
		private BufferedWriter writer;
		private String dateTime;
		
		public Printer(){
			dateTime = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());
			outputFile = new File(url+dateTime +".txt"); //TODO ad in date/time
			try {
				outputFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			System.out.println("Output file found at:" + outputFile.getAbsolutePath());
			try {
				outStream = new FileWriter(outputFile);
				writer = new BufferedWriter(outStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void close(){
			try {
				writer.write("\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				writer.close();
				outStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void writeInt(int input){
			try {
				writer.write(input + ";\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		public static void printArray(int[] array){
			System.out.print("[");
			for (int j : array){
				System.out.print(j + ", ");
			}
		System.out.println("]");
		}
		
}

