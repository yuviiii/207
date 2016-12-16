// **********************************************************
// Assignment2:
// Student1:
// CDF user_name:c4wangbo
// UT Student #:1001289982
// Author:Bojun Wang
//
// Student2:
// CDF user_name:c5wuzhif
// UT Student #: 1001175051
// Author: Wu Zhifan
//
// Student3:
// CDF user_name:c5zhouxk
// UT Student #:1001139902
// Author:Xiyan Zhou
//
// Student4:
// CDF user_name:c5dingyi
// UT Student #:1001126186
// Author:Yitian Ding
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package driver;

import java.util.ArrayList;
import java.util.Scanner;
import Command.CommandMkdir;
import Command.CommandCd;
import Command.CommandEcho;
import Command.CommandExit;
import Command.CommandMan;
import Command.CommandLs;
import Command.CommandPopd;
import Command.CommandPushd;
import Command.CommandCat;
import Command.CommandHistory;
import Command.CommandPwd;
import System.File;
import System.Path;
import System.Edit;


/**
 * This Jshell class represents input/output. It takes in input from users,
 * checks whether or not it is valid, and outputs (or terminates when prompted
 * to exit) accordingly.
 */
public class JShell {

  /**
   * The mass record String that stores the string in the input array.
   */
  static String massRecord = "";
  /**
   * The newCheck array to clone the check array.
   */
  static String[] newCheck = new String[1];
  /**
   * The count we define a loop
   */
  static int count = 0;

