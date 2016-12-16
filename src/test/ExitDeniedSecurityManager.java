package test;

import java.security.Permission;

/**
 * A helper class for the test command exit.
 *
 */
public class ExitDeniedSecurityManager extends SecurityManager {
  /**
   * Using the security exception to test if it exit the program.
   *
   */
  public static final class ExitSecurityException extends SecurityException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Initialize a status integer which you use for system.exit
     */
    private final int status;

    /**
     * Initialize the status.
     * 
     * @param status
     */
    public ExitSecurityException(final int status) {
      this.status = status;
    }

    /**
     * Get the integer status
     * 
     * @return status
     */
    public int getStatus() {
      return this.status;
    }
  }

  /**
   * Check exception to exit the program.
   */
  @Override
  public void checkExit(final int status) {
    throw new ExitSecurityException(status);
  }

  @Override
  public void checkPermission(final Permission perm) {}
}
