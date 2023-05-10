package world.exception;

/**
 * A custom exception wrapper for database actions.
 */
public class DbException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public DbException() {
  }

  public DbException(String message) {
    super(message);
  }

  public DbException(Throwable cause) {
    super(cause);
  }

  public DbException(String message, Throwable cause) {
    super(message, cause);
  }

  public DbException(String message, Throwable cause, boolean enableSuppression,
                     boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

