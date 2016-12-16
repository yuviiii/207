package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import Command.CommandCd;
import Command.CommandEcho;
import Command.CommandMkdir;
import Command.CommandCp;
import System.File;
import System.Path;

public class CommandCpTest {

  CommandCp cp = new CommandCp();

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  /**
   * The string array to test cp.
   */
  private String[] args1 = {"cp", "a", "b"};
  /**
   * The string array to test cp.
   */
  private String[] args2 = {"cp", "apple.txt", "~/c"};
  /**
   * The string array to test cp.
   */
  private String[] args3 = {"cp", "~/b", "apple.txt"};
  /**
   * The string array to test cp.
   */
  private String[] args4 = {"cp", "a"};

  /**
   * Set up the command and clear the path and file.
   * 
   * @throws Exception
   */
  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
    Path.clear();
    File.clear();;
  }

  /**
   * Test method for testing moving directories.
   */
  @Test
  public void testMoveDirectories() {
    Path.clear();
    File.clear();
    String[] cdPath = {"cd", "~/b/a"};
    CommandMkdir.execute(args1);
    CommandCp.execute(args1);
    CommandCd.execute(cdPath);
    assertEquals("~/b/a", Path.getPath());
    Boolean myBool = Path.getPathArray().contains("~/a");
    assertEquals(true, myBool);
  }

  /**
   * Test cases for testing moving the files.
   */
  @Test
  public void testMoveFiles() {
    Path.clear();
    File.clear();
    String[] mkdirPath = {"mkdir", "~/c"};
    String[] echoPath = {"echo", "apple", ">", "apple.txt"};
    CommandMkdir.execute(mkdirPath);
    CommandEcho.execute(echoPath);
    CommandCp.execute(args2);
    Boolean myBool = Path.getPathArray().contains("~/c/apple.txt");
    assertEquals(true, myBool);
    Boolean myBool2 = Path.getPathArray().contains("~/apple.txt");
    assertEquals(true, myBool2);
  }

  /**
   * Test methods for testing moving the directory to file.
   */
  @Test
  public void testMoveDirToFile() {
    Path.clear();
    File.clear();
    String[] mkdirPath = {"mkdir", "~/b"};
    String[] echoPath = {"echo", "apple", ">", "apple.txt"};
    CommandMkdir.execute(mkdirPath);
    CommandEcho.execute(echoPath);
    String msg = CommandCp.execute(args3);
    assertEquals("apple.txt: is a file not a directory.", msg);
  }

  /**
   * Test the out print Stream.
   */
  @Test
  public void testPrintUsage() {
    cp.printUsage();
    assertEquals("cp OLDPATH NEWPATH\n" + "Copy item OLDPATH to NEWPATH. "
        + "Both OLDPATH and NEWPATH may be relative to the current"
        + " directory or may be full paths.  If NEWPATH is a directory, "
        + "copy the item into the directory. If OLDPATH is a directory, "
        + "copy the contents. \n", outContent.toString());
  }

  /**
   * Test method for test the error message.
   */
  @Test
  public void testNeedOneMoreArg() {
    Path.clear();
    File.clear();
    String msg = CommandCp.execute(args4);
    assertEquals("Need 3 arguments.", msg);
  }
}
