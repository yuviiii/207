package test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import System.FileDirectory;

/**
 * Intergration for The File Directory test.
 * 
 * @author Xiyan Zhou
 *
 */
public class FileDirectoryTest {
  /**
   * Initialize the directory string.
   */
  String directory;
  /**
   * Initialize a string for the actual output.
   */
  String actualOutput;
  /**
   * Initialize a string represent the expected output.
   */
  String expectedOutput;
  /**
   * Initialize a file directory.
   */
  FileDirectory dir;
  /**
   * Initialize the directory stack
   */
  Stack<String> dirStack;
  /**
   * Initialize a string.
   */
  static String usrLocalBin = "/usr/local/bin";
  /**
   * Initialize a string.
   */
  static String devNull = "/dev/null";

  @Before
  public void setUp() {}

  @After
  public void tearDown() {
    dir = null;
  }

  /**
   * Test the set directory method.
   */
  @Test
  public void testSetDir() {
    StringBuilder sb = new StringBuilder();
    sb.append(directory);
    String actualOutput = FileDirectory.setDir(directory);
    String expectedOutput = sb.toString();
    assertEquals(actualOutput, expectedOutput);
  }

  /**
   * test the pop directory stack.
   */
  @Test
  public void testPopDirDirectoryStack() {
    Stack<String> dirStack = FileDirectory.getDirectoryStack();
    dirStack.push(devNull);
    dirStack.push(usrLocalBin);
    assertEquals(usrLocalBin, dirStack.pop());
    assertEquals(devNull, dirStack.pop());
  }

  /**
   * Test the get directory stack.
   */
  @Test
  public void testGetDirectoryStack() {
    Stack<String> actualOutput = FileDirectory.getDirectoryStack();
    Stack<String> expectedOutput = FileDirectory.getDirectoryStack();;
    assertEquals(actualOutput, expectedOutput);
  }

}
