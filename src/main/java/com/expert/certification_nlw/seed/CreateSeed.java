package com.expert.certification_nlw.seed;

import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateSeed {

  private final JdbcTemplate jdbcTemplate;

  public CreateSeed(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public static void main(String[] args) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost:5434/nlw");
    dataSource.setUsername("postgres");
    dataSource.setPassword("postgres");

    CreateSeed createSeed = new CreateSeed(dataSource);
    createSeed.run(args);
  }

  public void run(String... args) {
    executeSqlFile("src/main/resources/create.sql");
  }

  private void executeSqlFile(String filePath) {
    try {
      String sqlScript = new String(Files.readAllBytes(Paths.get(filePath)));
      System.out.println("Executing SQL script: " + filePath);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}
