package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandHistory;
import Command.CommandNumber;

public class CommandNumberTest {
  /**
   * The command of number
   */
  private CommandNumber cmdNumber;
  /**
   * The string for test
   */
  private String set1 = "aaa";
  /**
   * The string input
   */
  private String input = "!1";
  /**
   * The print stream for testing the out print.
   */
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * Set up the number command and the print stream.
   * 
   * @throws Exception
   */
  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
    cmdNumber = new CommandNumber();
  }

  @After
  public void tearDown() {
    cmdNumber = null;
    System.setOut(null);
    System.setErr(null);
  }

  /**
   * Test the execute method
   */
  @Test
  public void testExecute() {
    // General case
    outContent.reset();
    CommandHistory.setInfo(set1);
    String[] testArray = CommandNumber.execute(input);
    assertEquals("aaa", testArray[0]);
  }

  /**
   * Test the out print Stream.
   */
  @Test
  public void testPrintUsage() {
    cmdNumber.printUsage();
    assertEquals(
        "!number" + "\n" + "find out the specific history and execute it",
        outContent.toString());
  }

}
