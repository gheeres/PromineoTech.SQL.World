package world.exception;

public class DbException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public DbException(String message, Throwable exception) {
    super(message, exception);
  }
}
