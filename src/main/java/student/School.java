package student;

import java.util.ArrayList;
import java.util.List;

interface IsThisStudentInteresting {
  boolean test(Student s);
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
      if (criterion.test(s)) {
        res.add(s);
      }
    }
    return res;
  }

  public static void main(String[] args) {
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
  }
}
