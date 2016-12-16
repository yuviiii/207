package test;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import System.File;

/**
 * Integration test for File.
 * 
 * @author Xiyan Zhou
 *
 */
public class FileTest {
  /**
   * Initialize the txt string represent a test file.
   */
  private String txt = "txt";
  /**
   * Initialize a hashtable to match the file name.
   */
  private Hashtable<String, String> hashtable = new Hashtable<String, String>();
  /**
   * Initialize a string.
   */
  private String a = "a";

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  /**
   * Test the set file information method.
   */
  @Test
  public void testSetFileInfo() {
    File.setFileInfo(txt, a);
    hashtable.put(txt, a);
    assertEquals("txt", File.getFileInfo(a));
  }

  /**
   * Test the get file information.
   */
  @Test
  public void testGetFileInfo() {
    File.setFileInfo(a, txt);
    hashtable.put(a, txt);
    assertEquals("txt", File.getFileInfo(a));
  }

  /**
   * Test the add file method.
   */
  @Test
  public void testAddFile() {
    File.setFileInfo(a, txt);
    assertEquals("txt", File.getFileInfo(a));
  }

}
