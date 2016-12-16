package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.Command;
import Command.CommandMan;
import Command.CommandPwd;

/**
 * The unit test for the man command.
 * 
 * @author Xiyan Zhou
 *
 */
public class CommandManTest {
  /**
   * Initialize the man command.
   */
  private CommandMan manCmd;

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
    manCmd = new CommandMan();
    System.setOut(new PrintStream(usageContent));
  }

  @After
  public void tearDown() throws Exception {
    manCmd = null;
    System.setOut(null);
    System.setErr(null);
  }

  /**
   * Test the execute method
   */
  @Test
  public void testExecute() {
    String[] array = createNewArray("man", "pwd");
    CommandMan.execute(array);
    assertEquals(
        "PWD" + "\n" + "Print the current working "
            + "directory.(including the whole path)" + "\n",
        usageContent.toString());
    usageContent.reset();
  }

  /**
   * Test the out print stream.
   */
  @Test
  public void testPrintUsage() {
    usageContent.reset();
    manCmd.printUsage();
    assertEquals("Man" + "\n" + "Print documentation for CMD." + "\n",
        usageContent.toString());


  }

  /**
   * The method for create a new array
   * 
   * @param command The command String for command name.
   * @param Directory The String that stores the directory.
   * @return array The array to test the man command.
   */
  private String[] createNewArray(String command, String Directory) {
    String[] array = new String[2];
    array[0] = command;
    array[1] = Directory;
    return array;
  }

  /**
   * Test the validCommand method.
   */
  @Test
  public void testValidCommand() {
    String[] array = createNewArray("man", "pwd");
    assertEquals(true, CommandMan.validCommand(array));
  }

  /**
   * Test the get command method.
   */
  @Test
  public void testGetCommand() {
    String[] array = createNewArray("man", "pwd");
    Command command = new CommandPwd();
    assertEquals(command.getClass(),
        CommandMan.getCommand(array[1]).getClass());
  }
}
