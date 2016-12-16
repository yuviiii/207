package Command;

import driver.JShell;

public class CommandMan extends Command {
	private String command;

	public void execute(String[] args) throws ClassNotFoundException {
		printOnScreen(args);
		printCommandUsage();
	}

	public void usage() {
		System.out.println("Print documentation for CMD");
		System.out.println("	Provides usage information for builtin JShell\n" + "    commands.");
	}

	private void printOnScreen(String[] args) {
		if (args.length == 0) {
			command = "what usage of command do you want?";
		}
	}

	private void printCommandUsage() {
		try {
			String class_name = "jshell.command." + command;
			System.out.println(class_name);
			command.usage();
		} catch (ClassNotFoundException e) {
			System.out.println(command + " is not a JShell command.");
		}
	}

}
