-- CREATE DATABASE world;
-- CREATE SCHEMA world;
-- DROP USER 'worlduser'@'localhost';
-- CREATE USER 'worlduser'@'localhost' IDENTIFIED BY 'world';
-- GRANT ALL PRIVILEGES ON world.* TO 'worlduser'@'localhost';
USE world;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS country_language;
DROP TABLE IF EXISTS language;
DROP TABLE IF EXISTS city_weather;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS country;

CREATE TABLE country (
  country_code CHAR(3) NOT NULL,
  country_code2 CHAR(2) NOT NULL,
  country_name VARCHAR(52) NOT NULL,
  continent ENUM('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') NOT NULL DEFAULT 'Asia',
  country_capital INT DEFAULT NULL,
  country_population INT NOT NULL DEFAULT 0,
  PRIMARY KEY(
    country_code
  )
);  

CREATE TABLE city (
  city_id INT AUTO_INCREMENT NOT NULL,
  country_code CHAR(3) NOT NULL,
  city_name VARCHAR(64) NOT NULL,
  latitude decimal(8,6) DEFAULT NULL,
  longitude decimal(9,6) DEFAULT NULL,  
  city_population INT DEFAULT NULL,
  PRIMARY KEY(
    city_id
  ),
  FOREIGN KEY (country_code) REFERENCES country(country_code)
);
-- ALTER TABLE country ADD CONSTRAINT fk_country_capital FOREIGN KEY (country_capital) REFERENCES city(city_id);

CREATE TABLE city_weather (
  weather_id INT AUTO_INCREMENT NOT NULL,
  city_id INT NOT NULL,
  timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  temperature_low DECIMAL NULL,
  temperature_high DECIMAL NULL,
  PRIMARY KEY(
    weather_id
  ),
  FOREIGN KEY (city_id) REFERENCES city(city_id)
);

CREATE TABLE language(
  language_code CHAR(3) NOT NULL,
  language_code2 CHAR(2) NOT NULL,
  language_name VARCHAR(30) NOT NULL,
  language_notes TEXT,
  PRIMARY KEY(
    language_code
  )
);

CREATE TABLE country_language(
  country_language_id INT AUTO_INCREMENT NOT NULL,
  country_code CHAR(3) NOT NULL,
  language_code CHAR(3) NOT NULL,
  is_official ENUM('T','F') NOT NULL DEFAULT 'F',
  language_percentage DECIMAL(4,1) NOT NULL DEFAULT '0.0',
  PRIMARY KEY(
    country_language_id
  ),
  FOREIGN KEY (country_code) REFERENCES country(country_code),
  FOREIGN KEY (language_code) REFERENCES language(language_code)
);
SET FOREIGN_KEY_CHECKS = 1;
