package System;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * The File class represent the file that contains fileName and a hashTable.
 *
 */
public class File {
  /**
   * The String fileName that represent the file name.
   */
  protected static String fileName;
  /**
   * The string txt represent the file information.
   */
  protected static String txt;
  /**
   * The path String represent the current Path.
   */
  private String path;

  /**
   * The hashTable to store file information.
   */
  public static Hashtable<String, String> hashtable =
      new Hashtable<String, String>();

  /**
   * The hashTable to map the information to the key
   * 
   * @param a The information.
   * @param b The key.
   */
  public static void setFileInfo(String a, String b) {
    // store information(string a) to a key(string b).
    hashtable.put(a, b);
  }

  /**
   * Return the file information in the hashtable.
   * 
   * @param key The key in the hashtable.
   * @return String The error message or the information which the key points to
   *         in the hashtable.
   */
  public static String getFileInfo(String key) {
    // get value from hashtable, so that user can use file name to get the
    // info inside it.
    if (hashtable.containsKey(key)) {
      return hashtable.get(key);
    } else {
      // if there is no such filename in hashtable, return error.
      return "Error: fileName doesn't found\n";
    }

  }

  /**
   * This method is used to put together the fileNmae and dot txt and set up the
   * file information.
   */
  public static void addFile() {
    Path.setPath(fileName + ".txt");
    setFileInfo(fileName, txt);
  }


  /**
   * Getter method that returns the name of the file
   * 
   * @param None
   * @return name Name of the file
   */
  public String getName() {
    // Return the name of the file
    return File.fileName;
  }

  /**
   * The constructor of the File class
   * 
   * @param name The fileName of a file.
   */
  public File(String name) {
    fileName = name;
  }

  /**
   * Find and return the File object given the file's name and it's parent
   * directory
   * 
   * @param fileName Name of the file
   * @param parentDir Parent directory of the file
   */
  public static File findFile(String fileName) {
    // Search the given parent directory for the file
    ArrayList<String> pathArray = Path.getPathArray();
    File file = new File(fileName);
    for (int i = 0; i < pathArray.size(); i++) {
      if (pathArray.get(i).toString().endsWith(file.getName())) {
        return file;
      }
    }
    // Return null if the file is not found
    return null;
  }

  /**
   * Delete the content in a file
   * 
   * @param file The file we inputed.
   */
  public static void deleteContents(File file) {}

  /**
   * Get the working directory of a file.
   * 
   * @return The path of this file.
   */
  public String getPath() {
    return path;
  }

  /**
   * Set the working directory of a file
   * 
   * @param path The path of a file.
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Determine if it is a file, not a directory.
   * 
   * @param myPath
   * @return
   */
  public static boolean isFile(String myPath) {
    return (hashtable.containsKey(myPath));
  }

  public static void clear() {
    hashtable = new Hashtable<String, String>();
  }

  /**
   * The method to move a file into a new working directory.
   * 
   * @param fullOldPath The old working directory of this file.
   * @param fullNewPath The new working directory of a file.
   */
  public static void moveFile(String fullOldPath, String fullNewPath) {
    // Get the content of the file.
    String myValue = hashtable.get(fullOldPath);
    // Remove the old file.
    hashtable.remove(fullOldPath);
    hashtable.put(fullNewPath, myValue);
  }

  /**
   * The method to copy the file content into a new file.
   * 
   * @param fullOldPath The working directory of the previous file.
   * @param fullNewPath The working directory of the new file.
   */
  public static void copyFile(String fullOldPath, String fullNewPath) {
    // Get the content of the file
    String myValue = hashtable.get(fullOldPath);
    hashtable.put(fullNewPath, myValue);
  }
}
