package Command;

import driver.JShell;
import System.FileDirectory;
import System.FileSystem;

public class CommandPwd extends Command {
  private FileSystem fs;
  
  public void execute(String[] arguments){
    if (arguments.length >= 1) {
      System.out.println("Command Pwd does not take any arguments");
    }
    System.out.println(fs.getCwd() + "\n");
  }
  
  public void usage(){
    System.out.println("PWD");
    System.out.println("Print the current working directory.");
  }
}
