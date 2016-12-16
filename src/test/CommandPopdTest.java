package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Stack;

import Command.CommandPopd;
import Command.CommandPushd;
import System.FileDirectory;
import System.Path;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The unit test for the popd command.
 * 
 * @author Yitian Ding
 *
 */
public class CommandPopdTest {
  /**
   * The Array for the cat command.
   */
  private String[] args1 = {"popd"};
  /**
   * Initialize the Popd command.
   */
  CommandPopd p1 = new CommandPopd();
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
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    Path.setPath("a");
  }

  /**
   * Test the StackEmpty method
   */
  @Test
  public void testStackEmpty() {
    while (!FileDirectory.getDirectoryStack().isEmpty()) {
      FileDirectory.popDirectoryStack();
    }
    assertEquals("directory stack empty", CommandPopd.execute(args1));
  }

  /**
   * Test the General method
   */
  @Test
  public void testGeneral() {
    Stack<String> expected = new Stack<String>();
    String[] push = {"pushd", "a"};
    CommandPushd.execute(push);
    CommandPopd.execute(args1);
    assertEquals("~", Path.getPath());
    assertEquals(expected, FileDirectory.getDirectoryStack());
  }


  /**
   * Test the out print stream.
   */
  @Test
  public void testPrintUsage() {
    p1.printUsage();
    assertEquals("popd\nRemove the entry from the directory"
        + " stack, and cd into it\n", outContent.toString());
  }

  @After
  public void cleanUpStreams() {
    System.setOut(null);
  }
}
