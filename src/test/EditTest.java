package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandHistory;
import Command.CommandNumber;
import System.Edit;
import System.File;
import System.Path;

/**
 * The unit test for a redirection class
 * 
 * @author Xiyan Zhou
 *
 */
public class EditTest {
  /**
   * The PrintStream for test the out print.
   */
  private final ByteArrayOutputStream usageContent =
      new ByteArrayOutputStream();
  /**
   * The Array for test.
   */
  private String[] record1 = createTwoArray("echo", "m");

  /**
   * The Array for test.
   */
  private String[] record2 = createNewArray("echo", "a", ">", "b");

  /**
   * The second Array for test.
   */
  private String[] record3 = createNewArray("echo", "c", ">>", "b");

  /**
   * The Array for test.
   */
  private String[] record4 = createNewArray("man", "pwd", ">", "test");

  /**
   * The Array for test.
   */
  private String[] record5 = createNewArray("man", "man", ">>", "test");

  /**
   * The Array for the cat command.
   */
  private String[] array1 = createTwoArray("cat", "b");

  /**
   * The Array for the cat command.
   */
  private String[] array2 = createTwoArray("cat", "test");
  /**
   * The string for test the string check method.
   */
  private String number = "!2";

  /**
   * Set up the print stream.
   * 
   * @throws Exception
   */
  @Before
  public void setUp() {
    Path.clear();
    File.clear();
    CommandHistory.clearInfo();
    System.setOut(new PrintStream(usageContent));
  }

  @After
  public void tearDown() {
    System.setOut(null);
  }

  /**
   * The method for create a new array
   * 
   * @param command The command String for command name.
   * @param Directory The String that stores the directory.
   * @return array The array to test the cat command.
   */
  private String[] createTwoArray(String command, String Directory) {
    String[] array = new String[2];
    array[0] = command;
    array[1] = Directory;
    return array;
  }

  /**
   * The method for create a new array
   * 
   * @param command The command String for command name.
   * @param Directory The String that stores the directory.
   * @param mark The ">" or ">>" mark.
   * @param fileName The fileName of a file.
   * @return array The array to test the cat command.
   */
  private String[] createNewArray(String command, String Directory, String mark,
      String fileName) {
    String[] array = new String[4];
    array[0] = command;
    array[1] = Directory;
    array[2] = mark;
    array[3] = fileName;
    return array;
  }

  /**
   * Test cases for the string check, which takes a string input.
   * 
   * @throws Exception
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
  @Test
  public void testStringCheck() {
    // test the general case
    CommandHistory.setInfo("aaa");
    CommandHistory.setInfo("~/");
    Edit.stringCheck(number);
    String[] testArray = CommandNumber.execute(number);
    assertEquals("~/", testArray[0]);
  }

  /**
   * Test cases for check method.
   * 
   * @throws ClassNotFoundException Do not find the class.
   * @throws IllegalAccessException
   * @throws Exception
   */
  @Test
  public void testCheck() {
    // Determine if it does not contain a redirection
    Edit.check(record1);
    assertEquals("m\n", usageContent.toString());
    usageContent.reset();


    // Determine if it contains the redirection
    Edit.check(record2);
    Edit.check(array1);
    assertEquals("a\n", usageContent.toString());
    usageContent.reset();
  }


  /**
   * Test cases for redirection.
   * 
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws Exception
   */
  @Test
  public void testOutFileExecute() {
    // Deal with one mark
    Edit.check(record2);
    Edit.check(array1);
    assertEquals("a\n", usageContent.toString());
    usageContent.reset();

    // Deal with two mark
    Edit.check(record2);
    Edit.check(record3);
    Edit.check(array1);
    assertEquals("a\nc\n", usageContent.toString());
    usageContent.reset();
  }

  /**
   * Test cases for one mark test.
   * 
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws Exception
   */
  @Test
  public void testDealOneMark() {
    // Deal with one mark
    Edit.check(record2);
    Edit.check(array1);
    assertEquals("a\n", usageContent.toString());
    usageContent.reset();
  }

  /**
   * Test cases for two marks test.
   * 
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws Exception
   */
  @Test
  public void testDealTwoMarks() {
    // Deal with two mark with out new line separate
    Edit.check(record2);
    Edit.check(record3);
    Edit.check(array1);
    assertEquals("a\nc\n", usageContent.toString());
    usageContent.reset();

    // Deal with two mark with new line separate
    Edit.check(record4);
    Edit.check(record5);
    Edit.check(array2);
    assertEquals("PWD" + "\n" + "Print the current working "
        + "directory.(including the whole path)" + "\n\n" + "Man" + "\n"
        + "Print documentation for CMD.\n", usageContent.toString());
    usageContent.reset();
  }

}
