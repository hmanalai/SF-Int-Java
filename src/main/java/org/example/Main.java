package org.example;

public class Main {
  public static void main(String[] args) {
    Object x = "Hello Java 20 preview world!";
    System.out.println(
      switch (x) {
          case String s when (s.length() > 4) -> "Message is " + s;
          default -> "Message is unknown";
      }
    );
  }
}