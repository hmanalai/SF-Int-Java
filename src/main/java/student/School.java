package student;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

interface IsThisStudentInteresting {
  boolean test(Student s);
//  void doStuff();
}

class SmartStudent implements IsThisStudentInteresting {
  private double threshold;

  public SmartStudent(double threshold) {
    this.threshold = threshold;
  }

  @Override
  public boolean test(Student s) {
    return s.getGpa() > threshold;
  }
}

class EnthusiasticStudent implements IsThisStudentInteresting {

  @Override
  public boolean test(Student s) {
    return s.getCourses().size() > 3;
  }
}

public class School {
  public static void showAll(List<Student> ls) {
    for (Student s : ls) {
      System.out.println("> " + s);
    }
  }

//  public static void showAllSmart(List<Student> ls) {
//    for (Student s : ls) {
//      if (s.getGpa() > 3.0) {
//        System.out.println("> " + s);
//      }
//    }
//  }

//  private static double threshold = 3.0;
//  public static void setThreshold(double t) {
//    // validate?
//    threshold = t;
//  }
  // identify what changes independently, and separate it out
  // identify an "interface" for that "thing that changes"
  //   which will allow the rest of the code to interact as needed
  // find a way to bring the parts back together as/when needed
  //   to perform their function
//  public static List<Student> getSmart(List<Student> ls, double threshold) {
//  public static List<Student> getSmart(List<Student> ls) {
//    List<Student> res = new ArrayList<>();
//    for (Student s : ls) {
//      if (s.getGpa() > threshold) { // "is THIS STUDENT" smart?
//        res.add(s);
//      }
//    }
//    return res;
//  }

//  public static List<Student> getEnthusiastic(List<Student> ls, int thresholdCourseCount) {
//    List<Student> res = new ArrayList<>();
//    for (Student s : ls) {
//      if (s.getCourses().size() > thresholdCourseCount) {
//        res.add(s);
//      }
//    }
//    return res;
//  }

  // If we had STORED the criterion in a field, then OO would
  // call that the "Strategy" pattern.
  // Strategy is less composable / resuable than command, but
  // is the best alternative way to create code reuse without
  // implementation inheritance

  // passing an argument for the purpose of the BEHAVIOR it contains is called
  // in OO -- Command pattern
  // in FP -- one example of a "higher order function"
  public static List<Student> getInteresting(
    List<Student> ls, IsThisStudentInteresting criterion) {

    List<Student> res = new ArrayList<>();
    for (Student s : ls) {
      // NOT a "function literal", if it were, you'd simply say "criterion(s)"
      if (criterion.test(s)) {
        res.add(s);
      }
    }
    return res;
  }

  public static void main(String[] args) throws Throwable {
    List<Student> school = List.of(
      Student.builder()
        .name("Fred").gpa(3.2).course("Math").course("Physics")
        .build(),
      Student.builder()
        .name("Jim").gpa(2.2).course("Journalism")
        .build(),
      Student.builder()
        .name("Sheila").gpa(3.9).course("Math").course("Physics")
        .course("Quantum Mechanics").course("Astrophysics")
        .build()
    );
    System.out.println(school);
    System.out.println("-------------------------");
    showAll(school);
    System.out.println("-------------------------");
//    showAllSmart(school);
//    showAll(getSmart(school));
//    showAll(getSmart(school, 3.0));
    showAll(getInteresting(school, new SmartStudent(3.0)));
    System.out.println("-------------------------");
//    showAll(getSmart(school, 3.5));
//    threshold = 3.5;
//    showAll(getSmart(school));
    showAll(getInteresting(school, new SmartStudent(3.5)));
    System.out.println("-------------------------");
//    showAll(getEnthusiastic(school, 3));
    showAll(getInteresting(school, new EnthusiasticStudent()));
    System.out.println("-------------------------");


    // ??? must be an object (in Java at least)
    // and must implement the IsThisStudentInteresting interface!!!
    // Further -- that interface declares EXACTLY ONE abstract method
    // -- and the compiler KNOWS the exact format of that method (arg types
    //    and return type.
    // SO, IF (but only if) we are ONLY interested in providing an implementation
    // of that one abtract method, we could have a syntax that simplifies the
    // situation...
    // compiler can construct a class from our method (implementing the
    // interface) and then instantiate it :)
    // must give "permission" to do this, using the arrow
    // NOTE, the lambda in Java is an OBJECT expression IF but only if
    // the context defines an interface according to the above rules
    showAll(getInteresting(school,
/*     new IsThisStudentInteresting() {
      public boolean test*/(Student s) -> { // must be intended to be "test"
        return s.getCourses().size() > 3;
      }
    /*}*/
      ));
    System.out.println("-------------------------");
    // context can be assignment, function argument, function return,
    // or a cast (which is odd!)
//    Object aTest = (IsThisStudentInteresting)(Student s) -> {
//      return s.getCourses().size() > 3;
//    };
    // multiple arguments must all be handled "the same"
    // if inferrable, can leave out the type, or can use var
//    IsThisStudentInteresting aTest = (s) -> {
//    IsThisStudentInteresting aTest = (@Deprecated var s) -> {
//      return s.getCourses().size() > 3;
//    };

    // parens optional ONLY for a single, not type specifier, formal param
//    IsThisStudentInteresting aTest = s -> {
//      return s.getCourses().size() > 3;
//    };

    // IF, but only if, body is curly, return, expression, semicolon, curly
    // you can leave out everything except the expression
    IsThisStudentInteresting aTest = s -> s.getCourses().size() > 3  ;

    // Lambdas do NOTHING (functionally) that we can't do any other way
    // BUT, they might provide better readability, partly through brevity
    // more importantly (generally) it can put self-documenting behavior
    // in the place where it interests you.

    showAll(getInteresting(school, s -> s.getCourses().size() > 3 ));



    System.out.println(aTest.getClass().getName());
    Class<?> cl = aTest.getClass();
    Method [] methods = cl.getMethods();
    for (Method m : methods) {
      System.out.println(m);
    }
    Method testIt = aTest.getClass().getMethod("test", Student.class);
    System.out.println(testIt.invoke(aTest, school.get(2)));
  }
}

/*
write code -- of your own choosing ideally
-- create/improve a section method (not using stream, but build
something on the lines of my getInteresting method
-- define an interface like "isinteresting" and implement using
lambdas

Or, if you used streams but aren't totally comfortable with lambdas
maybe play with a variety of lambda formats (note, the interface used
by streams is "Predicate<T>" not "is interesting..." but it behaves
the same

 */