package System;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class File {
  
  protected static StringBuilder content;
  protected static String fileName;
  protected static String txt;
  
  
  public File() {
    content = new StringBuilder();
  }
  
  public static String readFile(String fileName){
    try{
      InputStreamReader fr = new InputStreamReader(new FileInputStream(fileName));
      BufferedReader br = new BufferedReader(fr);
      String line = null;
      while ((line = br.readLine() )!= null){
        content.append(line);
        content.append(System.lineSeparator());
      }
      fr.close();
    } catch (Exception e){
      System.out.print(e.getMessage());
    }
    txt = content.toString();
    return txt;   
  }

  /**
   * set the content of the file.
   * 
   * @param contentToSet A string of any size
   */
  public void setContent(String contentToSet) {
    this.content = new StringBuilder(contentToSet);
  }

  /**
   * Append to the content of this file.
   * 
   * @param contentToAppend A string of any size
   */
  public void appendToContent(String contentToAppend) {
    this.content.append(contentToAppend);
  }
}
