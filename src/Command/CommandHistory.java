package Command;

import java.util.ArrayList;

/**
 * This class represents the command "History".This history command will print
 * out recent commands, one command per line.
 * 
 * @author Bojun Wang
 */
public class CommandHistory extends Command {
  /**
   * 
   */
  public static ArrayList<String> infoArray = new ArrayList<String>();

  /**
   * Whenever user input anything, record it and save it to an array.
   * 
   * @param x The string represent the commands.
   */
  public static void setInfo(String x) {
    infoArray.add(x);
  }

  /**
   * Clear the info in the history array.
   */
  public static void clearInfo() {
    infoArray = new ArrayList<String>();
  }

  /**
   * The execute method print out recent commands, one commmand per line.
   * 
   * @param arg The String we inputed in the Jshell.
   * @return errorMsg Return the error message.
   */
  public static String execute(String[] arg) {
    String errorMsg = "";
    // following case is history without a integer behind it
    if (arg.length == 1) {
      // simple for loop, run through all the information in the
      // infoArray.
      for (int i = 0; i < infoArray.size(); i++) {
        int columnNumber = i + 1;
        System.out.println(columnNumber + "." + infoArray.get(i));
      }
    }
    // following case is history with a integer behind it
    else if (arg.length == 2) {
      if (!isInteger(arg[1])) {
        errorMsg = "numeric argument required";
        return errorMsg;
      }
      int number = Integer.parseInt(arg[1]);
      // the integer can indicate the index we need, and print to the end.

      for (int j = infoArray.size() - number; j < infoArray.size(); j++) {
        int anotherColumnNumber = j + 1;
        System.out.println(anotherColumnNumber + "." + infoArray.get(j));
      }
    } else {
      errorMsg = "History command only takes at most 1 number";
    }
    return errorMsg;
  }

  /**
   * Helper method to define whether a string can represent an integer.
   * 
   * @param arg argument input by user.
   * @return boolean represents that whether arg can represent an integer.
   */
  private static boolean isInteger(String arg) {
    try {
      Integer.parseInt(arg);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  /**
   * Return the usage for the history command which is used for man command.
   */
  public void printUsage() {
    System.out.println("History");
    System.out.println("Print out the history of used command");
  }
}
