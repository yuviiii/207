package System;

import java.util.ArrayList;

import Command.CommandCd;

/**
 * A class that represents and manages paths.
 */
public class Path {

  /**
   * The first parameter of string path.
   */
  protected static String path = "~";

  /**
   * The initial Directory string.
   */
  protected static String initialDirectory;

  /**
   * The String filename represent the name of file in the path.
   */
  protected static String fileName;

  /**
   * The initial String path.
   */
  protected static String createdPath = "~/";

  /**
   * The path array to contain all the paths.
   */
  protected static ArrayList<String> pathArray = new ArrayList<String>();

  /**
   * The constructor of the path class initializing the initial path.
   */
  public Path() {
    if (!pathArray.contains("~")) {
      Path.pathArray.add("~");
    }
  }


  /**
   * Determine whether it is an absolute path or an relative path
   * 
   * @param path The path of the Jshell.
   * @return boolean if True it is an absolute path, False otherwise.
   */

  public boolean absolutePath(String path) {
    return path != null && path.charAt(0) == '~' && path.charAt(1) == '/';
  }

  /**
   * Determine whether it is an absolute path or an relative path
   * 
   * @param path The path of the Jshell.
   * @return boolean if True it is an relative path, False otherwise.
   */
  public boolean relativePath(String path) {
    return !absolutePath(path);
  }

  /**
   * set a path for the Jshell.
   * 
   * @param a The string which is a combination of path.
   */
  public static void setPath(String a) {
    createdPath = path + "/" + a;
    if (!pathArray.contains(createdPath)) {
      pathArray.add(createdPath);
    }
  }

  /**
   * Add a path to the path Array.
   * 
   * @param a The current Path.
   */
  public static void addPath(String a) {
    if (!pathArray.contains(a)) {
      pathArray.add(a);
    }
  }

  /**
   * Change the current Path.
   * 
   * @param a
   */
  public static void changePath(String a) {
    Path.path = a;
  }


  /**
   * Get the path of the Jshell.
   * 
   * @return path The whole path of the current working directory.
   */
  public static String getPath() {
    return Path.path;

  }

  /**
   * Get the initial Directory of the path.
   * 
   * @return initialDirectory The initialDirectory in the path.
   */
  public static String getInitialDirectory() {
    return initialDirectory;
  }

  /**
   * Get the file name in the path
   * 
   * @return fileName The file name in the path.
   */
  public static String getFileName() {
    return fileName;
  }

  /**
   * In the current working directory, return the created path depends on the
   * previous path.
   * 
   * @return createdPath The string which represents the created path.
   */
  public static String getCreatedPath() {
    return createdPath;
  }


  /**
   * In the current working directory, return the array list representation of
   * the path.
   * 
   * @return pathArray The array list which contains the whole path.
   */
  public static ArrayList<String> getPathArray() {
    if (!pathArray.contains("~")) {
      pathArray.add("~");
    }
    return pathArray;
  }

  public static void clear() {
    pathArray = new ArrayList<String>();
    path = "~";
  }

  /**
   * Move the current Path to a new Path.
   * 
   * @param oldPath The current Path to move.
   * @param targetPath The new Path to be moved to.
   */
  public static void movePaths(String oldPath, String targetPath) {
    // Get the Path array
    ArrayList<String> allPaths = Path.getPathArray();
    // loop the elements in the path array
    for (int i = 0; i < allPaths.size(); i++) {
      // Get the Path
      String path = allPaths.get(i);
      boolean changePath = false;
      // Get the path to be changed with
      if (path.contains(oldPath + "/") || (path.equals(oldPath))) {
        changePath = true;
      }
      if (changePath) {
        // Set up an empty path
        String restPath = "";
        if (!path.equals(oldPath)) {
          restPath = path.substring(oldPath.length() + 1);
        }
        // Set the new Path
        String newPath = targetPath;
        if (restPath.length() != 0) {
          newPath = newPath + "/" + restPath;
        }
        // Set the new path to path array and cd into it
        if ((Path.getPath()).equals(allPaths.get(i))) {
          pathArray.set(i, newPath);
          String[] target = {"cd", newPath};
          CommandCd.execute(target);
        } else {
          pathArray.set(i, newPath);
        }
        // If it is a file
        if (File.isFile(path)) {
          File.moveFile(path, newPath);
        }
      }
    }
  }

  /**
   * Copy the current path to a new Path
   * 
   * @param oldPath The current Path
   * @param targetPath The new Path
   * @param dirLength The length of the directory.
   */
  public static void copyPath(String oldPath, String targetPath,
      int dirLength) {
    // copy the path array
    @SuppressWarnings("unchecked")
    ArrayList<String> copyPathArray = (ArrayList<String>) pathArray.clone();
    int i;
    // loop the path array
    for (i = 0; i < copyPathArray.size() - 1; i++) {
      // Determine if the path contains the old path
      if ((copyPathArray.get(i)).contains(oldPath)) {
        // Get the different part of the path
        int start = oldPath.length() - dirLength;
        int end = (copyPathArray.get(i)).length();
        String leftChars =
            (String) (copyPathArray.get(i)).subSequence(start, end);
        // Set up a new path
        String newPath = targetPath + leftChars;
        // Add the new Path to the path array
        pathArray.add(newPath);
      }
    }
  }
}
