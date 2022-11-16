package world.entity;

/**
 * Contains basic information about a spoken language.
 */
public class LanguageBaseEntity {
  private String code;
  private String name;
  
  public LanguageBaseEntity(String code) {
    this.code = code;
  }

  public LanguageBaseEntity(LanguageBaseEntity language) {
    if (language != null) {
      this.code = language.getCode();
      setName(language.getName());
    }
  }

  public String getName() {
    return name;
  }

  public LanguageBaseEntity setName(String name) {
    this.name = name;
    return this;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return String.format("[%s] %s", getCode(), getName());
  }
}
