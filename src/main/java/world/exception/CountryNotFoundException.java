package world.exception;

public class CountryNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private String code;
  
  public CountryNotFoundException(String code) {
    super(String.format("The requested country (%s) was not found.", code));
    setCode(code);
  }
  
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
