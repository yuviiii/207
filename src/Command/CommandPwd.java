package Command;

import java.io.FileNotFoundException;
import System.Path;

/**
 * Class CommandPwd represent the pwd command in JShell.
 * 
 * @author Yitian Ding,Xiyan Zhou
 */
public class CommandPwd extends Command {
  /**
   * The execute method takes in an array and return the current working
   * directory in the JShell.
   * 
   * @param arguments An array for the input command.
   * @return Return appropriate error messages when there are errors exit.
   */
  public static String execute(String[] arguments) {
    String errorMsg = "";
    // Determine if it is a valid command line for Pwd command
    if (arguments.length > 1) {
      // Print the error message
      errorMsg = "Command Pwd does not take any arguments";
      // Determine if it is the initial Path
    } else if (Path.getPath().equals("~")) {
      System.out.println("~/");
    } else {
      System.out.println(Path.getPath());
    }
    return errorMsg;
  }

  /**
   * Return the usage for the pwd command which is used for man command.
   */
  public void printUsage() {
    // Print the command name
    System.out.println("PWD");
    // Print its usage
    System.out.println(
        "Print the current working directory.(including the whole path)");
  }

  /**
   * Return the usage for the pwd command which is used for man command.
   */
  public String printStringUsage() {
    // Print the command usage
    return ("PWD" + "\n" + "Print the current working directory.");
  }
}
