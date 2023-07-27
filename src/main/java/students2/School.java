package students2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
// A a = someB "B is assignable to A" (kinda A is parent of B)
public class School {
                                                            // vvv this is "contravariance"
  // function arguments constrain the caller!
  // make them more general, your function is more reusable
  // return type constrains my implementation
  public static <E> List<E> filter(Iterable<E> in, Predicate<? super E> criterion) {
    List<E> results = new ArrayList<>();
    for (E s : in) {
      if (criterion.test(s)) {
        results.add(s);
      }
    }
    return results;
  }

  public static <E, F> List<F> map(Iterable<E> in, Function<E, F> op) {
    List<F> res = new ArrayList<>();
    for (E e : in) {
      res.add(op.apply(e));
    }
    return res;
  }

  public static String letterGrade(double gpa) {
    if (gpa > 3.6) return "A";
    if (gpa > 3.1) return "B";
    if (gpa > 2.6) return "C";
    return "D";
  }
  public static void main(String[] args) {
    List<Student> school = List.of(
      Student.of("Fred", 3.2, "Math", "Physics"),
      Student.of("Jim", 2.2, "Journalism"),
      Student.of("Sheila", 3.9, "Math", "Physics", "Astro-physics", "Quantum Mechanics")
    );

    System.out.println(school);
    System.out.println("---------------");
    System.out.println(filter(school, s -> s.getGpa() > 3));
    System.out.println("---------------");

    List<String>  names = List.of("Alice", "Bob", "Susan", "Bill");
    System.out.println(filter(names, s -> s.length() > 4));
    System.out.println("---------------");

//    List<CharSequence> names2 = List.of("Alice", "Bob", "Susan", "Bill", new StringBuilder("Marianne"));
    List<String> names2 = List.of("Alice", "Bob", "Susan", "Bill");
    Predicate<CharSequence> pcs = s -> s.length() > 4;
    System.out.println(filter(names2, pcs));
    System.out.println("---------------");

    System.out.println(map(school,
      s -> "Student: " + s.getName() + " takes " + s.getCourses().size()
           + " courses and has grade " + letterGrade(s.getGpa())));

  }
}

// one student -> one "output"
// Result apply(Argument a);
// this one is "java.util.function.Function<A, R>
// interface Function<A, R> {
//   R apply(A a);
// }
// zero arguments zero result -> java.lang.Runnable
// two arguments of the same type and int result -> Comparator<A>
// zero arguments a result -> java.util.function.Supplier<R>
// argument and no result -> Consumer<A>
// function constrained to have result type == argument type ...Operator<T>
// Two arguments? Bi (Binary) prefix
// Primitives?? autoboxing (inefficient), or prefixes:
// ToInt, ToLong, ToDouble (primitve return)
// Int, Long, Double (primitive argument, except IntSupplier etc.)
