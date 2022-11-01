package world;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import world.exception.ConfigurationException;

/**
 * Class used to provide property access to the 
 * application configuration.
 */
public final class Configuration {
  /**
   * The configured instance of Properties
   */
  protected static Properties properties;

  /**
   * Static constructor. Ensures that properties/file
   * is loaded once.
   */
  static {
    ClassLoader loader = Thread.currentThread()
                               .getContextClassLoader();
    properties = new Properties();
    try (InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
      properties.load(resourceStream);
    } catch (IOException e) {
    }
  }
  
  /**
   * An explicit constructor to prevent instance creation.
   */
  private Configuration() {
  }

  /**
   * Retrieves the available properties instance.
   * @return The global static/shared properties instance.
   */
  public static Properties getProperties() {
    if (properties == null) {
      properties = new Properties();
    }
    return properties;
  }
  
  /**
   * Searches for the property with the specified key 
   * in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns null if the property is not found.
   * @param key the hash table key.
   * @return the value in this property list with the specified key value.
   */
  public static String getProperty(String key) {
    return(getProperties().getProperty(key));
  }

  /**
   * Searches for the property with the specified key 
   * in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns null if the property is not found.
   * @param key the hash table key.
   * @param defaultValue a default value
   * @return the value in this property list with the specified key value.
   */
  public static String getProperty(String key, String defaultValue) {
    return(getProperties().getProperty(key, defaultValue));
  }
  
  /**
   * Reads the embedded resource as string and returns the value.
   * @param resourceName
   * @return The contents of the specified resource / value.
   */
  public static String getResourceAsString(String resourceName) {
    StringBuilder resource = new StringBuilder();
    ClassLoader loader = Thread.currentThread()
                               .getContextClassLoader();
    try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
      try (InputStreamReader reader = new InputStreamReader(resourceStream, StandardCharsets.UTF_8)) {
        try (BufferedReader buffer = new BufferedReader(reader)) {
          String line;
          while ((line = buffer.readLine()) != null) {
            resource.append(line)
                    .append("\n");
          }
        }
      } 
    } catch (IOException e) {
      throw new ConfigurationException(String.format("Failed to load resource (%s)", resourceName), e);
    }
    return resource.toString();
  }
}
