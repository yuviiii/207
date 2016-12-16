package System;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import Command.CommandCat;
import Command.CommandCd;
import Command.CommandCp;
import Command.CommandEcho;
import Command.CommandGet;
import Command.CommandGrep;
import Command.CommandHistory;
import Command.CommandLs;
import Command.CommandMan;
import Command.CommandMkdir;
import Command.CommandMv;
import Command.CommandNumber;
import Command.CommandPopd;
import Command.CommandPushd;
import Command.CommandPwd;
import System.Path;;

public class Edit {

  /**
   * Set up a string
   */
  private static String msg = "";
  /**
   * Set up an array
   */
  public static String[] str;
  /**
   * Set up a index.
   */
  public static int index = 0;

  /**
   * 
   * @param arguments A string represent the user's input.
   */
  public static void check(String[] arguments) {
    // Set up an array list to check if it is a redirection
    ArrayList<String> array = new ArrayList<String>();
    // loop the arguments to create the array list
    for (int i = 0; i < arguments.length; i++) {
      array.add(arguments[i]);
    }
    int markIndex = 0;
    // Determine if it is a redirection
    if (array.contains(">") == true || array.contains(">>") == true) {
      // String command = arguments[0];
      // Use redirection method
      if (array.contains(">") == true) {
        markIndex = array.indexOf(">");
      } else if (array.contains(">>") == true) {
        markIndex = array.indexOf(">>");
      }
      int fileIndex = array.indexOf(">") + 1;
      // Find the file name
      String fileName = array.get(fileIndex);
      if (arguments[0].equals("mkdir")) {
        if (fileName.equals(array.get(markIndex - 1))) {
          System.out.println("File exists!");
        }
      }
      if (Path.getPathArray().contains(Path.getPath() + "/" + fileName)) {
        if (!File.isFile(Path.getPath() + "/" + fileName)) {
          System.out.println("Error: file exists!");
        } else {
          outFileExecute(array, "");
        }
      } else {
        outFileExecute(array, "");
      }
    } else {
      String command = arguments[0];
      commandRun(arguments, command);
      if (command.equals("ls")) {
        if (!msg.equals("")) {
          System.out.print(msg);
        }
      } else {
        if (!msg.equals("")) {
          System.out.println(msg);
        }
      }
    }
  }



  /**
   * The string check to execute.
   * 
   * @param arguments A string that represents user's input.
   */
  public static void stringCheck(String arguments) {
    String[] args = null;
    // Determine if it is a redirection
    if (arguments.contains(">") == true || arguments.contains(">>") == true) {
      if (arguments.contains(">") && !arguments.contains(">>")) {
        index = arguments.indexOf(">");
      } else {
        index = arguments.indexOf(">>");
      }
      // Set up an array list to check if it is a redirection
      ArrayList<String> array = new ArrayList<String>();
      args = arguments.split(" ");
      // loop the arguments to create the array list
      for (int i = 0; i < args.length; i++) {
        array.add(args[i]);
      }
      // Use redirection method
      outFileExecute(array, arguments);
    } else {
      str = CommandNumber.execute(arguments);
    }
  }

  /**
   * Redirection cases.
   * 
   * @param array The array list we separate the input array.
   */
  public static void outFileExecute(ArrayList<String> array, String myInput) {
    // Check if it is a ">" mark
    if (array.contains(">") == true) {
      dealOneMark(array, myInput);
      // Check if it is a ">>" mark
    } else {
      dealTwoMarks(array, myInput);
    }
  }

