package superiterable;

import students2.Student;

import java.util.List;

public class UseSuperIterable {
  public static void main(String[] args) {
    List<Student> school = List.of(
      Student.of("Fred", 3.2, "Math", "Physics"),
      Student.of("Jim", 2.2, "Journalism"),
      Student.of("Sheila", 3.9, "Math", "Physics", "Astro-physics", "Quantum Mechanics")
    );
    SuperIterable<Student> siStudent = new SuperIterable<>(school);

    SuperIterable<String> sis = new SuperIterable<>(
      List.of("Fred", "Jim", "Sheila")
    );

//    for (String s : sis) {
//      System.out.println("> " + s);
//    }

    // forEvery illustrates what forEach already did :)
    sis.forEach(s -> System.out.println("> " + s));

//    SuperIterable<String> shortString = sis
//      .filter(s -> s.length() < 6)
//      .map(s -> s + " is a short name!");
//    for (String s : shortString) {
//      System.out.println(">> " + s);
//    }

    sis
      .filter(s -> s.length() < 6)
      .map(s -> s + " is a short name!")
      .forEach(s -> System.out.println(">> " + s));

    System.out.println("--------------------------");

    siStudent
//      .filter(s -> s.getGpa() < 3.6)
      .flatMap(s -> new SuperIterable<>(s.getCourses()))
//      .map(s -> s.getCourses())
      .forEach(s -> System.out.println("course: " + s));

    System.out.println("--------------------------");
    school.stream()
      .filter(s -> s.getGpa() < 3.6)
      .flatMap(s -> s.getCourses().stream())
//      .map(s -> s.getCourses())
      .forEach(s -> System.out.println("course: " + s));
  }
}
