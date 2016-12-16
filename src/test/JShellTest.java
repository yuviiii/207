package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import System.Path;
import driver.JShell;

public class JShellTest {
  /**
   * The different cases for JShell.
   */
  private String args1 = "      pwd       ";
  private String args2 = "cd     a";
  private String args3 = "mkdir        a            b";
  private String args4 = "       mkdir         a";


  @Before
  public void setUp() {
    Path.setPath("~/");
  }

  @After
  public void tearDown() {
    System.setOut(null);
  }

  /**
   * Test different cases contains the white space.
   */
  @Test
  public void testSplitWhiteSpace() {
    assertEquals("pwd", JShell.splitWhiteSpace(args1));
    assertEquals("cd a", JShell.splitWhiteSpace(args2));
    assertEquals("mkdir a b", JShell.splitWhiteSpace(args3));
    assertEquals("mkdir a", JShell.splitWhiteSpace(args4));
  }

}
