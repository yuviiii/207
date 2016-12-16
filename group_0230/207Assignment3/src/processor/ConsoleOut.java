package processor;

public class ConsoleOut {

	private String output;

	public ConsoleOut(String output){
		this.output = output;
	}
	
	public void printInConsole(){
		System.out.println(output);
	}
}
