// **********************************************************
// Assignment2:
// Student1:
// CDF user_name:
// UT Student #:
// Author:
//
// Student2:
// CDF user_name:
// UT Student #:
// Author:
//
// Student3:
// CDF user_name:
// UT Student #:
// Author:
//
// Student4:
// CDF user_name:
// UT Student #:
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package driver;

import java.util.Scanner;

public class JShell {
  static String record = "";
  static String[] check = new String[1];
  static int count = 0;

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);
    System.out.print("/# ");
    record = input.nextLine();
    check = record.split(" "); // Split the user input in order to check the
                               // keyword.
    while (count < 1) {

      if (check[0].equals("mkdir")) { // If the keyword match "mkdir" then run
                                      // the Mkdir method.
        CommandMkdir CommandMkdir
        JShell.main(args);
      }
      if (check[0].equals("cd")) { // If the keyword match "cd" then run the Cd
                                   // method.
        JShell.Cd();
        JShell.main(args);
      }
      if (check[0].equals("ls")) { // If the keyword match "ls" then run the Ls
                                   // method.
        JShell.Ls();
        JShell.main(args);
      }
      if (check[0].equals("pwd")) {// If the keyword match "pwd" then run the
                                   // Pwd method.
        JShell.Pwd();
        JShell.main(args);
      }
      if (check[0].equals("mv")) {// If the keyword match "mv" then run the Mv
                                  // method.
        JShell.Mv();
        JShell.main(args);
      }
      if (check[0].equals("cp")) {// If the keyword match "cp" then run the Cp
                                  // method.
        JShell.Cp();
        JShell.main(args);
      }
      if (check[0].equals("cat")) {// If the keyword match "cat" then run the
                                   // Cat method.
        JShell.Cat();
        JShell.main(args);
      }
      if (check[0].equals("get")) {// If the keyword match "get" then run the
                                   // Get method.
        JShell.Get();
        JShell.main(args);
      }
      if (check[0].equals("echo")) {// If the keyword match "echo" then run the
                                    // Echo method.
        JShell.Echo();
        JShell.main(args);
      }
      if (check[0].equals("exit")) {// If the keyword match "exit" then break
                                    // the loop.
        break;
      }
      System.out.println("command not found");
      JShell.main(args); // call the loop again.



    }
    input.close();

  }
}
