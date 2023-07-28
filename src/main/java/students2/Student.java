package students2;

import java.util.List;

public final class Student {
  private String name;
  private double gpa;
  private List<String> courses;

  private Student(String name, double gpa, List<String> courses) {
    if (!isValid(name, gpa, courses)) {
      throw new IllegalArgumentException("Bad Student!");
    }
    this.name = name;
    this.gpa = gpa;
    this.courses = courses;
  }

  public static Student of(String name, double gpa, String ... courses) {
    return new Student(name, gpa, List.of(courses));
  }

  public String getName() {
    return name;
  }

  public double getGpa() {
    return gpa;
  }

  public List<String> getCourses() {
    return courses;
  }

  public Student withName(String name) {
    if (!isValid(name, this.gpa, this.courses)) {
      throw new IllegalArgumentException("Bad student name");
    }
    return new Student(name, this.gpa, this.courses);
  }

  public static boolean isValid(String name, double gpa, List<String> courses) {
    return name != null && name.length() > 0 && gpa >= 0 && gpa <= 5.0;
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