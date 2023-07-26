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
 */