package Command;

import System.File;

public class CommandCat extends Command{
  private String txt;
  
  @Override
  public void execute(String[] arguments){
    if (arguments.length == 1) {
      System.out.println("The cat command should take at least 1 file name.");
    }
    for (int i = 1; i < arguments.length; i++) {
      System.out.println(File.readFile(arguments[i]));
      if (i == arguments.length - 1){
        System.out.println("\n" + "\n" + "\n");
      }
    }
  }
  
  public void usage(){
    System.out.println("cat");
    System.out.println("Display the contents of files in the shell");
  }

  
}
