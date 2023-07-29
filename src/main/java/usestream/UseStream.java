package usestream;

import students2.Student;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UseStream {
  public static void main(String[] args) {
    List<Student> school = List.of(
      Student.of("Fred", 3.2, "Math", "Physics"),
      Student.of("Jim", 2.2, "Journalism"),
      Student.of("Sheila", 3.9, "Math", "Physics", "Astro-physics", "Quantum Mechanics")
    );

    System.out.println(school.stream()
      .flatMap(s -> s.getCourses().stream())
      .distinct()
      .sorted()
//      .forEach(s -> System.out.println(s));
//      .forEach(System.out::println);
      .collect(Collectors.joining( ", ", "Students at our school take: ","")));

    System.out.println(school.stream()
      .mapToDouble(s -> s.getGpa())
      .summaryStatistics());

    // most basic reduction. Get OptionalInt if no
    // data reaches the "end" of the pipeline
    IntStream.iterate(1, x -> x + 1)
      .limit(10)
      .reduce((a, b) -> a + b)
      .ifPresentOrElse(s -> System.out.println("sum is " + s),
        () -> System.out.println("oops, no data!"));
//      .forEach(System.out::println);
    System.out.println("---------------------");
    System.out.println("Sum is " +
      IntStream.iterate(1, x -> x + 1)
      .limit(10)
      .reduce(0, (a, b) -> a + b));

    // multiply zero numbers together, get ONE!!
    // 10^2 -> 100
    // 10^1 -> 10
    // 10^0 -> 1
    // 10^-1 -> 0.1
    // 10^-2 -> 0.01
    // ALSO, for the "Identity" value, applying it to the data / operation
    // must make no difference.
  }
}
