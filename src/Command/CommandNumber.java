package Command;

import Command.CommandHistory;

/**
 * This class represents the command "!number". The !number command will find
 * out the specific command from history and execute it. file or
 * 
 * @author Bojun Wang
 */
public class CommandNumber extends Command {
  /**
   * Execute !number command, will form a new array according to the specific
   * command
   * 
   * @param myInput User's input.
   */
  public static String[] execute(String myInput) {
    String Rcommand;
    StringBuilder r = new StringBuilder();
    // put the user's input into a StringBuilder.
    for (int i = 1; i < myInput.length(); i++) {
      r.append(myInput.charAt(i));
    }
    String R = r.toString();
    // parse the input string to integer.
    int index = Integer.parseInt(R);
    // use CommandHistory class to find the history according to the input.
    Rcommand = CommandHistory.infoArray.get(index - 1);
    String[] Rarray = new String[10];
    Rarray = Rcommand.split(" ");
    return Rarray;
  }

  /**
   * Print the usage of !number command.
   */
  public void printUsage() {
    System.out.println("!number");
    System.out.print("find out the specific history and execute it");
  }
}