  /**
   * Run the JShell program.
   * 
   * @param args The input of the JShell
   * @throws Exception The exception which might happen.
   */
  public static void main(String[] args) throws Exception {
    Scanner input = new Scanner(System.in);
    System.out.print("/# ");
    // Read the input into a string
    massRecord = input.nextLine();
    if (!massRecord.contains("!")) {
      CommandHistory.setInfo(massRecord);
    }
    // Split the whitespace
    String myInput = massRecord.replaceAll("\\s+", " ").trim();
    // Split the mass Path
    if (!myInput.startsWith("get")) {
      myInput = myInput.replaceAll("/+", "/");
    }
    String[] newArray = myInput.split(" ");
    // Deal with the mark place
    String[] newCheck = dealWithMarks(newArray);

    while (count < 1) {
      // if the command is cat
      if (newCheck[0].equals("cat")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is cd
      } else if (newCheck[0].equals("cd")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is cp
      } else if (newCheck[0].equals("cp")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is exit
      } else if (newCheck[0].equals("exit")) {
        Command.CommandExit.execute(newCheck);
        // if the command is echo
      } else if (newCheck[0].equals("echo")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is get
      } else if (newCheck[0].equals("get")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is grep
      } else if (newCheck[0].equals("grep")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is history
      } else if (newCheck[0].equals("history")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is ls
      } else if (newCheck[0].equals("ls")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is man
      } else if (newCheck[0].equals("man")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is mkdir
      } else if (newCheck[0].equals("mkdir")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is mv
      } else if (newCheck[0].equals("mv")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command id popd
      } else if (newCheck[0].equals("popd")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is pushd
      } else if (newCheck[0].equals("pushd")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is pwd
      } else if (newCheck[0].equals("pwd")) {
        Edit.check(newCheck);
        JShell.main(args);
        // if the command is !Number
      } else if (newCheck[0].startsWith("!")) {
        int integer = Integer.parseInt(newCheck[0].substring(1, 2));
        if (CommandHistory.infoArray.size() < integer) {
          System.out.println("Error: The number you input is out of range.");
          JShell.main(args);
        } else {
          // run the string check for redirection class
          Edit.stringCheck(myInput);
          // catch the new array for the number corresponding command
          String[] str = Edit.str;
          int index = Edit.index;
          if (myInput.contains(">") || myInput.contains(">>")) {
            StringBuilder check = new StringBuilder();
            for (int i = 0; i < str.length; i++) {
              if (i != str.length - 1) {
                check.append(str[i] + " ");
              } else {
                check.append(str[i]);
              }
            }
            String newString = check.toString() + " "
                + myInput.substring(index, myInput.length());
            newCheck = newString.split(" ");
          } else {
            newCheck = str;
          }
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < newCheck.length; i++) {
            sb.append(newCheck[i]);
          }
          // Set the history
          CommandHistory.setInfo(sb.toString());
          while (count < 1) {
            // if the corresponding command is cat
            if (newCheck[0].equals("cat")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is cd
            } else if (newCheck[0].equals("cd")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is cp
            } else if (newCheck[0].equals("cp")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is echo
            } else if (newCheck[0].equals("echo")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is get
            } else if (newCheck[0].equals("get")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is grep
            } else if (newCheck[0].equals("grep")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is history
            } else if (newCheck[0].equals("history")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is ls
            } else if (newCheck[0].equals("ls")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is man
            } else if (newCheck[0].equals("man")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is mkdir
            } else if (newCheck[0].equals("mkdir")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is mv
            } else if (newCheck[0].equals("mv")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is popd
            } else if (newCheck[0].equals("popd")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is pushd
            } else if (newCheck[0].equals("pushd")) {
              Edit.check(newCheck);
              JShell.main(args);
              // if the corresponding command is pwd
            } else if (newCheck[0].equals("pwd")) {
              Edit.check(newCheck);
              JShell.main(args);
            }
          }
        }
      } else {
        // if it is not the valid command
        // print out the error message
        System.out.println("Command not found!");
        JShell.main(args);
      }
      input.close();
    }
  }


  /**
   * The method to split the white space with input string.
   * 
   * @param massRecord The string you input.
   * @return String the string after split the space.
   */
  public static String splitWhiteSpace(String massRecord) {
    // Split the whitespace
    String myInput = massRecord.replaceAll("\\s+", " ").trim();
    @SuppressWarnings("unused")
    String[] newCheck = myInput.split(" ");
    return myInput;
  }

  /**
   * Deal with the input array which contains > and >> marks, which the command
   * and the redirection do not split by space.
   * 
   * @param array The input array which contains the command and redirection.
   * @return newArray The array which split the command and redirection.
   */
  private static String[] dealWithMarks(String[] array) {
    // A boolean to judge if the command and marks are containing white space
    boolean exist = false;
    // the index of > mark
    int oneMarkIndex = 0;
    // the index of two mark
    int twoMarkIndex = 0;
    // the index of the place which the array contains the marks
    int index = 0;
    // Set up a new array list
    ArrayList<String> list = new ArrayList<String>();
    // Set up the returned array
    String[] newArray = null;
    // loop the element in the input array
    for (int i = 0; i < array.length; i++) {
      // if the input array does not split the command and redirection mark
      if (array[i].contains(">") || array[i].contains(">>")) {
        exist = true;
      }
      // change array to the array list
      list.add(array[i]);
    }
    if (!exist) {
      return array;
    } else {
      // loop the element in the array list
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i).contains(">>")) {
          // find the index of string which contains the mark in the array
          index = i;
          // find the mark index
          twoMarkIndex = list.get(i).indexOf(">>");
        } else if (list.get(i).contains(">")
            && !list.get(index).contains(">>")) {
          index = i;
          oneMarkIndex = list.get(i).indexOf(">");
        }
      }
      // determine which mark does it have
      if (list.get(index).contains(">>")) {
        newArray = dealTwoMarks(list, twoMarkIndex, index, newArray, array);
      } else if (list.get(index).contains(">")
          && !list.get(index).contains(">>")) {
        newArray = dealOneMark(list, oneMarkIndex, index, newArray, array);
      }
      return newArray;
    }
  }

  /**
   * The helper method for deal with mark method
   * 
   * @param list the array list representation of the array
   * @param oneMarkIndex The index of > mark in the string which contains > mark
   * @param index the index of the String which contain the one mark
   * @param newArray The new array to return
   * @param array The input array.
   * @return The new array.
   */
  private static String[] dealOneMark(ArrayList<String> list, int oneMarkIndex,
      int index, String[] newArray, String[] array) {
    // Determine if the input array is for one mark
    if (list.get(index).contains(">") && !list.get(index).contains(">>")) {
      // Set up the string before the mark
      String before = list.get(index).substring(0, oneMarkIndex);
      // Set up a string after the mark
      String after =
          list.get(index).substring(oneMarkIndex + 1, list.get(index).length());
      // if there is no element after the mark
      if (after.equals("") && !before.equals("")) {
        // remove the String which contains the mark
        list.remove(list.get(index));
        // add the separate string to the list
        list.add(index, before);
        list.add(index + 1, ">");
        // set up a new array
        newArray = new String[list.size()];
        // create the return array
        for (int i = 0; i < list.size(); i++) {
          newArray[i] = list.get(i);
        }
        // if there is no element before the mark
      } else if (before.equals("") && !after.equals("")) {
        // remove the String which contains the mark
        list.remove(list.get(index));
        // add the separate string to the list
        list.add(index, ">");
        list.add(index + 1, after);
        // set up a new array
        newArray = new String[list.size()];
        // create the return array
        for (int i = 0; i < list.size(); i++) {
          newArray[i] = list.get(i);
        }
        // if there is no element after the mark and before the mark
      } else if (after.equals("") && before.equals("")) {
        // return the new array
        newArray = array;
        // general case
      } else {
        // remove the String which contains the mark
        list.remove(list.get(index));
        // add the separate string to the list
        list.add(index, before);
        list.add(index + 1, ">");
        list.add(index + 2, after);
        // create the return array
        newArray = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
          newArray[i] = list.get(i);
        }
      }
    }
    return newArray;
  }

  /**
   * The helper method for deal with mark method
   * 
   * @param list the array list representation of the array
   * @param oneMarkIndex The index of > mark in the string which contains > mark
   * @param index the index of the String which contain the one mark
   * @param newArray The new array to return
   * @param array The input array.
   * @return The new Array
   */
  private static String[] dealTwoMarks(ArrayList<String> list, int twoMarkIndex,
      int index, String[] newArray, String[] array) {
    // Determine if the input array is for two mark
    if (list.get(index).contains(">>")) {
      // Set up the string before the mark
      String before = list.get(index).substring(0, twoMarkIndex);
      // Set up a string after the mark
      String after =
          list.get(index).substring(twoMarkIndex + 2, list.get(index).length());
      // if there is no element after the mark
      if (after.equals("") && !before.equals("")) {
        // remove the String which contains the mark
        list.remove(list.get(index));
        // add the separate string to the list
        list.add(index, before);
        list.add(index + 1, ">>");
        // set up a new array
        newArray = new String[list.size()];
        // create the return array
        for (int i = 0; i < list.size(); i++) {
          newArray[i] = list.get(i);
        }
      } else if (before.equals("") && !after.equals("")) {
        // remove the String which contains the mark
        list.remove(list.get(index));
        // add the separate string to the list
        list.add(index, ">>");
        list.add(index + 1, after);
        // set up a new array
        newArray = new String[list.size()];
        // create the return array
        for (int i = 0; i < list.size(); i++) {
          newArray[i] = list.get(i);
        }
        // if there is no element before the mark
      } else if (after.equals("") && before.equals("")) {
        newArray = array;
      } else {
        // remove the String which contains the mark
        list.remove(list.get(index));
        // add the separate string to the list
        list.add(index, before);
        list.add(index + 1, ">>");
        list.add(index + 2, after);
        // set up a new array
        newArray = new String[list.size()];
        // create the return array
        for (int i = 0; i < list.size(); i++) {
          newArray[i] = list.get(i);
        }
      }
    }
    return newArray;
  }
}


