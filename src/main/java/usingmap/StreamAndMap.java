package usingmap;

import java.util.Map;

record Course(String name, String description) { }

public class StreamAndMap {
  public static void main(String[] args) {
    Map<String, Course> data = Map.of(
      "Math 101", new Course("Mathematics", "Mind-bending"),
      "Physics 103", new Course("Physics for genius", "Even more mind-bending")
      );

    data.entrySet().stream()
      .forEach(e -> System.out.println("Entry has key " + e.getKey()
        + " which has value " + e.getValue()));
  }
}
