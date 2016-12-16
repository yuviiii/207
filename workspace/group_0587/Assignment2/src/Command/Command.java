package Command;

import java.util.ArrayList;
abstract public class Command {
  
  private String[] arguments;
  
  String cat = "cat";
  String cd = "cd";
  String echo = "echo";
  String echoappend = "echoappend";
  String exit = "exit";
  String ls = "ls";
  String man = "man";
  String mkdir = "mkdir";
  String popd = "popd";
  String pushed = "pushed";
  String pwd = "pwd";
  String history = "history";
  
  private ArrayList<String> command_list;
  
  public abstract void execute(String[] arguments);
  
  public void usage(){
    command_list.add(cat);
    command_list.add(cd);
    command_list.add(echo);
    command_list.add(echoappend);
    command_list.add(exit);
    command_list.add(ls);
    command_list.add(man);
    command_list.add(mkdir);
    command_list.add(popd);
    command_list.add(pushed);
    command_list.add(pwd);
    command_list.add(history);
    
    if (!command_list.contains(arguments)){
      System.out.println("The command you inputted is not a valid command.");
      
    }
  }

}
