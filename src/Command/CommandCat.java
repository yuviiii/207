package Command;

import System.File;
import System.Path;

/**
 * A Command used to display the contents of files.
 * 
 * @author Yitian Ding, Zhifan Wu,Xiyan Zhou,Bojun Wang
 */
public class CommandCat extends Command {

  /**
   * The execute method for printing the file content.
   * 
   * @param args The argument we input in the JShell.
   * @return errorMsg Return the error message.
   */
  public static String execute(String[] args) {
    String errorMsg = "";
    // Determine the error case which cat command does not
    // take any file
    if (args.length == 1) {
      errorMsg = "The cat command should take at least 1 file name.";
    } else {
      // loop the file elements to cat
      for (int i = 1; i < args.length; i++) {
        // Determine the keys in the hashTable to store files
        // Which the keys are the current working directory
        if (Path.getPath().endsWith(args[i])) {
          // Determine if the value of the key is null
          if (File.getFileInfo(Path.getPath()).equals("")) {
            System.out.print("");
          } else {
            // Return the content in the file
            System.out.print(File.getFileInfo(Path.getPath()));
          }
          // Determine if the value of the key is null
        } else if (File.getFileInfo(Path.getPath() + "/" + args[i])
            .equals("")) {
          System.out.print("");
        } else {
          // Get the key of the corresponding file
          String key = Path.getPath() + "/" + args[i];
          // Print out the comment
          System.out.print(File.getFileInfo(key));
        }
        if (i != args.length - 1) {
          // print three blank lines between contents of each file.
          System.out.print("\n" + "\n" + "\n");
        }
      }
    }
    return errorMsg;
  }

  /**
   * Print the usage of Command cat.
   */
  public void printUsage() {
    System.out.println("cat");
    System.out.println("Display the contents of files in the shell");
  }

  /**
   * Return the usage for the Cat command which is used for man command.
   */
  public String printStringUsage() {
    // Print the command usage
    return ("cat" + "\n" + "Display the contents of files in the shell");
  }
}
