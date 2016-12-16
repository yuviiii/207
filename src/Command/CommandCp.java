package Command;

import java.util.ArrayList;

import System.File;
import System.Path;

/**
 * A command that can copy item OLDPATH to NEWPATH.
 * 
 * @author Yitian Ding
 *
 */
public class CommandCp extends Command {

  /**
   * Execute this command, copy contents OLDPATH to NEWPATH.
   * 
   * @param arguments A string that represents user's input.
   * @return Return appropriate error messages when there are errors exit.
   * 
   */
  public static String execute(String[] arguments) {
    ArrayList<String> allPath = Path.getPathArray();
    String oldPath, targetPath;
    String errorMessage = "";
    // Check whether 3 arguments are given by user.
    if (arguments.length != 3) {
      errorMessage = "Need 3 arguments.";
      return errorMessage;
    }
    // check if the old path is a full path or not.
    if (arguments[1].startsWith("~")) {
      oldPath = arguments[1];
    } else {
      oldPath = Path.getPath() + "/" + arguments[1];
    }
    // check if the target path is a full path or not.
    if (arguments[2].startsWith("~")) {
      targetPath = arguments[2];
    } else {
      targetPath = Path.getPath() + "/" + arguments[2];
    }
    // set a boolean.
    boolean oldExist = allPath.contains(oldPath);
    // check if the old path is valid or not.
    if (!oldExist) {
      errorMessage = arguments[1] + ": No such file or directory.";
      return errorMessage;
    }
    // check if the oldPath is a directory and targetPath is a file.
    if (!File.isFile(oldPath) && File.isFile(targetPath)) {
      errorMessage = arguments[2] + ": is a file not a directory.";
      return errorMessage;
    }
    // set a boolean.
    boolean targetExist = allPath.contains(targetPath);
    // check if the target path is valid or not.
    if (targetExist) {
      String[] myOldPath = oldPath.split("/");
      targetPath = targetPath + "/" + myOldPath[myOldPath.length - 1];
      // check if the new targetPaht is valid or not.
      if (allPath.contains(targetPath)) {
        errorMessage = arguments[1] + " already exist in " + arguments[2] + ".";
        return errorMessage;
      }
    }
    // check if the old path is a directory and target path does not exist.
    if (!File.isFile(oldPath) && !targetExist) {
      errorMessage = "omitting directory " + arguments[1];
      return errorMessage;
    }
    // call helper method.
    executeRec(oldPath, targetPath);
    return errorMessage;
  }

  /**
   * A helper method of the execute of Cp command.
   * 
   * @param oldPath A string represent the first user's input path.
   * @param targetPath A string represent the second user's input path.
   */
  private static void executeRec(String oldPath, String targetPath) {
    Path.addPath(targetPath);
    // check if the oldPaht is a string.
    if (File.isFile(oldPath)) {
      String content = File.getFileInfo(oldPath);
      File.setFileInfo(targetPath, content);
      return;
    }
    ArrayList<String> allPaths = Path.getPathArray();
    ArrayList<String> subDirt = new ArrayList<String>();
    // loop the pathArray.
    for (String path : allPaths) {
      if (path.contains(oldPath + '/')) {
        String restPath = path.substring(oldPath.length() + 1);
        String[] name = restPath.split("/");
        if (name.length == 1) {
          subDirt.add(name[0]);
        }
      }
    }
    // check the subDirt size.
    if (subDirt.size() == 0) {
      return;
    }
    // loop the subDirt.
    for (String dirt : subDirt) {
      if (!File.isFile(oldPath + "/" + dirt)) {
        executeRec(oldPath + "/" + dirt, targetPath + "/" + dirt);
      }
    }

  }

  /**
   * Print the usage of the cp command.
   */
  public void printUsage() {
    System.out.println("cp OLDPATH NEWPATH");
    System.out.println("Copy item OLDPATH to NEWPATH. "
        + "Both OLDPATH and NEWPATH may be relative to the current"
        + " directory or may be full paths.  If NEWPATH is a directory, "
        + "copy the item into the directory. If OLDPATH is a directory, "
        + "copy the contents. ");
  }
}
