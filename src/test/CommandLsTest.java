package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandCd;
import Command.CommandLs;
import System.File;
import System.Path;

/**
 * The unit test for CommanLs class.
 * 
 * @author: Wu Zhifan
 *
 */
public class CommandLsTest {
  // initialize the cd command
  CommandLs ls = new CommandLs();
  // initialize the path
  Path path = new Path();
  /**
   * The different array cases for test.
   */
  private String[] args1 = {"ls"};
  private String[] args2 = {"ls", "~/a"};
  private String[] args4 = {"ls", "~???"};
  /**
   * The print stream for testing the out print.
   */
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setUp() {
    Path.clear();
    File.clear();
    Path.setPath("a");
    Path.setPath("a/b");
    Path.setPath("a/b/c");
    Path.setPath("a/x.txt");
    System.setOut(new PrintStream(outContent));
  }

  /**
   * Test whether there is only the cd command.
   */
  @Test
  public void testNoArgs() {
    String[] rootCommandCd = {"cd", "~"};
    CommandCd.execute(rootCommandCd);
    CommandLs.execute(args1);
    String expectOutput = "a\n";
    assertEquals(expectOutput, outContent.toString());
  }

  /**
   * Test if the arguments is one valid path.
   */
  @Test
  public void testOneArgs() {
    Path.changePath("~/a");
    CommandLs.execute(args2);
    String expectOutput = "b\nx.txt\n";
    assertEquals(expectOutput, outContent.toString());
  }

  /**
   * Test if the arguments is an invalid path.
   */
  @Test
  public void testWrongArgs() {
    Path.changePath("~");
    CommandLs.execute(args4);
    String expectOutput = "~???: No such file or directory\n";
    assertEquals(expectOutput, CommandLs.execute(args4));
  }

  /**
   * Test the out print Stream.
   */
  @Test
  public void testPrintUsage() {
    ls.printUsage();
    assertEquals(
        "ls [-R] [PATH...]\n"
            + "If -R is present, recursively list all subdirectories."
            + "If no paths are given, print the contents (file or directory) "
            + "of the current directory, " + "with a new line following "
            + "each of the content (file or directory)." + "\n"
            + "Otherwise, for each path p, the order listed: " + "\n"
            + "If p specifies a file, print p." + "\n"
            + "If p specifies a directory, print p, a colon, "
            + "then the contents of that directory, then an extra new line."
            + "\n" + "If p does not exist, print a suitable message.\n",
        outContent.toString());
  }

  @After
  public void cleanUpStreams() {
    System.setOut(null);
  }
}
