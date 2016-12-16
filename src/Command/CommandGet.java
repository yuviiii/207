package Command;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import System.File;
import System.Path;

/**
 * A command get that user can use in the shell.
 * 
 * @author Xiyan Zhou
 *
 */
public class CommandGet extends Command {
  /**
   * Set up the error message string.
   */
  private static String errorMsg = "";

  /**
   * The method that execute the get command.
   * 
   * @param arguments The array we inputed.
   * @return errorMsg Return the error message.
   */
  public static String execute(String[] arguments) {
    errorMsg = "";
    try {
      if (arguments.length == 1) {
        errorMsg = "The get command should take at least 1 URL.";
        // determine the length of the get command
      } else if (arguments.length > 2) {
        errorMsg = "Get Command only takes one argument!";
      } else {
        // the string representation of the URL
        String urlString = arguments[1];
        URL url = new URL(urlString);
        // Connect the URL to the link
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        // Get the web site code
        int responseCode = huc.getResponseCode();
        // determine if it is an error web page.
        if (responseCode == 404) {
          errorMsg = "The URL does not exit!";
        } else {
          // Get the content of the URL page
          setContent(urlString);
        }
      }
    } catch (IOException e) {
      errorMsg = "Error: URL is broken";
    }
    return errorMsg;
  }

  /**
   * Set the content on the web page to current working directory
   * 
   * @param urlString The URL string representation.
   * @throws IOException
   */
  public static void setContent(String urlString) throws IOException {
    // determine the URL
    if (!urlString.endsWith(".html") && !urlString.endsWith(".txt")) {
      errorMsg = "URL must end with .txt or .html";
    } else {
      URL url = new URL(urlString);
      HttpURLConnection huc = (HttpURLConnection) url.openConnection();
      // change the current working directory
      addFileDirectory(urlString, url, huc);
    }
  }

  /**
   * The method to change the current working directory to the URL.
   * 
   * @param urlString The URL string representation
   * @param url The URL
   * @param huc The HttpURLConnection
   * @throws IOException
   */
  public static void addFileDirectory(String urlString, URL url,
      HttpURLConnection huc) throws IOException {
    String output = "";
    try {
      // Set up a printStream to store the content
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(outContent);
      PrintStream old = System.out;
      System.setOut(ps);
      // open the stream and put it into BufferedReader
      BufferedReader br =
          new BufferedReader(new InputStreamReader(huc.getInputStream()));
      // The String for input
      String inputLine;
      // Get the new String for the content
      while ((inputLine = br.readLine()) != null)
        // Print out the content
        System.out.println(inputLine);
      br.close();
      // Store the content in a output String
      output = outContent.toString();
      System.setOut(old);
      // The String in the URL file
      String fileInfo = output;
      // create the file
      int lastSlash = urlString.lastIndexOf("/");
      // Get the file name
      String fileName = urlString.substring(lastSlash + 1, urlString.length());
      File file = File.findFile(fileName);
      // Get the current Path
      String currentPath = Path.getPath();
      String key = currentPath + "/" + fileName;
      // If the file exists
      if (file != null) {
        File.deleteContents(file);
        File.setFileInfo(key, fileInfo);
      } else {
        File.setFileInfo(key, fileInfo);
      }
      if (currentPath.endsWith(fileName)) {
        errorMsg = "You are already in this directory!";
      } else {
        // add the file to the current working directory
        String newPath = currentPath + "/" + fileName;
        Path.setPath(fileName);
        Path.addPath(newPath);
      }
    } catch (IOException e) {
      errorMsg = "Error: URL is broken";
    }
    System.out.println(output);
  }

  /**
   * Print the usage of Command cat.
   */
  public void printUsage() {
    System.out.println("get URL");
    System.out.println("URL is a web address. Retrieve the file at "
        + "that URL and add it to the current working directory.");
  }
}
