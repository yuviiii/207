package Command;

import java.util.ArrayList;

import System.Path;

/**
 * CommandMkdir can create directories, each of which may be relative to the
 * current directory or may be a full path.
 * 
 * @author Yitian Ding
 */
public class CommandMkdir extends Command {

  /**
   * error message.
   */
  private static String errorMsg = "";

  /**
   * Execute this command, create directories.
   * 
   * @param args User's input.
   * @return Return appropriate error messages when there are errors exit.
   */
  public static String execute(String[] args) {
    errorMsg = "";
    // check whether user need to input more arguments.
    if (args.length < 2) {
      errorMsg = "mkdir must have at least 1 argument.";
    } else if (args[1].equals("..")) {
      errorMsg = "Error: .. is not a directory";
    } else if (args[1].equals(".")) {
      errorMsg = "Error: . is not a directory";
    } else {
      int i;
      // Create myPath represents current path.
      String myPath = Path.getPath();
      // Create allPath represents the ArrayList of all paths.
      ArrayList<String> allPath = Path.getPathArray();
      for (i = 1; i < args.length; i++) {
        String[] myDir = args[i].split("/");
        // Check whether the input is a directory name or a path.
        if (myDir.length == 1) {
          String createdPath = myPath + "/" + myDir[0];
          // Check whether the DIR already exists in the path array.
          if (allPath.contains(createdPath)) {
            errorMsg = "File exists.";
            // Add the path to the path array.
          } else {
            Path.setPath(myDir[0]);
          }
          // Check whether the DIR is a relative path.
        } else if (!myDir[0].equals("~")) {
          // call the helper method that handle with relative paths.
          CommandMkdir.mkdirRelative(myPath, myDir, allPath);
          // When input is a absolute path.
        } else {
          int k;
          String parentPath = "~";
          for (k = 1; k < myDir.length - 1; k++) {
            // Get the parent path of the input path.
            parentPath += "/" + myDir[k];
          }
          // Check whether the parent path exists.
          if (!allPath.contains(parentPath)) {
            errorMsg = "Invalid absolute path.";
            // Check whether the input path already exists in the path array.
          } else if (allPath.contains(args[i])) {
            errorMsg = "File exists.";
          } else {
            Path.addPath(args[i]);
          }
        }
      }
    }
    return errorMsg;
  }

  /**
   * A helper method to handle with relative paths.
   * 
   * @param myPath Current path.
   * @param myDir User's inputs.
   * @param allPath The path array that contains all the paths.
   */
  public static void mkdirRelative(String myPath, String[] myDir,
      ArrayList<String> allPath) {
    int i, j;
    // Initialize a new path.
    String newPath = myPath;
    // Initialize the exist as false.
    Boolean exist = false;
    // Add directories to newPath to make the newPath as the parent path of the
    // directory we want to make.
    for (j = 0; j < myDir.length - 1; j++) {
      newPath += "/" + myDir[j];
    }
    // Check whether the new path exists.
    for (i = 0; i < allPath.size(); i++) {
      String path = allPath.get(i);
      if (path.equals(newPath)) {
        exist = true;
      }
    }
    if (exist) {
      // Get the new path.
      newPath = newPath + "/" + myDir[myDir.length - 1];
      // Check whether the newPath already exists in the path array.
      if (allPath.contains(newPath)) {
        errorMsg = "File exists.";
      } else {
        Path.addPath(newPath);
      }
      // When the input relative path is invalid.
    } else {
      errorMsg = "Invalid relative path.";
    }
  }

  /**
   * A method used to print usage of CommandMkdir.
   */
  public void printUsage() {
    System.out.println("mkdir DIR ...");
    System.out.print("Create directories, each of which "
        + "may be relative to the current directory or may be a full path.");
  }
}
