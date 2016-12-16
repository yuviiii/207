package Command;

import java.util.Hashtable;
import Command.CommandCat;
import Command.CommandCd;
import Command.CommandEcho;
import Command.CommandExit;
import Command.CommandMan;
import Command.CommandLs;
import Command.CommandMkdir;
import Command.CommandPopd;
import Command.CommandPushd;
import Command.CommandPwd;
import Command.CommandHistory;

/**
 * A command that user can use in the shell.
 * 
 * @author Yitian Ding, Zhifan Wu,Xiyan Zhou,Bojun Wang
 */
abstract public class Command {
  /**
   * commandTable The HashTable of String names mapped to their command class.
   */
  private static Hashtable<String, Command> commandTable;

  /**
   * Return a subclasses represents a specific command.
   * 
   * @param command A string that represents a command.
   * @return A class represents different command class.
   */
  public static Command getCommand(String command) {
    // build a new HashTable that contains command names and command classes.
    commandTable = new Hashtable<String, Command>();
    // put keys and values into the HashTable.
    commandTable.put("cat", new CommandCat());
    commandTable.put("cd", new CommandCd());
    commandTable.put("echo", new CommandEcho());
    commandTable.put("exit", new CommandExit());
    commandTable.put("man", new CommandMan());
    commandTable.put("ls", new CommandLs());
    commandTable.put("mkdir", new CommandMkdir());
    commandTable.put("popd", new CommandPopd());
    commandTable.put("pushd", new CommandPushd());
    commandTable.put("pwd", new CommandPwd());
    commandTable.put("history", new CommandHistory());
    commandTable.put("get", new CommandGet());
    commandTable.put("!Number", new CommandNumber());
    commandTable.put("mv", new CommandMv());
    commandTable.put("cp", new CommandCp());
    commandTable.put("grep", new CommandGrep());

    return commandTable.get(command);
  }

  /**
   * execute this command.
   * 
   * @param arguments An array that represents user's input.
   * @throws ClassNotFoundException If the class does not exist.
   * @throws IllegalAccessException If the current execute method does not have
   *         access to the method.
   * @throws Exception Check exceptions.
   */
  public static String execute(String[] arguments)
      throws ClassNotFoundException, IllegalAccessException, Exception {
    return null;
  }

  /**
   * print the usage of this command.
   */
  public void printUsage() {}

}
