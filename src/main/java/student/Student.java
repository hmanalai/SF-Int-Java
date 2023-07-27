package student;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Student {
  // let's make this immutable... (using final fields is likely smart anyway)
  private /*final*/ String name;
  private double gpa;
  // courses... array? fixed length, maybe not. List? allows duplicates :(
  // go with Set, to prevent duplicates
  private Set<String> courses;

  private Student(String name, double gpa, Set<String> courses) {
    this.name = name;
    this.gpa = gpa;
    this.courses = courses;
  }

/*
  // mutable objects provided by a caller are not safe for us to use!!!
  public Student(String name, double gpa, Set<String> courses) {
    this.name = name;
    this.gpa = gpa;
//    this.courses = courses; // really bad, see comment above
//    this.courses = new HashSet<>(courses); // our own copy, but still mutable :)

    //does this work?? Not sure, but ugly anyway
    String[] theCourses = (String[])courses.toArray();
    // Set<StringBuilder> still permits changes to the individual StringBuilders!!!
    this.courses = Set.of(theCourses); // unmodifiable Set :) since Java 9

    // For List prior to Java 9, look at Arrays.asList
    // -- not really immutable!!! Can call set on this!!! (but not add/remove/clear)

    // This is a "proxy"
    // still vulnerable to caller provided structure being changed
//    this.courses = Collections.unmodifiableSet(courses);
  }
*/

  public static class Builder {
    private Student target = new Student();
    { target.courses = new HashSet<>(); }
    private Builder() {}

    public Builder name(String n) {
      target.name = n;
      return this;
    }

    public Builder gpa(double gpa) {
      target.gpa = gpa;
      return this;
    }

    public Builder course(String c) {
      target.courses.add(c);
      return this;
    }

    public Student build() {
      // validate!!!
      if (!Student.isValid(target.name, target.gpa)) {
        throw new IllegalArgumentException();
      }
      Student result = target;
      target = new Student();
      return result;
    }
  }

  public static boolean isValid(String name, double gpa) {
    return name != null && name.length() > 0
      && gpa >= 0 && gpa <= 5.0;
  }

  public static Builder builder() {
    return new Builder();
  }

  private Student() {
  }

  public String getName() {
    return name;
  }

  public double getGpa() {
    return gpa;
  }

  // handing a mutable reference to our internal data is unsafe!
//  someStudent.getCourses().add("Underwater basket weaving")
  public Set<String> getCourses() {
    return courses; // OK if our courses is immutable, e.g. Set.of..
    // wrap for the caller, but allow ourselves to change it?
//    return Collections.unmodifiableSet(courses);
  }

  // NO NO NO!!! we want immutability
//  public void addCourse(String course) {
//    courses.add(course);
//  }

  public Student withCourse(String c) {
    Set<String> newCourses = new HashSet<>(courses);
    newCourses.add(c);
    return new Student(this.name, this.gpa,
      Collections.unmodifiableSet(newCourses));
  }

  @Override
  public String toString() {
    return "Student{" +
      "name='" + name + '\'' +
      ", gpa=" + gpa +
      ", courses=" + courses +
      '}';
  }
}

/*
Lab 2 suggestions (ideally, do what interests you!!!)
- make a factory (or builder if you're up for it!) and prevent
  use of constructor by clients
- make your Student immutable :)
- if you didn't complete "print all students & print smart students"
  maybe do that now

  "Spaced Repetition"
 */
