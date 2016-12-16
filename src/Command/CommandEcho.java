package Command;

import java.util.ArrayList;
import System.File;
import System.Path;

/**
 * This class represents the command "echo". The Echo command outputs to a new
 * file or appends to an old file.
 * 
 * @author Yitian Ding, Zhifan Wu,Xiyan Zhou,Bojun Wang
 */
public class CommandEcho extends Command {
  /**
   * Execute echo command, create or edit a file with String in it.
   * 
   * @param record User's input.
   * @return errorMsg Return the error message.
   */
  public static String execute(String[] arguments) {
    // Set up the error message
    String errorMsg = "";
    if (arguments.length == 1) {
      errorMsg =
          "Echo command should take String " + "and also can take fileName.";
    } else {
      // Set up a new array list
      ArrayList<String> args = new ArrayList<String>();
      // loop the elements in the input array
      for (int i = 0; i < arguments.length; i++) {
        // add the elements which do not contain a quotation mark
        args.add(arguments[i].replaceAll("^\"|\"$", ""));
      }
      // Determine if it has marks
      if (!(args.contains(">") || args.contains(">>"))) {
        // loop the element to print the string
        for (int i = 1; i < args.size(); i++) {
          if (i != args.size() - 1) {
            System.out.print(args.get(i) + " ");
          } else {
            System.out.println(args.get(i));
          }
        }
      } else {
        if (args.contains(">")) {
          // deal with marks
          dealOneMark(args);
        } else {
          dealTwoMark(args);
        }
      }
    }
    return errorMsg;
  }

  /**
   * The helper method to deal with two marks.
   * 
   * @param args The array list representation of the input array.
   */
  private static void dealTwoMark(ArrayList<String> args) {
    // Set up the file name
    String fileName = "";
    // Set up the file info
    String fileInfo = "";
    // get the mark index
    int markIndex = args.indexOf(">>");
    for (int i = markIndex + 1; i < args.size(); i++) {
      // get the file name
      fileName += args.get(i);
    }
    for (int i = 1; i < markIndex; i++) {
      // get the file info
      fileInfo += args.get(i);
    }
    String key = Path.getPath() + "/" + fileName;
    // set up the file
    if (File.hashtable.containsKey(key)) {
      String origin = File.getFileInfo(key);
      File.setFileInfo(key, origin + fileInfo);
    } else {
      File.setFileInfo(key, fileInfo);
      Path.setPath(fileName);
      Path.addPath(key);
    }
  }

  /**
   * The helper method for one mark.
   * 
   * @param args The array list representation of the input array.
   */
  private static void dealOneMark(ArrayList<String> args) {
    // Set up the file name
    String fileName = "";
    // Set up the file info
    String fileInfo = "";
    // Get the mark index
    int markIndex = args.indexOf(">");
    for (int i = markIndex + 1; i < args.size(); i++) {
      // get the file name
      fileName += args.get(i);
    }
    for (int i = 1; i < markIndex; i++) {
      // get the file info
      fileInfo += args.get(i);
    }
    // set up the file
    String key = Path.getPath() + "/" + fileName;
    File.setFileInfo(key, fileInfo);
    Path.setPath(fileName);
    Path.addPath(key);
  }

  /**
   * Print the usage of echo command.
   */
  public void printUsage() {
    System.out.println("Echo");
    System.out.println("User could use echo command to edit or create file");
  }
}
