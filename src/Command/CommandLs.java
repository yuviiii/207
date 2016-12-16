package Command;

import System.File;
import System.Path;

import java.util.ArrayList;

/**
 * A command used to print the file and contents in the current directory.
 * 
 * @author Wu Zhifan
 */
public class CommandLs extends Command {
  /**
   * The char represent the flag.
   */
  private static char flag;
  /**
   * The boolean represent if there is a flag.
   */
  private static boolean flagValid;
  /**
   * Set up an array list
   */
  private static ArrayList<String> relativeArgs;
  /**
   * Set up the error message.
   */
  private static String errorMsg;

  /**
   * Modify the arguments and change any relative path to full path and check if
   * the "-R" is represented or not.
   * 
   * @param arguments A String that represents user's input.
   * @return the contents in the current path.
   */
  private static ArrayList<String> modify(String[] arguments) {
    ArrayList<String> results = new ArrayList<String>();
    int i;
    String pathAdded = null;
    ArrayList<String> allPath = Path.getPathArray();
    // loop the user's input String
    for (i = 1; i < arguments.length; i++) {
      // check if the arguments include "-"
      if (arguments[i].charAt(0) == '-') {
        flag = arguments[i].charAt(1);
        // check if the "R" is represented
        if (!(flag == ' ') && !(flag == 'R')) {
          flagValid = false;
          break;
        }
      }
      // check if the arguments are full paths or not
      else if (arguments[i].charAt(0) == '~') {
        // check if the input path is valid
        if (!allPath.contains(arguments[i])) {
          errorMsg += arguments[i] + ": No such file or directory\n";
        } else {
          results.add(arguments[i]);
          relativeArgs.add(arguments[i]);
        }
      } else {
        pathAdded = Path.getPath() + '/' + arguments[i];
        // check if the pathAdded is valid
        if (!allPath.contains(pathAdded)) {
          errorMsg += arguments[i] + ": No such file or directory\n";
        } else {
          results.add(pathAdded);
          relativeArgs.add(arguments[i]);
        }
      }
    }
    // check the size of the results
    if (results.size() == 0 && errorMsg.equals("")) {
      results.add(Path.getPath());
    }
    return results;
  }

  /**
   * A helper method that execute paths when "R" is not representing.
   * 
   * @param paths A string represent the user's input after modifying it.
   */
  private static void executeWithoutMode(ArrayList<String> paths) {
    ArrayList<String> allPaths = Path.getPathArray();
    // set a boolean
    boolean twoOrMorePath = (paths.size() > 1);
    // loop the paths
    for (int i = 0; i < paths.size(); i++) {
      // check if there are files in paths list
      if (File.isFile(paths.get(i))) {
        System.out.println(relativeArgs.get(i));
      }
      // check if the there are invalid paths in paths list
      else if (!allPaths.contains(paths.get(i))) {
        errorMsg += relativeArgs.get(i) + ": No such file or directory\n";
      } else {
        if (twoOrMorePath) {
          System.out.println(relativeArgs.get(i) + ":");
        }
        // loop the all the exiting path
        for (String path : allPaths) {
          // check if all the paths in paths list is valid
          if (path.contains(paths.get(i) + '/') && !path.equals(paths.get(i))) {
            String restPath = path.substring((paths.get(i)).length() + 1);
            String[] name = restPath.split("/");
            // check the length
            if (name.length == 1) {
              System.out.println(name[0]);
            }
          }
        }
      }
      if (i < paths.size() - 1) {
        System.out.println();
      }
    }
  }

  /**
   * A helper function to help with execute method when "R" is representing.
   * 
   * @param fullPath A string represent the full path.
   * @param represent A string represent when printing.
   * @param level A integer represent the level of recursion.
   */
  private static void executeWithModeR(String fullPath, String represent,
      int level) {
    // check if the fullPath is a file or not
    if (File.isFile(fullPath)) {
      return;
    }
    ArrayList<String> allPaths = Path.getPathArray();
    ArrayList<String> subDirt = new ArrayList<String>();
    // loop the path in all existing paths
    for (String path : allPaths) {
      // check if the path is valid
      if (path.contains(fullPath + '/')) {
        String restPath = path.substring(fullPath.length() + 1);
        String[] name = restPath.split("/");
        // check the length
        if (name.length == 1) {
          subDirt.add(name[0]);
        }
      }
    }
    // check the value of level
    if (level != 1) {
      System.out.println();
    }
    System.out.println(represent + ":");
    // check the size of subDirt
    if (subDirt.size() == 0) {
      return;
    }
    // loop the dirt
    for (String dirt : subDirt) {
      System.out.println(dirt);
    }
    // loop the dirt
    for (String dirt : subDirt) {
      // check if the path is a file or not.
      if (!File.isFile(fullPath + "/" + dirt)) {
        level++;
        executeWithModeR(fullPath + "/" + dirt, represent + "/" + dirt, level);
      }
    }
  }

  /**
   * 
   * @param arguments A string represent user's input.
   * @return Return error message when there are errors.
   */
  public static String execute(String[] arguments) {
    errorMsg = "";
    flag = ' ';
    flagValid = true;
    relativeArgs = new ArrayList<String>();
    ArrayList<String> paths = modify(arguments);
    ArrayList<String> allPath = Path.getPathArray();
    // check if the flagValid is true.
    if (!flagValid) {
      errorMsg = "Invalid option\n";
    }
    // check if there are "-R" in the arguments or not.
    else if (flag == ' ') {
      executeWithoutMode(paths);
    } else if (flag == 'R') {
      if (relativeArgs.size() == 0) {
        executeWithModeR(paths.get(0), ".", 1);
      } else {
        // loop the arguments after modify.
        for (int i = 0; i < paths.size(); i++) {
          // check the length.
          if (i != 0) {
            System.out.println();
          }
          // check if there exists file in arguments.
          if (File.isFile(paths.get(i))) {
            System.out.println(relativeArgs.get(i));
          }
          // check if the path in arguments is valid or not.
          else if (!allPath.contains(paths.get(i))) {
            errorMsg += relativeArgs.get(i) + ": No such file or directory.\n";
          } else {
            executeWithModeR(paths.get(i), relativeArgs.get(i), 1);
          }
        }
      }
    }
    return errorMsg;
  }

  /**
   * print the usage of the ls command.
   */
  public void printUsage() {
    System.out.println("ls [-R] [PATH...]");
    System.out.println("If -R is present, recursively list all subdirectories."
        + "If no paths are given, print the contents (file or directory) "
        + "of the current directory, " + "with a new line following "
        + "each of the content (file or directory)." + "\n"
        + "Otherwise, for each path p, the order listed: " + "\n"
        + "If p specifies a file, print p." + "\n"
        + "If p specifies a directory, print p, a colon, "
        + "then the contents of that directory, then an extra new line." + "\n"
        + "If p does not exist, print a suitable message.");
  }
}
