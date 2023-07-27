package annotations;

import java.sql.SQLException;

//@RunMe
public class MyTests {
//  @RunMe // Wrong "ElementType"--not allowed...
  private String value = "Hello";

//  @SetMe // i.e. "dependency injection"
//  private String myName;

  @RunMe("This is the value")
  public void testSomething() {
    System.out.println("testSomething");
  }

  @RunMe(value="MyValue", name="Alice", count=22, exception = SQLException.class)
  private void testSomethingElse() throws SQLException {
    System.out.println("testSomethingElse");
    if (Math.random() > 0.5) {
      System.out.println("Breaking!!!");
      throw new SQLException("DB busted");
    }
  }

  private void supportingMethod() {
    System.out.println("supportingMethod");
  }

  @Override
  public String toString() {
    return "Look! We made a test object :)";
  }
}

class RunStuff {
  public static void main(String[] args) {
    MyTests mt = new MyTests();
    mt.testSomething();
//    try {
//      mt.testSomethingElse();
//    } catch (SQLException sqle) {
//      System.out.println(sqle.getMessage());
//    }
  }
}