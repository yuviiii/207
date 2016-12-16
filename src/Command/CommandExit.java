package Command;

/**
 * A command used to exit the program.
 * 
 * @author Yitian Ding, Zhifan Wu,Xiyan Zhou,Bojun Wang
 */
public class CommandExit extends Command {
  /**
   * The execute method to execute the exit command and exit the program.
   * 
   * @param args
   */
  public static String execute(String[] args) {
    String errorMsg = "";
    // exit the system.
    System.exit(0);
    return errorMsg;
  }

  /**
   * Print the usage of exit command.
   */
  public void printUsage() {
    System.out.println("exit");
    System.out.print("Quit the program.");
  }
}
