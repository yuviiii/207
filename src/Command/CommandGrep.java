package Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import System.Edit;
import System.File;
import System.Path;

/**
 * This class represents the command "grep". The grep command will find out the
 * specific line from a file or recursively go through a directory and find file
 * that contains specific content.
 * 
 * @author Bojun Wang
 */
public class CommandGrep extends Command {
  /**
   * The method that execute the grep command.
   * 
   * @param arguments The array we inputed.
   * @return errorMsg Return the error message.
   */
  // dealing with the case that without recursion.
  public static String execute(String[] argus) {
    String errorMsg = "";
    if (argus.length == 1) {
      errorMsg = "Need more arguments.";
      return errorMsg;
    }
    // using length to check recursion should be used or not.
    if (argus.length == 3) {
      if (File.isFile(Path.getPath() + "/" + argus[2])) {
        String fileName = argus[2];
        String content = File.getFileInfo(Path.getPath() + "/" + fileName);
        String[] contentArray = content.split(" ");
        for (int i = 0; i < contentArray.length; i++) {
          if (contentArray[i].contains(argus[1])) {
            System.out.println(contentArray[i]);
          }
        }
      }
      // when recursion need to be used , run exectueCase2.
    } else if (argus.length == 4) {
      executeCase2(argus, 1);
    }
    return errorMsg;
  }

  /**
   * The method that execute the case when the array length is 4.
   * 
   * @param arguments The array we inputed.
   * 
   */
  // executeCase2 is a method dealing with recursion.
  public static void executeCase2(String[] argus, int level) {

    // recursion base case : when file are reached.
    if (File.isFile(Path.getPath() + "/" + argus[3])) {
      String[] myArguments2 = ("cd ..").split(" ");
      String fileName2 = argus[3];
      String content2 = File.getFileInfo(Path.getPath() + "/" + fileName2);
      String[] contentArray2 = content2.split(" ");
      for (int i = 0; i < contentArray2.length; i++) {
        if (contentArray2[i].contains(argus[2])) {
          System.out.println(
              Path.getPath() + "/" + fileName2 + ": " + contentArray2[i]);

        }
      }
      String s = Path.getPath();
      int counter = 0;
      for (int index = 0; index < s.length(); index++) {
        if (s.charAt(index) == '/') {
          counter++;
        }
      }
      if (counter > 1) {
        CommandCd.execute(myArguments2);
      }
      // when other directory reached.
    } else {
      // then go in to the directory.
      String[] myArguments = ("cd " + argus[3]).split(" ");
      CommandCd.execute(myArguments);
      // whatever the ls printout, the result will be used to do further
      // recursion.
      String[] inputArray = "ls".split(" ");
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(outContent);
      PrintStream old = System.out;
      System.setOut(ps);
      CommandLs.execute(inputArray);
      String outputString = outContent.toString();
      System.setOut(old);
      String[] a = outputString.split("\n");
      for (int i = 0; i < a.length; i++) {
        String[] recurArray =
            (argus[0] + " " + argus[1] + " " + argus[2] + " " + a[i])
                .split(" ");
        if (recurArray.length == 4) {
          level++;
          if (level < 100) {
            executeCase2(recurArray, level);
          }
        }
      }
    }
  }

  /**
   * Print the usage of grep command.
   */
  public void printUsage() {
    System.out.println("grep");
    System.out.println("The grep command will find out the specific line f"
        + "rom a file or recursively go through a di"
        + "rectory and find file that contains specific content.");
  }
}
