package Command;

import java.util.ArrayList;
import java.util.Stack;
import System.Path;
import System.FileDirectory;

/**
 * This class represents the command "Pushd". The Pushd command stores the
 * current working directory into memory/stack and changes the current working
 * directory into a specified directory.
 * 
 * @author Yitian Ding
 */
public class CommandPushd extends Command {

  /**
   * The dirStack is a private static instance variable that is a stack of
   * directories.
   */
  private static Stack<String> dirStack = FileDirectory.getDirectoryStack();

  /**
   * The execute method stores the current working directory into memory and
   * changes the current working directory to another.
   * 
   * @param arguments An array for the input command.
   * @return Return appropriate error messages when there are errors exit.
   */
  public static String execute(String[] arguments) {
    String errorMsg = "";
    if (arguments.length < 2) {
      errorMsg = "No other directory";
    } else {
      // Initialize a new path
      String newPath = "";
      // Get the current path array
      String[] curPathArray = (Path.getPath()).split("/");
      // Determine the directory
      if (arguments[1].equals("..")) {
        // Determine the length
        if (curPathArray.length == 1) {
          dirStack.push("~");
        } else {
          // loop the array to get a new path
          for (int i = 0; i < curPathArray.length - 1; i++) {
            // Determine the new path array
            if (i == curPathArray.length - 2) {
              newPath += curPathArray[i];
            } else {
              newPath += curPathArray[i] + "/";
            }
          }
          // Push the directory into stack
          dirStack.push(Path.getPath());
          Path.changePath(newPath);
        }
        // Determine the directory
      } else if (arguments[1].equals(".")) {
        // Determine the length
        if (curPathArray.length == 1) {
          dirStack.push("~");
          // Push the directory into stack
        } else {
          dirStack.push(Path.getPath());
        }
        // Get the current path array
      } else {
        if (((arguments[1].split("/"))[0]).equals("~")) {
          newPath = arguments[1];
        } else {
          // get the new path
          newPath = Path.getPath() + "/" + arguments[1];
        }
        ArrayList<String> dirPathArray = Path.getPathArray();
        // The boolean to check the existence
        boolean exist = false;
        boolean hasChanged = false;
        // loop the current path array
        for (int j = 0; j < dirPathArray.size(); j++) {
          if (dirPathArray.get(j).endsWith(arguments[1])) {
            exist = true;
          }
          // Determine if it is changed
          if (exist && !hasChanged) {
            hasChanged = true;
            // Determine the length
            if (curPathArray.length == 1) {
              // Push the directory into stack
              dirStack.push("~");
            } else {
              dirStack.push(Path.getPath());
            }
            // Change the current path
            Path.changePath(newPath);
          }
        }
        if (!exist) {
          errorMsg = "No such File or directory!";
        }
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
   * Return the usage for the pushd command which is used for man command.
   */
  public void printUsage() {
    // Print the command name
    System.out.println("pushd");
    // Print its usage
    System.out
        .print("Saves the current working directory by pushing onto directory "
            + "stack and then changes the new current "
            + "working directory to DIR");
  }
}
