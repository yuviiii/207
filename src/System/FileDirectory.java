package System;

import java.util.Stack;

/**
 * A class used to manage directory stack.
 */
public class FileDirectory {

  /**
   * A directory that represents a file directory.
   */
  private FileDirectory directory;

  /**
   * A stack that store all directories.
   */
  private static Stack<String> dirStack = new Stack<String>();

  /**
   * A method used to set current directory.
   */
  public static String setDir(String directory) {
    // create a StringBuilder.
    StringBuilder sb = new StringBuilder();
    sb.append(directory);
    return sb.toString();
  }

  /**
   * A method used to get the directory.
   * 
   * @return The current directory.
   */
  public FileDirectory getDir() {
    return directory;
  }

  /**
   * Push directories into dirStack.
   * 
   * @param args The directories that should be pushed into the stack.
   */
  public static void setDirectoryStack(String args) {
    dirStack.push(args);
  }

  /**
   * Pop directories from dirStack.
   */
  public static void popDirectoryStack() {
    dirStack.pop();
  }

  /**
   * Return the directory stack.
   * 
   * @return The directory stack.
   */
  public static Stack<String> getDirectoryStack() {
    return FileDirectory.dirStack;
  }

  /**
   * Return the current file working directory.
   * 
   * @param Filedirectory The current working directory of a file.
   */
  public FileDirectory(String Filedirectory) {}
}
