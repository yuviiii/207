package Command;

import java.util.ArrayList;

import System.File;
import System.Path;

/**
 * A command used to get into a directory.
 * 
 * @author Yitian Ding, Zhifan Wu,Xiyan Zhou,Bojun Wang
 */
public class CommandCd extends Command {

  /**
   * Set up an error message.
   */
  private static String errorMsg = "";

  /**
   * Execute cd command, get into a specific directory.
   * 
   * @param myArguments User's input.
   * @return errorMsg Return the error message.
   */
  public static String execute(String[] arguments) {
    errorMsg = "";
    String myCurrentPath = Path.getPath();
    // check whether the user add too many arguments.
    if (arguments.length > 2) {
      errorMsg = "Too many arguments.";
      // check whether the user need to input one more argument.
    } else if (arguments.length == 1) {
      errorMsg = "Need one more argument";
    } else {
      // get the directory name or the path that input by user.
      String myDir = arguments[1];
      ArrayList<String> allPath = Path.getPathArray();
      // this represents whether the directory/path exists.
      Boolean exist = false;
      String[] dirArray = myDir.split("/");
      // check whether the user wants to get to a parent directory.
      if (myDir.equals("..")) {
        // call the helper method that cd into parent directory.
        cdParent(myCurrentPath);
        exist = true;
      } else {
        // call the helper method that cd into a specific directory.
        exist = cdDir(allPath, dirArray, myCurrentPath, myDir);
      }
      if (!exist && !myDir.equals(".")) {
        errorMsg = "No such file or directory.";
      }
    }
    return errorMsg;
  }

  /**
   * helper method used to get into parent directory.
   * 
   * @param myCurrentPath The current path.
   */
  private static void cdParent(String myCurrentPath) {
    // check whether the path is a absolute or a relative one.
    if (!myCurrentPath.equals("~")) {
      String[] currentPath = myCurrentPath.split("/");
      String newPath = "";
      // get the length of the last directory in currentPath.
      int backLength = currentPath[currentPath.length - 1].length();
      int i;
      for (i = 0; i < myCurrentPath.length() - backLength - 1; i++) {
        // get the newPath we get into.
        newPath += myCurrentPath.charAt(i);
      }
      // change the current path to the new path.
      Path.changePath(newPath);
    }
  }

  /**
   * helper method used to CD into a directory or a path.
   * 
   * @param allPath All the paths exist.
   * @param dirArray Directories in path that input by user.
   * @param myCurrentPath The current path.
   * @param myDir The directory name or the path input by user.
   * @return A boolean that represents whether the directory/path input by user
   *         exists.
   */
  private static boolean cdDir(ArrayList<String> allPath, String[] dirArray,
      String myCurrentPath, String myDir) {
    int i;
    String myPath;
    // initialize a boolean that represents whether the directory/path exists.
    boolean exist = false;
    String newPath = "";
    for (i = 0; i < allPath.size(); i++) {
      myPath = allPath.get(i);
      String[] myPathArray = myPath.split("/");
      // check whether the user input a directory name.

      if (dirArray.length == 1) {
        if (!dirArray[0].equals("~")) {
          newPath = myCurrentPath + "/" + dirArray[0];
        } else {
          newPath = "~";
        }
        // check whether the user input a relative path.
      } else if (!dirArray[0].equals("~")) {
        newPath = myCurrentPath + "/" + myDir;
      }
      // check whether we newPath equals to myPath.
      if (newPath.equals(myPath)) {
        exist = true;
        // call the helper method check whether the directory is a txt file
        // and cd into it.
        cdPath(dirArray, newPath);
        // check whether the input is an absolute path and whether this path
        // exists.
      } else if (dirArray.length == myPathArray.length) {
        boolean found = isFound(myPathArray, dirArray);
        if (found) {
          exist = true;
          // call the helper method check whether the directory is a txt file
          // and cd into it.
          cdPath(dirArray, myPath);
        }
      }
    }
    return exist;
  }

  /**
   * Check whether the directory we want to get into is a txt file, and cd into
   * it if it is not a txt.
   * 
   * @param dirArray Directories in path that input by user.
   * @param myPath The path we want to get into.
   */
  private static void cdPath(String[] dirArray, String myPath) {
    // check whether the file is a txt.
    if (File.isFile(myPath)) {
      // print a message to show the user is trying to get into a txt file.
      errorMsg = "cannot cd into a file";
    } else {
      // change current path to myPath.
      Path.changePath(myPath);
    }
  }

  /**
   * A helper method to check whether the path represented by dirArray is the
   * same as the path represented by myPathArray.
   * 
   * @param myPathArray Represent directories in one path of all paths.
   * @param dirArray Represent directories in path that input by user.
   * @return the boolean that represents whether the path represented by
   *         dirArray is the same as the path represented by myPathArray.
   */
  private static boolean isFound(String[] myPathArray, String[] dirArray) {
    int j;
    Boolean isFound = true;
    // loop for each directory names in the path.
    for (j = 0; j < myPathArray.length; j++) {
      // check whether the directory in two paths is the same.
      if (!myPathArray[j].equals(dirArray[j])) {
        isFound = false;
      }
    }
    return isFound;
  }

  /**
   * Print the usage of cd command.
   */
  public void printUsage() {
    System.out.println("cd");
    System.out.println(
        "Change directory to DIR, " + "which may be relative to the current,"
            + "may be a full path. As with Unix, '..' "
            + "means a parent directory and '.' means the current directory. "
            + "The directory must be /, "
            + "the foot of the file system is a single slash: /.");
  }
}
