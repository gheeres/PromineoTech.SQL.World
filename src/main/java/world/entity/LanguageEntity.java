package world.entity;

/**
 * Represents details about a language.
 */
public class LanguageEntity extends LanguageBaseEntity {
  private String code2;
  private String notes;

  public LanguageEntity(String code) {
    super(code);
  }

  public LanguageEntity(LanguageBaseEntity language) {
    super(language);
  }

  public String getCode2() {
    return code2;
  }

  public LanguageEntity setCode2(String code2) {
    this.code2 = code2;
    return this;
  }

  public String getNotes() {
    return notes;
  }

  public LanguageEntity setNotes(String notes) {
    this.notes = notes;
    return this;
  }
}
