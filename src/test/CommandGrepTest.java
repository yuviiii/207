package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandCd;
import Command.CommandEcho;
import Command.CommandGrep;
import Command.CommandMkdir;

/**
 * Integration test for CatCommand.
 * 
 * @author Bojun Wang
 * 
 */
public class CommandGrepTest {
  /**
   * Initialize the cat command.
   */
  private CommandGrep grepCmd;
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
    System.setOut(new PrintStream(outContent));
    grepCmd = new Command.CommandGrep();
  }

  @After
  public void tearDown() {
    grepCmd = null;
    System.setOut(null);
    System.setErr(null);
  }

  /**
   * Test the execute method.
   * 
   * @throws Exception
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
  @Test
  public void testExecute() {
    outContent.reset();
    CommandEcho.execute("echo a > b".split(" "));
    CommandGrep.execute("grep a b".split(" "));
    assertEquals("a" + "\n", outContent.toString());
    outContent.reset();

  }

  /**
   * Test the out print stream.
   */
  @Test
  public void testPrintUsage() {
    outContent.reset();
    grepCmd.printUsage();
    assertEquals(
        "grep\nThe grep command will find out the specific line f"
            + "rom a file or recursively go through a di"
            + "rectory and find file that contains specific content.\n",
        outContent.toString());

  }

  /**
   * Test the executeCase2 method.
   * 
   * @throws Exception
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
  @Test
  public void testExecuteCase2() {
    outContent.reset();
    CommandEcho.execute("echo a > b".split(" "));
    CommandGrep.executeCase2("grep -R a b".split(" "), 1);
    assertEquals("~/b: a" + "\n", outContent.toString());
    outContent.reset();

    outContent.reset();
    CommandMkdir.execute("mkdir a".split(" "));
    CommandCd.execute("cd a".split(" "));
    CommandEcho.execute("echo a > b".split(" "));
    CommandMkdir.execute("mkdir a2".split(" "));
    CommandCd.execute("cd a2".split(" "));
    CommandEcho.execute("echo aa > b2".split(" "));
    CommandCd.execute("cd ..".split(" "));
    CommandGrep.executeCase2("grep -R a a".split(" "), 1);
    assertEquals("~/a/b: a" + "\n" + "~/a/a2/b2: aa" + "\n",
        outContent.toString());
    outContent.reset();
  }

}
