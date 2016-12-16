package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandPwd;
import System.File;
import System.Path;

/**
 * The unit test for the pwd command.
 * 
 * @author Xiyan Zhou
 *
 */
public class CommandPwdTest {
  /**
   * The PrintStream for test the out print.
   */

  private final ByteArrayOutputStream usageContent =
      new ByteArrayOutputStream();

  /**
   * Set up the command and the print stream.
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    Path.clear();
    File.clear();
    new Command.CommandPwd();
    System.setOut(new PrintStream(usageContent));
  }

  @After
  public void tearDown() throws Exception {
    System.setOut(null);
  }

  /**
   * Test the execute method
   */
  @Test
  public void testExecute() throws Exception {
    // No directory case
    String[] pwdArray = {"pwd"};
    CommandPwd.execute(pwdArray);
    String expectedOutput2 = "~/\n";
    assertEquals(expectedOutput2, usageContent.toString());
    usageContent.reset();

    // General case
    String[] array = createNewArray("mkdir", "a");
    Command.CommandMkdir.execute(array);
    String[] cdArray = createNewArray("cd", "a");
    Command.CommandCd.execute(cdArray);
    String[] mkdirArray = createNewArray("mkdir", "b");
    Command.CommandMkdir.execute(mkdirArray);
    String[] newCdArray = createNewArray("cd", "b");
    Command.CommandCd.execute(newCdArray);
    String expectedOutput = "~/a/b\n";
    CommandPwd.execute(pwdArray);
    assertEquals(expectedOutput, usageContent.toString());
    usageContent.reset();

  }

  /**
   * The method for create a new array
   * 
   * @param command The command String for command name.
   * @param Directory The String that stores the directory.
   * @return array The array to test the pwd command.
   */
  private String[] createNewArray(String command, String Directory) {
    String[] array = new String[2];
    array[0] = command;
    array[1] = Directory;
    return array;
  }

  /**
   * Test the out print stream.
   */
  @Test
  public void testPrintUsage() {
    System.out.print("PWD" + "\n" + "Print the current working directory.");
    assertEquals("PWD" + "\n" + "Print the current working directory.",
        usageContent.toString());
  }

}
