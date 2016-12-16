package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandHistory;
import System.File;
import System.Path;

/**
 * The unit test for the history command.
 * 
 * @author Bojun Wang
 *
 */
public class CommandHistoryTest {
  /**
   * Initialize the history command.
   */
  private CommandHistory historyCmd;
  /**
   * The first String for test.
   */
  private String set1 = "aaa";
  /**
   * The second String for test.
   */
  private String set2 = "history";

  /**
   * The method for create a new array
   * 
   * @param command The command String for command name.
   * @param Directory The String that stores the directory.
   * @return array The array to test the history command.
   */
  private String[] createNewArray(String command, String Directory) {
    String[] array = new String[2];
    array[0] = command;
    array[1] = Directory;
    return array;
  }

  /**
   * The method for create a new array
   * 
   * @param command2 The command String for command name.
   *
   * @return array The array to test the history command.
   */
  private String[] createNewArray2(String command2) {
    String[] array2 = new String[1];
    array2[0] = command2;
    return array2;
  }

  /**
   * The PrintStream for test the out print.
   */

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Set up the command and the print stream.
   * 
   * @throws Exception
   */
  @Before
  public void setUp() {
    Path.clear();
    File.clear();
    CommandHistory.clearInfo();
    System.setOut(new PrintStream(outContent));

    historyCmd = new CommandHistory();
  }

  @After
  public void tearDown() {
    historyCmd = null;
    System.setOut(null);
    System.setErr(null);
  }

  /**
   * Test the execute method
   */
  @Test
  public void testExecute() {
    outContent.reset();
    String[] array1 = createNewArray2("history");
    String[] array2 = createNewArray("history", "1");

    CommandHistory.setInfo(set1);
    CommandHistory.setInfo(set2);
    CommandHistory.execute(array1);
    assertEquals("1.aaa\n2.history\n", outContent.toString());

    outContent.reset();
    CommandHistory.execute(array2);
    assertEquals("2.history\n", outContent.toString());
  }

  /**
   * Test the out print stream.
   */
  @Test
  public void testPrintUsage() {
    outContent.reset();
    historyCmd.printUsage();
    assertEquals("History\nPrint out the history of used command\n",
        outContent.toString());
  }

  /**
   * Test the out setInfo method.
   */
  @Test
  public void testSetInfo() {
    outContent.reset();
    CommandHistory.setInfo(set1);
    assertEquals("aaa", CommandHistory.infoArray.get(0));
  }

}
