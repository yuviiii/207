package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandCd;
import Command.CommandPushd;
import System.File;
import System.FileDirectory;
import System.Path;

/**
 * The unit test for the Pushd command.
 * 
 * @author Yitian Ding
 *
 */
public class CommandPushdTest {
  /**
   * Set up a new path.
   */
  Path path = new Path();
  /**
   * Initialize the pushed command.
   */
  CommandPushd p1 = new CommandPushd();
  /**
   * The first Array for test.
   */
  private String[] args1 = {"pushd", "a"};
  /**
   * The second Array for test.
   */
  private String[] args2 = {"pushd", "b"};
  /**
   * The third Array for test.
   */
  private String[] args3 = {"pushd", ".."};
  /**
   * The fourth Array for test.
   */
  private String[] args4 = {"pushd", "."};
  /**
   * The fifth Array for test.
   */
  private String[] argsCd = {"Cd", "~"};
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
    Path.clear();
    File.clear();
    System.setOut(new PrintStream(outContent));
    Path.setPath("a");
    Path.setPath("a/b");
  }

  /**
   * Test the generalPushd method
   */
  @Test
  public void testGeneralPushd() {
    Stack<String> expected = new Stack<String>();
    expected.push("~");
    CommandCd.execute(argsCd);
    CommandPushd.execute(args1);
    assertEquals("~/a", Path.getPath());
    assertEquals(expected, FileDirectory.getDirectoryStack());
  }

  /**
   * Test the ParentPushd method
   */
  @Test
  public void testParentPushd() {
    Stack<String> expected = new Stack<String>();
    expected.push("~");
    expected.push("~/a");
    expected.push("~/a/b");
    while (!FileDirectory.getDirectoryStack().isEmpty()) {
      FileDirectory.popDirectoryStack();
    }
    CommandCd.execute(argsCd);
    CommandPushd.execute(args1);
    CommandPushd.execute(args2);
    CommandPushd.execute(args3);
    assertEquals("~/a", Path.getPath());
    assertEquals(expected, FileDirectory.getDirectoryStack());
  }

  /**
   * Test the NochangePushd method
   */
  @Test
  public void testNoChangePushd() {
    Stack<String> expected = new Stack<String>();
    expected.push("~");
    expected.push("~/a");
    while (!FileDirectory.getDirectoryStack().isEmpty()) {
      FileDirectory.popDirectoryStack();
    }
    CommandCd.execute(argsCd);
    CommandPushd.execute(args1);
    CommandPushd.execute(args4);
    assertEquals("~/a", Path.getPath());
    assertEquals(expected, FileDirectory.getDirectoryStack());
  }

  /**
   * Test the out print stream.
   */

  @Test
  public void testPrintUsage() {
    p1.printUsage();
    assertEquals("pushd" + "\n"
        + "Saves the current working directory by pushing onto directory "
        + "stack and then changes the new current "
        + "working directory to DIR", outContent.toString());
  }

  @After
  public void cleanUpStreams() {
    System.setOut(null);
  }
}

