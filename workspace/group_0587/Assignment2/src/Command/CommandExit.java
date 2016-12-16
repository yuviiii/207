package Command;

public class CommandExit extends Command {
	public void execute(String[] args) {
		System.exit(0);
	}

	public void usage() {
		System.out.println("exit");
		System.out.println("	Quit the program");
	}

}
