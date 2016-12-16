package Command;

import java.util.ArrayList;
import java.util.Stack;
import Command.CommandCd;
import System.FileDirectory;
import System.Path;

/**
 * This class represents the command "Popd". The Popd command removes the top
 * most directory from the directory stack and makes it the current working
 * directory.
 * 
 * @author Yitian Ding, Xiyan Zhou
 */
public class CommandPopd extends Command {
  /**
   * The dirStack is a private static instance variable that is a stack of
   * directories.
   */
  static Stack<String> dirStack = FileDirectory.getDirectoryStack();

  /**
   * The execute method removes the top most directory from the directory stack
   * and makes it the current working directory.
   * 
   * @param arguments An array for the input command.
   * @return Return appropriate error messages when there are errors exit.
   */
  public static String execute(String[] arguments) {
    String errorMsg = "";
    // Determine if it is a valid command line for Popd command
    if (arguments.length > 1) {
      // Print the error message
      errorMsg = "Command Popd does not take any arguments";
      // Determine if it is the initial Path
    } else if (dirStack.empty()) {
      errorMsg = "directory stack empty";
    } else {
      // Initial a popd Directory
      String popdDir = dirStack.pop();
      if (popdDir.equals("/")) {
        Path.changePath("~");
      } else {
        // Get the current path array
        ArrayList<String> array = Path.getPathArray();
        // Initial a directory path
        String dirPath = "";
        // Loop the array list to find the current path
        for (int i = 0; i < array.size(); i++) {
          // Initial a new String
          String newArray;
          // Get the current path
          newArray = array.get(i);
          if (newArray.endsWith(popdDir)) {
            // set the path to the current working directory
            dirPath = newArray;
          }
        }
        // Initialize a new String array
        String[] myArgs = new String[2];
        // make a new array for command cd to get into the current working
        // directory
        myArgs[0] = "cd";
        myArgs[1] = dirPath;
        CommandCd.execute(myArgs);
      }
      System.out.print(Path.getPath());
      int i;
      for (i = dirStack.size() - 1; i >= 0; i--) {
        System.out.print(" " + dirStack.elementAt(i));
      }
      System.out.println();
    }
    return errorMsg;
  }


  /**
   * Return the usage for the popd command which is used for man command.
   */
  public void printUsage() {
    // Print the command name
    System.out.println("popd");
    // Print its usage
    System.out
        .println("Remove the entry from the directory stack, and cd into it");
  }



}
