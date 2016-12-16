package Command;

import java.util.Hashtable;

import Command.Command;

/**
 * This class represents the command "man". The man command is used to print
 * documentation for CMD.
 * 
 * @author Yitian Ding, Zhifan Wu,Xiyan Zhou,Bojun Wang
 */
public class CommandMan extends Command {
  /**
   * The string command to determine the arguments we input.
   */
  private static String command;
  /**
   * commandTable The HashTable of String names mapped to their command class.
   */
  private static Hashtable<String, Command> commandTable;
  /**
   * The boolean valid to check if it is a valid command name.
   */
  private static boolean valid = true;
  /**
   * The error massage to print.
   */
  private static String errorMsg = "";

  /**
   * The execute method print the documentation for CMD.
   * 
   * @param args The array we inputed in the Jshell.
   * @return Return appropriate error messages when there are errors exit.
   */
  public static String execute(String[] args) {
    errorMsg = "";
    // call the helper methods
    if (args.length == 1) {
      errorMsg = "Man command should take at least 1 argument!";
    } else {
      printOnScreen(args);
      validCommand(args);
      // valid commands can print their usage
      if (valid == true) {
        printCommandUsage();
      }
    }
    return errorMsg;
  }


  /**
   * The method to check if it is a valid command by extract the class name in
   * the commandTable.
   * 
   * @param args The array we inputed in the Jshell.
   * @return
   */
  public static boolean validCommand(String[] args) {
    // build a new Hashtable that contains command names and command classes.
    commandTable = new Hashtable<String, Command>();
    // put keys and values into the Hashtable.
    commandTable.put("cat", new CommandCat());
    commandTable.put("cd", new CommandCd());
    commandTable.put("echo", new CommandEcho());
    commandTable.put("exit", new CommandExit());
    commandTable.put("man", new CommandMan());
    commandTable.put("ls", new CommandLs());
    commandTable.put("mkdir", new CommandMkdir());
    commandTable.put("popd", new CommandPopd());
    commandTable.put("pushd", new CommandPushd());
    commandTable.put("pwd", new CommandPwd());
    commandTable.put("history", new CommandHistory());
    commandTable.put("get", new CommandGet());
    commandTable.put("!", new CommandNumber());
    commandTable.put("mv", new CommandMv());
    commandTable.put("cp", new CommandCp());
    commandTable.put("grep", new CommandGrep());
    // Determine if the commandTable contains this command name
    if (!commandTable.containsKey(args[1])) {
      valid = false;
      errorMsg = "Invalid command for man";
    }
    return valid;
  }

  /**
   * Return the usage for the man command which is used for man command.
   */
  public void printUsage() {
    // Print the command name
    System.out.println("Man");
    // Print its usage
    System.out.println("Print documentation for CMD.");
  }

  /**
   * The method to get the command name from the arguments we input from the
   * Jshell.
   * 
   * @param args The array we inputed in the Jshell.
   */
  private static void printOnScreen(String[] args) {
    if (args.length > 2) {
      errorMsg = "Man command only takes one command";
    }
    // Determine the argument is valid
    else if (args.length == 1) {
      command = "what usage of command do you want?";
      // Extract the command name
    } else {
      command = args[1];
    }
  }

  /**
   * The helper method to get the command class with regard to their name, and
   * print their usage.
   */
  public static void printCommandUsage() {
    // Get the command class with regard to its name
    Command newcommand = Command.getCommand(command);
    // print its usage
    newcommand.printUsage();
  }

  /**
   * Return the usage for the pwd command which is used for man command.
   */
  public String printStringUsage() {
    // Print the command usage
    return ("Man" + "\n" + "Print documentation for CMD.");
  }
}
