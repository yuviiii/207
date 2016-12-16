package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandCat;
import System.Edit;
import System.File;
import System.Path;

/**
 * Integration test for CatCommand.
 * 
 * @author Bojun Wang, Xiyan Zhou
 * 
 */
public class CommandCatTest {
  /**
   * Initialize the cat command.
   */
  private CommandCat catCmd;
  /**
   * The first String for test.
   */
  private String[] record1 = createNewArray("echo", "a", ">", "b");
  /**
   * The second String for test.
   */
  private String[] record2 = createNewArray("echo", "c", ">>", "b");
  /**
   * The second String for test.
   */
  private String[] record3 = createNewArray("echo", "m", ">", "d");
  /**
   * The second String for test.
   */
  private String[] record4 = createNewArray("mkdir", "a", ">", "b.txt");
  /**
   * The Array for the cat command.
   */
  private String[] array1 = createTwoArray("cat", "b");
  /**
   * The Array for the cat command.
   */
  private String[] array2 = createThreeArray("cat", "b", "d");
  /**
   * The Array for the cat command.
   */
  private String[] array3 = createTwoArray("cat", "b.txt");
  /**
   * The PrintStream for test the out print.
   */
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

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
   * @param fileName1 The fileName of a file.
   * @param fileName2 The fileName of a file.
   * @return array The array to test the cat command.
   */
  private String[] createThreeArray(String command, String fileName1,
      String fileName2) {
    String[] array = new String[3];
    array[0] = command;
    array[1] = fileName1;
    array[2] = fileName2;
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
   * Set up the command and the print stream.
   * 
   * @throws Exception
   */
  @Before
  public void setUp() {
    Path.clear();
    File.clear();
    System.setOut(new PrintStream(outContent));
    catCmd = new CommandCat();
  }

  @After
  public void tearDown() {
    catCmd = null;
    System.setOut(null);
    System.setErr(null);
  }

  /**
   * Test the execute method.
   * 
   * @throws Exception
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
  @Test
  public void testExecute() {
    Path.clear();
    File.clear();
    // The echo command which has one ">" mark
    Edit.check(record1);
    Edit.check(array1);
    assertEquals("a" + "\n", outContent.toString());
    outContent.reset();

    // The echo command which has two ">" mark
    Path.clear();
    File.clear();
    Edit.check(record1);
    Edit.check(record2);
    Edit.check(array1);
    assertEquals("a\nc" + "\n", outContent.toString());
    outContent.reset();

    // Cat two files
    Path.clear();
    File.clear();
    Edit.check(record1);
    Edit.check(record3);
    Edit.check(array2);
    assertEquals("a\n\n\n\nm" + "\n", outContent.toString());
    outContent.reset();

    // Cat a file with empty content
    Path.clear();
    File.clear();
    Edit.check(record4);
    Edit.check(array3);
    assertEquals("", outContent.toString());
    outContent.reset();
  }

  /**
   * Test the out print stream.
   */
  @Test
  public void testPrintUsage() {
    catCmd.printUsage();
    assertEquals("cat\nDisplay the contents of files in the shell\n",
        outContent.toString());

  }

}