  /**
   * The helper function to deal with one > mark.
   * 
   * @param array The array representation of string.
   * @param myInput the input string.
   */
  public static void dealOneMark(ArrayList<String> array, String myInput) {
    String output = "";
    // Find the index of the ">" mark
    int fileIndex = array.indexOf(">") + 1;
    // Find the file name
    String fileName = array.get(fileIndex);
    // Change it to the general case
    array.remove(fileIndex);
    array.remove(fileIndex - 1);
    String[] newArray = array.toArray(new String[array.size()]);
    String command = newArray[0];
    // Get the output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(outContent);
    PrintStream old = System.out;
    System.setOut(ps);
    // Determine if it is the number command
    if (!newArray[0].startsWith("!")) {
      commandRun(newArray, command);
    } else {
      str = CommandNumber.execute(newArray[0]);
    }
    output = outContent.toString();
    System.setOut(old);
    // print the error message
    if (command.equals("ls")) {
      // deal with the ls case
      if (!msg.equals("")) {
        System.out.print(msg);
      }
    } else {
      if (!msg.equals("")) {
        System.out.println(msg);
      }
    }
    // Set up the file
    String key = Path.getPath() + "/" + fileName;
    if (!File.hashtable.containsKey(key)) {
      // Override the file
      File.setFileInfo(key, output);
      Path.setPath(fileName);
      Path.addPath(key);
    } else {
      File.setFileInfo(key, output);
    }
  }

  /**
   * The helper function to deal with >> mark.
   * 
   * @param array The array representation of string.
   * @param myInput the input string.
   */
  public static void dealTwoMarks(ArrayList<String> array, String myInput) {
    String output = "";
    // Find the index of the ">>" mark
    int fileIndex = array.indexOf(">>") + 1;
    // Find the file name
    String fileName = array.get(fileIndex);
    // Change it to the general case
    array.remove(fileIndex);
    array.remove(fileIndex - 1);
    String[] newArray = array.toArray(new String[array.size()]);
    String command = newArray[0];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(outContent);
    PrintStream old = System.out;
    System.setOut(ps);
    // Check the head of newArray.
    if (!newArray[0].startsWith("!")) {
      commandRun(newArray, command);
    } else {
      str = CommandNumber.execute(newArray[0]);
    }
    // deal with ls case.
    if (command.equals("ls")) {
      if (!msg.equals("")) {
        System.out.print(msg);
      }
    } else {
      if (!msg.equals("")) {
        System.out.println(msg);
      }
    }

    output = outContent.toString();

    System.setOut(old);
    String key = Path.getPath() + "/" + fileName;
    // check if path is a file.
    if (!File.hashtable.containsKey(key)) {
      File.setFileInfo(key, output);
      Path.setPath(fileName);
      Path.addPath(key);
    } else {
      String content = File.getFileInfo(key);
      if (command.equals("pwd")) {
        if (Path.getPath().equals("~")) {
          File.setFileInfo(key, content + "\n" + content);
        } else {
          File.setFileInfo(key, content + "\n" + Path.getPath());
        }
      } else if (command.equals("echo")) {
        File.setFileInfo(key, content + output);
      } // deal with echo case.
      else {
        File.setFileInfo(key, content + "\n" + output);
      }
    }
  }

  /**
   * The method to check if it is a valid command by extract the class name in
   * the commandTable.
   * 
   * @param args The array we inputed in the Jshell.
   */
  public static void commandRun(String[] args, String command) {
    // run the corresponding command
    if (command.equals("cat")) {
      msg = CommandCat.execute(args);
    } else if (command.equals("cd")) {
      msg = CommandCd.execute(args);
    } else if (command.equals("man")) {
      msg = CommandMan.execute(args);
    } else if (command.equals("ls")) {
      msg = CommandLs.execute(args);
    } else if (command.equals("mkdir")) {
      msg = CommandMkdir.execute(args);
    } else if (command.equals("popd")) {
      msg = CommandPopd.execute(args);
    } else if (command.equals("pushd")) {
      msg = CommandPushd.execute(args);
    } else if (command.equals("pwd")) {
      msg = CommandPwd.execute(args);
    } else if (command.equals("history")) {
      msg = CommandHistory.execute(args);
    } else if (command.equals("get")) {
      msg = CommandGet.execute(args);
    } else if (command.equals("mv")) {
      msg = CommandMv.execute(args);
    } else if (command.equals("cp")) {
      msg = CommandCp.execute(args);
    } else if (command.equals("grep")) {
      msg = CommandGrep.execute(args);
    } else if (command.equals("echo")) {
      msg = CommandEcho.execute(args);
    }
  }
}
