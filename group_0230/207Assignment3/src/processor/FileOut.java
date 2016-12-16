package processor;

import java.io.FileWriter;
import java.io.IOException;

public class FileOut {

	private String output;
	private String fileName;
	
	public FileOut(String output, String fileName){
		this.output = output;
		this.fileName = fileName;
	}
	
	public void writeToFile() throws IOException{
		FileWriter writer = new FileWriter(fileName);
		output += "\n";
		writer.write(output);
		writer.flush();
	    writer.close();
	}
}
