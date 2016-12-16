package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Command.CommandExit;
import test.ExitDeniedSecurityManager.ExitSecurityException;

/**
 * The unit test for the exit command.
 * 
 * @author Xiyan Zhou
 *
 */
public class CommandExitTest {
  /**
   * Initialize the Exit command.
   */
  CommandExit ex = new CommandExit();
  /**
   * The first String for test.
   */
  private String[] a;
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
    System.setSecurityManager(new ExitDeniedSecurityManager());
    System.setOut(new PrintStream(outContent));
  }

  /**
   * Test the execute method
   */
  @Test
  public void testExecute() {
    try {
      CommandExit.execute(a);
      Assert.fail("Expected exit");
    } catch (ExitSecurityException e) {
      int status = e.getStatus();
      Assert.assertEquals(0, status);
    }
  }

  /**
   * Test the out print stream.
   */
  @Test
  public void testPrintUsage() {
    ex.printUsage();
    assertEquals("exit\n" + "Quit the program.", outContent.toString());
  }
}
