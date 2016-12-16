package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Command.CommandCat;
import Command.CommandGet;
import Command.CommandLs;
import System.Edit;
import System.Path;
import System.File;

public class CommandGetTest {

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
  public void setUp() {
    Path.clear();
    File.clear();
    CommandGet get = new CommandGet();
    System.setOut(new PrintStream(usageContent));
  }

  @After
  public void tearDown() {
    CommandGet get = null;
    System.setOut(null);
  }

  /**
   * Test the general execute method for error links.
   * 
   * @throws IOException The URL link is broken.
   */
  @Test
  public void testExecute() {
    String url = "http://www.ub.edu/gilcub/SIMPLE/simple.html";
    String[] arguments = new String[] {"get", url};
    CommandGet.execute(arguments);
    System.out.print("The URL does not exit!");
    assertEquals("The URL does not exit!", usageContent.toString());
    usageContent.reset();
  }

  /**
   * Test the content in the URl link.
   * 
   * @throws IOException The URL link is broken.
   */
  @Test
  public void testSetContent() {
    usageContent.reset();
    String url = "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt";
    String fileName = "074.txt";
    String[] arguments = new String[] {"get", url, ">", "test"};
    String[] args = new String[] {"cat", fileName};
    CommandGet.execute(arguments);
    CommandCat.execute(args);
    assertEquals("Error: fileName doesn't found\n", usageContent.toString());
    usageContent.reset();
  }

  /**
   * Test the current working directory.
   * 
   * @throws Exception
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
  @Test
  public void testAddFileDirectory() {
    String url = "http://www.google.com/robots.txt";
    String[] arguments = new String[] {"get", url, ">", "test"};
    Edit.check(arguments);
    String[] args = new String[] {"ls"};
    CommandLs.execute(args);
    assertEquals("robots.txt" + "\n" + "test" + "\n", usageContent.toString());
    usageContent.reset();
  }

  /**
   * Test the out print stream.
   */
  @Test
  public void testPrintUsage() {
    System.out.println(
        "get URL" + "\n" + "URL is a web address. Retrieve the file at "
            + "that URL and add it to the current working directory.");
    assertEquals(
        "get URL" + "\n" + "URL is a web address. Retrieve the file at "
            + "that URL and add it to the current working directory." + "\n",
        usageContent.toString());
  }
}
