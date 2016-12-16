package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandEcho;
import System.File;
import System.Path;;

/**
 * The unit test for the cd command.
 * 
 * @author Bojun Wang, Xiyan Zhou
 *
 */
public class CommandEchoTest {
  /**
   * Initialize the Echo command.
   */
  private CommandEcho echoCmd;
  /**
   * The first Array for test.
   */
  private String[] record1 = createNewArray("echo", "a", ">", "b");
  /**
   * The second Array for test.
   */
  private String[] record2 = createNewArray("echo", "c", ">>", "b");
  /**
   * The Array for test.
   */
  private String[] record6 = createNewArray("echo", "c", ">>", "m");
  /**
   * The Array for test.
   */
  private String[] record3 = createTwoArray("echo", "m");
  /**
   * The Array for test.
   */
  private String[] record4 = createThreeArray("echo", "a", "a");
  /**
   * The Array for test.
   */
  private String[] record5 = createTwoArray("echo", " ");
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
    echoCmd = new CommandEcho();
  }

  @After
  public void tearDown() {
    echoCmd = null;
    System.setOut(null);
    System.setErr(null);
  }

  /**
   * Test the out print stream.
   */
  @Test
  public void testPrintUsage() {
    outContent.reset();
    echoCmd.printUsage();
    assertEquals("Echo\nUser could use echo command to edit or create file\n",
        outContent.toString());
  }

  /**
   * Test the first helper method
   */
  @Test
  public void testdealOneMark() {
    CommandEcho.execute(record1);
    String actualOutputA = File.getFileInfo(Path.getPath() + "/" + "b");
    assertEquals("a", actualOutputA);
  }

  /**
   * Test the second helper method
   */
  @Test
  public void testdealTwoMarks() {
    // only two marks
    CommandEcho.execute(record6);
    String actualOutputB = File.getFileInfo(Path.getPath() + "/" + "m");
    assertEquals("c", actualOutputB);

    // call one mark then call two mark to append
    CommandEcho.execute(record1);
    CommandEcho.execute(record2);
    assertEquals("ac", File.getFileInfo(Path.getPath() + "/" + "b"));
  }

  /**
   * Test the execute String method
   */
  @Test
  public void testExecuteString() {
    // echo general string
    outContent.reset();
    CommandEcho.execute(record3);
    assertEquals("m\n", outContent.toString());
    outContent.reset();

    // echo string containing space
    CommandEcho.execute(record4);
    assertEquals("a a\n", outContent.toString());
    outContent.reset();

    // echo empty string
    CommandEcho.execute(record5);
    assertEquals(" \n", outContent.toString());
    outContent.reset();
  }

}
