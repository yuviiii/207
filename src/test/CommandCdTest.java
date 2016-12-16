package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandCd;
import System.File;
import System.Path;

/**
 * The unit test for the cd command.
 * 
 * @author Yitian Ding
 *
 */
public class CommandCdTest {
  // initialize the cd command
  CommandCd c1 = new CommandCd();
  // initialize the path
  Path path = new Path();
  /**
   * The different array cases for test.
   */
  private String[] args1 = {"cd"};
  private String[] args2 = {"cd", "a"};
  private String[] args3 = {"cd", "~/a/b"};
  private String[] args4 = {"cd", "b/c"};
  private String[] args5 = {"cd", "a", "b"};
  private String[] args6 = {"cd", "dd"};
  private String[] args7 = {"cd", ".."};
  private String[] args8 = {"cd", "."};
  private String[] initialArgs = {"cd", "~"};

  /**
   * The print stream for testing the out print.
   */
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setUpStreams() {
    Path.clear();
    File.clear();
    System.setOut(new PrintStream(outContent));
    Path.setPath("a");
    Path.setPath("a/b");
    Path.setPath("a/b/c");
  }

  /**
   * Test if there is only the cd command.
   */
  @Test
  public void testNoArgs() {
    String errorMsg = CommandCd.execute(args1);
    assertEquals("Need one more argument", errorMsg);
  }

  /**
   * Test if the arguments is only one directory name.
   */
  @Test
  public void testDirectoryName() {
    CommandCd.execute(initialArgs);
    CommandCd.execute(args2);
    assertEquals("~/a", Path.getPath());
  }

  /**
   * Test if the arguments is an absolute path.
   */
  @Test
  public void testAbsolutePath() {
    CommandCd.execute(initialArgs);
    CommandCd.execute(args3);
    CommandCd.execute(args7);
    assertEquals("~/a", Path.getPath());
  }

  /**
   * Test if the arguments are directory names.
   */
  @Test
  public void testParentDir() {
    CommandCd.execute(initialArgs);
    CommandCd.execute(args3);
    assertEquals("~/a/b", Path.getPath());
  }

  /**
   * Test if the arguments are directory names but not change.
   */
  @Test
  public void testNoChangeDir() {
    CommandCd.execute(initialArgs);
    CommandCd.execute(args3);
    CommandCd.execute(args8);
    assertEquals("~/a/b", Path.getPath());
  }

  /**
   * Test if the arguments is a relative path.
   */
  @Test
  public void testRelativePath() {
    CommandCd.execute(initialArgs);
    CommandCd.execute(args2);
    CommandCd.execute(args4);
    assertEquals("~/a/b/c", Path.getPath());
  }

  /**
   * Test if there are many arguments.
   */
  @Test
  public void testTooManyArgs() {
    String errorMsg = CommandCd.execute(args5);
    assertEquals("Too many arguments.", errorMsg);
  }

  /**
   * Test if there is no such directory.
   */
  @Test
  public void testNoSuchFile() {
    String errorMsg = CommandCd.execute(args6);
    assertEquals("No such file or directory.", errorMsg);
  }

  /**
   * Test the out print Stream.
   */
  @Test
  public void testPrintUsage() {
    c1.printUsage();
    assertEquals(
        ("cd\n" + "Change directory to DIR, "
            + "which may be relative to the current,"
            + "may be a full path. As with Unix, '..' "
            + "means a parent directory and '.' means the current directory. "
            + "The directory must be /, "
            + "the foot of the file system is a single slash: /.\n"),
        outContent.toString());
  }

  @After
  public void cleanUpStreams() {
    System.setOut(null);
  }
}


