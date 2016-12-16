package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandCd;
import Command.CommandMkdir;
import System.File;
import System.Path;

public class CommandMkdirTest {
  // Initialize a new command mkdir.
  CommandMkdir mkdir = new CommandMkdir();
  /**
   * The different array cases for test.
   */
  private String[] args1 = {"mkdir", "a"};
  private String[] args2 = {"mkdir", "a/b"};
  private String[] args3 = {"mkdir", "a", "b"};
  private String[] args4 = {"cd", "~/a"};

  /**
   * The print stream for testing the out print.
   */
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setUp() {
    Path.clear();
    File.clear();
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void tearDown() {}

  /**
   * Test if the arguments is only one directory name.
   */
  @Test
  public void testDirectoryName() {
    Path.clear();
    File.clear();
    CommandMkdir.execute(args1);
    CommandCd.execute(args4);
    assertEquals("~/a", Path.getPath());
  }

  /**
   * Test if the arguments are many directory names.
   */
  @Test
  public void testTooManyArgs() {
    Path.clear();
    File.clear();
    CommandMkdir.execute(args3);
    ArrayList<String> array = new ArrayList<String>();
    array.add("~");
    array.add("~/a");
    array.add("~/b");
    assertEquals(array, Path.getPathArray());
  }

  /**
   * Test the out print Stream.
   */
  @Test
  public void testPrintUsage() {
    mkdir.printUsage();
    assertEquals(
        "mkdir DIR ..." + "\n" + "Create directories, each of which "
            + "may be relative to the current directory or may be a full path.",
        outContent.toString());
  }

  /**
   * Test if the arguments is a relative path.
   */
  @Test
  public void testMkdirRelative() {
    Path.clear();
    File.clear();
    String[] cdPath = {"cd", "~/a/b"};
    CommandMkdir.execute(args1);
    CommandMkdir.execute(args2);
    CommandCd.execute(cdPath);
    assertEquals("~/a/b", Path.getPath());
  }
}
