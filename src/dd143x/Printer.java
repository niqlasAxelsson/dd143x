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
		private int sum =0;
		private int best = 0;
		private int worst = 300;
		
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
			System.out.println("medelvärde: "  + sum/1000 + " 1 000 körningar");
			System.out.println("best: " + best);
			System.out.println("worst: " + worst );
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
				sum += input;
				if (input > best){
					best = input;
				}
				if (input < worst){
					worst = input;
				}
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

		public static void printArraybOOL(boolean[] stegarKollade){
			System.out.print("[");
			for (Object j : stegarKollade){
				System.out.print(j.toString() + ", ");
			}
		System.out.println("]");
		}
		
}

