package com.corrector.model.csv;

public record CsvUserRecord(String std) {
  public String stdLowercase() {
    return std.toLowerCase();
  }
}
