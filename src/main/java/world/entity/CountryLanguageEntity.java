package world.entity;

/**
 * Information about a language spoken in a country.
 */
public class CountryLanguageEntity {
  private CountryBaseEntity country;
  private LanguageBaseEntity language;
  private boolean isOfficial;
  private Float percentage;
  
  public CountryBaseEntity getCountry() {
    return country;
  }
  
  public CountryLanguageEntity setCountry(CountryBaseEntity country) {
    this.country = country;
    return this;
  }
  
  public LanguageBaseEntity getLanguage() {
    return language;
  }
  
  public CountryLanguageEntity setLanguage(LanguageBaseEntity language) {
    this.language = language;
    return this;
  }
  
  public boolean isOfficial() {
    return isOfficial;
  }
  
  public CountryLanguageEntity setOfficial(boolean isOfficial) {
    this.isOfficial = isOfficial;
    return this;
  }
  
  public Float getPercentage() {
    return percentage;
  }

  public CountryLanguageEntity setPercentage(Float percentage) {
    this.percentage = percentage;
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("[%s] %s (%3.1f)",
                         getCountry().getCode(), 
                         getLanguage().getName(),
                         getPercentage());
  }
}
