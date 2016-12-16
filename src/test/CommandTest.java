package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.Command;
import Command.CommandCat;
import Command.CommandCd;
import Command.CommandEcho;
import Command.CommandExit;
import Command.CommandMan;
import Command.CommandLs;
import Command.CommandMkdir;
import Command.CommandPopd;
import Command.CommandPushd;
import Command.CommandPwd;
import Command.CommandHistory;

/**
 * The unit test for the command.
 * 
 * @author Xiyan Zhou
 *
 */
public class CommandTest {

  /**
   * Set up the command
   * 
   */
  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  /**
   * Test the getCommand method
   */
  @Test
  public void testGetCommand() {
    assertTrue(Command.getCommand("exit") instanceof CommandExit);
    assertTrue(Command.getCommand("cat") instanceof CommandCat);
    assertTrue(Command.getCommand("cd") instanceof CommandCd);
    assertTrue(Command.getCommand("echo") instanceof CommandEcho);
    assertTrue(Command.getCommand("history") instanceof CommandHistory);
    assertTrue(Command.getCommand("ls") instanceof CommandLs);
    assertTrue(Command.getCommand("man") instanceof CommandMan);
    assertTrue(Command.getCommand("mkdir") instanceof CommandMkdir);
    assertTrue(Command.getCommand("popd") instanceof CommandPopd);
    assertTrue(Command.getCommand("pushd") instanceof CommandPushd);
    assertTrue(Command.getCommand("pwd") instanceof CommandPwd);
  }
}
