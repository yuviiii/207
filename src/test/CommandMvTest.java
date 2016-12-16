package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import Command.CommandCd;
import Command.CommandEcho;
import Command.CommandMkdir;
import Command.CommandMv;
import System.File;
import System.Path;

public class CommandMvTest {

  CommandMv mv = new CommandMv();

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  private String[] args1 = {"mv", "a", "b"};
  private String[] args2 = {"mv", "apple.txt", "~/c"};
  private String[] args3 = {"mv", "~/b", "apple.txt"};
  private String[] args4 = {"mv", "a"};
  private String[] args5 = {"mv", "mm", "pig"};

  /**
   * set up the test
   */
  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
    Path.clear();
    File.clear();;
  }

  /**
   * test case of move directory
   */
  @Test
  public void testMoveDirectories() {
    Path.clear();
    File.clear();
    String[] cdPath = {"cd", "~/b/a"};
    CommandMkdir.execute(args1);
    CommandMv.execute(args1);
    CommandCd.execute(cdPath);
    assertEquals("~/b/a", Path.getPath());
  }

  /**
   * test case of move file
   */
  @Test
  public void testMoveFiles() {
    Path.clear();
    File.clear();
    Boolean myBool;
    String[] mkdirPath = {"mkdir", "~/c"};
    String[] echoPath = {"echo", "apple", ">", "apple.txt"};
    CommandMkdir.execute(mkdirPath);
    CommandEcho.execute(echoPath);
    CommandMv.execute(args2);
    myBool = Path.getPathArray().contains("~/c/apple.txt");
    assertEquals(true, myBool);
  }

  /**
   * test of trying to move a directory to a file
   */
  @Test
  public void testMoveDirToFile() {
    Path.clear();
    File.clear();
    String[] mkdirPath = {"mkdir", "~/b"};
    String[] echoPath = {"echo", "apple", ">", "apple.txt"};
    CommandMkdir.execute(mkdirPath);
    CommandEcho.execute(echoPath);
    String msg = CommandMv.execute(args3);
    assertEquals("apple.txt: is a file not a directory.", msg);
  }

  /**
   * test of input need one more argument
   */
  @Test
  public void testNeedOneMoreArg() {
    Path.clear();
    File.clear();
    String msg = CommandMv.execute(args4);
    assertEquals("Need 3 arguments.", msg);
  }

  /**
   * test of move directory and rename
   */
  @Test
  public void testMoveDirAndRename() {
    Path.clear();
    File.clear();
    String[] mkdirPath = {"mkdir", "~/mm"};
    CommandMkdir.execute(mkdirPath);
    CommandMv.execute(args5);
    boolean myBool = Path.getPathArray().contains("~/pig");
    assertEquals(true, myBool);
  }

  /**
   * Test the out print Stream.
   */
  @Test
  public void testPrintUsage() {
    mv.printUsage();
    assertEquals(
        "mv\n" + "Move item OLDPATH to NEWPATH. Both OLDPATH and NEWPATH"
            + " may be relative to the current directory or may be full paths. "
            + "If NEWPATH is a directory, move the item into the directory.\n",
        outContent.toString());
  }
}
