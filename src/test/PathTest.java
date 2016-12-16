package test;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import System.File;
import System.Path;

/**
 * Integration test for path class.
 * 
 * @author Yitian Ding
 *
 */
public class PathTest {

  Path path = new Path();

  @Before
  public void setUpStreams() {
    Path.clear();
    File.clear();
    Path.changePath("~");
    Path.addPath("~");
    Path.setPath("a");
    Path.setPath("a/b");
    Path.setPath("a/b/c");
  }

  /**
   * Test for checking if it is a absolute path.
   */
  @Test
  public void testAbsolutPath() {
    Boolean myBool = path.absolutePath("~/a");
    assertEquals(true, myBool);
  }

  /**
   * Test if it is a relative path.
   */
  @Test
  public void testRelativePath() {
    Boolean myBool = path.relativePath("b/c");
    assertEquals(true, myBool);
  }

  /**
   * Test for setting up a path
   */
  @Test
  public void testSetPath() {
    Path.changePath("~");
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("~");
    expected.add("~/a");
    expected.add("~/a/b");
    expected.add("~/a/b/c");
    assertEquals(expected, Path.getPathArray());
  }

  /**
   * Test for adding a new path.
   */
  @Test
  public void testAddPath() {
    Path.addPath("~/a");
    boolean bool = true;
    assertEquals(bool, Path.getPathArray().contains("~/a"));
  }

  /**
   * Test for changing a path.
   */
  @Test
  public void testChangePath() {
    String expected = "~/a/b";
    Path.changePath("~/a/b");
    assertEquals(expected, Path.getPath());
  }

  /**
   * Test for getting the current path.
   */
  @Test
  public void testGetPath() {
    String expected = "~/a/b";
    Path.changePath("~/a/b");
    assertEquals(expected, Path.getPath());
  }

  /**
   * Test for getting a created path.
   */
  @Test
  public void testGetCreatedPath() {
    Path.changePath("~");
    Path.setPath("a");
    assertEquals("~/a", Path.getCreatedPath());
  }

  /**
   * Test for getting a path array.
   */
  @Test
  public void GetPathArray() {
    Path.changePath("~");
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("~");
    expected.add("~/a");
    expected.add("~/a/b");
    expected.add("~/a/b/c");
    assertEquals(expected, Path.getPathArray());
  }
}
