package Command;

import java.util.ArrayList;

import System.File;
import System.Path;

/**
 * CommandMv can move item OLDPATH to NEWPATH
 * 
 * @author Yitian Ding
 */
public class CommandMv extends Command {

  /**
   * Execute this command, move item OLDPATH to NEWPATH.
   * 
   * @param arguments User's input.
   * @return Return appropriate error messages when there are errors exit.
   */
  public static String execute(String[] arguments) {
    String errorMessage = "";
    // Check whether 3 arguments are given by user.
    if (arguments.length != 3) {
      errorMessage = "Need 3 arguments.";
      return errorMessage;
    }
    ArrayList<String> allPath = Path.getPathArray();
    String oldPath, targetPath;
    // check if the input old path is a full path or not.
    if (arguments[1].startsWith("~")) {
      oldPath = arguments[1];
    } else {
      oldPath = Path.getPath() + "/" + arguments[1];
    }
    // check if the input target path is a full path or not.
    if (arguments[2].startsWith("~")) {
      targetPath = arguments[2];
    } else {
      targetPath = Path.getPath() + "/" + arguments[2];
    }
    boolean oldExist = allPath.contains(oldPath);
    // check if the input old file or path is valid or not.
    if (!oldExist) {
      errorMessage = arguments[1] + ": No such file or directory.";
      return errorMessage;
    }
    // check if the input target file or path is valid or not.
    if (!File.isFile(oldPath) && File.isFile(targetPath)) {
      errorMessage = arguments[2] + ": is a file not a directory.";
      return errorMessage;
    }
    // check if the inputs are both files or not.
    if (File.isFile(oldPath) && File.isFile(targetPath)) {
      errorMessage = arguments[1] + " and " + arguments[2] + " both exist.";
      return errorMessage;
    }

    boolean targetExist = allPath.contains(targetPath);
    // check if the target path is valid or not.
    if (targetExist) {
      String[] myOldPath = oldPath.split("/");
      targetPath = targetPath + "/" + myOldPath[myOldPath.length - 1];
      // check if the targetPath is valid or not.
      if (allPath.contains(targetPath)) {
        errorMessage = arguments[1] + " already exist in " + arguments[2] + ".";
        return errorMessage;
      }
    }
    Path.movePaths(oldPath, targetPath);
    return errorMessage;
  }



  /**
   * Print the usage of the mv command.
   */
  public void printUsage() {
    System.out.println("mv");
    System.out.println("Move item OLDPATH to NEWPATH. Both OLDPATH and NEWPATH"
        + " may be relative to the current directory or may be full paths. "
        + "If NEWPATH is a directory, move the item into the directory.");
  }
}
